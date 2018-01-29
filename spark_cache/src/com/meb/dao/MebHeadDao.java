package com.meb.dao;

import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.dao.SimpleDataSetDao;

public class MebHeadDao extends SimpleDataSetDao{
	
	private static final String DYNAMIC_SQL = "$dynamicsql"; 
	private final String MEB_HEAD_INFO = "meb_head_info";
	private final String MEB_HEAD_INFO_SQL = "sql/meb_head_info";
	
	/**
	 * 会员表添加头像记录
	 * @param data
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap addMebHeadInfo(ParaMap data)throws Exception{
		return insert(MEB_HEAD_INFO, data);
	}
	
	/**
	 * 更新会员头像信息
	 * @param updateData
	 * @param conditions
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap updateMebHeadInfo(ParaMap updateData, ParaMap conditions)throws Exception{
		return update(MEB_HEAD_INFO, conditions, updateData);
	}
	
	/**
	 * 获取会员头像信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getMebHeadInfo(ParaMap inMap) throws Exception{
		SQLMap sqlMap=new SQLMap();
		sqlMap.setSQL(MEB_HEAD_INFO_SQL, "getMebHeadInfo");

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
}
