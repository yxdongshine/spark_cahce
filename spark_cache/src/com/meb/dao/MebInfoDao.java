package com.meb.dao;

import com.base.utils.ParaMap;
import com.common.dao.SimpleDataSetDao;

public class MebInfoDao extends SimpleDataSetDao{
	private final String MEB_INFO = "meb_info";
	
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
	 * 根据条件获取基本会员信息
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getMebInfoList(ParaMap conditions, String orderBy, String returnFields)throws Exception{
		return querySimple(MEB_INFO, conditions, orderBy, returnFields);
	}
	
	/**
	 * 根据条件获取基本会员信息
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getMebInfoList(ParaMap conditions, String returnFields)throws Exception{
		return querySimple(MEB_INFO, conditions, null, returnFields);
	}
}
