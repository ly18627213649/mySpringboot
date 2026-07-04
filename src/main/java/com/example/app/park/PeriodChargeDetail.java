package com.example.app.park;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 周期计费明细
 */
@Data
@AllArgsConstructor
public class PeriodChargeDetail {

    /** 周期开始时间 */
    private String periodStart;
    /** 周期结束时间 */
    private String periodEnd;
    /** 本周期内停车时长（分钟） */
    private int minutesInPeriod;
    /** 本周期收费金额 */
    private BigDecimal chargeInPeriod;
    /** 是否是第一个周期 */
    private boolean firstCycle;
}
