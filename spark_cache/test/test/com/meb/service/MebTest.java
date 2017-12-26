package test.com.meb.service;


import junit.framework.TestCase;

import com.base.ds.DataSourceManager;
import com.base.utils.ParaMap;
import com.meb.service.MebService;

public class MebTest  extends TestCase{
	
	private MebService mebService =  new MebService();
	
	public MebTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		System.out.println("test begin...");
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		System.out.println("test end...");
	}
	
	/**
	 * http://127.0.0.1:8080/spark_cache/data?module=meb&service=Meb&method=regMeb&account=13628484078&password=123456&longitude=32.95&latitude=112.32&age=10&sex=1&height=180&want_sex=0&age_rang=18-20
	 * @throws Exception
	 * @author YXD
	 */
	public void testRegByTel() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("account", "13628484078");
		inMap.put("password", "123456");
		inMap.put("longitude", "32.95");
		inMap.put("latitude", "112.32");
		inMap.put("age", "10");
		inMap.put("sex", "1");
		inMap.put("height", "180");
		inMap.put("want_sex", "0");
		inMap.put("age_rang", "18-20");
		ParaMap outMap = mebService.regMeb(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	/**
	 * http://127.0.0.1:8080/spark_cache/data?module=meb&service=Meb&method=loginByAccount&account=13628484078&password=123456&longitude=32.95&latitude=112.32
	 * 登录
	 * @throws Exception
	 * @author YXD
	 */
	public void testLoginByAccount() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("account", "13628484078");
		inMap.put("password", "123456");
		ParaMap outMap = mebService.loginByAccount(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
}
