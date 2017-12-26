package com.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.base.utils.ParaMap;
import com.common.consts.GlobalConsts.IsEnable;

/**
 * 
 * @author OL
 *
 */
public class EnumUtils {

	private EnumUtils() {
	}

	/**
	 * 获取枚举的描述属性值
	 * @param enumType
	 * @param value
	 * @return
	 * @author OL
	 */
	public static String getDesc(Class<?> enumType, int value) {
		String desc = null;
		if (!enumType.isEnum()) {
			return desc;
		}

		Enum<?>[] enums = (Enum<?>[]) enumType.getEnumConstants();
		try {
			for (Enum<?> em : enums) {
				Class<?> clazz = em.getClass();

				Field field = clazz.getField("value");
				if (field.getInt(em) == value) {
					desc = String.valueOf(clazz.getField("desc").get(em));
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return desc;
	}
	/**
	 * 获取枚举的描述属性值与键值
	 * @param enumType
	 * @param value
	 * @return
	 * @author OL
	 */
	public static List<ParaMap> getAll(Class<?> enumType) {

		List<ParaMap> outListMap = new ArrayList<ParaMap>();
		if (!enumType.isEnum()) {
			return outListMap;
		}

		Enum<?>[] enums = (Enum<?>[]) enumType.getEnumConstants();
		try {
			for (Enum<?> em : enums) {
				Class<?> clazz = em.getClass();
				Field field = clazz.getField("value");
				ParaMap outMap = new ParaMap();
				outMap.put("key", field.getInt(em));
				outMap.put("value", String.valueOf(clazz.getField("desc").get(em)));
				outListMap.add(outMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outListMap;
	}
	
	/**
	 * 获取枚举的描述属性值与键值
	 * @param enumType
	 * @param value
	 * @return
	 * @author OL
	 */
	public static List<ParaMap> getAll(Class<?> enumType, String value, String valueDesc) {

		List<ParaMap> outListMap = new ArrayList<ParaMap>();
		if (!enumType.isEnum()) {
			return outListMap;
		}

		Enum<?>[] enums = (Enum<?>[]) enumType.getEnumConstants();
		try {
			for (Enum<?> em : enums) {
				Class<?> clazz = em.getClass();
				Field field = clazz.getField("value");
				ParaMap outMap = new ParaMap();
				outMap.put(value, field.getInt(em));
				outMap.put(valueDesc, String.valueOf(clazz.getField("desc").get(em)));
				outListMap.add(outMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outListMap;
	}
	/**
	 * 检验枚举是否包含某属性值
	 * @param enumType
	 * @param value
	 * @return
	 * @author OL
	 */
	public static boolean checkValue(Class<?> enumType, int value) {
		boolean isCheck = false;
		if (!enumType.isEnum()) {
			return isCheck;
		}

		Enum<?>[] enums = (Enum<?>[]) enumType.getEnumConstants();
		try {
			for (Enum<?> em : enums) {
				Class<?> clazz = em.getClass();
				Field field = clazz.getField("value");
				if (field.getInt(em) == value) {
					isCheck = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isCheck;
	}
	/**
	 * 获取范围key
	 * @param enumType
	 * @param value
	 * @return
	 * @author daixu
	 */
	public static List<Integer> getKeys(Class<?> enumType, int minKey,int maxKey) {
		List<Integer> keys = new ArrayList<Integer>();
		if (!enumType.isEnum()) {
			return null;
		}

		Enum<?>[] enums = (Enum<?>[]) enumType.getEnumConstants();
		try {
			for (Enum<?> em : enums) {
				Class<?> clazz = em.getClass();
				Field field = clazz.getField("value");
				int key = field.getInt(em);
				if (key >= minKey && key <= maxKey) {
					keys.add(key);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keys;
	}
	public static void main(String[] args) throws Exception {
		System.out.println(getDesc(IsEnable.class, 0));
		System.out.println(checkValue(IsEnable.class, 9));
	}
}
