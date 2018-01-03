package com.meb.service;

import com.base.annotation.Author;
import com.base.annotation.ServiceDesc;
import com.base.annotation.UpdateTime;
import com.base.annotation.Version;
import com.base.service.BaseService;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.util.PubUtils;
import com.common.util.RespUtils;
import com.meb.consts.MebConsts.GetMebInfo;
import com.meb.consts.MebConsts.LoginByAccount;
import com.meb.consts.MebConsts.RegMeb;
import com.meb.consts.MebConsts.UpdateMebInfo;
import com.meb.consts.MebConsts.UpdatePwdNoConfirm;
import com.meb.internal.MebAccountInternal;
import com.meb.internal.MebInternal;

@ServiceDesc("会员基本操作类")
@Author("杨晓冬")
@Version("1.0")
@UpdateTime("2017-12-25")
public class MebService extends BaseService {

	private MebInternal mebInternal = new MebInternal();
	private MebAccountInternal maInternal = new MebAccountInternal();

	/**
	 * 会员注册
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author yxd
	 */
	public ParaMap regMeb(ParaMap inMap) throws Exception {
		ParaMap logResult = checkLogInfo(inMap);
		if (logResult != null) {
			return logResult;
		}
		if (StrUtils.isNull(inMap.getString("account"))) {
			return RespUtils.resFail(RegMeb.ERR_ACCOUNT_NULL.code, RegMeb.ERR_ACCOUNT_NULL.mes);
		}
		if (StrUtils.isNull(inMap.getString("password"))) {
			return RespUtils.resFail(RegMeb.ERR_PASS_NULL.code, RegMeb.ERR_PASS_NULL.mes);
		}
		return mebInternal.regMeb(inMap);
	}
	
	

	/**
	 * 账号+密码  对外开放接口
	 * 
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap loginByAccount(ParaMap inMap) throws Exception {
		ParaMap outMap = new ParaMap();
		// 验证参数
		if (StrUtils.isNull(inMap.getString("account"))) {// 账号
			return RespUtils.resFail(LoginByAccount.ERR_ACCOUNT_NULL.code, LoginByAccount.ERR_ACCOUNT_NULL.mes);
		}

		if (StrUtils.isNull(inMap.getString("password"))) {// 密码
			return RespUtils.resFail(LoginByAccount.ERR_PASSWORD_NULL.code, LoginByAccount.ERR_PASSWORD_NULL.mes);
		}

		ParaMap logResult = checkLogInfo(inMap);
		if (logResult != null) {
			return logResult;
		}
		outMap = maInternal.loginByAccount(inMap);
		return outMap;
	}
	
	/**
	 * 验证日志信息
	 * @param inMap
	 * @return
	 * @author YXD
	 */
	private ParaMap checkLogInfo(ParaMap inMap) {
	
		final String lo = inMap.getString("longitude");
		if (lo != null) {
			try {
				Double.valueOf(lo);
			} catch (NumberFormatException e) {
				return RespUtils.resFail(RegMeb.ERR_LO_TYPE.code, RegMeb.ERR_LO_TYPE.mes);
			}
		}

		final String la = inMap.getString("latitude");
		if (la != null) {
			try {
				Double.valueOf(la);
			} catch (NumberFormatException e) {
				return RespUtils.resFail(RegMeb.ERR_LA_TYPE.code, RegMeb.ERR_LA_TYPE.mes);
			}
		}
		return null;
	}

	
	/**
	 * 修改个人会员信息
	 * 
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap updateMebInfo(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("uid"))) {
			return RespUtils.resFail(UpdateMebInfo.ERR_UID_NULL.code, UpdateMebInfo.ERR_UID_NULL.mes);
		}
		return mebInternal.updateMebInfo(inMap);
	}
	
	
	
	/**
	 * 获取个人会员信息 对外开放接口
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	@SuppressWarnings("unchecked")
	public ParaMap getMebInfo(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		//验证输入参数
		if (StrUtils.isNull(inMap.getString("uid"))) {
			return RespUtils.resFail(GetMebInfo.ERR_UID_NULL.code, UpdateMebInfo.ERR_UID_NULL.mes);
		}
		outMap = mebInternal.getMebInfo(inMap);
		outMap.putAll(RespUtils.resSuccess(GetMebInfo.SUCC_GET_MEB_INFO.code, GetMebInfo.SUCC_GET_MEB_INFO.mes));
		outMap = PubUtils.ConvertJsonMap(outMap);
		return outMap;
	}
	
	
	/**
	 * 更新密码 
	 * 
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap updatePwdNoConfirm(ParaMap inMap) throws Exception {
		ParaMap outMap = new ParaMap();
		// 验证参数
		if (StrUtils.isNull(inMap.getString("uid"))) {// 会员编号
			return RespUtils.resFail(UpdatePwdNoConfirm.ERR_UID_NULL.code,
					UpdatePwdNoConfirm.ERR_UID_NULL.mes);
		}
		outMap = maInternal.updatePwdConfirm(inMap);
		return outMap;
	}
}