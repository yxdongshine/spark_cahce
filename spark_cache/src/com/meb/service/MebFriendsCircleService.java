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
import com.meb.consts.BeanInfoConsts.MebLikesSTATUS;
import com.meb.consts.MebConsts.AddMebCommentInfo;
import com.meb.consts.MebConsts.AddMebLikesInfo;
import com.meb.consts.MebConsts.GetMebFriendsCircleList;
import com.meb.consts.MebConsts.MebPublishFriendsCircle;
import com.meb.consts.MebConsts.UpdateMebLikesInfo;
import com.meb.internal.MebCommentInternal;
import com.meb.internal.MebFriendsCircleInternal;


@ServiceDesc("会员朋友圈操作类")
@Author("杨晓冬")
@Version("1.0")
@UpdateTime("2018-01-22")
public class MebFriendsCircleService extends BaseService {
	
	private MebFriendsCircleInternal mfcInternal = new MebFriendsCircleInternal();
	private MebCommentInternal mcInternal = new MebCommentInternal();

	/**
	 * 会员发布朋友圈信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author yxd
	 */
	@SuppressWarnings("unchecked")
	public ParaMap mebPublishFriendsCircle(ParaMap inMap) throws Exception {ParaMap outMap = new ParaMap();
		//验证输入参数
		if (StrUtils.isNull(inMap.getString("uid"))) {
			return RespUtils.resFail(MebPublishFriendsCircle.ERR_UID_NULL.code, MebPublishFriendsCircle.ERR_UID_NULL.mes);
		}
		if (StrUtils.isNull(inMap.getString("words"))) {
			return RespUtils.resFail(MebPublishFriendsCircle.ERR_WORDS_NULL.code, MebPublishFriendsCircle.ERR_WORDS_NULL.mes);
		}
		outMap = mfcInternal.mebPublishFriendsCircle(inMap);
		outMap.putAll(RespUtils.resSuccess(MebPublishFriendsCircle.SUCC_MEB_PUBLISH_FRIENDS_CIRCLE.code, MebPublishFriendsCircle.SUCC_MEB_PUBLISH_FRIENDS_CIRCLE.mes));
		outMap = PubUtils.ConvertJsonMap(outMap);
		return outMap;
	}
	
	/**
	 * 会员点赞信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author yxd
	 */
	@SuppressWarnings("unchecked")
	public ParaMap addMebLikesInfo(ParaMap inMap) throws Exception {ParaMap outMap = new ParaMap();
		//验证输入参数
		if (StrUtils.isNull(inMap.getString("friends_circle_id"))) {
			return RespUtils.resFail(AddMebLikesInfo.ERR_FRIENDS_CIRCLE_ID_NULL.code, AddMebLikesInfo.ERR_FRIENDS_CIRCLE_ID_NULL.mes);
		}
		if (StrUtils.isNull(inMap.getString("from_uid"))) {
			return RespUtils.resFail(AddMebLikesInfo.ERR_FROM_UID_NULL.code, AddMebLikesInfo.ERR_FROM_UID_NULL.mes);
		}
		if (StrUtils.isNull(inMap.getString("status"))) {
			return RespUtils.resFail(AddMebLikesInfo.ERR_STATUS_NULL.code, AddMebLikesInfo.ERR_STATUS_NULL.mes);
		}
		//验证是否已经点赞
		if(mcInternal.hadMebLikesInfo(inMap)){
			return RespUtils.resFail(AddMebLikesInfo.ERR_HAD_LIKES_NULL.code, AddMebLikesInfo.ERR_HAD_LIKES_NULL.mes);
		}
		outMap = mcInternal.addMebLikesInfo(inMap);
		outMap.putAll(RespUtils.resSuccess(AddMebLikesInfo.SUCC_ADD_MEB_LIKES_INFO.code, AddMebLikesInfo.SUCC_ADD_MEB_LIKES_INFO.mes));
		outMap = PubUtils.ConvertJsonMap(outMap);
		return outMap;
	}
	
