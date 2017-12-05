package com.yxd.netty;

public class UtilTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub 
		//System.out.println(System.getProperty("line.separator"));
		String str = "headerKey2value2-2�2017-12-04 09:53:20,939 R0171204095320939217960365154509 [com.portal.service.PortalService.access:31] DEBUG 【请求】：{";
		String[] splitArr = str.split("#@#");
		for (int i = 0; i < splitArr.length; i++) {
			System.out.println(splitArr[i]);
		}
		String str32 = splitArr[0].substring(splitArr[0].indexOf("2017"),splitArr[0].length());
		System.out.println(str32);
	}

}
