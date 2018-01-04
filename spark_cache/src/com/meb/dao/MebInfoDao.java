package com.meb.dao;

import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.dao.SimpleDataSetDao;
import com.meb.consts.MebConsts;
public class MebInfoDao extends SimpleDataSetDao{
	
	private static final String DISTANCE_SQL = "$distancesql"; 
	private static final String DYNAMIC_SQL = "$dynamicsql"; 
	private static final String SQL_URL = "sql/meb_info";
	private static final String MEB_INFO = "meb_info";
	
	/**
	 * 添加基本会员记录
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap addMebInfo(ParaMap inMap)throws Exception{
		return insert(MEB_INFO, inMap);
	}
	
	/**
	 * 修改基本会员信息
	 * @param updateData
	 * @param conditions
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap updateMebInfo(ParaMap updateData, ParaMap conditions)throws Exception{
		return update(MEB_INFO, conditions, updateData);
	}
	
	/**
	 * 根据id获取基本会员信息
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getMebInfoById(String id, String returnFields)throws Exception{
		return querySimpleById(MEB_INFO, id, null, returnFields);
	}
	
	/**
	 * 获取会员列表根据登录日志
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getMebListByLogin(ParaMap inMap) throws Exception{
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL(SQL_URL ,"getMebListByLogin");
		
		StringBuilder dynamicSql = new StringBuilder();
		sqlMap.setPlaceHolder(DYNAMIC_SQL, dynamicSql.toString());
		if (inMap.getInt("is_page") == MebConsts.NOT_PAGE) {
			return query(sqlMap);
		}
		sqlMap.put("pageIndex", inMap.getInt(MebConsts.STR_PAGE_INDEX, MebConsts.PAGE_INDEX));
		sqlMap.put("pageSize", inMap.getInt(MebConsts.STR_PAGE_SIZE, MebConsts.PAGE_SIZE));
		ParaMap outMap = queryForPage(sqlMap);
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
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL(SQL_URL ,"getMebListByReg");
		
		StringBuilder dynamicSql = new StringBuilder();
		sqlMap.setPlaceHolder(DYNAMIC_SQL, dynamicSql.toString());
		if (inMap.getInt("is_page") == MebConsts.NOT_PAGE) {
			return query(sqlMap);
		}
		sqlMap.put("pageIndex", inMap.getInt(MebConsts.STR_PAGE_INDEX, MebConsts.PAGE_INDEX));
		sqlMap.put("pageSize", inMap.getInt(MebConsts.STR_PAGE_SIZE, MebConsts.PAGE_SIZE));
		ParaMap outMap = queryForPage(sqlMap);
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
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL(SQL_URL ,"getMebListByNear");
		
		StringBuilder dynamicSql = new StringBuilder();
		StringBuilder distancesSql = new StringBuilder();
		String longitude = inMap.getString("longitude");//经度
		String latitude = inMap.getString("latitude");//维度
		if (StrUtils.isNotNull(longitude)
				&& StrUtils.isNotNull(latitude)) {
			//拼接条件
			dynamicSql.append(" AND (SELECT power(power((? - mll.longitude),2)+power((? - mll.latitude),2),0.5))*100 < 50000 ");
			sqlMap.addParam(longitude);
			sqlMap.addParam(latitude);
			//拼接分组
			distancesSql.append(" power(power((? - mll.longitude),2)+power((? - mll.latitude),2),0.5)*100 ");
			sqlMap.addParam(longitude);
			sqlMap.addParam(latitude);
		}
		sqlMap.setPlaceHolder(DYNAMIC_SQL, dynamicSql.toString());
		sqlMap.setPlaceHolder(DISTANCE_SQL, distancesSql.toString());
		if (inMap.getInt("is_page") == MebConsts.NOT_PAGE) {
			return query(sqlMap);
		}
		sqlMap.put("pageIndex", inMap.getInt(MebConsts.STR_PAGE_INDEX, MebConsts.PAGE_INDEX));
		sqlMap.put("pageSize", inMap.getInt(MebConsts.STR_PAGE_SIZE, MebConsts.PAGE_SIZE));
		ParaMap outMap = queryForPage(sqlMap);
		return outMap;
	}
}
