package test.com.meb.service;

import junit.framework.TestCase;

import com.base.ds.DataSourceManager;
import com.base.utils.ParaMap;
import com.meb.service.MebFollowService;

public class MebFollowTest extends TestCase{
	
	private MebFollowService mfService =  new MebFollowService();
	
	public MebFollowTest(String name) {
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
	
	public void testAddMebFollowInfo() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("f_uid", "20180118030100758617675254764375");
		inMap.put("cover_uid", "20180118032653666459603142393974");
		ParaMap outMap = mfService.addMebFollowInfo(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	public void testUpdateMebFollowInfo() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("f_uid", "20180118030100758617675254764375");
		inMap.put("cover_uid", "20180118032653666459603142393974");
		ParaMap outMap = mfService.updateMebFollowInfo(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	
	public void testGetMebFriendsCircleList() throws Exception{
		ParaMap inMap = new ParaMap();
		//inMap.put("f_uid", "20180118030100758617675254764375");
		//inMap.put("cover_uid", "20180118032653666459603142393974");
		//inMap.put("status", "0");
		//inMap.put("isvalid", "1");
		//inMap.put("id", "20180123104228794819742753660701");
		inMap.put("page_size", "10");
		inMap.put("page_index", "1");
		ParaMap outMap = mfService.getMebFollowList(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	
}
