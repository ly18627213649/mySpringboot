package com.example.util;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * 类型转换
 */
public class TypeUtil {

	public static final String castToString(Object value) {
		if (value == null) {
			return null;
		}
		return value.toString();
	}

	public static final Byte castToByte(Object value) throws Exception {
		if (value == null) {
			return null;
		}
		if (value instanceof Number) {
			return ((Number) value).byteValue();
		}
		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0) {
				return null;
			}
			return Byte.parseByte(strVal);
		}
		throw new Exception("can not cast to byte, value : " + value);
	}

	public static final Character castToChar(Object value) throws Exception {
		if (value == null) {
			return null;
		}
		if (value instanceof Character) {
			return (Character) value;
		}
		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0) {
				return null;
			}
			if (strVal.length() != 1) {
				throw new Exception("can not cast to byte, value : "
						+ value);
			}
			return strVal.charAt(0);
		}
		throw new Exception("can not cast to byte, value : " + value);
	}

	public static final Short castToShort(Object value) throws Exception {
		if (value == null) {
			return null;
		}
		if (value instanceof Number) {
			return ((Number) value).shortValue();
		}
		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0) {
				return null;
			}
			return Short.parseShort(strVal);
		}
		throw new Exception("can not cast to short, value : " + value);
	}

	public static final BigDecimal castToBigDecimal(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		}
		if (value instanceof BigInteger) {
			return new BigDecimal((BigInteger) value);
		}
		String strVal = value.toString();
		if (strVal.length() == 0) {
			return null;
		}
		return new BigDecimal(strVal);
	}

	public static final BigInteger castToBigInteger(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof BigInteger) {
			return (BigInteger) value;
		}
		String strVal = value.toString();
		if (strVal.length() == 0) {
			return null;
		}
		return new BigInteger(strVal);
	}

	public static final Float castToFloat(Object value) throws Exception {
		if (value == null) {
			return null;
		}
		if (value instanceof Number) {
			return ((Number) value).floatValue();
		}
		if (value instanceof String) {
			String strVal = value.toString();
			if (strVal.length() == 0) {
				return null;
			}
			return Float.parseFloat(strVal);
		}
		throw new Exception("can not cast to float, value : " + value);
	}

	public static final Double castToDouble(Object value) throws Exception {
		if (value == null) {
			return null;
		}
		if (value instanceof Number) {
			return ((Number) value).doubleValue();
		}
		if (value instanceof String) {
			String strVal = value.toString();
			if (strVal.length() == 0) {
				return null;
			}
			return Double.parseDouble(strVal);
		}
		throw new Exception("can not cast to double, value : " + value);
	}

	public static final Date castToDate(Object value) throws Exception {
		if (value == null) {
			return null;
		}
		if (value instanceof Calendar) {
			return ((Calendar) value).getTime();
		}
		if (value instanceof Date) {
			return (Date) value;
		}
		long longValue = 0;
		if (value instanceof Number) {
			longValue = ((Number) value).longValue();
		}
		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0) {
				return null;
			}
			longValue = Long.parseLong(strVal);
		}
		if (longValue <= 0) {
			throw new Exception("can not cast to Date, value : " + value);
		}
		return new Date(longValue);
	}

	public static final java.sql.Date castToSqlDate(Object value) throws Exception {
		if (value == null) {
			return null;
		}
		if (value instanceof Calendar) {
			return new java.sql.Date(((Calendar) value).getTimeInMillis());
		}
		if (value instanceof java.sql.Date) {
			return (java.sql.Date) value;
		}
		if (value instanceof Date) {
			return new java.sql.Date(((Date) value).getTime());
		}
		long longValue = 0;
		if (value instanceof Number) {
			longValue = ((Number) value).longValue();
		}
		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0) {
				return null;
			}
			longValue = Long.parseLong(strVal);
		}
		if (longValue <= 0) {
			throw new Exception("can not cast to Date, value : " + value);
		}
		return new java.sql.Date(longValue);
	}

	public static final java.sql.Timestamp castToTimestamp(Object value) throws Exception {
		if (value == null) {
			return null;
		}
		if (value instanceof Calendar) {
			return new java.sql.Timestamp(((Calendar) value).getTimeInMillis());
		}
		if (value instanceof java.sql.Timestamp) {
			return (java.sql.Timestamp) value;
		}
		if (value instanceof Date) {
			return new java.sql.Timestamp(((Date) value).getTime());
		}
		long longValue = 0;
		if (value instanceof Number) {
			longValue = ((Number) value).longValue();
		}
		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0) {
				return null;
			}
			longValue = Long.parseLong(strVal);
		}
		if (longValue <= 0) {
			throw new Exception("can not cast to Date, value : " + value);
		}
		return new java.sql.Timestamp(longValue);
	}

	public static final Long castToLong(Object value) throws Exception {
		if (value == null) {
			return null;
		}
		if (value instanceof Number) {
			return ((Number) value).longValue();
		}
		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0) {
				return null;
			}
			return Long.parseLong(strVal);
		}
		throw new Exception("can not cast to long, value : " + value);
	}

	public static final Integer castToInt(Object value) throws Exception {
		if (value == null) {
			return null;
		}
		if (value instanceof Integer) {
			return (Integer) value;
		}
		if (value instanceof Number) {
			return ((Number) value).intValue();
		}
		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0) {
				return null;
			}
			return Integer.parseInt(strVal);
		}
		throw new Exception("can not cast to int, value : " + value);
	}

