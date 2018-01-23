package com.meb.internal;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.common.consts.GlobalConsts;
import com.common.util.RespUtils;
import com.meb.consts.BeanInfoConsts.MebFollowSTATUS;
import com.meb.consts.MebConsts.UpdateMebFollowInfo;
import com.meb.dao.MebCommentDao;
import com.meb.dao.MebFollowDao;
import com.meb.util.DataSetUtil;

public class MebFollowInternal {

	private MebFollowDao mfDao = new MebFollowDao();
	
	/**
	 * 关注
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap addMebFollowInfo(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		//添加主键
		inMap.put("id", IDGenerator.newGUID());
		inMap.put("status", MebFollowSTATUS.STATUS_YES.value);
		//插入数据库
		outMap = mfDao.addMebFollowInfo(inMap);
		return outMap;
	}
	
	/**
	 * 更新关注信息
	 * @param inMap,conditions
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap updateMebFollowInfo(ParaMap inMap,ParaMap conditions) throws Exception{
		ParaMap outMap = new ParaMap();
		//更新数据库
		ParaMap updateOutMap = mfDao.updateMebFollowInfo(inMap,conditions);
		if(updateOutMap.getInt("num") == 0){
			outMap = RespUtils.resFail(UpdateMebFollowInfo.ERR_UPDATE_MEB_FOLLOW_INFO.code, UpdateMebFollowInfo.ERR_UPDATE_MEB_FOLLOW_INFO.mes);
		}else{
			outMap = RespUtils.resSuccess(UpdateMebFollowInfo.SUCC_UPDATE_MEB_FOLLOW_INFO.code, UpdateMebFollowInfo.SUCC_UPDATE_MEB_FOLLOW_INFO.mes);
		}
		return outMap;
	}
	
	/**
	 * 获取会员关注列表
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getMebFollowList(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		outMap = mfDao.getMebFollowList(inMap);
		return outMap;
	}
}
