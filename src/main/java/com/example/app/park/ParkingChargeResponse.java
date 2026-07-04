package com.example.app.park;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

/**
 * 停车计费响应(带周期)
 */
@Data
@Builder
public class ParkingChargeResponse {

    /** 停车总时长（分钟） */
    private int totalMinutes;
    /** 停车总时长（格式化） */
    private String totalDurationDesc;
    /** 本次应收金额 */
    private BigDecimal chargeAmount;
    /** 单次封顶金额 */
    private BigDecimal capAmount;

    /** 多次停车周期封顶金额 */
    private BigDecimal dayLimitAmount;

    /** 当前周期开始时间 */
    private String cycleStartTime;
    /** 当前周期累计停车时长（分钟） */
    private int cycleTotalMinutes;
    /** 当前周期已支付金额 */
    private BigDecimal cyclePaidAmount;
    /** 当前周期累计停车次数 */
    private int cycleParkCount;


    /** 各周期计费明细 */
    private List<PeriodChargeDetail> periodDetails;

    /** 计费明细描述 */
    private String detailDescription;

    /** 入场时间 */
    private String entryTime;
    /** 出场时间 */
    private String exitTime;
}
