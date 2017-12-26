package com.common.consts;



/**
 * 公共常量
 */
public final class GlobalConsts {

	/**
	 * 返回对象键值
	 */
	public enum RespKey {

		STATE("state"), MSG("message"), CODE("code"), DATA("data");

		public String value;

		RespKey(String value) {
			this.value = value;
		}
	}

	/**
	 * 接口返回State对应值
	 */
	public enum RespState {

		ERROR(0), SUCCESS(1);

		public int value;

		RespState(int value) {
			this.value = value;
		}
	}
	
	/**
	 * 接口返回State对应值
	 */
	public enum IsEnable {

		FALSE(0, "停用"), TRUE(1, "启用");

		public int value;
		public String desc;

		IsEnable(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}
		
		public static String getDesc(int value){
			String desc = null;
			IsEnable[] enums = IsEnable.values();
			for (IsEnable obj : enums) {
				if(obj.value == value){
					desc = obj.desc;
					break;
				}
			}
			return desc;
		}
		public static boolean checkValue(int value){
			boolean isCheck = false;
			IsEnable[] enums = IsEnable.values();
			for (IsEnable obj : enums) {
				if(obj.value == value){
					isCheck = true;
					break;
				}
			}
			return isCheck;
		}
	}
	
	
	public enum IsTrue {

		FALSE(0, false, "false"), TRUE(1, true, "true");

		public int value;
		public boolean val;
		public String desc;

		IsTrue(int value, boolean val, String desc) {
			this.value = value;
			this.val = val;
			this.desc = desc;
		}
		
		public static boolean getVal(int value){
			boolean val = false;
			IsTrue[] enums = IsTrue.values();
			for (IsTrue obj : enums) {
				if(obj.value == value){
					val = obj.val;
					break;
				}
			}
			return val;
		}
	}
	
	
	/**
	 * 授权对象类型
	 */
	public enum AuthType {

		USER(1, "账号"), ORG(2, "组织机构"), SYSTEM(3, "系统");

		public int value;
		public String desc;

		AuthType(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}
	}
	
	public enum OrgType {

		COMPANY(10, "公司"), DEPARTMENT(20, "部门"), OTHER(30, "其它");

		public int value;
		public String desc;

		OrgType(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}
	}
	
	public enum Sex {
		MALE(1, "男"),
		FEMALE(2, "女"),
		OTHER(3, "其他");

		public int value;
		public String desc;

		Sex(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}
		
		public static String getDesc(int value){
			String desc = null;
			Sex[] enums = Sex.values();
			for (Sex obj : enums) {
				if(obj.value == value){
					desc = obj.desc;
					break;
				}
			}
			return desc;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(RespState.ERROR.value);
		System.out.println(IsEnable.getDesc(0));
		
		System.out.println(IsTrue.getVal(1));
	}
}
