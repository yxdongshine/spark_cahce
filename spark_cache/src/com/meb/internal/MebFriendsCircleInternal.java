package com.meb.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.common.util.PubUtils;
import com.meb.dao.MebCommentDao;
import com.meb.dao.MebFriendsCircleDao;

public class MebFriendsCircleInternal {

	public static final String COMMENT_DATA_DES = "comment_data";
	public static final int COMMENT_DATA_SIZE = 50000;
	private MebFriendsCircleDao mfcDao =  new MebFriendsCircleDao();
	private MebCommentDao mcDao =  new MebCommentDao();

	/**
	 * 会员发布朋友圈信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap mebPublishFriendsCircle(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		//添加主键
		inMap.put("id", IDGenerator.newGUID());
		//插入数据库
		outMap = mfcDao.addMebFriendsCircleInfo(inMap);
		return outMap;
	}
	
	/**
	 * * 获取会员朋友圈列表
	 * 评论参考：http://blog.csdn.net/u010098331/article/details/51447144
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	public ParaMap getMebFriendsCircleList(ParaMap inMap) throws Exception{
		ParaMap outMap = new ParaMap();
		outMap = mfcDao.getMebFriendsCircleList(inMap);
		outMap = addFiledMap(COMMENT_DATA_DES,COMMENT_DATA_SIZE,outMap);
		ParaMap commentInMap = new ParaMap();
		//每条朋友圈获取评论
		for (int i = 0; i < outMap.getRecordCount(); i++) {
			String friendsCircleId = outMap.getRecordString(i, "id");
			commentInMap.put("friends_circle_id", friendsCircleId);
			ParaMap commentOutMap = mcDao.getMebCommentList(commentInMap);
			ParaMap commentParaMap = PubUtils.ConvertJsonList(commentOutMap);
			outMap.setRecordValue(i, COMMENT_DATA_DES, commentParaMap.get("data"));
		}
		return outMap;
	}
	
	/**
	 * 
	 * @param name
	 * @param inMap
	 * @return
	 * @author YXD
	 */
	public static ParaMap addFiledMap(String name,int size,ParaMap inMap){    
		ParaMap keyMap = new ParaMap();
		keyMap.put("name", name);
		keyMap.put("size", size);
		return PubUtils.addFsMapForString(inMap, keyMap);		
	}
	
	
	/**
	 * 按照算法排序成树形
	 * @param inMap
	 * @return
	 * @throws Exception
	 * @author YXD
	 */
	@SuppressWarnings("unchecked")
	public ParaMap sortCommentToTree(ParaMap dataMap,String parentId) throws Exception{
		ParaMap dataParaMap = PubUtils.ConvertJsonList(dataMap);
		List<ParaMap> dataJson = (List<ParaMap>) dataParaMap.get("data");
		HashMap<String, List<ParaMap>> dataHm = new HashMap<String, List<ParaMap>>();
		for (int i = 0; i < dataJson.size(); i++) {
			ParaMap data = dataJson.get(i);
			String parentId2 = data.getString("parent_id");
			if(parentId2.equals(parentId)){
				if(dataHm.containsKey(parentId2)){
					dataHm.get(parentId2).add(data);
				}else{
					List<ParaMap> newListParaMap =  new ArrayList<ParaMap>();
					newListParaMap.add(data);
					dataHm.put(parentId2, newListParaMap);
				}
			}
		}
		return dataMap;
	}
}
