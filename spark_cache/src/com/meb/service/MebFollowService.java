package com.meb.service;

import com.base.annotation.Author;
import com.base.annotation.ServiceDesc;
import com.base.annotation.UpdateTime;
import com.base.annotation.Version;
import com.base.service.BaseService;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.util.PubUtils;
import com.common.util.RespUtils;
import com.meb.consts.BeanInfoConsts.MebFollowSTATUS;
import com.meb.consts.MebConsts.AddMebCommentInfo;
import com.meb.consts.MebConsts.AddMebFollowInfo;
import com.meb.consts.MebConsts.GetMebFollowList;
import com.meb.consts.MebConsts.GetMebFriendsCircleList;
import com.meb.consts.MebConsts.GetMebList;
import com.meb.consts.MebConsts.MebPublishFriendsCircle;
import com.meb.consts.MebConsts.UpdateMebFollowInfo;
import com.meb.internal.MebCommentInternal;
import com.meb.internal.MebFollowInternal;
import com.meb.internal.MebFriendsCircleInternal;


@ServiceDesc("会员朋友圈操作类")
@Author("杨晓冬")
@Version("1.0")
@UpdateTime("2018-01-23")
public class MebFollowService extends BaseService {
	
	private MebFollowInternal mfInternal = new MebFollowInternal();

	/**
	 * 关注
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author yxd
	 */
	@SuppressWarnings("unchecked")
	public ParaMap addMebFollowInfo(ParaMap inMap) throws Exception {ParaMap outMap = new ParaMap();
		//验证输入参数
		if (StrUtils.isNull(inMap.getString("f_uid"))) {
			return RespUtils.resFail(AddMebFollowInfo.ERR_F_UID_NULL.code, AddMebFollowInfo.ERR_F_UID_NULL.mes);
		}
		if (StrUtils.isNull(inMap.getString("cover_uid"))) {
			return RespUtils.resFail(AddMebFollowInfo.ERR_COVER_UID_NULL.code, AddMebFollowInfo.ERR_COVER_UID_NULL.mes);
		}
		outMap = mfInternal.addMebFollowInfo(inMap);
		outMap.putAll(RespUtils.resSuccess(AddMebFollowInfo.SUCC_ADD_MEB_FOLLOW_INFO.code, AddMebFollowInfo.SUCC_ADD_MEB_FOLLOW_INFO.mes));
		outMap = PubUtils.ConvertJsonMap(outMap);
		return outMap;
	}
	
	
	/**
	 * 更新关注信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap updateMebFollowInfo(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		String fUid = inMap.getString("f_uid");
		//验证输入参数
		if (StrUtils.isNull(fUid)) {
			return RespUtils.resFail(UpdateMebFollowInfo.ERR_F_UID_NULL.code, UpdateMebFollowInfo.ERR_F_UID_NULL.mes);
		}
		String coverUid = inMap.getString("cover_uid");
		if (StrUtils.isNull(coverUid)) {
			return RespUtils.resFail(UpdateMebFollowInfo.ERR_COVER_UID_NULL.code, UpdateMebFollowInfo.ERR_COVER_UID_NULL.mes);
		}
			
		ParaMap conditions = new ParaMap();
		conditions.put("f_uid", fUid);
		conditions.put("cover_uid", coverUid);
		inMap.put("status", MebFollowSTATUS.STATUS_NO.value);
		outMap = mfInternal.updateMebFollowInfo(inMap,conditions);
		return outMap;
	}
	
	/**
	 * 获取会员关注列表 对外开放接口
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	@SuppressWarnings("unchecked")
	public ParaMap getMebFollowList(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		outMap = mfInternal.getMebFollowList(inMap);
		outMap.putAll(RespUtils.resSuccess(GetMebFollowList.SUCC_GET_MEB_FOLLOW_LIST.code, GetMebFollowList.SUCC_GET_MEB_FOLLOW_LIST.mes));
		outMap = PubUtils.ConvertJsonList(outMap);
		return outMap;
	}
}
