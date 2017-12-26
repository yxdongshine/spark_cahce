package com.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.base.dao.SQLMap;
import com.base.security.ApiKeyBean;
import com.base.security.ApiUtils;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
/**
 * 公共工具类
 * @author YXD
 *
 */
public class PubUtils {
	
	public static final String STR_PAGE_INDEX = "page_index";
	public static final String STR_PAGE_SIZE = "page_size";
	public static final int PAGE = 0;//0-分页
	public static final int NOT_PAGE = 1;//1-不分页
	public static final int PAGE_INDEX = 1;
	public static final int PAGE_SIZE = 10;
	
	
	/**
	 * 添加String 类型的fs
	 * @param inMap
	 * @param keyMap
	 * @author YXD
	 * @return
	 */
	public static ParaMap addFsMapForString(ParaMap inMap,ParaMap keyMap)
	{
		String name = keyMap.getString("name");
		int size = keyMap.getInt("size");
		ParaMap fsMap = getKeyMap(name,size);
		List<ParaMap> fsList = inMap.getList("fs");
		for(ParaMap fsNameMap : fsList)
		{
			if(StrUtils.equals(name, fsNameMap.getString("name")))
				return inMap;
		}
		fsList.add(fsMap);
		inMap.put("fs", fsList);
		List<List<Object>> rsList = inMap.getList("rs");
		for(List<Object> rs : rsList)
		{
			rs.add("");
		}
		return inMap;
	}
	
	/**
	 * 创建String 类型的fsMap
	 * @param name
	 * @param size
	 * @author YXD
	 * @return
	 */
	private static ParaMap getKeyMap(String name,int size)
	{
		ParaMap keyMap = new ParaMap();
		keyMap.put("name", name);
		keyMap.put("precision", 32);
		keyMap.put("scale", 0);
		keyMap.put("size", size);
		keyMap.put("type", 12);
		keyMap.put("typeName", "VARCHAR");
		return keyMap;
	}
	/**
	 * 列表的结构化方法
	 * @param inMap
	 * @return
	 * @author YXD
	 */
	public static ParaMap ConvertJsonList(ParaMap inMap)
	{
		List<ParaMap> dataJson = new ArrayList<ParaMap>();
		List<String> fields = new ArrayList<>(inMap.keySet());
		List<ParaMap> fsList = inMap.getList("fs");
		for(int i=0;i<inMap.getRecordCount();i++){
			ParaMap dataMap = new ParaMap();
			for(ParaMap fsNameMap : fsList)	{
				String fieldName = fsNameMap.getString("name");
				dataMap.put(fieldName, inMap.getRecordValue(i, fieldName));
			}
			dataJson.add(dataMap);
		}
		inMap.put("data", dataJson);
		if(inMap.getInteger("pageIndex") != null){
			inMap.put("page_count", inMap.get("pageCount"));
			inMap.put("page_index", inMap.get("pageIndex"));
			inMap.put("page_size", inMap.get("pageSize"));
		}
		inMap.put("total_count", inMap.get("totalCount"));
		inMap.remove("fs");
		inMap.remove("rs");
		inMap.remove("num");
		inMap.remove("status");
		inMap.remove("rowCount");
		inMap.remove("pageCount");
		inMap.remove("pageIndex");
		inMap.remove("pageSize");
		inMap.remove("totalCount");
		return inMap;
	}
	/**
	 * 列表的结构化方法
	 * @param inMap
	 * @return
	 * @author YXD
	 */
	public static ParaMap ConvertJsonMap(ParaMap inMap)
	{
		List<String> fields = new ArrayList<>(inMap.keySet());
		List<ParaMap> fsList = inMap.getList("fs");
		if(fsList == null)
			return inMap;
		ParaMap dataMap = new ParaMap();
		if(inMap.getRecordCount()>0){
			for(ParaMap fsNameMap : fsList)	{
				String fieldName = fsNameMap.getString("name");
				dataMap.put(fieldName, inMap.getRecordValue(0, fieldName));
			}
		}

		inMap.put("data", dataMap);
		inMap.put("total_count", inMap.get("totalCount"));
		inMap.remove("fs");
		inMap.remove("rs");
		inMap.remove("num");
		inMap.remove("status");
		inMap.remove("rowCount");
		inMap.remove("pageCount");
		inMap.remove("pageIndex");
		inMap.remove("pageSize");
		inMap.remove("totalCount");
		return inMap;
	}
	
