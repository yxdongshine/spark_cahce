package com.meb.consts;

public class BeanInfoConsts {

	public enum MebInfo_TYPE{
		MEB_TYPE_PER(10, "普通会员"),
		MEB_TYPE_ENT(20, "企业会员");
		
		public int value;
		public String desc;
		MebInfo_TYPE(int value, String desc){
			this.value = value;
			this.desc = desc;
		}
	}
	
	public enum MebInfo_RULE{
		
		RULE_TYPE_NORMAL(1, "正常"),
		RULE_TYPE_BLACKLIST(2, "黑名单"),
		RULE_TYPE_WHITELIST(3, "白名单");
		
		public int value;
		public String desc;
		MebInfo_RULE(int value, String desc){
			this.value = value;
			this.desc = desc;
		}
	}
	
	public enum MebInfo{
		STATUS_NORMAL(1, "启用"),
		STATUS_CANCELING(2, "注销中"),
		STATUS_CANCEL(3, "已注销"),
		STATUS_DISABLE(4, "停用");
		
		public int value;
		public String desc;
		MebInfo(int value, String desc){
			this.value = value;
			this.desc = desc;
		}
	}
	
	public enum MebAccountSTATUS{
		STATUS_YES(1,"可用"),
		STATUS_NO(0,"不可用");
		
		public int value;
		public String desc;
		MebAccountSTATUS(int value, String desc){
			this.value = value;
			this.desc = desc;
		}
	}
	
	
	public enum MebFollowSTATUS{
		STATUS_YES(1,"已关注"),
		STATUS_NO(0,"已取消关注");
		
		public int value;
		public String desc;
		MebFollowSTATUS(int value, String desc){
			this.value = value;
			this.desc = desc;
		}
	}
}
