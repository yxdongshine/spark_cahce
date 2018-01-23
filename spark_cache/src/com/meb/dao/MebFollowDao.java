package com.meb.dao;

import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.dao.SimpleDataSetDao;
import com.meb.consts.MebConsts;

public class MebFollowDao extends SimpleDataSetDao{
	
	private static final String DYNAMIC_SQL = "$dynamicsql"; 
	private final String MEB_FOLLOW_INFO = "meb_follow_info";
	private final String MEB_FOLLOW_INFO_SQL = "sql/meb_follow_info";
	
	/**
	 * 关注
	 * @param data
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap addMebFollowInfo(ParaMap data)throws Exception{
		return insert(MEB_FOLLOW_INFO, data);
	}
	
	/**
	 * 更新关注信息
	 * @param updateData
	 * @param conditions
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap updateMebFollowInfo(ParaMap updateData, ParaMap conditions)throws Exception{
		return update(MEB_FOLLOW_INFO, conditions, updateData);
	}
	
	/**
	 * 获取会员关注列表
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getMebFollowList(ParaMap inMap) throws Exception{
		SQLMap sqlMap=new SQLMap();
		sqlMap.setSQL(MEB_FOLLOW_INFO_SQL, "getMebFollowList");

		StringBuffer dynamicSql = new StringBuffer();
		String id = inMap.getString("id");
		if (StrUtils.isNotNull(id)) {
			dynamicSql.append(" AND mfi.id = ? ");
			sqlMap.addParam(id);
		}
		String fUid = inMap.getString("f_uid");
		if (StrUtils.isNotNull(fUid)) {
			dynamicSql.append(" AND mfi.f_uid = ? ");
			sqlMap.addParam(fUid);
		}
		String coverUid = inMap.getString("cover_uid");
		if (StrUtils.isNotNull(coverUid)) {
			dynamicSql.append(" AND mfi.cover_uid = ? ");
			sqlMap.addParam(coverUid);
		}
		String status = inMap.getString("status");
		if (StrUtils.isNotNull(status)) {
			dynamicSql.append(" AND mfi.status = ? ");
			sqlMap.addParam(status);
		}
		String isvalid = inMap.getString("isvalid");
		if (StrUtils.isNotNull(isvalid)) {
			dynamicSql.append(" AND mfi.isvalid = ? ");
			sqlMap.addParam(isvalid);
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