	/**
	 * 列表的结构化方法 fs
	 * @param inMap
	 * @return
	 * @author YXD
	 * @throws Exception 
	 */
	public static ParaMap ConvertJsonTree(ParaMap inMap,String parent_id,String id,String sort) throws Exception
	{
		List<ParaMap> dataList = new ArrayList<ParaMap>();
		List<ParaMap> fsList = inMap.getList("fs");
		for(int i=0;i<inMap.getRecordCount();i++){
			ParaMap dataMap = new ParaMap();
			for(ParaMap fsNameMap : fsList)	{
				String fieldName = fsNameMap.getString("name");
				dataMap.put(fieldName, inMap.getRecordValue(i, fieldName));
			}
			dataList.add(dataMap);
		}
		inMap.put("data", buildListToTree(dataList,sort,parent_id,id));
		inMap.remove("fs");
		inMap.remove("rs");
		inMap.remove("num");
		inMap.remove("status");
		inMap.remove("rowCount");
		inMap.remove("pageCount");
		inMap.remove("pageIndex");
		inMap.remove("pageSize");
		inMap.remove("totalCount");
		return inMap;
	}
    public static List<ParaMap> buildListToTree(List<ParaMap> dirs,String sort,String p_id,String id) throws Exception {
    	listSort(dirs,sort);
    	List<ParaMap> roots = findRoots(dirs,p_id,id);
        return roots;
    }

    private static List<ParaMap> findRoots(List<ParaMap> allNodes,String p_id,String id) {
        List<ParaMap> results = new ArrayList<ParaMap>();
        List<ParaMap> nextNodes = new ArrayList<ParaMap>();
        nextNodes.addAll(allNodes);
        for (ParaMap node : allNodes) {
            boolean isRoot = true;
            for (ParaMap comparedOne : allNodes) {
            	String nodePid = node.getString(p_id);
            	String cid = comparedOne.getString(id);
                if (StrUtils.equals(cid, nodePid)) {
                    isRoot = false;
                    break;
                }
            }
            if (isRoot) {
                results.add(node);
                nextNodes.remove(node);
            }
        }
        List<ParaMap> outListMap = new ArrayList<ParaMap>();
        for(ParaMap dataMap : results)
        {
        	List<ParaMap> dataListMap = findChildren(dataMap,nextNodes,p_id,id);
           	if(dataListMap.size()>0)
           		dataMap.put("data", dataListMap);
        	outListMap.add(dataMap);
        }
        return outListMap;
    }

    private static List<ParaMap> findChildren(ParaMap root, List<ParaMap> allNodes,String p_id,String id) {
    	List<ParaMap> results = new ArrayList<ParaMap>();
    	List<ParaMap> nextNodes = new ArrayList<ParaMap>();
        nextNodes.addAll(allNodes);
        for (ParaMap comparedOne : nextNodes) {
        	String nodePid = comparedOne.getString(p_id);
        	String cid = root.getString(id);
            if (StrUtils.equals(cid, nodePid)) {
            	List<ParaMap> dataMap = findChildren(comparedOne,allNodes,p_id,id);
            	if(dataMap.size()>0)
            		comparedOne.put("data", dataMap);
            	results.add(comparedOne);
                allNodes.remove(comparedOne);
            }
        }
        return results;
    }
    public static void listSort(List<ParaMap> resultList,String sort) throws Exception{  
        // resultList是需要排序的list，其内放的是Map  
    	System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        // 返回的结果集  
        Collections.sort(resultList,new Comparator<ParaMap>(){  
        	public int compare(ParaMap o1,ParaMap o2){  
	        	//o1，o2是list中的Map，可以在其内取得值，按其排序，此例为升序，s1和s2是排序字段值  
	        	int s1 = o1.getInt(sort);  
	        	int s2 = o2.getInt(sort);  
		        if(s1>s2)
		        	return 1;  
		        else if(s1==s2)
		        	return 0; 
		        else
		        	return -1;
        	}  
        });  
	}
    
