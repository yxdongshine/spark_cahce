package com.meb.internal;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.common.consts.GlobalConsts.RespKey;
import com.common.consts.GlobalConsts.RespState;
import com.common.util.RespUtils;
import com.meb.consts.BeanInfoConsts.MebAccountSTATUS;
import com.meb.consts.BeanInfoConsts.MebInfo;
import com.meb.consts.BeanInfoConsts.MebInfo_RULE;
import com.meb.consts.BeanInfoConsts.MebInfo_TYPE;
import com.meb.consts.MebConsts.IsRegByTel;
import com.meb.consts.MebConsts.RegMeb;
import com.meb.consts.MebConsts.UpdateMebInfo;
import com.meb.dao.MebDao;
import com.meb.dao.MebHeadDao;
import com.meb.dao.MebPerIntentionDao;
import com.meb.util.DataSetUtil;

public class MebInternal {

	private MebAccountInternal maInternal = new MebAccountInternal();
	private MebInfoInternal mebInfoInternal = new MebInfoInternal();
	private MebDao mebDao = new MebDao();
	private MebPerIntentionDao mpiDao = new MebPerIntentionDao();
	private LoginLogInternal loginLogInternal = new LoginLogInternal();
	private RegLogInternal regLogInternal = new RegLogInternal();
	private MebHeadDao mhDao = new MebHeadDao();
	
	/**
	 * 注册会员
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap regMeb(ParaMap inMap)throws Exception{
		final String account = inMap.getString("account"); 
		String password = inMap.getString("password"); 
		//检查账号是否已经注册
		ParaMap checkReg = maInternal.isReg(account);
		if(checkReg.getInt(RespKey.CODE.value) == IsRegByTel.ERR_HAD_REG.code){
			checkReg.put(RespKey.STATE.value, RespState.ERROR.value);
			return checkReg;
		}
		//新增基本会员
		final String uid = mebInfoInternal.addMebInfo(IDGenerator.newGUID(),
				IDGenerator.newGUID(),
				MebInfo_TYPE.MEB_TYPE_PER.value,
				MebInfo_RULE.RULE_TYPE_NORMAL.value,
				MebInfo.STATUS_NORMAL.value
				);
		
		ParaMap mebData = new ParaMap();//要存到数据库的用户信息
		//新增个人用户
		mebData.put("id", IDGenerator.newGUID());
		mebData.put("uid", uid);
		mebData.put("age", inMap.getString("age"));
		mebData.put("sex", inMap.getString("sex"));
		mebData.put("height", inMap.getString("height"));
		mebData.put("email", inMap.getString("email"));
		mebData.put("photo", inMap.getString("photo"));

		ParaMap mebResult = mebDao.addPerMeb(mebData);
		if(!DataSetUtil.addSuccess(mebResult)){
			throw new Exception("增加个人会员信息失败");
		}

		//新增会员意图信息
		ParaMap intentionData = new ParaMap();
		intentionData.put("id", IDGenerator.newGUID());
		intentionData.put("uid", uid);
		intentionData.put("want_sex", inMap.getString("want_sex"));
		intentionData.put("age_rang", inMap.getString("age_rang"));
		ParaMap intentionResult = mpiDao.addPerIntention(intentionData);
		if(!DataSetUtil.addSuccess(intentionResult)){
			throw new Exception("新增会员意图信息失败");
		}
		//新增账号
		ParaMap accountData = new ParaMap();
		accountData.put("id", IDGenerator.newGUID());
		accountData.put("uid", uid);
		accountData.put("account", account);
		accountData.put("password", password);
		accountData.put("status", MebAccountSTATUS.STATUS_YES.value);
		ParaMap accountResult = maInternal.addMebAccount(accountData);
		if(!DataSetUtil.addSuccess(accountResult)){
			throw new Exception("增加个人会员账号信息失败");
		}
		//新增注册信息 
		inMap.put("uid", uid);
		long currentTime = System.currentTimeMillis();
		//插入注册信息表
		inMap.put("id", IDGenerator.newGUID());
		inMap.put("reg_time", currentTime);
		regLogInternal.addRegLog(inMap);
		//插入登录信息表
		inMap.put("id", IDGenerator.newGUID());
		inMap.put("login_time", currentTime);
		loginLogInternal.addLoginLog(inMap);
		ParaMap uidData = new ParaMap();
		uidData.put("uid", uid);
		return RespUtils.result(RegMeb.SUCCESS.code, RegMeb.SUCCESS.mes, uidData);
	}
	
	
	/**
	 * 修改个人会员信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap updateMebInfo(ParaMap inMap)throws Exception{
		ParaMap conditions = new ParaMap();
		conditions.put("uid", inMap.getString("uid"));
		conditions.put("isvalid", 1);
		ParaMap result = mebDao.updateMebInfo(inMap, conditions);
		if(DataSetUtil.updateSuccess(result)){
			return RespUtils.resSuccess(UpdateMebInfo.SUCC_UPDATE_MEB_INFO.code, UpdateMebInfo.SUCC_UPDATE_MEB_INFO.mes);
		}else{
			return RespUtils.resFail(UpdateMebInfo.FAIL_UPDATE_MEB_INFO.code, UpdateMebInfo.FAIL_UPDATE_MEB_INFO.mes);
		}
	}
	
	
	/**
	 * 获取个人会员信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getMebInfo(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		outMap = mebDao.getMebInfo(inMap);
		return outMap;
	}
	
	
	/**
	 * 获取个人会员头像信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getMebHeadInfo(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		outMap = mhDao.getMebHeadInfo(inMap);
		return outMap;
	}
}
