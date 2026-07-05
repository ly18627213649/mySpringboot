package com.example.app.park;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@RestController
@RequestMapping("/parking")
@Tag(name = "停车收费接口")
public class ParkingChargeController {

    @Autowired
    private ParkingChargeService parkingChargeService2;

    @GetMapping("/clear")
    public String remove(){
        parkingChargeService2.removeCarNum();
        return "成功";
    }

    @PostMapping("/chargeTest")
    @Operation(summary = "停车收费测试", description = "根据入场出场时间和车牌计算停车费用")
    public Map<String,Object> chargeTest(@RequestBody Map<String,String> param){
        Map<String, Object> result = new HashMap<>();

        String carNum = param.get("carNum");//车牌号
        String stime = param.get("stime");  //入场时间
        String etime = param.get("etime");  //出场时间

        try {
            // 1. 参数校验
            if (StringUtils.isBlank(stime) || StringUtils.isBlank(etime)) {
                result.put("code", 400);
                result.put("message", "入场时间和出场时间不能为空");
                return result;
            }

            // 2. 字符串转 Date（支持多种格式）
            Date entryTime = parseDate(stime);
            Date exitTime = parseDate(etime);

            if (entryTime == null || exitTime == null) {
                result.put("code", 400);
                result.put("message", "时间格式错误，请使用 yyyy-MM-dd HH:mm:ss 格式");
                return result;
            }

            if (exitTime.before(entryTime)) {
                result.put("code", 400);
                result.put("message", "出场时间不能早于入场时间");
                return result;
            }

            // 3. 获取收费配置
            DayHourCharge config = getTestConfig();

            // 4. 调用计费服务
            ParkingChargeResponse response = parkingChargeService2.calculateCharge(entryTime, exitTime, carNum, config);

            // 5. 构建返回结果
            result.put("code", 200);
            result.put("message", "成功");
            result.put("result", response);

        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "计算异常：" + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取测试配置
     */
    private DayHourCharge getTestConfig() {
        DayHourCharge config = new DayHourCharge();
        config.setFreeTime(15);                // 免费30分钟
        config.setChargeMaxAmount(17);         // 封顶10元
        config.setOverNightCharges(5);         // 过夜费1元
        config.setFirstUnit(30);               // 首停60分钟
        config.setFirstCharge(0.5f);           // 首停5元
        config.setIsFreeTimeCount(true);       // 停车时间包含免费时间
        config.setIsTopContainOverNight(true); // 封顶包含过夜费
        config.setIsDayChargeLimit(false);     // 开启多次进出按天封顶
        config.setDayChargeLimitType(2);       // (暂无效)停车12小时, 实际传的true
        config.setDayChargeLimitCharge(0);     // 多次停车周期收费限制金额 10元
        config.setHalf(2);                     // (暂无效)开启半小时
        return config;
    }

    /**
     * 时间字符串解析（支持多种格式）
     * @param timeStr
     * @return
     */
    private Date parseDate(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) {
            return null;
        }
        try {
            // 尝试多种格式
            String[] patterns = {
                    "yyyy-MM-dd HH:mm:ss",
                    "yyyy-MM-dd HH:mm",
                    "yyyy/MM/dd HH:mm:ss",
                    "yyyy/MM/dd HH:mm",
                    "yyyy-MM-dd'T'HH:mm:ss",
                    "yyyyMMddHHmmss"
            };
            for (String pattern : patterns) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                    return sdf.parse(timeStr);
                } catch (Exception ignored) {
                }
            }

            return null;
            // 使用 hutool 自动解析
//            return DateUtil.parse(timeStr);
        } catch (Exception e) {
            return null;
        }
    }
}
