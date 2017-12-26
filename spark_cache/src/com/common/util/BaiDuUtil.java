package com.common.util;

import java.util.ArrayList;
import java.util.Collections;

import com.alibaba.fastjson.JSONObject;
import com.base.http.HttpManager;
import com.base.log.Logging;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;


/**
 * 百度工具类
 * 
 */
public class BaiDuUtil {
	
	private static Logging log = Logging.getLogging(BaiDuUtil.class.getName());

	public static final String BAIDU_MAP_AK = "BaGqhrzBmFlpVNi5PdoOZyMaic8XBwKx";
	
	public static final String BAIDU_URL = "http://api.map.baidu.com/geocoder/v2/";
	
	public static final String OUTPUT = "json";
	
	public static String getData(String url, ParaMap requestParam)
			throws Exception {
		String params = getJoinUrl(requestParam);
		String result = HttpManager.getData(url + "?", params);
		return result;
	}

	
	public static String getJoinUrl(ParaMap inMap) {
		ArrayList<String> keys = new ArrayList<String>(inMap.keySet());
		Collections.sort(keys);
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = inMap.getString(key);
			if (buff.toString().length() == 0) {
				buff.append(key + "=" + value);
			} else {
				buff.append("&" + key + "=" + value);
			}
		}
		return buff.toString();
	}
	/**
	 * 根据经纬度查询地址（百度地图）
	 * 
	 * @param lat 纬度
	 * @param lng 经度
	 * @return
	 * @throws Exception 
	 */
    public static String getLocationInfo(String lat, String lng) throws Exception {
    	ParaMap inMap = new ParaMap();
    	inMap.put("location", lat+","+lng);
    	inMap.put("output", OUTPUT);
    	inMap.put("ak", BAIDU_MAP_AK);
    	String data = getData(BAIDU_URL, inMap);
        return data;  
    }
	
    /**
	 * 根据地址获取经纬度
	 * 
	 * @param address 
	 * @return 
	 * 			lng-经度，lat-纬度，status-状态(0：有返回经纬度数据，1：无匹配的经纬度)，msg-返回描述信息 
     * @throws Exception 
	 */
    public static String getLngAndLat(String address) throws Exception{
        ParaMap inMap = new ParaMap();
    	inMap.put("address", address);
    	inMap.put("output", OUTPUT);
    	inMap.put("ak", BAIDU_MAP_AK);
    	String data = getData(BAIDU_URL, inMap);
        return data;
    }

    /**
     * 获取行政区区号
     * @param lat
     * @param lng
     * @return
     * @throws Exception
     * @author YXD
     */
    public static String getAdcode(String lat, String lng) {
    	String adcode = "";
    	String data = "";
		try {
			data = BaiDuUtil.getLocationInfo(lat, lng);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("获取百度api出错!!!");
		}
    	if(StrUtils.isNotNull(data)){
            JSONObject dataJson = new JSONObject().parseObject(data);
            if(null != dataJson){
            	JSONObject resultJson = dataJson.getJSONObject("result");
            	if(null != resultJson){
            		JSONObject acJson = resultJson.getJSONObject("addressComponent");  
                	if(null != acJson){
                		adcode = acJson.getString("adcode");
                	}
            	}
            }
    	}
    	return adcode;  
    }
    public static void main(String[] args) throws Exception {
    	
//    	String address = "宝山";
//    	String outMap = getLngAndLat(address);
//    	System.out.println(outMap);
    	String lat = "22.5748728670";
    	String lng = "113.8774799369";
    	//String json = BaiDuUtil.getLocationInfo(lat, lng);
    	String json = getAdcode(lat, lng);
    	System.out.println(json);
    }  
}
