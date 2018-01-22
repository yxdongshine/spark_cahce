package com.meb.dao;

import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.dao.SimpleDataSetDao;
import com.meb.consts.MebConsts;

public class MebFriendsCircleDao extends SimpleDataSetDao{
	
	private static final String DYNAMIC_SQL = "$dynamicsql"; 
	private final String MEB_FRIENDS_CIRCLE_INFO = "meb_friends_circle_info";
	private final String MEB_FRIENDS_CIRCLE_INFO_SQL = "sql/meb_friends_circle_info";
	
	/**
	 * 会员发布朋友圈信息
	 * @param data
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap addMebFriendsCircleInfo(ParaMap data)throws Exception{
		return insert(MEB_FRIENDS_CIRCLE_INFO, data);
	}
	
	
	/**
	 * 会员更新朋友圈信息
	 * @param updateData
	 * @param conditions
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap updateMebFriendsCircleInfo(ParaMap updateData, ParaMap conditions)throws Exception{
		return update(MEB_FRIENDS_CIRCLE_INFO, conditions, updateData);
	}
	
	/**
	 * 获取会员朋友圈信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getMebFriendsCircleInfo(ParaMap inMap) throws Exception{
		SQLMap sqlMap=new SQLMap();
		sqlMap.setSQL(MEB_FRIENDS_CIRCLE_INFO_SQL, "getMebFriendsCircleInfo");

		StringBuffer dynamicSql = new StringBuffer();
		String uid = inMap.getString("uid");
		if (StrUtils.isNotNull(uid)) {
			dynamicSql.append(" AND mhi.uid = ? ");
			sqlMap.addParam(uid);
		}
		sqlMap.setPlaceHolder(DYNAMIC_SQL, dynamicSql.toString());
		ParaMap outMap = query(sqlMap);
		return outMap;
	}
	
	
	/**
	 * 获取会员朋友圈列表
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getMebFriendsCircleList(ParaMap inMap) throws Exception{
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL(MEB_FRIENDS_CIRCLE_INFO_SQL ,"getMebFriendsCircleList");
		
		StringBuilder dynamicSql = new StringBuilder();
		String uid = inMap.getString("uid");//会员主键
		if (StrUtils.isNotNull(uid)) {
			dynamicSql.append(" AND mfci.uid = ? ");
			sqlMap.addParam(uid);
		}
		sqlMap.setPlaceHolder(DYNAMIC_SQL, dynamicSql.toString());
		if (inMap.getInt("is_page") == MebConsts.NOT_PAGE) {
			return query(sqlMap);
		}
		sqlMap.put("pageIndex", inMap.getInt(MebConsts.STR_PAGE_INDEX, MebConsts.PAGE_INDEX));
		sqlMap.put("pageSize", inMap.getInt(MebConsts.STR_PAGE_SIZE, MebConsts.PAGE_SIZE));
		ParaMap outMap = queryForPage(sqlMap);
		return outMap;
	}
}
