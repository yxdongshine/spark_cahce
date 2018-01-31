package com.meb.internal;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.common.util.RespUtils;
import com.meb.consts.BeanInfoConsts.ALLSTATUS;
import com.meb.consts.BeanInfoConsts.MebLikesSTATUS;
import com.meb.consts.MebConsts.UpdateMebFollowInfo;
import com.meb.dao.MebCommentDao;
import com.meb.dao.MebLikesDao;

public class MebCommentInternal {

	private MebCommentDao mcDao = new MebCommentDao();
	private MebLikesDao mlDao = new MebLikesDao();
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
	
	/**
	 * 是否已经点赞
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public Boolean hadMebLikesInfo(ParaMap inMap) throws Exception{
		Boolean hadMebLikes = false;
		ParaMap outMap = new ParaMap();
		inMap.put("isvalid", ALLSTATUS.STATUS_YES.value);
		inMap.put("status", MebLikesSTATUS.STATUS_YES.value);
		//插入数据库
		outMap = mlDao.getMebLikesList(inMap);
		if(null != outMap
				&& 0 < outMap.getRecordCount())
			hadMebLikes = true;
		return hadMebLikes ;
	}
	
	/**
	 * 会员点赞
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap addMebLikesInfo(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		//添加主键
		inMap.put("id", IDGenerator.newGUID());
		//插入数据库
		outMap = mlDao.addMebLikesInfo(inMap);
		return outMap;
	}
	
	/**
	 * 更新点赞信息
	 * @param inMap,conditions
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap updateMebFollowInfo(ParaMap inMap,ParaMap conditions) throws Exception{
		ParaMap outMap = new ParaMap();
		//更新数据库
		outMap = mlDao.updateMebLikesInfo(inMap, conditions);
		return outMap;
	}
}
