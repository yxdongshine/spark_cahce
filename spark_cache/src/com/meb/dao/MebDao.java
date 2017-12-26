package com.meb.dao;

import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.dao.SimpleDataSetDao;
import com.meb.consts.BeanInfoConsts.MebInfo_TYPE;
import com.meb.consts.MebConsts;

public class MebDao extends SimpleDataSetDao{
	
	private static final String DYNAMIC_SQL = "$dynamicsql"; 
	private final String MEB_INFO_PER = "meb_info_per";
	private final String MEB_INFO_PER_SQL = "sql/meb_info_per";
	/**
	 * 注册个人会员时往个人会员表添加记录
	 * @param data
	 * @return
	 * @throws Exception
	 * @author lgr
	 */
	public ParaMap addPerMeb(ParaMap data)throws Exception{
		return insert(MEB_INFO_PER, data);
	}
	
	/**
	 * 获取用户信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getMebInfo(ParaMap inMap) throws Exception{
		SQLMap sqlMap=new SQLMap();
		sqlMap.setSQL(MEB_INFO_PER_SQL, "getMebInfo");

		StringBuffer dynamicSql = new StringBuffer();
		String uid = inMap.getString("uid");
		if (StrUtils.isNotNull(uid)) {
			dynamicSql.append(" AND mi.uid = ? ");
			sqlMap.addParam(uid);
		}
		
		String type = inMap.getString("type");
		if (StrUtils.isNotNull(type)) {
			dynamicSql.append(" AND mi.type = ? ");
			sqlMap.addParam(type);
		}
	
		sqlMap.setPlaceHolder(DYNAMIC_SQL, dynamicSql.toString());
		ParaMap outMap = query(sqlMap);
		return outMap;
	}
	
	
	/**
	 *根据条件查询个人会员基本用户列表
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author lgr
	 */
	public ParaMap getMebList(ParaMap inMap) throws Exception{
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL(MEB_INFO_PER_SQL ,"getMebListCount");
		
		StringBuffer dynamicSql = new StringBuffer();
		dynamicSql.append(" mi.isvalid = 1 ");
		dynamicSql.append(" and mi.type = ? ");
		sqlMap.addParam(MebInfo_TYPE.MEB_TYPE_PER.value);
		
		String uid = inMap.getString("uid");//主键
		if (StrUtils.isNotNull(uid)) {
			dynamicSql.append(" AND mip.uid = ? ");
			sqlMap.addParam(uid);
		}
		String tel = inMap.getString("tel");//手机
		if (StrUtils.isNotNull(tel)) {
			dynamicSql.append(" AND  mip.tel like '%" + tel.trim() + "%'");
		}
	
		String systemId = inMap.getString("system_id");//系统主键
		if (StrUtils.isNotNull(systemId)) {
			dynamicSql.append(" AND  mi.system_id = ? ");
			sqlMap.addParam(systemId );
		}
		String status = inMap.getString("status");//status
		if (StrUtils.isNotNull(status)) {
			dynamicSql.append(" AND  mi.status = ? ");
			sqlMap.addParam(status);
		}
		String regchaCode = inMap.getString("regcha_code");//注册渠道
		if (StrUtils.isNotNull(regchaCode)) {
			dynamicSql.append(" AND  reg.regcha_code = ? ");
			sqlMap.addParam(regchaCode);
		}
		Long start = inMap.getLong("start_time");//注册	开始时间
		Long end = inMap.getLong("end_time");//注册	结束时间
		if (start != null) {
			dynamicSql.append(" AND  reg.reg_time >= ? ");
			sqlMap.addParam(start);
		}
		if(end != null){
			dynamicSql.append(" AND  reg.reg_time <= ? ");
			sqlMap.addParam(end);
		}
		
		sqlMap.setPlaceHolder(DYNAMIC_SQL, dynamicSql.toString());
		ParaMap count = query(sqlMap);
		
		int total = count.getRecordInt(0, 0);
		int pageIndex = inMap.getInt(MebConsts.STR_PAGE_INDEX, MebConsts.PAGE_INDEX);
		int pageSize = inMap.getInt(MebConsts.STR_PAGE_SIZE, MebConsts.PAGE_SIZE);
		return attachData(sqlMap, total, MEB_INFO_PER_SQL, "getMebList", pageSize, pageIndex);
	}
	
	
	public ParaMap updateMebInfo(ParaMap updateData, ParaMap conditions)throws Exception{
		return update(MEB_INFO_PER, conditions, updateData);
	}
}
