package com.meb.dao;

import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.common.dao.SimpleDataSetDao;

public class MebAccountDao extends SimpleDataSetDao{
	
	private static final String TABLE_NAME = "meb_account";
	private static final String TABLE_MEB_ACCOUNT_EXTEND_NAME = "meb_account_extend";
	private static final String DYNAMIC_SQL = "$dynamicsql"; 
	private static final String SQL_URL = "sql/meb_account";
	/**
	 * 判断该账户是不是已经被注册
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap isReg(String account) throws Exception{
		SQLMap sqlMap=new SQLMap();
		sqlMap.setSQL(SQL_URL, "isReg");
		sqlMap.addParam(account);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}
	
	/**
	 * 添加会员账号信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap addMebAccount(ParaMap inMap) throws Exception{
		ParaMap outMap = insert(TABLE_NAME, inMap);
		return outMap;
	}
	
	
	/**
	 * 根据账号密码登录
	 * @param account
	 * @param password
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap loginByAccount(String account,String password) throws Exception{
		SQLMap sqlMap=new SQLMap();
		sqlMap.setSQL(SQL_URL, "loginByAccount");
		sqlMap.addParam(account);
		sqlMap.addParam(password);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}
}
