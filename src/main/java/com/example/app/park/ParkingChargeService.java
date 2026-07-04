package com.example.app.park;

import com.example.xhlang.DateUtil;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ParkingChargeService {

    // 周期停车记录
    private final ConcurrentHashMap<String, CycleParkingRecord> cycleRecordMap = new ConcurrentHashMap<>();

    /**
     * 计算停车费（单次）
     */
    public ParkingChargeResponse calculateCharge(Date entryTime, Date exitTime,
                                                 String carNum, DayHourCharge config) {
        // 1. 计算实际停车时长（分钟）
        long diffMillis = exitTime.getTime() - entryTime.getTime();
        int totalMinutes = (int) Math.ceil(diffMillis / (60.0 * 1000));
        if (totalMinutes <= 0) {
            totalMinutes = 1;  // 最少按1分钟计
        }

        // 3. 获取周期记录（多次进出封顶用）
        CycleParkingRecord cycleRecord = cycleRecordMap.computeIfAbsent(carNum, k -> {
            // 首次, 初始化
            CycleParkingRecord record = new CycleParkingRecord();
            record.setCycleStartTime(entryTime);
            record.setCycleTotalMinutes(0);
            record.setCyclePaidAmount(BigDecimal.ZERO);
            record.setCycleParkCount(0);
            record.setFirstCycle(true);
            record.setFirstChargeUsed(false);
            return record;
        });

        // !!! 每次新停车，重置 isFirstCycle = true，表示本次停车的第一周期享受完整计费
        cycleRecord.setFirstCycle(true);

        // 4. 执行计费
        return doCalculate(totalMinutes, entryTime, exitTime, config, cycleRecord);
    }


    /**
     * 核心计费逻辑
     */
    private ParkingChargeResponse doCalculate(int totalMinutes, Date entryTime, Date exitTime,
                                              DayHourCharge config, CycleParkingRecord cycleRecord) {

        boolean isDayChargeLimit = config.getIsDayChargeLimit() != null && config.getIsDayChargeLimit();

        // 定义变量
        BigDecimal totalCharge = BigDecimal.ZERO;
        List<PeriodChargeDetail> periodDetails = new ArrayList<>();

        Date currentStart = entryTime;
        int remainingMinutes = totalMinutes;    //本次停车剩余待计费分钟数
        boolean isFirstCycle = cycleRecord.isFirstCycle();

        // 检查是否重新新周期
        if (cycleRecord.getCycleStartTime() != null) {
            Date cycleEnd = getCycleEndTime(cycleRecord.getCycleStartTime()); // 周期结束时间
            if (!entryTime.before(cycleEnd)) {
                // 入场时间已超过当前周期结束时间，重新进入新周期
                cycleRecord.setCycleStartTime(entryTime);
                cycleRecord.setCycleTotalMinutes(0);
                cycleRecord.setCyclePaidAmount(BigDecimal.ZERO);
                cycleRecord.setCycleParkCount(0);
                cycleRecord.setFirstChargeUsed(false);
            }
        }

        while (remainingMinutes > 0) {
            Date cycleEnd = getCycleEndTime(cycleRecord.getCycleStartTime()); // 周期结束时间

            // 当前周期剩余时长(分钟)
            long cycleRemainingMillis = cycleEnd.getTime() - currentStart.getTime();
            int cycleRemainingMinutes = (int) Math.ceil(cycleRemainingMillis / (60.0 * 1000));

            // 跨周期: 触发条件: 周期时间到了
            if (cycleRemainingMinutes <= 0) {
                // 进入下一个周期
                cycleRecord.setCycleStartTime(currentStart);
                cycleRecord.setCycleTotalMinutes(0);
                cycleRecord.setCyclePaidAmount(BigDecimal.ZERO);
                cycleRecord.setCycleParkCount(0);
                cycleRecord.setFirstCycle(false);
                cycleRecord.setFirstChargeUsed(false);
                isFirstCycle = false;
                continue;
            }

            // 本次循环能处理多少分钟 = min(本次停车剩余待计费分钟数, 周期剩余时长)
            int minutesInThisCycle = Math.min(remainingMinutes, cycleRemainingMinutes);
            // 本段结束时间,  处理的时间段：currentStart - (currentStart + minutesInThisCycle)
            Date segmentExit = new Date(currentStart.getTime() + minutesInThisCycle * 60 * 1000L);

            // 计算本周期费用
            BigDecimal segmentCharge; // 本次停车分段计费
            if (isFirstCycle) {
                // 第一个周期：完整计费
                segmentCharge = calculateFullCycleCharge(minutesInThisCycle, currentStart, segmentExit, config, cycleRecord);
            } else {
                // 后续周期：只用阶梯
                segmentCharge = calculateStepOnlyCharge(minutesInThisCycle, currentStart, segmentExit, config);
            }

            // 周期内多次停车封顶
            if (isDayChargeLimit && cycleRecord.getCycleParkCount() >= 2) {
                BigDecimal multipleCap = BigDecimal.valueOf(config.getDayChargeLimitCharge());// 周期内多次停车封顶金额
                BigDecimal alreadyPaid = cycleRecord.getCyclePaidAmount();  // 已经累计支付
                BigDecimal remainingCap = multipleCap.subtract(alreadyPaid);// 剩余可收费 = 封顶价-已经累计支付
                if (remainingCap.compareTo(BigDecimal.ZERO) < 0) {
                    remainingCap = BigDecimal.ZERO;
                }
                if (segmentCharge.compareTo(remainingCap) > 0) {
                    segmentCharge = remainingCap;
                }
            }

            totalCharge = totalCharge.add(segmentCharge);
            // 周期计费明细
            PeriodChargeDetail periodChargeDetail = new PeriodChargeDetail(
                    DateUtil.formatByDateTime(cycleRecord.getCycleStartTime()),
                    DateUtil.formatByDateTime(cycleEnd),
                    minutesInThisCycle,
                    segmentCharge,
                    isFirstCycle
            );
            periodDetails.add(periodChargeDetail);

            // 更新当前周期相关属性
            cycleRecord.setCycleTotalMinutes(cycleRecord.getCycleTotalMinutes() + minutesInThisCycle);
            cycleRecord.setCyclePaidAmount(cycleRecord.getCyclePaidAmount().add(segmentCharge));
            cycleRecord.setCycleParkCount(cycleRecord.getCycleParkCount() + 1);

            // 推进时间
            currentStart = segmentExit;
            remainingMinutes -= minutesInThisCycle;

            // TODO 同周期内多次停车，累计满12小时,极端不会触发
            if (cycleRecord.getCycleTotalMinutes() >= 12 * 60) {
                cycleRecord.setCycleStartTime(currentStart);
                cycleRecord.setCycleTotalMinutes(0);
                cycleRecord.setCyclePaidAmount(BigDecimal.ZERO);
                cycleRecord.setFirstCycle(false);
                cycleRecord.setCycleParkCount(0);
                cycleRecord.setFirstChargeUsed(false);
                isFirstCycle = false;
            }
        }

        return buildResponse(totalMinutes, config, totalCharge, cycleRecord, periodDetails, entryTime, exitTime);
    }

    /**
     * 第一个周期：完整计费（免费 + 首停 + 阶梯 + 封顶 + 过夜费）
     */
    private BigDecimal calculateFullCycleCharge(int minutes, Date entryTime, Date exitTime, DayHourCharge config
                                                ,CycleParkingRecord cycleRecord) {
        // 免费时间
        int freeTime = config.getFreeTime();
        // 首停分钟
        int firstUnit = config.getFirstUnit();
        // 首停收费
        float firstCharge = config.getFirstCharge();
        // 封顶收费
        float chargeMaxAmount = config.getChargeMaxAmount();
        // 停车时间包含免费时间
        boolean isFreeTimeCount = config.getIsFreeTimeCount() != null && config.getIsFreeTimeCount();
        // 过夜费
        float overNightCharges = config.getOverNightCharges();
        // 封顶收费包含过夜费
        boolean isTopContainOverNight = config.getIsTopContainOverNight() != null && config.getIsTopContainOverNight();

        BigDecimal chargeAmount = BigDecimal.ZERO;

        // 1. 免费时间,30分钟内
        if (minutes <= freeTime){
            return BigDecimal.ZERO; // 全部免费
        }
        int chargeMinutes; // 计费时长
        // 判断: 停车时间包含免费时间
        if (isFreeTimeCount) {
            chargeMinutes = minutes;
        } else {
            chargeMinutes = minutes - freeTime;  // 不包含免费时间：扣掉免费时间
        }

        // 2. 首停
        int afterFirstMinutes = chargeMinutes;
        if (firstUnit > 0 && !cycleRecord.isFirstChargeUsed()) {
            // 小于60分钟
            if (chargeMinutes <= firstUnit) {
                // 在首停范围内
                chargeAmount = chargeAmount.add(BigDecimal.valueOf(firstCharge));
                afterFirstMinutes = 0;
            } else {
                // 超出首停
                chargeAmount = chargeAmount.add(BigDecimal.valueOf(firstCharge));
                afterFirstMinutes = chargeMinutes - firstUnit;
            }

            // 标记首停已使用
            cycleRecord.setFirstChargeUsed(true);
        }

        // 3. 阶梯费用
        if (afterFirstMinutes > 0) {
            BigDecimal stepCharge = BigDecimal.valueOf(calculateStepCharge(afterFirstMinutes));
            chargeAmount = chargeAmount.add(stepCharge);
        }

        // 4. 过夜费（先加，再判断封顶, 或后加, 再判断封顶）
        boolean hasOverNight = isOverNight(entryTime, exitTime);
        BigDecimal chargeMax = BigDecimal.valueOf(chargeMaxAmount);

        if (isTopContainOverNight) {
            // ===== 封顶包含过夜费：先加过夜费，再判断封顶 =====
            if (hasOverNight) {
                chargeAmount = chargeAmount.add(BigDecimal.valueOf(overNightCharges));
            }
            if (chargeAmount.compareTo(chargeMax) > 0) {
                chargeAmount = chargeMax;
            }
        } else {
            // ===== 封顶不包含过夜费：先判断封顶，再加过夜费 =====
            if (chargeAmount.compareTo(chargeMax) > 0) {
                chargeAmount = chargeMax;
            }
            if (hasOverNight) {
                chargeAmount = chargeAmount.add(BigDecimal.valueOf(overNightCharges));
            }
        }

        return chargeAmount;
    }

    /**
     * 后续周期：只用阶梯（无免费、无首停）
     */
    private BigDecimal calculateStepOnlyCharge(int minutes, Date entryTime, Date exitTime, DayHourCharge config) {
        float chargeMaxAmount = config.getChargeMaxAmount();
        float overNightCharges = config.getOverNightCharges();
        boolean isTopContainOverNight = config.getIsTopContainOverNight() != null && config.getIsTopContainOverNight();

        BigDecimal chargeAmount = BigDecimal.valueOf(calculateStepCharge(minutes));

        boolean hasOverNight = isOverNight(entryTime, exitTime);
        BigDecimal chargeMax = BigDecimal.valueOf(chargeMaxAmount);
        if (isTopContainOverNight) {
            // ===== 封顶包含过夜费：先加过夜费，再判断封顶 =====
            if (hasOverNight) {
                chargeAmount = chargeAmount.add(BigDecimal.valueOf(overNightCharges));
            }
            if (chargeAmount.compareTo(chargeMax) > 0) {
                chargeAmount = chargeMax;
            }
        } else {
            // ===== 封顶不包含过夜费：先判断封顶，再加过夜费 =====
            if (chargeAmount.compareTo(chargeMax) > 0) {
                chargeAmount = chargeMax;
            }
            if (hasOverNight) {
                chargeAmount = chargeAmount.add(BigDecimal.valueOf(overNightCharges));
            }
        }

        return chargeAmount;
    }

    /**
     * 计算阶梯费用（向上取整，1元/小时）
     */
    private float calculateStepCharge(int minutes) {
        if (minutes <= 0) {
            return 0;
        }
        int hours = (int) Math.ceil(minutes / 60.0);
        return hours * 1.0f;
    }

    /**
     * 获取周期结束时间（周期开始 + 12小时）
     */
    private Date getCycleEndTime(Date cycleStart) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(cycleStart);
        cal.add(Calendar.HOUR_OF_DAY, 12);
        return cal.getTime();
    }

    /**
     * 判断是否过夜（跨0点）
     */
    private boolean isOverNight(Date entryTime, Date exitTime) {
        if (entryTime == null || exitTime == null) {
            return false;
        }

        // 判断是否跨天：入场日期和出场日期不是同一天
        Calendar cal = Calendar.getInstance();
        cal.setTime(entryTime);
        int entryDay = cal.get(Calendar.DAY_OF_YEAR);
        int entryYear = cal.get(Calendar.YEAR);

        cal.setTime(exitTime);
        int exitDay = cal.get(Calendar.DAY_OF_YEAR);
        int exitYear = cal.get(Calendar.YEAR);

        // 跨 0 点 = 日期不同（年份不同 或 年内天数不同）
        return entryYear != exitYear || entryDay != exitDay;
    }

    /**
     * 时间加分钟
     */
    private Date addMinutes(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }


    /**
     * 构建响应
     */
    private ParkingChargeResponse buildResponse(int totalMinutes, DayHourCharge config,
                                                BigDecimal chargeAmount,
                                                CycleParkingRecord cycleRecord,
                                                List<PeriodChargeDetail> periodDetails,
                                                Date entryTime,
                                                Date exitTime) {
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        String durationDesc = hours + "小时" + (minutes > 0 ? minutes + "分钟" : "");

        // 生成明细描述
//        String detailDesc = buildDetailDesc(totalMinutes, config, chargeAmount, periodDetails);

        return ParkingChargeResponse.builder()
                .totalMinutes(totalMinutes)
                .totalDurationDesc(durationDesc)
                .chargeAmount(chargeAmount)
                .capAmount(BigDecimal.valueOf(config.getChargeMaxAmount()))
                .dayLimitAmount(BigDecimal.valueOf(config.getDayChargeLimitCharge()))
                .cycleStartTime(DateUtil.formatByDateTime(cycleRecord.getCycleStartTime()))
                .cycleTotalMinutes(cycleRecord.getCycleTotalMinutes())
                .cyclePaidAmount(cycleRecord.getCyclePaidAmount())
                .cycleParkCount(cycleRecord.getCycleParkCount())
                .periodDetails(periodDetails)
//                .detailDescription(detailDesc)
                .entryTime(DateUtil.formatByDateTime(entryTime))
                .exitTime(DateUtil.formatByDateTime(exitTime))
                .build();
    }


    /**
     * 生成计费明细描述
     */
