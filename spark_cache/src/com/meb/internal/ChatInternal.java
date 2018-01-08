package com.meb.internal;

import io.rong.RongCloud;
import io.rong.models.TokenResult;

import java.io.Reader;

import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.util.RespUtils;
import com.meb.consts.MebConsts.UpdateMebInfo;

public class ChatInternal {

	private static final String KEY = "sfci50a7s4vki"; 
	private static final String SECRET = "3IDH5NeitVblq8"; 

	
	public ParaMap getToken(ParaMap inMap) throws Exception {
		ParaMap outMap = new ParaMap();
		RongCloud rongCloud = RongCloud.getInstance(KEY, SECRET);
		// 获取 Token 方法 
		TokenResult userGetTokenResult = null;
		try {
			userGetTokenResult = rongCloud.user.getToken(inMap.getString("uid"), "", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outMap.put("data", userGetTokenResult);
		return outMap;
	}
	
}
