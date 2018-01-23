package com.meb.consts;

public class MebConsts {

	public static final int PAGE = 0;//0-分页
	public static final int NOT_PAGE = 1;//1-不分页
	public static final int PAGE_INDEX = 1;
	public static final int PAGE_SIZE = 10;
	public static final String STR_PAGE_INDEX = "page_index";
	public static final String STR_PAGE_SIZE = "page_size";
	public static final String STR_NULL = "null";

	public enum IsRegByTel{
		SUCC_NOT_REG(200100, "没有注册"),
		ERR_TYPE_CODE_NULL(200101, "登录类型编码不能为空"),
		ERR_SYSTEM_CODE_NULL(200102, "系统编码不能为空"),
		ERR_ACCOUNT_NULL(200103, "账号不能为空"),
		ERR_HAD_REG(200104, "已经注册"),
		ERR_TEL_REG(200105, "手机号码已注册"),
		ERR_WX_REG(200106, "微信已绑定");
		public int code;
		public String mes;
		IsRegByTel(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	
	public enum LoginByPwd{
		SUCC_LOGIN(200200, "登录成功"),
		ERR_TYPE_CODE_NULL(200201, "登录类型编码不能为空"),
		ERR_SYSTEM_ID_NULL(200202, "系统主键不能为空"),
		ERR_ACCOUNT_NULL(200203, "账号不能为空"),
		ERR_PASSWORD_NULL(200204, "密码不能为空"),
		ERR_SYSTEM_CODE_NULL(200205, "系统编码不能为空"),
		ERR_PASSWORD_NOT_SET(200206, "该账号没有设置密码"),
		ERR_PASSWORD(200207, "密码不正确"),
		ERR_UID_EXIT(200208, "账号不存在");
		public int code;
		public String mes;
		LoginByPwd(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum CheckTelImei{
		SUCC_SAME_IMEI(200300, "imei编码相同"),
		ERR_UID_NULL(200301, "会员uid编号不能为空"),
		ERR_IMEI_NULL(200302, "imei编码不能为空"),
		ERR_NOT_SAME_IMEI(200303, "imei编码不相同"),
		ERR_NOT_FOUND_IMEI(200304, "没有找到imei编码"),
		ERR_NOT_FOUND_UID_LOG_INFO(200305, "没有找到该会员登录日志");
		public int code;
		public String mes;
		CheckTelImei(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum LoginByOpenid{
		SUCC_WX_LOGIN(200400, "登录成功"),
		ERR_SYSTEM_CODE_NULL(200401, "系统编码不能为空"),
		ERR_OPEN_ID_NULL(200402, "微信openid不能为空"),
		ERR_ACCESS_TOKEN_NULL(200403, "微信登录的accessToken不能为空"),
		ERR_WX_NO_LOGIN(200404, "微信没有登录"),
		ERR_OPEN_ID_EXIT(200405, "微信openid不存在"),
		ERR_EXCEPTION(200405, "异常");
		public int code;
		public String mes;
		LoginByOpenid(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum GetOpenidByTel{
		SUCC_NOT_BAND_OPEN_ID(200500, "没有绑定微信号"),
		ERR_TEL_NULL(200501, "手机号码不能为空"),
		ERR_OPEN_ID_NULL(200502, "微信openid不能为空"),
		ERR_ACCESS_TOKEN_NULL(200503, "微信登录的accessToken不能为空"),
		ERR_WX_NO_LOGIN(200504, "微信没有登录"),
		ERR_OPEN_ID_EXIT(200505, "微信openid不存在"),
		ERR_BAND_OPEN_ID(200506, "已经绑定了其他微信"),
		ERR_SAME_OPEN_ID(200507, "微信openid相同，直接登录");
		public int code;
		public String mes;
		GetOpenidByTel(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	
	public enum AddBindTel{
		SUCC_BAND_Tel(200600, "绑定成功"),
		ERR_SYSTEM_CODE_NULL(200601, "系统编码不能为空"),
		ERR_VERIFY_CODE_NULL(200602, "短信验证码不能为空"),
		ERR_TYPE_CODE_NULL(200603, "登录类型编码不能为空"),
		ERR_TEL_NULL(200604, "手机号码不能为空"),
		ERR_OPEN_ID_NULL(200605, "微信openid不能为空"),
		ERR_FUNCTION_CODE_NULL(200606, "功能模块代码不能为空"),
		ERR_ACCOUNT_EXIT(200607, "该账号不存在"),
		ERR_SAME_OPEN_ID(200608, "微信openid相同，直接登录"),
		ERR_OPEN_ID_EXITS(200609, "已经存在微信账号不能再添加"),
		ERR_UPDATE_WX(200610, "该微信openid不存在");
		public int code;
		public String mes;
		AddBindTel(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	
	public enum RegMeb{
		SUCCESS(100500, "注册成功"),
		ERR_ACCOUNT_NULL(100501, "账号为空"),
		ERR_SYS_NULL(100502, "系统编号为空"),
		ERR_TYPE_NULL(100503, "登录账户类型为空"),
		ERR_PASS_NULL(100504, "登录账户密码为空"),
		ERR_THIRD_NULL(100505, "第三方id为空"),
		ERR_OS_MAX(100505, "手机操作系统超过20个字符"),
		ERR_OV_MAX(100506, "操作系统版本超过20个字符"),
		ERR_CV_MAX(100508, "渠道版本超过20个字符"),
		ERR_PT_MAX(100509, "手机型号超过32个字符"),
		ERR_IC_MAX(100510, "移动设备身份码超过64个字符"),
		ERR_LO_TYPE(100511, "经度必须为数字类型"),
		ERR_LA_TYPE(100512, "纬度必须为数字类型"),
		ERR_IP_MAX(100513, "IP超过64个字符"),
		ERR_DN_MAX(100514, "设备名称超过32个字符"),
		ERR_SYS_NOTEXIT(100515, "系统不存在"),
		ERR_OS_TYPE(100516, "operate_system必须为数字1或2"),
		ERR_LOGIN_CHANNEL(100517, "login_channel必须为数字1或2或3");
		
		public int code;
		public String mes;
		RegMeb(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum LoginByMess{
		SUCC_LOGIN(200700, "登录成功"),
		ERR_TYPE_CODE_NULL(200701, "登录类型编码不能为空"),
		ERR_SYSTEM_ID_NULL(200702, "系统主键不能为空"),
		ERR_ACCOUNT_NULL(200703, "账号不能为空"),
		ERR_VERIFY_CODE_NULL(200704, "短信验证不能为空"),
		ERR_SYSTEM_CODE_NULL(200705, "系统编码不能为空"),
		ERR_FUNCTION_CODE_NULL(200706, "功能模块代码不能为空"),
		ERR_UID_EXIT(200707, "账号不存在");
		public int code;
		public String mes;
		LoginByMess(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum UpdatePwdByMess{
		SUCC_UPDATE_PASS(200800, "修改密码成功"),
		ERR_TYPE_CODE_NULL(200801, "登录类型编码不能为空"),
		ERR_NEW_PWD_NULL(200802, "新密码不符合规定"),
		ERR_TEL_NULL(200803, "手机号不能为空"),
		ERR_VERIFY_CODE_NULL(200804, "短信验证不能为空"),
		ERR_SYSTEM_CODE_NULL(200805, "系统编码不能为空"),
		ERR_FUNCTION_CODE_NULL(200806, "功能模块代码不能为空"),
		ERR_UID_EXIT(200807, "账号不存在"),
		ERR_FAIL(200807, "修改密码失败");
		public int code;
		public String mes;
		UpdatePwdByMess(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum GetPwd{
		SUCC_GET_PASS(200900, "已经设置密码"),
		ERR_SYSTEM_CODE_NULL(200901, "系统编码不能为空"),
		ERR_TEL_NULL(200902, "手机号码不能为空"),
		ERR_OLD_PWD_NULL(200903, "原来的密码不能为空"),
		ERR_UID_NOT_NULL(200904, "该账号不存在"),
		ERR_NOT_GET_PASS(200905, "未设置密码");
		public int code;
		public String mes;
		GetPwd(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum VerifyPwd{
		SUCC_CORRECT_PASS(201000, "密码正确"),
		ERR_TYPE_CODE_NULL(201001, "登录类型编码不能为空"),
		ERR_UID_NULL(201002, "会员编号不能为空"),
		ERR_OLD_PWD_NULL(201003, "原来的密码不能为空"),
		ERR_PWD_MANY(201004, "密码存在多个"),
		ERR_NO_SET_PWD(201005, "未设置密码"),
		ERR_UID_NOT_NULL(201006, "该账号不存在"),
		ERR_PASS(201007, "密码错误");
		public int code;
		public String mes;
		VerifyPwd(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum UpdatePwdConfirm{
		SUCC_UPDATE_PASS(201100, "修改密码成功"),
		ERR_TYPE_CODE_NULL(201101, "登录类型编码不能为空"),
		ERR_UID_NULL(201102, "会员编号不能为空"),
		ERR_OLD_PWD_NULL(201103, "原来的密码不能为空"),
		ERR_NEW_PWD_NULL(201104, "新密码不能为空"),
		ERR_CONFIRM_PWD_NULL(201105, "确认密码不能为空"),
		ERR_TWICE_PWD_NOT_SAME(201106, "两次修改密码不相同"),
		ERR_OLD_PWD(201107, "原来的密码不正确"),
		ERR_MAIL_NULL(201107, "没有找到邮箱地址"),
		ERR_FAIL(201108, "修改密码失败");
		public int code;
		public String mes;
		UpdatePwdConfirm(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum GetMebInfo{
		ERR_UID_NULL(201201, "会员uid不能为空"),
		SUCC_GET_MEB_INFO(201100, "获取会员信息成功");
		public int code;
		public String mes;
		GetMebInfo(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum UnablePerMeb{
		SUCCESS(101000, "基本会员注销成功"),
		ERR_UID_NULL(101001, "会员uid不能为空"),
		ERR_CODE_MAX(101002, "code最大长度不能超过32个字符"),
		ERR_REASON_MAX(101003, "reason最大长度不能超过256个字符"),
		ERR_UID_CANCELED(101004, "该会员已被注销");
		
		public int code;
		public String mes;
		
		UnablePerMeb(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum SWITCHMEB{
		SUCCESS(101100, "状态修改成功"),
		ERR_UID_NULL(101101, "会员uid不能为空"),
		ERR_MEB_STATUS(101102, "该会员信息错误"),
		ERR(101103, "该会员状态修改错误");
		
		public int code;
		public String mes;
		
		SWITCHMEB(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	public enum AddMebAccountInfo{
		SUCC_ADD_MEB_ACCOUNT_INFO(201600, "添加会员账号信息成功！"),
		ERR_ADD_MEB_ACCOUNT_INFO(201601, "添加会员账号信息异常！"),
		ERR_ADD_MEB_ACCOUNT_EXTEND_INFO(201602, "添加会员账号信息异常！");
		public int code;
		public String mes;
		AddMebAccountInfo(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	
	public enum PublicCheck{
		ERR_MEB_INFO_STATUS_CANCLE(201701, "该会员已经注销！"),
		ERR_MEB_INFO_STATUS_DISABLE(201702, "该会员已经停用！"),
		ERR_MEB_ACCOUNT_NO(201703, "该会员账号不可用！");
		public int code;
		public String mes;
		PublicCheck(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	
	public enum LoginByAlipayid{
		SUCC_ZFB_LOGIN(201800, "登录成功"),
		ERR_ALIPAY_ID_NULL(201801, "支付宝alipay_id不能为空"),
		ERR_ALIPAY_ID_EXIT(201802, "支付宝alipay_id不存在"),
		ERR_EXCEPTION(201803, "异常");
		public int code;
		public String mes;
		LoginByAlipayid(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum IdentityVerifyByTel{
		SUCC_IDENTITY_VERIFY(201900, "身份验证成功"),
		ERR_ACCOUNT_NULL(201901, "手机不能为空"),
		ERR_PASSWORD_NULL(201902, "密码不能为空"),
		ERR_FAIL_IDENTITY_VERIFY(201903, "身份验证失败");
		public int code;
		public String mes;
		IdentityVerifyByTel(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum ResetPwd{
		SUCC_RESET_PASS(201900, "重置密码成功"),
		ERR_TEL_NULL(201901, "手机号不能为空"),
		ERR_NEW_PWD_NULL(201902, "新密码不符合规定"),
		ERR_UID_EXIT(201903, "账号不存在"),
		ERR_FAIL(201904, "修改密码失败");
		public int code;
		public String mes;
		ResetPwd(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum UpdatePwdNoConfirm{
		SUCC_UPDATE_PASS(202000, "修改密码成功"),
		ERR_UID_NULL(202001, "会员编号不能为空"),
		ERR_OLD_PWD_NULL(202002, "原来的密码不能为空"),
		ERR_NEW_PWD_NULL(202003, "新密码不能为空"),
		ERR_OLD_PWD(202004, "原来的密码不正确"),
		ERR_FAIL(202005, "修改密码失败");
		public int code;
		public String mes;
		UpdatePwdNoConfirm(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum IsOpenidBind{
		SUCC_OPENID_BINDED(202000, "已经绑定"),
		ERR_OPEN_ID_NULL(202001, "微信openid不能为空"),
		ERR_OPENID_NOT_BIND(202002, "没有绑定");
		public int code;
		public String mes;
		IsOpenidBind(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum IsAlipayidBind{
		SUCC_ALIPAY_ID_BINDED(202100, "已经绑定"),
		ERR_ALIPAY_ID_NULL(202101, "支付宝alipay_id不能为空"),
		ERR_ALIPAY_ID_NOT_BIND(202102, "没有绑定");
		public int code;
		public String mes;
		IsAlipayidBind(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum IsTelBindOpenid{
		SUCC_TEL_BINDED_OPENID(202200, "已经绑定"),
		ERR_TEL_NULL(202201, "手机号码不能为空"),
		ERR_TEL_EXIT(202202, "手机号码不存在"),
		ERR_TEL_NOT_BIND_OPENID(202204, "没有绑定微信openid");
		public int code;
		public String mes;
		IsTelBindOpenid(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum IsTelBindAlipayid{
		SUCC_TEL_BINDED_ALIPAY_ID(202300, "已经绑定"),
		ERR_TEL_NULL(202301, "手机号码不能为空"),
		ERR_TEL_NOT_BIND_ALIPAY_ID(202302, "没有绑定支付宝alipay_id");
		public int code;
		public String mes;
		IsTelBindAlipayid(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum LoginByAccount{
		SUCC_LOGIN(202400, "登录成功"),
		ERR_TYPE_CODE_NULL(202401, "登录类型编码不能为空"),
		ERR_SYSTEM_ID_NULL(202402, "系统主键不能为空"),
		ERR_ACCOUNT_NULL(202403, "账号不能为空"),
		ERR_PASSWORD_NULL(202404, "密码不能为空"),
		ERR_SYSTEM_CODE_NULL(202405, "系统编码不能为空"),
		ERR_PASSWORD_NOT_SET(202406, "该账号没有设置密码"),
		ERR_PASSWORD(202407, "密码不正确"),
		ERR_UID_EXIT(202408, "账号不存在");
		public int code;
		public String mes;
		LoginByAccount(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum UpdateMebInfo{
		SUCC_UPDATE_MEB_INFO(202400, "编辑信息成功"),
		ERR_UID_NULL(202403, "UID不能为空"),
		FAIL_UPDATE_MEB_INFO(202408, "编辑信息失败");
		public int code;
		public String mes;
		UpdateMebInfo(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}

	public enum GetMebList{
		ERR_LONGITUDE_NULL(202403, "经度必须输入"),
		ERR_LATITUDE_NULL(202404, "维度必须输入"),
		SUCC_GET_MEB_LIST(201100, "获取会员列表成功");
		public int code;
		public String mes;
		GetMebList(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	
	public enum GetToken{
		SUCC_GET_TOKEN(202400, "获取token信息成功"),
		ERR_UID_NULL(202403, "UID不能为空"),
		FAIL_GET_TOKEN(202408, "获取token信息失败");
		public int code;
		public String mes;
		GetToken(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	
	public enum MebPublishFriendsCircle{
		ERR_UID_NULL(201201, "会员uid不能为空"),
		ERR_WORDS_NULL(201202, "文字内容不能为空"),
		SUCC_MEB_PUBLISH_FRIENDS_CIRCLE(201100, "会员发布朋友圈信息成功");
		public int code;
		public String mes;
		MebPublishFriendsCircle(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum AddMebCommentInfo{
		ERR_FRIENDS_CIRCLE_ID_NULL(201201, "朋友圈说说主键不能为空"),
		ERR_TO_UID_NULL(201202, "评论谁的uid不能为空"),
		ERR_FROM_UID_NULL(201203, "谁评论的uid不能为空"),
		ERR_CONTENT_NULL(201204, "评论内容不能为空"),
		ERR_PARENT_ID_NULL(201205, "上一级id不能为空"),
		SUCC_ADD_MEB_COMMENT_INFO(201100, "会员添加评论信息成功");
		public int code;
		public String mes;
		AddMebCommentInfo(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum GetMebFriendsCircleList{
		SUCC_GET_MEB_FRIENDS_CIRCLE_LIST(201900, "获取朋友圈列表成功");
		public int code;
		public String mes;
		GetMebFriendsCircleList(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum AddMebFollowInfo{
		ERR_F_UID_NULL(201501, "主动关注会员uid不能为空"),
		ERR_COVER_UID_NULL(201202, "被动关注会员uid不能为空"),
		SUCC_ADD_MEB_FOLLOW_INFO(201500, "关注会员成功");
		public int code;
		public String mes;
		AddMebFollowInfo(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	
	public enum UpdateMebFollowInfo{
		ERR_F_UID_NULL(201501, "主动关注会员uid不能为空"),
		ERR_COVER_UID_NULL(201202, "被动关注会员uid不能为空"),
		ERR_UPDATE_MEB_FOLLOW_INFO(201203, "取消关注会员失败"),
		SUCC_UPDATE_MEB_FOLLOW_INFO(201500, "取消关注会员成功");
		public int code;
		public String mes;
		UpdateMebFollowInfo(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
	
	public enum GetMebFollowList{
		SUCC_GET_MEB_FOLLOW_LIST(201600, "获取会员关注列表成功");
		public int code;
		public String mes;
		GetMebFollowList(int code, String mes){
			this.code = code;
			this.mes = mes;
		}
	}
}
