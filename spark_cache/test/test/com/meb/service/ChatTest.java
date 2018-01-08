package test.com.meb.service;

import com.base.ds.DataSourceManager;
import com.base.utils.ParaMap;
import com.meb.service.ChatService;

import junit.framework.TestCase;

public class ChatTest extends TestCase{
	
	private ChatService chatService =  new ChatService();
	
	public ChatTest(String name) {
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

	
	public void testGetMebInfo() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("uid", "20171227055013435318271168050208");
		ParaMap outMap = chatService.getToken(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
}