    public static void listSortDesc(List<ParaMap> resultList, String sort) throws Exception {
		// resultList是需要排序的list，其内放的是Map
		// 返回的结果集
		Collections.sort(resultList, new Comparator<ParaMap>() {
			public int compare(ParaMap o1, ParaMap o2) {
				String s1 = o1.getString(sort);
				String s2 = o2.getString(sort);
				int i = s1.compareTo(s2);
				if (i > 0){
					return -1;
				} else if (i == 0){
					return 0;
				} else {
					return 1;
				}
			}
		});
	}
    
    /**
     *  
     * @param list
     * @param childrenKey
     * @param sortKey
     * @param sortType 0-升序，1-降序
     * @throws Exception
     * @author YXD
     */
	@SuppressWarnings("unchecked")
	public static void sortTree(List<ParaMap> list, String childrenKey, String sortKey, int sortType) throws Exception {
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		Collections.sort(list, new Comparator<ParaMap>() {
			public int compare(ParaMap o1, ParaMap o2) {
				String s1 = o1.getString(sortKey);
				String s2 = o2.getString(sortKey);
				int i = s1.compareTo(s2);
				if(sortType == 0){
					if (i > 0){
						return 1;
					} else if (i == 0){
						return 0;
					} else {
						return -1;
					}
				} else {
					if (i > 0){
						return -1;
					} else if (i == 0){
						return 0;
					} else {
						return 1;
					}
				}
			}
		});
		for (ParaMap map : list) {
			if(map.containsKey(childrenKey))
				sortTree(map.getList(childrenKey), childrenKey, sortKey, sortType);
		}
	}
    
    /**
     * 装换page参数格式
     * @param sqlMap
     * @param inMap
     * @return
     * @author YXD
     */
    public static SQLMap transformPageFormat(SQLMap sqlMap,ParaMap inMap){
    	sqlMap.put("pageIndex", inMap.getInt(STR_PAGE_INDEX, PAGE_INDEX));
		sqlMap.put("pageSize", inMap.getInt(STR_PAGE_SIZE, PAGE_SIZE));
		return sqlMap;
    }
    
    /**
     * 将list转换成tree
     * @param list
     * @param primaryKey
     * @param parentKey
     * @param childrenKey
     * @return
     * @author OL
     */
	@SuppressWarnings("unchecked")
	public static List<ParaMap> convertListToTree(List<ParaMap> list, String primaryKey, String parentKey, String childrenKey){
		Set<String> ids = new HashSet<String>();
		for (ParaMap map1 : list) {
			String id = map1.getString(primaryKey);
			ids.add(id);
			List<ParaMap> children = map1.getList(childrenKey);
			if(children == null)
				children = new ArrayList<ParaMap>();
			for (ParaMap map2 : list) {
				if(id.equals(map2.getString(parentKey))){
					children.add(map2);
				}
			}
			map1.put(childrenKey, children);
		}
		
		// 移除非根节点的元素
		Iterator<ParaMap> it = list.iterator();
		while(it.hasNext()){
			ParaMap map = it.next();
			String parentId = map.getString(parentKey);
			if(ids.contains(parentId)) // parentId在id列表的则不是根节点
				it.remove();
		}
		
    	return list;
    }
	
	public static void main(String[] args) throws Exception{
		/*ResourceService resourceService = new ResourceService();
		ParaMap inMap = new ParaMap();	
		inMap.put("id", "20170629114634072528945938482253");
		ParaMap outMap = resourceService.getResource(inMap);
		System.out.println(ConvertJsonList(outMap));*/
	}
	
}