package com.meb.dao;

import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.dao.SimpleDataSetDao;
import com.meb.consts.MebConsts;

public class RegLogDao extends SimpleDataSetDao{

	private static final String DYNAMIC_SQL = "$dynamicsql"; 
	private static final String SQL_URL = "sql/meb_reg_log";
	private static final String TABLE_NAME = "meb_log_reg";
	
	/**
	 * 添加注册日志信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap addRegLog(ParaMap inMap) throws Exception{
		ParaMap outMap = insert(TABLE_NAME, inMap);
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
		SQLMap sqlMap=new SQLMap();
		sqlMap.setSQL(SQL_URL, "getRegLogInfo");

		StringBuffer dynamicSql = new StringBuffer();
		String id = inMap.getString("id");
		if (StrUtils.isNotNull(id)) {
			dynamicSql.append( " and id = ? ");
			sqlMap.addParam(id);
		}
		
		String uid = inMap.getString("uid");//也提供根据会员编号查询
		if (StrUtils.isNotNull(uid)) {
			dynamicSql.append( " and uid = ? ");
			sqlMap.addParam(uid);
		}
		
		sqlMap.setPlaceHolder(DYNAMIC_SQL, dynamicSql.toString());
		ParaMap outMap = query(sqlMap);
		return outMap;
	}
	
	/**
	 * 获取注册日志列表
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getRegLogList(ParaMap inMap) throws Exception{
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL(SQL_URL ,"getRegLogListCount");
		
		StringBuilder dynamicSql = new StringBuilder();
		String id = inMap.getString("id");//主键
		if (StrUtils.isNotNull(id)) {
			dynamicSql.append(" AND id = ? ");
			sqlMap.addParam(id);
		}
		String uid = inMap.getString("uid");//会员编号
		if (StrUtils.isNotNull(uid)) {
			dynamicSql.append(" AND uid = ? ");
			sqlMap.addParam(uid);
		}
		
		Long start = inMap.getLong("start_time");
		Long end = inMap.getLong("end_time");
		if(start != null){
			dynamicSql.append(" AND reg_time >= ? ");
			sqlMap.addParam(start);
		}
		if(end != null){
			dynamicSql.append(" AND reg_time <= ? ");
			sqlMap.addParam(end);
		}
		
		sqlMap.setPlaceHolder(DYNAMIC_SQL, dynamicSql.toString());
		
		ParaMap count = query(sqlMap);
		int total = count.getRecordInt(0, 0);
		int pageIndex = inMap.getInt(MebConsts.STR_PAGE_INDEX, MebConsts.PAGE_INDEX);
		int pageSize = inMap.getInt(MebConsts.STR_PAGE_SIZE, MebConsts.PAGE_SIZE);
		return attachData(sqlMap, total, SQL_URL, "getRegLogList", pageSize, pageIndex);
	}
}
