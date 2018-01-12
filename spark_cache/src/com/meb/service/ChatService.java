package com.meb.service;

import com.base.annotation.Author;
import com.base.annotation.ServiceDesc;
import com.base.annotation.UpdateTime;
import com.base.annotation.Version;
import com.base.service.BaseService;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.util.RespUtils;
import com.meb.consts.MebConsts.GetToken;
import com.meb.internal.ChatInternal;

@ServiceDesc("会员聊天类")
@Author("杨晓冬")
@Version("1.0")
@UpdateTime("2017-12-25")
public class ChatService extends BaseService {

	private ChatInternal cInternal = new ChatInternal();
	/**
	 * get个人会员token 对外公开服务
	 * 
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getToken(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("uid"))) {
			return RespUtils.resFail(GetToken.ERR_UID_NULL.code, GetToken.ERR_UID_NULL.mes);
		}
		return cInternal.getToken(inMap);
	}
	
}
