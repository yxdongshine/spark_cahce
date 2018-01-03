package com.meb.internal;


import com.base.log.Logging;
import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.util.MailUtil;
import com.common.util.RespUtils;
import com.meb.consts.MebConsts.IsRegByTel;
import com.meb.consts.MebConsts.LoginByAccount;
import com.meb.consts.MebConsts.UpdatePwdByMess;
import com.meb.consts.MebConsts.UpdatePwdConfirm;
import com.meb.dao.MebAccountDao;

public class MebAccountInternal {
	
	private Logging log = Logging.getLogging(this.getClass().getName());
	private MebAccountDao maDao = new MebAccountDao();
	private LoginLogInternal loginLogInternal = new LoginLogInternal();

	/**
	 * 是否注册
	 * @param account
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap isReg(String account) throws Exception {
		ParaMap outMap = maDao.isReg(account);
		int num = outMap.getRecordInt(0, "num");
		if(num == 0){
			return RespUtils.resSuccess(IsRegByTel.SUCC_NOT_REG.code, IsRegByTel.SUCC_NOT_REG.mes);
		}else {
			return RespUtils.resFail(IsRegByTel.ERR_HAD_REG.code, IsRegByTel.ERR_HAD_REG.mes);
		}
	}

	/**
	 * 添加会员账号
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap addMebAccount(ParaMap inMap)throws Exception{
		return maDao.addMebAccount(inMap);
	}
	
	
	/**
	 * 根据账号登录
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap loginByAccount(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		outMap = maDao.loginByAccount(inMap.getString("account").trim(), inMap.getString("password").trim());
		if(0 < outMap.getRecordCount()){//登录成功
			String uid = outMap.getRecordString(0, "uid");
			outMap = RespUtils.resSuccess(LoginByAccount.SUCC_LOGIN.code,LoginByAccount.SUCC_LOGIN.mes);
			ParaMap dataMap = new ParaMap();
			dataMap.put("uid", uid);
			outMap.put("data", dataMap);
			inMap.put("uid", uid);
			//添加login_time登录时间
			inMap.put("login_time", System.currentTimeMillis());
			//添加登录日志
			loginLogInternal.addLoginLog(inMap);
		}else{
			outMap = RespUtils.resFail(LoginByAccount.ERR_UID_EXIT.code, LoginByAccount.ERR_UID_EXIT.mes);
		}
		return outMap;
	}
	
	/**
	 * 更新密码 
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap updatePwdConfirm(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		//获取邮箱地址
		MebInternal mebInternal = new MebInternal();
		outMap = mebInternal.getMebInfo(inMap);
		if(null == outMap
				|| 0 == outMap.getRecordCount())
			return RespUtils.resFail(UpdatePwdConfirm.ERR_FAIL.code, UpdatePwdConfirm.ERR_FAIL.mes);
		String email = outMap.getRecordString(0, "email");
		if(StrUtils.isNull(email))
			return RespUtils.resFail(UpdatePwdConfirm.ERR_MAIL_NULL.code, UpdatePwdConfirm.ERR_MAIL_NULL.mes);
		ParaMap updateInMap = new ParaMap();
		//生成随机6位密码
		String randomStr = IDGenerator.newGUID();
		String password = randomStr.substring(randomStr.length()-6);
		updateInMap.put("password", password);
		ParaMap conditionInMap = new ParaMap();
		String uid = inMap.getString("uid");
		conditionInMap.put("uid", uid);
		outMap = maDao.updateMebAccount(updateInMap, conditionInMap);

		if(outMap.getInt("num") == 0){
			outMap = RespUtils.resFail(UpdatePwdConfirm.ERR_FAIL.code, UpdatePwdConfirm.ERR_FAIL.mes);
		}else{
			outMap = RespUtils.resSuccess(UpdatePwdConfirm.SUCC_UPDATE_PASS.code,UpdatePwdByMess.SUCC_UPDATE_PASS.mes);
			//成功之后发送邮件
			if(!MailUtil.sendMail(email, password))
				log.info("uid: "+uid+"**发送邮件失败！！！");
		}
		return outMap;
	}
}