	/**
	 * 会员取消点赞信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author yxd
	 */
	@SuppressWarnings("unchecked")
	public ParaMap updateMebLikesInfo(ParaMap inMap) throws Exception {ParaMap outMap = new ParaMap();
		//验证输入参数
		String id = inMap.getString("id");
		if (StrUtils.isNull(id)) {
			return RespUtils.resFail(UpdateMebLikesInfo.ERR_ID_NULL.code, UpdateMebLikesInfo.ERR_ID_NULL.mes);
		}
		if (StrUtils.isNull(inMap.getString("status"))) {
			return RespUtils.resFail(UpdateMebLikesInfo.ERR_STATUS_NULL.code, UpdateMebLikesInfo.ERR_STATUS_NULL.mes);
		}
		//验证是否已经点赞
		if(!mcInternal.hadMebLikesInfo(inMap)){
			return RespUtils.resFail(UpdateMebLikesInfo.ERR_NO_LIKES.code, UpdateMebLikesInfo.ERR_NO_LIKES.mes);
		}
		
		ParaMap conditions = new ParaMap();
		conditions.put("id", id);
		ParaMap updateMap = new ParaMap();
		updateMap.put("status", MebLikesSTATUS.STATUS_NO.value);
		outMap = mcInternal.updateMebFollowInfo(updateMap, conditions);
		outMap.putAll(RespUtils.resSuccess(UpdateMebLikesInfo.SUCC_UPDATE_MEB_LIKES_INFO.code, UpdateMebLikesInfo.SUCC_UPDATE_MEB_LIKES_INFO.mes));
		outMap = PubUtils.ConvertJsonMap(outMap);
		return outMap;
	}
	
	/**
	 * 会员评论信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author yxd
	 */
	@SuppressWarnings("unchecked")
	public ParaMap addMebCommentInfo(ParaMap inMap) throws Exception {ParaMap outMap = new ParaMap();
		//验证输入参数
		if (StrUtils.isNull(inMap.getString("friends_circle_id"))) {
			return RespUtils.resFail(AddMebCommentInfo.ERR_FRIENDS_CIRCLE_ID_NULL.code, AddMebCommentInfo.ERR_FRIENDS_CIRCLE_ID_NULL.mes);
		}
		if (StrUtils.isNull(inMap.getString("to_uid"))) {
			return RespUtils.resFail(AddMebCommentInfo.ERR_TO_UID_NULL.code, AddMebCommentInfo.ERR_TO_UID_NULL.mes);
		}
		if (StrUtils.isNull(inMap.getString("from_uid"))) {
			return RespUtils.resFail(AddMebCommentInfo.ERR_FROM_UID_NULL.code, AddMebCommentInfo.ERR_FROM_UID_NULL.mes);
		}
		if (StrUtils.isNull(inMap.getString("content"))) {
			return RespUtils.resFail(AddMebCommentInfo.ERR_CONTENT_NULL.code, AddMebCommentInfo.ERR_CONTENT_NULL.mes);
		}
		if (StrUtils.isNull(inMap.getString("parent_id"))) {
			return RespUtils.resFail(AddMebCommentInfo.ERR_PARENT_ID_NULL.code, AddMebCommentInfo.ERR_PARENT_ID_NULL.mes);
		}
		
		outMap = mcInternal.addMebCommentInfo(inMap);
		outMap.putAll(RespUtils.resSuccess(AddMebCommentInfo.SUCC_ADD_MEB_COMMENT_INFO.code, AddMebCommentInfo.SUCC_ADD_MEB_COMMENT_INFO.mes));
		outMap = PubUtils.ConvertJsonMap(outMap);
		return outMap;
	}
	
	/**
	 * 获取会员朋友圈列表 对外开放接口
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	@SuppressWarnings("unchecked")
	public ParaMap getMebFriendsCircleList(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		outMap = mfcInternal.getMebFriendsCircleList(inMap);
		outMap.putAll(RespUtils.resSuccess(GetMebFriendsCircleList.SUCC_GET_MEB_FRIENDS_CIRCLE_LIST.code, GetMebFriendsCircleList.SUCC_GET_MEB_FRIENDS_CIRCLE_LIST.mes));
		outMap = PubUtils.ConvertJsonList(outMap);
		return outMap;
	}
}
