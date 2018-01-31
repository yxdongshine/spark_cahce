package com.meb.dao;

import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.dao.SimpleDataSetDao;
import com.meb.consts.BeanInfoConsts.MebLikesSTATUS;
import com.meb.consts.MebConsts;

public class MebLikesDao extends SimpleDataSetDao{
	
	private static final String DYNAMIC_SQL = "$dynamicsql"; 
	private final String MEB_LIKES_INFO = "meb_likes_info";
	private final String MEB_LIKES_INFO_SQL = "sql/meb_likes_info";
	
	/**
	 * 点赞
	 * @param data
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap addMebLikesInfo(ParaMap data)throws Exception{
		return insert(MEB_LIKES_INFO, data);
	}
	
	/**
	 * 更新点赞信息
	 * @param updateData
	 * @param conditions
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap updateMebLikesInfo(ParaMap updateData, ParaMap conditions)throws Exception{
		return update(MEB_LIKES_INFO, conditions, updateData);
	}
	
	/**
	 * 获取会员关注列表
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getMebLikesList(ParaMap inMap) throws Exception{
		SQLMap sqlMap=new SQLMap();
		sqlMap.setSQL(MEB_LIKES_INFO_SQL, "getMebLikesList");

		StringBuffer dynamicSql = new StringBuffer();
		String id = inMap.getString("id");
		if (StrUtils.isNotNull(id)) {
			dynamicSql.append(" AND mli.id = ? ");
			sqlMap.addParam(id);
		}
		String friendsCircleId = inMap.getString("friends_circle_id");
		if (StrUtils.isNotNull(friendsCircleId)) {
			dynamicSql.append(" AND mli.friends_circle_id = ? ");
			sqlMap.addParam(friendsCircleId);
		}
		String fromUid = inMap.getString("from_uid");
		if (StrUtils.isNotNull(fromUid)) {
			dynamicSql.append(" AND mli.from_uid = ? ");
			sqlMap.addParam(fromUid);
		}
		String status = inMap.getString("status");
		if (StrUtils.isNotNull(status)) {
			dynamicSql.append(" AND mli.status = ? ");
			sqlMap.addParam(status);
		}
		String isvalid = inMap.getString("isvalid");
		if (StrUtils.isNotNull(isvalid)) {
			dynamicSql.append(" AND mli.isvalid = ? ");
			sqlMap.addParam(isvalid);
		}else{
			dynamicSql.append(" AND mli.isvalid = ? ");
			sqlMap.addParam(MebLikesSTATUS.STATUS_YES.value);
		}
		sqlMap.setPlaceHolder(DYNAMIC_SQL, dynamicSql.toString());
		
		inMap.put("is_page", MebConsts.NOT_PAGE);
		if (inMap.getInt("is_page") == MebConsts.NOT_PAGE) {
			return query(sqlMap);
		}
		sqlMap.put("pageIndex", inMap.getInt(MebConsts.STR_PAGE_INDEX, MebConsts.PAGE_INDEX));
		sqlMap.put("pageSize", inMap.getInt(MebConsts.STR_PAGE_SIZE, MebConsts.PAGE_SIZE));
		ParaMap outMap = queryForPage(sqlMap);
		return outMap;
	}
}