//    private String buildDetailDesc(int totalMinutes, DayHourCharge config,
//                                   BigDecimal chargeAmount, List<PeriodChargeDetail> periodDetails) {
//        int freeTime = config.getFreeTime();
//        int firstUnit = config.getFirstUnit();
//        float firstCharge = config.getFirstCharge();
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("停车").append(totalMinutes).append("分钟");
//
//        // 免费时间
//        if (totalMinutes <= freeTime) {
//            sb.append("，在免费").append(freeTime).append("分钟内，收费0元");
//            return sb.toString();
//        }
//
//        int chargeableMinutes = totalMinutes - freeTime;
//        sb.append("，免费").append(freeTime).append("分钟");
//
//        // 首停
//        if (firstUnit > 0) {
//            if (chargeableMinutes <= firstUnit) {
//                sb.append("，在首停").append(firstUnit).append("分钟内，收费").append(firstCharge).append("元");
//            } else {
//                sb.append("，首停").append(firstUnit).append("分钟收费").append(firstCharge).append("元");
//                int afterFirst = chargeableMinutes - firstUnit;
//                int stepHours = (int) Math.ceil(afterFirst / 60.0);
//                sb.append("，阶梯").append(afterFirst).append("分钟按").append(stepHours).append("小时计费")
//                        .append("×1元=").append(stepHours).append("元");
//            }
//        } else {
//            int stepHours = (int) Math.ceil(chargeableMinutes / 60.0);
//            sb.append("，阶梯").append(chargeableMinutes).append("分钟按").append(stepHours)
//                    .append("小时计费×1元=").append(stepHours).append("元");
//        }
//
//        // 周期信息
//        if (periodDetails != null && periodDetails.size() > 1) {
//            sb.append("，跨越").append(periodDetails.size()).append("个周期");
//        }
//
//        float maxAmount = config.getChargeMaxAmount();
//        if (chargeAmount.floatValue() >= maxAmount && maxAmount > 0) {
//            sb.append("，触发封顶").append(maxAmount).append("元");
//        }
//
//        sb.append("，实收").append(chargeAmount).append("元");
//        return sb.toString();
//    }

    /**
     * 测试
     */
    public  void removeCarNum(){
        cycleRecordMap.clear();
    }
}
