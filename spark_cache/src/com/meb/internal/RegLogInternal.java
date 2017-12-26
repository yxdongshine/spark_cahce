package com.meb.internal;

import com.base.log.Logging;
import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.util.RespUtils;
import com.meb.dao.RegLogDao;

public class RegLogInternal {
	private Logging log = Logging.getLogging(this.getClass().getName());
	private RegLogDao rlDao = new RegLogDao();
	/**
	 * 添加注册日志信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap addRegLog(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
        //验证参数
		if(StrUtils.isNull(inMap.getString("uid"))){//会员编号
			outMap = RespUtils.fail("reg_log.add.is_uid_null");
			return outMap;
		}
		//添加主键
		inMap.put("id", IDGenerator.newGUID());
		//插入数据库
		ParaMap addMap = rlDao.addRegLog(inMap);
		if(addMap.getInt("num") <= 0){
			outMap = RespUtils.fail("reg_log.add.fail");
		}else{
			outMap = RespUtils.success("reg_log.add.success");
		}
		return outMap;
	}
	
	/**
	 * 获取注册日志信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getRegLogInfo(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		outMap = rlDao.getRegLogInfo(inMap);
		return outMap;
	}
	
	/**
	 * 获取注册日志信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getRegLogList(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		outMap = rlDao.getRegLogList(inMap);
		return outMap;
	}
}
