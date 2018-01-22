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
import com.meb.consts.MebConsts.AddMebCommentInfo;
import com.meb.consts.MebConsts.GetMebFriendsCircleList;
import com.meb.consts.MebConsts.GetMebList;
import com.meb.consts.MebConsts.MebPublishFriendsCircle;
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
