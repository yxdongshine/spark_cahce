package test.com.meb.service;

import junit.framework.TestCase;

import com.base.ds.DataSourceManager;
import com.base.utils.ParaMap;
import com.meb.service.MebFriendsCircleService;

public class MebFriendsCircleTest extends TestCase{
	
	private MebFriendsCircleService mfcService =  new MebFriendsCircleService();
	
	public MebFriendsCircleTest(String name) {
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
	
	public void testMebPublishFriendsCircle() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("uid", "20180118030100758617675254764375");
		inMap.put("words", "好烦，贱贱又要死了。。。");
		inMap.put("urls", "http://baihkjfd.com#@#http://baihkjfd.com");
		ParaMap outMap = mfcService.mebPublishFriendsCircle(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	public void testAddMebCommentInfo() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("friends_circle_id", "20180122143235607553127485952420");
		inMap.put("to_uid", "20180118030100758617675254764375");
		inMap.put("from_uid", "20180118032653666459603142393974");
		inMap.put("content", "快点死！！");
		inMap.put("type", "1");
		ParaMap outMap = mfcService.addMebCommentInfo(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	public void testAddMebCommentInfo1() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("friends_circle_id", "20180122143235607553127485952420");
		inMap.put("to_uid", "20180118032653666459603142393974");
		inMap.put("from_uid", "20180118030100758617675254764375");
		inMap.put("content", "立马就去死！！");
		inMap.put("type", "2");
		ParaMap outMap = mfcService.addMebCommentInfo(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	public void testAddMebCommentInfo11() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("friends_circle_id", "20180122143235607553127485952420");
		inMap.put("to_uid", "20180118030100758617675254764375");
		inMap.put("from_uid", "20180118030100758617675254764376");
		inMap.put("content", "叫别人去死，你自己先死撒！！！");
		inMap.put("type", "2");
		ParaMap outMap = mfcService.addMebCommentInfo(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	public void testAddMebCommentInfo12() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("friends_circle_id", "20180122143235607553127485952420");
		inMap.put("to_uid", "20180118030100758617675254764376");
		inMap.put("from_uid", "20180118030100758617675254764375");
		inMap.put("content", "关你屁事！！！");
		inMap.put("type", "1");
		ParaMap outMap = mfcService.addMebCommentInfo(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	public void testAddMebCommentInfo2() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("friends_circle_id", "20180122143235607553127485952420");
		inMap.put("to_uid", "20180118030100758617675254764375");
		inMap.put("from_uid", "20180118032653666459603142393977");
		inMap.put("content", "耶 还没死！！");
		inMap.put("type", "1");
		ParaMap outMap = mfcService.addMebCommentInfo(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	public void testGetMebFriendsCircleList() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("page_size", "10");
		inMap.put("page_index", "1");
		ParaMap outMap = mfcService.getMebFriendsCircleList(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	
}
