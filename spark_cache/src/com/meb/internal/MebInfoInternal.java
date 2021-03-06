package com.meb.internal;

import com.base.log.Logging;
import com.base.utils.ParaMap;
import com.meb.dao.MebInfoDao;
import com.meb.util.DataSetUtil;

public class MebInfoInternal {
	private MebInfoDao mebInfoDao = new MebInfoDao();
	private Logging log = Logging.getLogging(this.getClass().getName());

	/**
	 * 新增会员
	 * @param id
	 * @param uid
	 * @param type
	 * @param ruleType
	 * @param status
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public String addMebInfo(String id, String uid,
			int type, int ruleType, int status)throws Exception{
		ParaMap uData = new ParaMap();
		uData.put("id", id);
		uData.put("uid", uid);
		uData.put("type", type);
		uData.put("rule_type", ruleType);
		uData.put("status", status);
		ParaMap uResult = mebInfoDao.addMebInfo(uData);
		if(!DataSetUtil.addSuccess(uResult)){
			log.error("新增基本会员失败" + uData);
			throw new Exception("新增基本会员失败");
		}else{
			return uid;
		}
	}
	
	
	/**
	 * 获取会员列表根据登录日志
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getMebListByLogin(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		outMap = mebInfoDao.getMebListByLogin(inMap);
		return outMap;
	}
	
	/**
	 * 获取会员列表根据注册日志
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getMebListByReg(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		outMap = mebInfoDao.getMebListByReg(inMap);
		return outMap;
	}
	
	/**
	 * 获取会员列表根据附近
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getMebListByNear(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		outMap = mebInfoDao.getMebListByNear(inMap);
		return outMap;
	}
}