//	public static final byte[] castToBytes(Object value) {
//		if (value instanceof byte[]) {
//			return (byte[]) value;
//		}
//		if (value instanceof String) {
//			return Base64.decode((String) value);
//		}
//		throw new CastException("can not cast to int, value : " + value);
//	}

	public static final Boolean castToBoolean(Object value) throws Exception {
		if (value == null) {
			return null;
		}
		if (value instanceof Boolean) {
			return (Boolean) value;
		}
		if (value instanceof Number) {
			return ((Number) value).intValue() == 1;
		}
		if (value instanceof String) {
			String str = (String) value;
			if (str.length() == 0) {
				return null;
			}
			if ("true".equals(str)) {
				return Boolean.TRUE;
			}
			if ("false".equals(str)) {
				return Boolean.FALSE;
			}
			if ("1".equals(str)) {
				return Boolean.TRUE;
			}
		}
		throw new Exception("can not cast to int, value : " + value);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final <T> T cast(Object obj, Class<T> clazz) throws Exception {
		if (obj == null) {
			return null;
		}
		if (clazz == obj.getClass()) {
			return (T) obj;
		}
		if (obj instanceof Map) {
			if (clazz == Map.class) {
				return (T) obj;
			}
			return cast((Map<String, Object>) obj, clazz);
		}
		if (clazz.isArray()) {
			if (obj instanceof Collection) {
				Collection collection = (Collection) obj;
				int index = 0;
				Object array = Array.newInstance(clazz.getComponentType(),
						collection.size());
				for (Object item : collection) {
					Object value = cast(item, clazz.getComponentType());
					Array.set(array, index, value);
					index++;
				}
				return (T) array;
			}
		}
		if (clazz.isAssignableFrom(obj.getClass())) {
			return (T) obj;
		}
		if (clazz == boolean.class || clazz == Boolean.class) {
			return (T) castToBoolean(obj);
		}
		if (clazz == char.class || clazz == Character.class) {
			return (T) castToChar(obj);
		}
		if (clazz == byte.class || clazz == Byte.class) {
			return (T) castToByte(obj);
		}
		if (clazz == short.class || clazz == Short.class) {
			return (T) castToShort(obj);
		}
		if (clazz == int.class || clazz == Integer.class) {
			return (T) castToInt(obj);
		}
		if (clazz == long.class || clazz == Long.class) {
			return (T) castToLong(obj);
		}
		if (clazz == float.class || clazz == Float.class) {
			return (T) castToFloat(obj);
		}
		if (clazz == double.class || clazz == Double.class) {
			return (T) castToDouble(obj);
		}
		if (clazz == String.class) {
			return (T) castToString(obj);
		}
		if (clazz == BigDecimal.class) {
			return (T) castToBigDecimal(obj);
		}
		if (clazz == BigInteger.class) {
			return (T) castToBigInteger(obj);
		}
		if (clazz == Date.class) {
			return (T) castToDate(obj);
		}
		if (clazz == java.sql.Date.class) {
			return (T) castToSqlDate(obj);
		}
		if (clazz == java.sql.Timestamp.class) {
			return (T) castToTimestamp(obj);
		}
		if (clazz.isEnum()) {
			return (T) castToEnum(obj, clazz);
		}
		if (obj instanceof String) {
			String strVal = (String) obj;
			if (strVal.length() == 0) {
				return null;
			}
		}
		throw new Exception("can not cast to : " + clazz.getName());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final <T> T castToEnum(Object obj, Class<T> clazz) throws Exception {
		try {
			if (obj instanceof String) {
				String name = (String) obj;
				if (name.length() == 0) {
					return null;
				}
				return (T) Enum.valueOf((Class<? extends Enum>) clazz, name);
			}
			if (obj instanceof Number) {
				int ordinal = ((Number) obj).intValue();
				Method method = clazz.getMethod("values");
				Object[] values = (Object[]) method.invoke(null);
				for (Object value : values) {
					Enum e = (Enum) value;
					if (e.ordinal() == ordinal) {
						return (T) e;
					}
				}
			}
		} catch (Exception ex) {
			throw new Exception("can not cast to : " + clazz.getName(), ex);
		}
		throw new Exception("can not cast to : " + clazz.getName());
	}

	@SuppressWarnings("unchecked")
	public static final <T> T cast(Object obj, Type type) throws Exception {
		if (obj == null) {
			return null;
		}
		if (type instanceof Class) {
			return (T) cast(obj, (Class<T>) type);
		}
		if (type instanceof ParameterizedType) {
			return (T) cast(obj, (ParameterizedType) type);
		}
		if (obj instanceof String) {
			String strVal = (String) obj;
			if (strVal.length() == 0) {
				return null;
			}
		}
		if (type instanceof TypeVariable) {
			return (T) obj;
		}
		throw new Exception("can not cast to : " + type);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final <T> T cast(Object obj, ParameterizedType type) throws Exception {
		Type rawTye = type.getRawType();
		if (rawTye == List.class || rawTye == ArrayList.class) {
			Type itemType = type.getActualTypeArguments()[0];
			if (obj instanceof Iterable) {
				List list = new ArrayList();
				for (Iterator it = ((Iterable) obj).iterator(); it.hasNext();) {
					Object item = it.next();
					list.add(cast(item, itemType));
				}
				return (T) list;
			}
		}
		if (rawTye == Map.class || rawTye == HashMap.class) {
			Type keyType = type.getActualTypeArguments()[0];
			Type valueType = type.getActualTypeArguments()[1];

			if (obj instanceof Map) {
				Map map = new HashMap();
				for (Map.Entry entry : ((Map<?, ?>) obj).entrySet()) {
					Object key = cast(entry.getKey(), keyType);
					Object value = cast(entry.getValue(), valueType);
					map.put(key, value);
				}
				return (T) map;
			}
		}
		if (obj instanceof String) {
			String strVal = (String) obj;
			if (strVal.length() == 0) {
				return null;
			}
		}
		if (type.getActualTypeArguments().length == 1) {
			Type argType = type.getActualTypeArguments()[0];
			if (argType instanceof WildcardType) {
				return (T) cast(obj, rawTye);
			}
		}
		throw new Exception("can not cast to : " + type);
	}

	/*public static void main(String[] args) {
		try {
			cast("ssdad", Date.class);
		} catch (CastException e) {
			System.out.println("1111111111111111");
		}
	}*/
}
