package com.meb.util;

import java.util.List;

import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.util.BaiDuUtil;
import com.common.util.EnumUtils;
import com.common.util.PubUtils;
import com.meb.consts.MebConsts;
/**
 * 数据集处理
 * @author YXD
 *
 */
public class DataSetUtil {
	public static final String STATUS_DES = "status_desc";
	public static final int STATUS_DES_SIZE = 32;
	public static final String STR_SYSTEM_CODE = "system_code";
	public static final int STR_SYSTEM_CODE_LENGTH = 32;
	public static final String STR_SYSTEM_NAME = "system_name";
	public static final int STR_SYSTEM_NAME_LENGTH = 32;
	public static final int DEFAULT_PAGESIZE = 10;
	public static final int DEFAULT_PAGEINDEX = 1;
	/**
	 * 判断添加成功
	 * @param result
	 * @return
	 * @author YXD
	 */
	public static boolean addSuccess(ParaMap result){
		return numGreaterZero(result);
	}
	
	/**
	 * 判断修改成功
	 * @param result
	 * @return
	 * @author YXD
	 */
	public static boolean updateSuccess(ParaMap result){
		return numGreaterZero(result);
	}
	

	private static boolean numGreaterZero(ParaMap map){
		return map != null && map.getInt("num") > 0;
	}
	
	/**
	 * 分页控制
	 * @param map
	 * @return
	 * @author YXD
	 */
	public static void setQueryForPage(ParaMap sourceMap, SQLMap sqlMap){
		int pageSize = sourceMap.getInt(MebConsts.STR_PAGE_SIZE);
		int pageIndex = sourceMap.getInt(MebConsts.STR_PAGE_INDEX);
		if (pageSize > 0) {
			sqlMap.put("pageSize", pageSize);
		} else {
			sqlMap.put("pageSize", DEFAULT_PAGESIZE);
		}
		if (pageIndex > 0) {
			sqlMap.put("pageIndex", pageIndex);
		} else {
			sqlMap.put("pageIndex", DEFAULT_PAGEINDEX);
		}
	}
	
	/**
	 * 根据经纬度转换成行政区编号
	 * @param inMap
	 * @author YXD
	 */
	public static ParaMap tranAdcode(ParaMap inMap){
		String lat = inMap.getString("latitude");
    	String lng = inMap.getString("longitude");
    	if(StrUtils.isNull(inMap.getString("canton_code"))){
    		if(StrUtils.isNotNull(lat) 
        			&& StrUtils.isNotNull(lng)){
        		inMap.put("canton_code", BaiDuUtil.getAdcode(lat, lng));
        	}
    	}
    	return inMap;
	}

	/**
	 * 
	 * @param name
	 * @param inMap
	 * @return
	 * @author YXD
	 */
	public static ParaMap addFiledMap(String name,int size,ParaMap inMap){    
		ParaMap keyMap = new ParaMap();
		keyMap.put("name", name);
		keyMap.put("size", size);
		return PubUtils.addFsMapForString(inMap, keyMap);		
	}
	
	
	
	/**
	 * 添加status_value 列
	 * @param inMap
	 * @return
	 * @author YXD
	 */
	public static ParaMap addFiledMapValue(ParaMap inMap){    
		ParaMap keyMap = new ParaMap();
		keyMap.put("name", STATUS_DES);
		keyMap.put("size", STATUS_DES_SIZE);
		return PubUtils.addFsMapForString(inMap, keyMap);		
	}
	
	/**
	 * 给相应字段添加desc
	 * @param inMap
	 * @param attachName
	 * @param name
	 * @param enumType
	 * @author YXD
	 */
	@SuppressWarnings("unchecked")
	public static void attachDesc(ParaMap inMap, String attachName, String name, Class<?> enumType){
		Object obj = inMap.get("data");
		if(obj instanceof List){
			List<ParaMap> list = (List<ParaMap>)obj;
			for(ParaMap data : list){
				data.put(attachName, EnumUtils.getDesc(enumType, data.getInt(name)));
			}
		}else{
			ParaMap data = (ParaMap)obj;
			data.put(attachName, EnumUtils.getDesc(enumType, data.getInt(name)));
		}
	}
}
