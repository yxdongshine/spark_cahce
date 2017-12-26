package com.common.util;

import com.base.utils.ParaMap;
import com.common.conf.MsgConfig;
import com.common.consts.GlobalConsts.RespKey;
import com.common.consts.GlobalConsts.RespState;

/**
 * 
 * @author YXD
 *
 */
public class RespUtils {
	
	private RespUtils() {}
	
	public static ParaMap success(String code){
		return resp(new ParaMap(), RespState.SUCCESS, code);
	}
	
	public static ParaMap success(String code, Object data){
		ParaMap outMap = resp(new ParaMap(), RespState.SUCCESS, code);
		outMap.put(RespKey.DATA.value, data);
		return outMap;
	}
	
	public static ParaMap success(ParaMap outMap, String code){
		return resp(outMap, RespState.SUCCESS, code);
	}
	
	public static ParaMap fail(String code){
		return resp(new ParaMap(), RespState.ERROR, code);
	}

	public static ParaMap resSuccess(int code, String message){
		return result(code, message, RespState.SUCCESS.value);
	}
	
	public static ParaMap resFail(int code, String message){
		return result(code, message, RespState.ERROR.value);
	}
	
	public static ParaMap result(int code, String message, int state){
		return resp(new ParaMap(), state, code, message);
	}
	
	public static ParaMap result(int code, String message, Object data){
		ParaMap outMap = resp(new ParaMap(), RespState.SUCCESS, code, message);
		outMap.put(RespKey.DATA.value, data);
		return outMap;
	}
	
	/**
	 * 构造接口响应ParaMap对象
	 * @param state
	 * @param code
	 * @param args 替换占位符参数
	 * @return
	 * @author YXD
	 */
	public static ParaMap resp(int state, String code) {
		return resp(new ParaMap(), state, code);
	}
	
	/**
	 * 
	 * @param state
	 * @param code
	 * @return
	 * @author YXD
	 */
	public static ParaMap resp(RespState state, String code) {
		return resp(state.value, code);
	}
	
	/**
	 * 构造接口响应ParaMap对象
	 * @param map
	 * @param state
	 * @param code
	 * @return
	 * @author YXD
	 */
	public static ParaMap resp(ParaMap map, int state, String code) {
		map.put(RespKey.STATE.value, state);
		// map.put(RespKey.CODE.value, code);
		map.put(RespKey.MSG.value, MsgConfig.get(code));
		return map;
	}
	
	
	/**
	 * 构造接口响应ParaMap对象
	 * @param map
	 * @param state
	 * @param code
	 * @return
	 * @author YXD
	 */
	public static ParaMap resp(ParaMap map, RespState state, String code) {
		return resp(map, state.value, code);
	}
	
	/**
	 * 构造接口响应ParaMap对象
	 * @param state
	 * @param code
	 * @param args 替换占位符参数
	 * @return
	 * @author YXD
	 */
	public static ParaMap resp(int state, String code, Object... args) {
		return resp(new ParaMap(), state, code, args);
	}
	
	/**
	 * 构造接口响应ParaMap对象
	 * @param state
	 * @param code
	 * @param args 替换占位符参数
	 * @return
	 * @author YXD
	 */
	public static ParaMap resp(RespState state, String code, Object... args) {
		return resp(new ParaMap(), state.value, code, args);
	}
	
	/**
	 * 构造接口响应ParaMap对象
	 * @param map
	 * @param state
	 * @param code
	 * @param args 需要被替换的占位符{0}参数
	 * @return
	 * @author YXD
	 */
	public static ParaMap resp(ParaMap map, int state, String code, Object... args) {
		map.put(RespKey.STATE.value, state);
		// map.put(RespKey.CODE.value, code);
		map.put(RespKey.MSG.value, MsgConfig.get(code, args));
		return map;
	}
	
	public static ParaMap resp(ParaMap map, RespState state, String code, Object... args) {
		return resp(map, state.value, code, args);
	}
	
	
	/**
	 * 构造接口响应ParaMap对象
	 * @param map
	 * @param RespState		
	 * @param code 		
	 * @param message 
	 * @return
	 * @author YXD
	 */
	public static ParaMap resp(ParaMap map, RespState state, int code, String message) {
		return resp(map, state.value, code, message);
	}
	/**
	 * 构造接口响应ParaMap对象
	 * @param map
	 * @param state		
	 * @param code 		
	 * @param message 
	 * @return
	 * @author YXD
	 */
	public static ParaMap resp(ParaMap map, int state, int code, String message) {
		map.put(RespKey.STATE.value, state);
		map.put(RespKey.CODE.value, code);
		map.put(RespKey.MSG.value, message);
		return map;
	}
	
	
	/**
	 * 构造接口响应ParaMap对象
	 * @param map
	 * @param state		
	 * @param code 		
	 * @param message 
	 * @return
	 * @author YXD
	 */
	public static boolean isSuccess(ParaMap map){
		return map != null && map.getInt(RespKey.STATE.value) == RespState.SUCCESS.value;
	}
}
