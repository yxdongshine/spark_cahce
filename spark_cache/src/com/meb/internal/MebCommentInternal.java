package com.meb.internal;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.meb.dao.MebCommentDao;

public class MebCommentInternal {

	private MebCommentDao mcDao = new MebCommentDao();
	
	/**
	 * 会员评论信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap addMebCommentInfo(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		//添加主键
		inMap.put("id", IDGenerator.newGUID());
		//插入数据库
		outMap = mcDao.addMebCommentInfo(inMap);
		return outMap;
	}
	
	
}
