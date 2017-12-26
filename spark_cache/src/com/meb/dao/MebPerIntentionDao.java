package com.meb.dao;

import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.dao.SimpleDataSetDao;

public class MebPerIntentionDao extends SimpleDataSetDao{
	
	private static final String DYNAMIC_SQL = "$dynamicsql"; 
	private final String MEB_PER_INTENTION = "meb_per_intention";
	private final String MEB_PER_INTENTION_SQL = "sql/meb_per_intention";
	/**
	 * 注册个人会员时往个人会员意图表添加记录
	 * @param data
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap addPerIntention(ParaMap data)throws Exception{
		return insert(MEB_PER_INTENTION, data);
	}
	
	/**
	 * 跟新
	 * @param updateData
	 * @param conditions
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap updatePerIntention(ParaMap updateData, ParaMap conditions)throws Exception{
		return update(MEB_PER_INTENTION, conditions, updateData);
	}
	
	/**
	 * 会员意图表记录信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getPerIntentionInfo(ParaMap inMap) throws Exception{
		SQLMap sqlMap=new SQLMap();
		sqlMap.setSQL(MEB_PER_INTENTION, "getPerIntentionInfo");

		StringBuffer dynamicSql = new StringBuffer();
		String uid = inMap.getString("uid");
		if (StrUtils.isNotNull(uid)) {
			dynamicSql.append(" AND mpi.uid = ? ");
			sqlMap.addParam(uid);
		}
	
		sqlMap.setPlaceHolder(DYNAMIC_SQL, dynamicSql.toString());
		ParaMap outMap = query(sqlMap);
		return outMap;
	}	

}
