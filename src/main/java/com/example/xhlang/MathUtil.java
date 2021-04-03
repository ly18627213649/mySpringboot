package com.example.xhlang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MathUtil {

    private static Logger log = LoggerFactory.getLogger(MathUtil.class);
    private static final int DEFAULT_SCALE = 2;

    public MathUtil() {
    }

    public static String add(String sourceAddend, String targetAddend) {
        sourceAddend = CommUtil.null2String(sourceAddend, "0");
        targetAddend = CommUtil.null2String(targetAddend, "0");
        BigDecimal sourceAddendBigDecimal = new BigDecimal(sourceAddend.trim());
        BigDecimal targetAddendBigDecimal = new BigDecimal(targetAddend.trim());
        BigDecimal result = add(sourceAddendBigDecimal, targetAddendBigDecimal);
        return CommUtil.null2String(result);
    }

    public static BigDecimal add(BigDecimal sourceAddend, BigDecimal targetAddend) {
        if (null == sourceAddend) {
            sourceAddend = BigDecimal.ZERO;
        }

        if (null == targetAddend) {
            targetAddend = BigDecimal.ZERO;
        }

        return sourceAddend.add(targetAddend);
    }

    public static String subtract(String sourceSubtrahend, String targetSubtrahend) {
        sourceSubtrahend = CommUtil.null2String(sourceSubtrahend, "0");
        targetSubtrahend = CommUtil.null2String(targetSubtrahend, "0");
        BigDecimal sourceSubtrahendBigDecimal = new BigDecimal(sourceSubtrahend.trim());
        BigDecimal targetSubtrahendBigDecimal = new BigDecimal(targetSubtrahend.trim());
        BigDecimal result = subtract(sourceSubtrahendBigDecimal, targetSubtrahendBigDecimal);
        return CommUtil.null2String(result);
    }

    public static BigDecimal subtract(BigDecimal sourceSubtrahend, BigDecimal targetSubtrahend) {
        if (null == sourceSubtrahend) {
            sourceSubtrahend = BigDecimal.ZERO;
        }

        if (null == targetSubtrahend) {
            targetSubtrahend = BigDecimal.ZERO;
        }

        return sourceSubtrahend.subtract(targetSubtrahend);
    }

    public static BigDecimal cheng(BigDecimal sourceMultiplier, BigDecimal targetMultiplier) {
        return multiply(sourceMultiplier, targetMultiplier);
    }

    public static String cheng(String sourceMultiplier, String targetMultiplier) {
        return multiply(sourceMultiplier, targetMultiplier);
    }

    public static String multiply(String sourceMultiplier, String targetMultiplier) {
        sourceMultiplier = CommUtil.null2String(sourceMultiplier, "0");
        targetMultiplier = CommUtil.null2String(targetMultiplier, "0");
        BigDecimal sourceMultiplierBigDecimal = new BigDecimal(sourceMultiplier.trim());
        BigDecimal targetMultiplierBigDecimal = new BigDecimal(targetMultiplier.trim());
        BigDecimal result = multiply(sourceMultiplierBigDecimal, targetMultiplierBigDecimal);
        return CommUtil.null2String(result);
    }

    public static BigDecimal multiply(BigDecimal sourceMultiplier, BigDecimal targetMultiplier) {
        if (null == sourceMultiplier) {
            sourceMultiplier = BigDecimal.ZERO;
        }

        if (null == targetMultiplier) {
            targetMultiplier = BigDecimal.ZERO;
        }

        return sourceMultiplier.multiply(targetMultiplier);
    }

    public static BigDecimal chu(BigDecimal sourceDivider, BigDecimal targetDivider) {
        return divide(sourceDivider, targetDivider);
    }

    public static String chu(String sourceDivider, String targetDivider) {
        return divide(sourceDivider, targetDivider);
    }

    public static String chu(String sourceDivider, String targetDivider, Integer scale) {
        return divide(sourceDivider, targetDivider, scale);
    }

    public static BigDecimal divide(BigDecimal sourceDivider, BigDecimal targetDivider) {
        return divide(sourceDivider, targetDivider, 2);
    }

    public static String divide(String sourceDivider, String targetDivider) {
        return divide(sourceDivider, targetDivider, 2);
    }

    public static String divide(String sourceDivider, String targetDivider, Integer scale) {
        sourceDivider = CommUtil.null2String(sourceDivider, "0");
        targetDivider = CommUtil.null2String(targetDivider, "1");
        BigDecimal sourceDividerBigDecimal = new BigDecimal(sourceDivider.trim());
        BigDecimal targetDividerBigDecimal = new BigDecimal(targetDivider.trim());
        BigDecimal result = divide(sourceDividerBigDecimal, targetDividerBigDecimal, scale);
        return CommUtil.null2String(result);
    }

    public static BigDecimal divide(BigDecimal sourceDivider, BigDecimal targetDivider, Integer scale) {
        if (null == sourceDivider) {
            return BigDecimal.ZERO;
        } else {
            if (null == targetDivider) {
                targetDivider = BigDecimal.ONE;
            }

            if (targetDivider.compareTo(BigDecimal.ZERO) == 0) {
                throw new IllegalArgumentException("被除数不能为0");
            } else if (scale < 0) {
                throw new IllegalArgumentException("精确度不能小于0");
            } else {
                return sourceDivider.divide(targetDivider, scale, 4);
            }
        }
    }

    public static BigDecimal sum(BigDecimal... valueList) {
        if (valueList != null && valueList.length > 0) {
            List<BigDecimal> bigDecimalList = Arrays.asList(valueList);
            return sum(bigDecimalList);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal sum(List<BigDecimal> valueList) {
        BigDecimal returnValue = BigDecimal.ZERO;
        if (null != valueList && !valueList.isEmpty()) {
            Iterator var2 = valueList.iterator();

            while (var2.hasNext()) {
                BigDecimal value = (BigDecimal) var2.next();
                if (null != value) {
                    returnValue = returnValue.add(value);
                }
            }

            return returnValue;
        } else {
            return returnValue;
        }
    }

    public static String sum(String... values) {
        if (null != values && values.length > 0) {
            BigDecimal result = BigDecimal.ZERO;
            String[] var2 = values;
            int var3 = values.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                String value = var2[var4];
                boolean isEmpty = CommUtil.null2String(value).equals("");
                if (!isEmpty) {
                    result = add(result, new BigDecimal(value.trim()));
                }
            }

            return CommUtil.null2String(result);
        } else {
            return "0";
        }
    }

    public static BigDecimal avg(BigDecimal... values) {
        if (values != null && values.length > 0) {
            List<BigDecimal> bigDecimalList = Arrays.asList(values);
            return avg(bigDecimalList);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal avg(List<BigDecimal> valueList) {
        return valueList != null && !valueList.isEmpty() ? divide(sum(valueList), new BigDecimal(valueList.size())) : BigDecimal.ZERO;
    }

    public static String avg(String... values) {
        return values != null && values.length > 0 ? divide(sum(values), CommUtil.null2String(values.length)) : "0";
    }

    public static String max(String... values) {
        String returnValue = "0";
        if (null != values && values.length > 0) {
            List<BigDecimal> bigDecimalList = new ArrayList(values.length);
            String[] var3 = values;
            int var4 = values.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                String value = var3[var5];
                if (!CommUtil.null2String(value).equals("")) {
                    bigDecimalList.add(new BigDecimal(CommUtil.null2String(value)));
                }
            }

            BigDecimal max = max((List) bigDecimalList);
            return CommUtil.null2String(max);
        } else {
            return returnValue;
        }
    }

    public static BigDecimal max(BigDecimal... values) {
        BigDecimal returnValue = BigDecimal.ZERO;
        if (null != values && values.length > 0) {
            List<BigDecimal> bigDecimalList = Arrays.asList(values);
            return max(bigDecimalList);
        } else {
            return returnValue;
        }
    }

    public static BigDecimal max(List<BigDecimal> valueList) {
        BigDecimal returnValue = BigDecimal.ZERO;
        if (null != valueList && !valueList.isEmpty()) {
            returnValue = (BigDecimal) valueList.get(0);
            Iterator var2 = valueList.iterator();

            while (var2.hasNext()) {
                BigDecimal value = (BigDecimal) var2.next();
                if (null != value && value.compareTo(returnValue) > 0) {
                    returnValue = value;
                }
            }

            return returnValue;
        } else {
            return returnValue;
        }
    }

    public static BigDecimal min(BigDecimal... values) {
        BigDecimal returnValue = BigDecimal.ZERO;
        if (null != values && values.length > 0) {
            List<BigDecimal> bigDecimalList = Arrays.asList(values);
            return min(bigDecimalList);
        } else {
            return returnValue;
        }
    }

    public static BigDecimal min(List<BigDecimal> valueList) {
        BigDecimal returnValue = BigDecimal.ZERO;
        if (null != valueList && !valueList.isEmpty()) {
            returnValue = (BigDecimal) valueList.get(0);
            Iterator var2 = valueList.iterator();

            while (var2.hasNext()) {
                BigDecimal value = (BigDecimal) var2.next();
                if (null != value && value.compareTo(returnValue) < 0) {
                    returnValue = value;
                }
            }

            return returnValue;
        } else {
            return returnValue;
        }
    }

    public static String min(String... values) {
        String returnValue = "0";
        if (null != values && values.length > 0) {
            List<BigDecimal> bigDecimalList = new ArrayList(values.length);
            String[] var3 = values;
            int var4 = values.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                String value = var3[var5];
                if (!CommUtil.null2String(value).equals("")) {
                    bigDecimalList.add(new BigDecimal(CommUtil.null2String(value)));
                }
            }

            BigDecimal min = min((List) bigDecimalList);
            return CommUtil.null2String(min);
        } else {
            return returnValue;
        }
    }
}
