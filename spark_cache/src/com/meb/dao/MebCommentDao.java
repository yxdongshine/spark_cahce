package com.meb.dao;

import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.dao.SimpleDataSetDao;

public class MebCommentDao extends SimpleDataSetDao{
	
	private static final String DYNAMIC_SQL = "$dynamicsql"; 
	private final String MEB_COMMENT_INFO = "meb_comment";
	private final String MEB_COMMENT_INFO_SQL = "sql/meb_comment";
	
	/**
	 * 评论信息
	 * @param data
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap addMebCommentInfo(ParaMap data)throws Exception{
		return insert(MEB_COMMENT_INFO, data);
	}
	
	/**
	 * 更新评论信息
	 * @param updateData
	 * @param conditions
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap updateMebCommentInfo(ParaMap updateData, ParaMap conditions)throws Exception{
		return update(MEB_COMMENT_INFO, conditions, updateData);
	}
	
	/**
	 * 获取会员评论列表
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getMebCommentList(ParaMap inMap) throws Exception{
		SQLMap sqlMap=new SQLMap();
		sqlMap.setSQL(MEB_COMMENT_INFO_SQL, "getMebCommentList");

		StringBuffer dynamicSql = new StringBuffer();
		String friendsCircleId = inMap.getString("friends_circle_id");
		if (StrUtils.isNotNull(friendsCircleId)) {
			dynamicSql.append(" AND mc.friends_circle_id = ? ");
			sqlMap.addParam(friendsCircleId);
		}
		sqlMap.setPlaceHolder(DYNAMIC_SQL, dynamicSql.toString());
		ParaMap outMap = query(sqlMap);
		return outMap;
	}
}
