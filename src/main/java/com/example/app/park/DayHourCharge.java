package com.example.app.park;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("12小时收费")
public class DayHourCharge {

    private float oneHoursCharge;
    private float twoHoursCharge;
    private float threeHoursCharge;
    private float fourHoursCharge;
    private float fiveHoursCharge;
    private float sixHoursCharge;
    private float sevenHoursCharge;
    private float eightHoursCharge;
    private float nineHoursCharge;
    private float tenHoursCharge;
    private float elevenHoursCharge;
    private float twelveHoursCharge;
    


    @ApiModelProperty(value = "免费时间")
    private int freeTime;

    @ApiModelProperty(value = "封顶收费")
    private float chargeMaxAmount;

    @ApiModelProperty(value = "过夜费")
    private float overNightCharges;


    // 首停分钟
    @ApiModelProperty(value = "首停单位")
    private int firstUnit;

    @ApiModelProperty(value = "开启半小时")
    private int half;

    @ApiModelProperty(value = "首停收费")
    private float firstCharge;

    @ApiModelProperty(value = "停车时间包含免费时间")
    private Boolean isFreeTimeCount = false;

    @ApiModelProperty(value = "封顶收费包含过夜费")
    private Boolean isTopContainOverNight = false;

    //--------------------------------------

    @ApiModelProperty(value = "是否启用多次进出按天封顶")
    private Boolean isDayChargeLimit = false;

    @ApiModelProperty(value = "多次进出按天封顶方式(1-0~24点收费，2-停车24小时)")
    private int dayChargeLimitType;

    @ApiModelProperty(value = "多次停车每日收费限制金额")
    private float dayChargeLimitCharge;

    //--------------------增强型----------------------

    @ApiModelProperty(value = "单次限额")
    private float onceTop;

    @ApiModelProperty(value = "过夜开始")
    @JSONField(format = "HH:mm:ss")
    private Date overNightStart;

    @ApiModelProperty(value = "过夜结束")
    @JSONField(format = "HH:mm:ss")
    private Date overNightEnd;

    @ApiModelProperty(value = "过夜时长")
    private float overNightUnit;

}
