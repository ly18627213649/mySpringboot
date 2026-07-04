package com.example.app.park;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 周期停车记录
 */
@Data
@AllArgsConstructor
public class CycleParkingRecord {
    // 当前周期开始时间（从第一次停车入场时间开始，每12小时一个周期）
    private Date cycleStartTime;
    // 当前周期已累计停车时长(分钟)
    private int cycleTotalMinutes;
    // 当前周期已累计支付
    private BigDecimal cyclePaidAmount;

    // 当前周期累计停车次数
    private int cycleParkCount;
    // 本次停车,当前周期是否是第一周期
    private boolean isFirstCycle = true;
    // 当前周期是否已使用过首停
    private boolean firstChargeUsed = false;


    public CycleParkingRecord() {

    }
}
