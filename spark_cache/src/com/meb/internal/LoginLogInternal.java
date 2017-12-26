package com.meb.internal;

import com.base.log.Logging;
import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.util.RespUtils;
import com.meb.dao.LoginLogDao;

public class LoginLogInternal {

	private Logging log = Logging.getLogging(this.getClass().getName());
	private LoginLogDao llDao = new LoginLogDao();
	/**
	 * 添加登录日志信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap addLoginLog(ParaMap inMap) throws Exception{
		log.info( "添加登录日志开始!!!");
		ParaMap outMap = new ParaMap();
        //验证参数
		String uid = inMap.getString("uid");
		if(StrUtils.isNull(uid)){//会员编号
			outMap = RespUtils.fail("login_log.add.is_uid_null");
			return outMap;
		}

		//检查是否已经存在uid的会员信息
		ParaMap infoOutMap = llDao.getLoginLogInfo(inMap);
		int count = infoOutMap.getRecordCount();
		if(count == 1){//存在情况下更新
			ParaMap conditions = new ParaMap();
			conditions.put("uid", uid);
			outMap = updateLoginLog(inMap, conditions);
		}else if(count == 0){//新增
			//添加主键
			inMap.put("id", IDGenerator.newGUID());
			//插入数据库
			outMap = llDao.addLoginLog(inMap);
		}

		if(outMap.getInt("num") == 0){
			outMap = RespUtils.fail("login_log.add.fail");
		}else{
			outMap = RespUtils.success("login_log.add.success");
			log.info("uid:"+uid+" 添加登录日志成功!!!");
		}
		return outMap;
	}
	
	/**
	 * 更新登录日志
	 * @param inMap,conditions
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap updateLoginLog(ParaMap inMap,ParaMap conditions) throws Exception{
		ParaMap outMap = new ParaMap();
		//验证输入参数
		String uid = inMap.getString("uid");
		if (StrUtils.isNull(uid)) {
			return RespUtils.fail("reg_log.add.is_uid_null");
		}

		outMap = llDao.updateLoginLogInfo(inMap, conditions);
		
		return outMap;
	}
	
	/**
	 * 获取登录日志信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getLoginLogInfo(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();

		outMap = llDao.getLoginLogInfo(inMap);
		return outMap;
	}
	
	/**
	 * 获取登录日志信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getLoginLogList(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		outMap = llDao.getLoginLogList(inMap);
		return outMap;
	}
}
