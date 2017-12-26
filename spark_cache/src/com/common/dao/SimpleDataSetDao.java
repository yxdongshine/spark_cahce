package com.common.dao;

import java.util.ArrayList;
import java.util.List;

import com.base.dao.AdvDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.CacheUtils;
import com.base.utils.DateUtils;
import com.base.utils.IDGenerator;
import com.base.utils.JsonUtils;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.util.PubUtils;

/**
 * 简单更新表记录类
 * @author samonhua
 *
 */
public class SimpleDataSetDao extends AdvDataSetDao {
	
	/**
	 * 简单表插入记录
	 * @param tableName 表名
	 * @param data 插入字段
	 * @return
	 * @throws Exception
	 */
	public ParaMap insert(String tableName, ParaMap data) throws Exception {
		return insert(tableName, null, data);
	}
	
	/**
	 * 简单表插入记录
	 * @param tableName 表名
	 * @param keyField 主键字段。单主键可以传空，多主键字段时可用分号分隔，也可无主键
	 * @param data 待更新字段
	 * @return
	 * @throws Exception
	 */
	public ParaMap insert(String tableName, String keyField, ParaMap data) throws Exception {
		//return this.save(tableName, keyField, null, data); //相当于旧的此方法
		if(!data.containsKey("createtime")){
			long nowTime = DateUtils.nowTime();
			data.put("createtime", nowTime);
			data.put("updatetime", nowTime);
		}
		data.put("isvalid", 1);
		ParaMap outMap = new ParaMap();
		outMap.put("status", 0);
		List<?> tableFields = getTableFields(tableName);
		if (tableFields == null || tableFields.size() == 0) {
			outMap.put("code", "table_not_exists");
			outMap.put("message", "表" + tableName + "不存在");
			return outMap;
		}
		List<String> keyFieldList = strToList(keyField);
		if(keyFieldList != null){
			for(int i = 0; i < keyFieldList.size(); i++) {
				if (tableFields.indexOf(keyFieldList.get(i)) == -1) {
					outMap.put("code", "key_field_not_exists");
					outMap.put("message", "主键字段" + keyFieldList.get(i) + "不存在");
					return outMap;
				}
			}
			if (keyFieldList.size() == 1) {
				String keyValue = data.containsKey(keyField) ? data.getString(keyField) : null;
				if (StrUtils.isNull(keyValue)) {
					keyValue = IDGenerator.newGUID();
					data.put(keyField, keyValue);
				}
			}
		}
		ParaMap validData = new ParaMap();
		List<String> fields = new ArrayList<>(data.keySet());
		for(String fieldName : fields) {
			if (tableFields.indexOf(fieldName) != -1)
				validData.put(fieldName, data.get(fieldName));
		}
		if (validData.size() == 0) {
			outMap.put("code", "field_invalid");
			outMap.put("message", "无有效更新字段");
			return outMap;
		}
		SQLMap sqlMap = new SQLMap();
		StringBuilder sqlLeft = new StringBuilder("insert into " + tableName + "(");
		StringBuilder sqlRight = new StringBuilder(" values(");
		fields = new ArrayList<>(validData.keySet());
		int i = 0;
		for(String fieldName : fields) {
			sqlLeft.append(fieldName + (i == fields.size() - 1 ? "" : ","));
			sqlRight.append(":" + fieldName + (i == fields.size() - 1 ? "" : ","));
			i++;
		}
		sqlLeft.append(")");
		sqlRight.append(")");
		sqlMap.setSQL(sqlLeft.toString() + sqlRight.toString());
		sqlMap.putAll(validData);
		outMap = execute(sqlMap);
		return outMap;
	}
	
	/**
	 * 简单更新表记录
	 * @param tableName 表名
	 * @param conditions 条件字段
	 * @param updateData 待更新字段
	 * @return
	 * @throws Exception
	 */
	public ParaMap update(String tableName, ParaMap conditions, ParaMap updateData) throws Exception {
		//List<String> keyFieldList = new ArrayList<>(keyFieldData.keySet());
		//String keyField = CommonUtils.listToStr(keyFieldList, ";");
		//return this.save(tableName, null, keyField, data); //相当于旧的此方法
		ParaMap outMap = new ParaMap();
		outMap.put("status", 0);
		List<?> tableFields = getTableFields(tableName);
		if (tableFields == null || tableFields.size() == 0) {
			outMap.put("code", "table_not_exists");
			outMap.put("message", "表" + tableName + "不存在");
			return outMap;
		}
		List<String> keyFieldList = new ArrayList<>(conditions.keySet());
		for(int i = 0; i < keyFieldList.size(); i++) {
			if (tableFields.indexOf(keyFieldList.get(i)) == -1) {
				outMap.put("code", "key_field_not_exists");
				outMap.put("message", "条件字段" + keyFieldList.get(i) + "不存在");
				return outMap;
			}
		}
		ParaMap validData = new ParaMap();
		List<String> fields = new ArrayList<>(updateData.keySet());
		for(String fieldName : fields) {
			if (tableFields.indexOf(fieldName) != -1)
				validData.put(fieldName, updateData.get(fieldName));
		}
		if (validData.size() == 0) {
			outMap.put("code", "field_invalid");
			outMap.put("message", "无有效更新字段");
			return outMap;
		}
		if (tableFields.contains("updatetime") && !validData.containsKey("updatetime"))
			validData.put("updatetime", DateUtils.nowTime());
		SQLMap sqlMap = new SQLMap();
		StringBuilder sql = new StringBuilder("update " + tableName + " set ");
		fields = new ArrayList<>(validData.keySet());
		for(String fieldName : fields) {
			/*String value = updateData.getString(fieldName);
			if(value.equals("(IS NULL)")){ // 如果值是此特殊字符串，数据库将更新为 null
				sql.append(fieldName + " = NULL,");
				continue;
			}*/
			sql.append(fieldName + " = :" + fieldName + ",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(" where ");
		fields = new ArrayList<>(conditions.keySet());
		int i = 0;
		ParaMap keyFieldMap = new ParaMap();
		for(String fieldName : fields) {
			Object value = conditions.get(fieldName);
			int inType = 0;
			String cFieldName = fieldName;
			if (value instanceof List) {
				inType = 1;
				if (fieldName.startsWith("!")) {
					cFieldName = fieldName.substring(1);
					inType = 2;
				}
			} else if (fieldName.startsWith("!")) {
				cFieldName = fieldName.substring(1);
				if (value == null)
					sql.append(cFieldName + " is not null ");
				else
					sql.append(cFieldName + " != :old_" + cFieldName + " ");
			} else if (fieldName.startsWith(">")) {
				cFieldName = fieldName.substring(1);
				sql.append(cFieldName + " > :old_" + cFieldName + " ");
			} else if (fieldName.startsWith(">=")) {
				cFieldName = fieldName.substring(2);
				sql.append(cFieldName + " >= :old_" + cFieldName + " ");
			} else if (fieldName.startsWith("<")) {
				cFieldName = fieldName.substring(1);
				sql.append(cFieldName + " < :old_" + cFieldName + " ");
			} else if (fieldName.startsWith("<=")) {
				cFieldName = fieldName.substring(2);
				sql.append(cFieldName + " <= :old_" + cFieldName + " ");
			} else {
				if (value == null)
					sql.append(cFieldName + " is null ");
				else
					sql.append(cFieldName + " = :old_" + cFieldName + " ");
			}
			if (inType > 0) {
				String inSql = getWhereValueList(listToStr((List) value, ";"), "old_" + cFieldName, sqlMap, cFieldName);
				inSql = inSql.replace(" and ", " ");
				if (inType == 2) {
					inSql = inSql.replace(" in (", " not in (");
				}
				sql.append(inSql);
			} else
				keyFieldMap.put("old_" + cFieldName, conditions.get(fieldName));
			if (i < fields.size() - 1)
				sql.append(" and ");
			i++;
		}
		
		
		sqlMap.setSQL(sql.toString());
		sqlMap.putAll(validData);
		sqlMap.putAll(keyFieldMap);
		return execute(sqlMap);
	}
	
	/**
	 * 简单更新表记录
	 * @param tableName 表名
	 * @param keyField 条件字段，值在data中。条件字段无法同步更新
	 * @param data 字段值
	 * @return
	 * @throws Exception
	 */
	public ParaMap update(String tableName, String conditions, ParaMap updateData) throws Exception {
		List<String> keyFieldList = strToList(conditions);
		ParaMap keyFieldData = new ParaMap();
		for(String fieldName : keyFieldList) {
			keyFieldData.put(fieldName, updateData.get(fieldName));
			updateData.remove(fieldName);
		}
		return update(tableName, keyFieldData, updateData);
	}
	
	/**
	 * 简单删除数据
	 * @param tableName 表名
	 * @param keyFieldData 条件字段
	 * @return
	 * @throws Exception
	 */
	public ParaMap delete(String tableName, ParaMap keyFieldData) throws Exception {
		SQLMap sqlMap = new SQLMap();
		StringBuilder sql = new StringBuilder("delete from " + tableName + " where ");
		List<String> fields = new ArrayList<>(keyFieldData.keySet());
		int i = 0;
		for(String fieldName : fields) {
			Object value = keyFieldData.get(fieldName);
			int inType = 0;
			String cFieldName = fieldName;
			if (value instanceof List) {
				inType = 1;
				if (fieldName.startsWith("!")) {
					cFieldName = fieldName.substring(1);
					inType = 2;
				}
			} else if (fieldName.startsWith("!")) {
				cFieldName = fieldName.substring(1);
				if (value == null)
					sql.append(cFieldName + " is not null ");
				else
					sql.append(cFieldName + " != :old_" + cFieldName + " ");
			} else if (fieldName.startsWith(">")) {
				cFieldName = fieldName.substring(1);
				sql.append(cFieldName + " > :old_" + cFieldName + " ");
			} else if (fieldName.startsWith(">=")) {
				cFieldName = fieldName.substring(2);
				sql.append(cFieldName + " >= :old_" + cFieldName + " ");
			} else if (fieldName.startsWith("<")) {
				cFieldName = fieldName.substring(1);
				sql.append(cFieldName + " < :old_" + cFieldName + " ");
			} else if (fieldName.startsWith("<=")) {
				cFieldName = fieldName.substring(2);
				sql.append(cFieldName + " <= :old_" + cFieldName + " ");
			} else {
				if (value == null)
					sql.append(cFieldName + " is null ");
				else
					sql.append(cFieldName + " = :old_" + cFieldName + " ");
			}
			if (inType > 0) {
				String inSql = getWhereValueList(listToStr((List) value, ";"), "old_" + cFieldName, sqlMap, cFieldName);
				inSql = inSql.replace(" and ", " ");
				if (inType == 2) {
					inSql = inSql.replace(" in (", " not in (");
				}
				sql.append(inSql);
			} else
				sqlMap.put("old_" + cFieldName, keyFieldData.get(fieldName));
			if (i < fields.size() - 1)
				sql.append(" and ");
			i++;
		}
		sql.deleteCharAt(sql.length() - 1);
		sqlMap.setSQL(sql.toString());
		return execute(sqlMap);
	}
	
	/**
	 * 将sql中的currenttime标签替换为含毫秒的当前时间戳，以替换原来的不含毫秒的【UNIX_TIMESTAMP() * 1000】
	 * @param sql
	 * @return
	 */
	public String replaceSqlCurrentTime(String sql) {
		/*
		SELECT REPLACE(UNIX_TIMESTAMP(CURRENT_TIMESTAMP(3)), '.', '') d1,
		  CONCAT(UNIX_TIMESTAMP(), SUBSTR(DATE_FORMAT(CURRENT_TIMESTAMP(6),'%f'), 1, 3)) d2,
		  CURRENT_TIMESTAMP(3) d3,
		  UNIX_TIMESTAMP() d4,
		  #currenttime# d5;
		*/
		String currentTime = "CAST(REPLACE(UNIX_TIMESTAMP(CURRENT_TIMESTAMP(3)), '.', '') AS SIGNED)";
		List<String> currentTimePlaceholders = new ArrayList<>();
		currentTimePlaceholders.add("#currenttime#");
		//currentTimePlaceholders.add("UNIX_TIMESTAMP() * 1000");
		//currentTimePlaceholders.add("UNIX_TIMESTAMP()*1000");
		if (StrUtils.isNotNull(sql)) {
			for(int i = 0; i < currentTimePlaceholders.size(); i++)
				sql = sql.replaceAll(currentTimePlaceholders.get(i), currentTime);
		}
		return sql;
	}
	
	private void replaceSqlCurrentTime(SQLMap sqlMap) {
		//本方法主要是替换sqlMap的sql中的当前日期，原来sql中写的【#currenttime#】，无毫秒数
		try {
			if (sqlMap != null && sqlMap.containsKey("SQL")) {
				sqlMap.setSQL(replaceSqlCurrentTime(sqlMap.getString("SQL")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ParaMap query(SQLMap sqlMap) throws Exception {
		replaceSqlCurrentTime(sqlMap);
		return super.query(sqlMap);
	}
	
	public ParaMap queryForPage(SQLMap sqlMap) throws Exception {
		replaceSqlCurrentTime(sqlMap);
		return super.queryForPage(sqlMap);
	}
	
	
	
	public ParaMap execute(SQLMap sqlMap) throws Exception {
		replaceSqlCurrentTime(sqlMap);
		return super.execute(sqlMap);
	}
	
	/**
	 * 获取表结构
	 * @param tableName 表名
	 * @param fromDB true强制从数据库获取，false则从缓存获取
	 * @return
	 * @throws Exception
	 */
	public List<String> getTableFields(String tableName, boolean fromDB) throws Exception {
		if (StrUtils.isNull(tableName))
			return null;
		List<String> result = null;
		String cacheKey = "__metadata_" + tableName + "__";
		if (!fromDB) {
			String cacheValue = CacheUtils.get(cacheKey);
			result = strToList(cacheValue);
		}
		if (result == null) {
			result = new ArrayList<>();
			SQLMap sqlMap = new SQLMap();
			sqlMap.setSQL("select * from " + tableName + " where 1 = 2");
			ParaMap metaData = query(sqlMap);
			List<?> fields = metaData.getFields();
			for(int i = 0; i < fields.size(); i++) {
				ParaMap field = (ParaMap) fields.get(i);
				result.add(field.getString("name"));
			}
			//表结构缓存1小时，如果修改新增或者删除字段需要重启服务或者清空缓存，或者强制从数据库获取（fromDB=true）覆盖一次缓存，否则需要1小时后生效
			CacheUtils.setTTL(cacheKey, listToStr(result, ";"), 60 * 60);
		}
		return result;
	}
	
	/**
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public List<String> getTableFields(String tableName) throws Exception {
		return getTableFields(tableName, false);
	}
	
	/**
	 * 字符串转换list
	 * @param o 待转换对象。支持：list对象、1,2,3、1;2;3、[1,2,3]
	 * @return
	 */
	public List<String> strToList(Object o) {
		List<String> list = null;
		if(o == null){
			return list;
		}
		if (o instanceof List){
			list = (List<String>) o;
		} else {
			String s = String.valueOf(o);
			if (StrUtils.isNull(s))
				return null;
			list = JsonUtils.strToList(s);
			if (list == null && StrUtils.isNotNull(s)) {
				if (s.startsWith("["))
					s = s.substring(1);
				if (s.endsWith("]"))
					s = s.substring(0, s.length() - 1);
				list = s.indexOf(";") != -1 ? StrUtils.getSubStrs(s, ";") : StrUtils.getSubStrs(s, ",");
			}
		}
		return list;
	}
	
	public String listToStr(List list, String split) {
		if (list == null || list.size() == 0)
			return null;
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < list.size(); i++) {
			if (i > 0)
				result.append(split);
			result.append(String.valueOf(list.get(i)));
		}
		return result.toString();
	}
	
	/**
	 * 获取sql查询list条件。返回格式如：:param0, :param1, :param2, ...。调用处需自行添加“ and field_name in (本方法返回字符串)”
	 * @param params 参数值字符串。可以是分号分隔的多个参数值
	 * @param paramName 参数名
	 * @param sqlMap 用于执行的sqlMap
	 * @return
	 */
	public String getWhereValueList(String params, String paramName, SQLMap sqlMap) {
		if (StrUtils.isNull(params) || StrUtils.isNull(paramName) || sqlMap == null)
			return null;
		List<String> paramList = strToList(params);
		return getWhereValueList(paramList, paramName, sqlMap);
	}
	
	/**
	 * 获取sql查询list条件。返回格式如：:paramName0, :paramName1, :paramName2, ...。调用处需自行添加“ and field_name in (本方法返回字符串)”
	 * @param params  参数值列表
	 * @param paramName 参数名
	 * @param sqlMap 用于执行的sqlMap
	 * @return
	 */
	public String getWhereValueList(List params, String paramName, SQLMap sqlMap) {
		if (params == null || params.size() == 0 || StrUtils.isNull(paramName) || sqlMap == null)
			return null;
		StringBuffer whereValueList = new StringBuffer();
		for(int i = 0; i < params.size(); i++) {
			String paramValue = String.valueOf(params.get(i));
			if (StrUtils.isNull(paramValue))
				continue;
			whereValueList.append(":" + paramName + i + ",");
			sqlMap.put(paramName + i, paramValue);
		}
		whereValueList.deleteCharAt(whereValueList.length() - 1);
		return whereValueList.toString();
	}
	
	/**
	 * 获取sql查询list条件sql，多个值使用in条件，单个值则使用=条件。返回格式如：and fieldName in (:paramName0, :paramName1, :paramName2, ...)。或：fieldName = :paramName
	 * @param params 参数值字符串。可以是分号分隔的多个参数值
	 * @param paramName 参数名。必须为调用处接收到的参数名（即写入sqlParaMap的参数名）
	 * @param sqlMap 用于执行的sqlMap
	 * @param fieldName 字段名，如果有表前缀则一并加上
	 * @return
	 */
	public String getWhereValueList(String params, String paramName, SQLMap sqlMap, String fieldName) {
		List paramList = strToList(params);
		if (paramList != null && paramList.size() == 1)
			return " and " + fieldName + " = :" + paramName + " ";
		else
			return " and " + fieldName + " in (" + getWhereValueList(paramList, paramName, sqlMap) + ") ";
	}
	
	
	/**
	 * 查询并将结果集转换成简单键-值对Json
	 * @param sqlMap
	 * @return
	 * @throws Exception
	 * @author OL
	 */
	public ParaMap queryFormat(SQLMap sqlMap) throws Exception {
		return PubUtils.ConvertJsonList(this.query(sqlMap));
	}
	
	/**
	 * 分页查询并将结果集转换成简单键-值对Json
	 * @param sqlMap
	 * @return
	 * @throws Exception
	 * @author OL
	 */
	public ParaMap queryForPageFormat(SQLMap sqlMap) throws Exception {
		if(!sqlMap.containsKey("pageIndex"))
			sqlMap.put("pageIndex", sqlMap.getInt("page_index", 1));
		if(!sqlMap.containsKey("pageSize"))
			sqlMap.put("pageSize", sqlMap.getInt("page_size", 10));
		return PubUtils.ConvertJsonList(this.queryForPage(sqlMap));
	}
	
	/**
	 * 查询单条记录并将结果集转换成简单键-值对Json
	 * @param sqlMap
	 * @return
	 * @throws Exception
	 * @author OL
	 */
	public ParaMap querySingleFormat(SQLMap sqlMap) throws Exception {
		return PubUtils.ConvertJsonMap(this.query(sqlMap));
	}
	
	/**
	 * 简单查询数据，主要用于编辑数据时查询单条记录无需定义数据集
	 * <br>并将结果集转换成简单键-值对Json
	 * @param tableName 查询数据的表名
	 * @param keyData 查询条件键值对
	 * @param orderBy 排序字段，格式如：abc desc, efg, xyz asc
	 * @param returnFields 返回结果集的字段列表
	 * @return 返回查询结果，通过status=1表示更新成功，否则请检查msg参数 MAP中还包含：<br/>
	 *         totalCount: 总记录数<br/>
	 *         rowCount: 当前结果集返回的记录数，实际同totalCount。主要是包含queryData返回值基本一致<br/>
	 * @throws Exception
	 */
	public ParaMap querySimpleFormat(String tableName, ParaMap keyData, String orderBy, String returnFields) throws Exception {
		return PubUtils.ConvertJsonList(super.querySimple(tableName, keyData, orderBy, returnFields));
	}

	/**
	 * 简单查询数据，主要用于编辑数据时查询单条记录无需定义数据集
	 * 
	 * @param tableName 查询数据的表名
	 * @param keyData 查询条件键值对
	 * @param orderBy 排序字段，格式如：abc desc, efg, xyz asc
	 * @return 返回查询结果，通过status=1表示更新成功，否则请检查msg参数 MAP中还包含：<br/>
	 *         totalCount: 总记录数<br/>
	 *         rowCount: 当前结果集返回的记录数，实际同totalCount。主要是包含queryData返回值基本一致<br/>
	 * @throws Exception
	 */
	public ParaMap querySimpleFormat(String tableName, ParaMap keyData, String orderBy) throws Exception {
		return PubUtils.ConvertJsonList(super.querySimple(tableName, keyData, orderBy));
	}

	/**
	 * 简单查询数据，主要用于编辑数据时查询单条记录无需定义数据集
	 * 
	 * @param tableName 查询数据的表名
	 * @param keyData 查询条件键值对
	 * @return 返回查询结果，通过status=1表示更新成功，否则请检查msg参数 MAP中还包含：<br/>
	 *         totalCount: 总记录数<br/>
	 *         rowCount: 当前结果集返回的记录数，实际同totalCount。主要是包含queryData返回值基本一致<br/>
	 * @throws Exception
	 */
	public ParaMap querySimpleFormat(String tableName, ParaMap keyData) throws Exception {
		return PubUtils.ConvertJsonList(super.querySimple(tableName, keyData));
	}

	/**
	 * 简单查询数据（重载方法）
	 * 
	 * @param tableName 查询数据的表名
	 * @param keyField 查询条件字段，多个字段请使用用半角逗号或者分号分隔。如“id”、“department_id,emp_id”
	 * @param data 字段键值对，如：<br/>
	 *            id: 1234567890<br/>
	 *            no: abcdefg<br/>
	 *            name: xxxxxx<br/>
	 *            主键字段也需包含
	 * @param orderBy 排序字段，格式如：abc desc, efg, xyz asc
	 * @param returnFields 返回结果集的字段列表
	 * @return 返回查询结果，通过status=1表示更新成功，否则请检查msg参数
	 * @throws Exception
	 */
	public ParaMap querySimpleFormat(String tableName, String keyField, ParaMap data, String orderBy, String returnFields) throws Exception {
		return PubUtils.ConvertJsonList(super.querySimple(tableName, keyField, data, orderBy, returnFields));
	}

	/**
	 * 简单查询数据（重载方法）
	 * 
	 * @param tableName 查询数据的表名
	 * @param keyField 查询条件字段，多个字段请使用用半角逗号或者分号分隔。如“id”、“department_id,emp_id”
	 * @param data 字段键值对，如：<br/>
	 *            id: 1234567890<br/>
	 *            no: abcdefg<br/>
	 *            name: xxxxxx<br/>
	 *            主键字段也需包含
	 * @param orderBy
	 *            排序字段，格式如：abc desc, efg, xyz asc
	 * @return 返回查询结果，通过status=1表示更新成功，否则请检查msg参数
	 * @throws Exception
	 */
	public ParaMap querySimpleFormat(String tableName, String keyField, ParaMap data, String orderBy) throws Exception {
		return PubUtils.ConvertJsonList(super.querySimple(tableName, keyField, data, orderBy));
	}

	/**
	 * 简单查询数据（重载方法）
	 * 
	 * @param tableName 查询数据的表名
	 * @param keyField 查询条件字段，多个字段请使用用半角逗号或者分号分隔。如“id”、“department_id,emp_id”
	 * @param data 字段键值对，如：<br/>
	 *            id: 1234567890<br/>
	 *            no: abcdefg<br/>
	 *            name: xxxxxx<br/>
	 *            主键字段也需包含
	 * @return 返回查询结果，通过status=1表示更新成功，否则请检查msg参数
	 * @throws Exception
	 */
	public ParaMap querySimpleFormat(String tableName, String keyField, ParaMap data) throws Exception {
		return PubUtils.ConvertJsonList(super.querySimple(tableName, keyField, data));
	}

	/**
	 * 简单查询数据（重载方法）
	 * 
	 * @param tableName 查询数据的表名
	 * @param keyField 查询条件字段，多个字段请使用用半角逗号或者分号分隔。如“id”、“department_id,emp_id”
	 * @param keyFieldValue 查询条件字段值，多个字段请使用用半角逗号或者分号分隔。如“0001”、“0001,0002”
	 * @param orderBy 排序字段，格式如：abc desc, efg, xyz asc
	 * @param returnFields 返回结果集的字段列表
	 * @return 返回查询结果，通过status=1表示更新成功，否则请检查msg参数
	 * @throws Exception
	 */
	public ParaMap querySimpleFormat(String tableName, String keyField, String keyFieldValue, String orderBy, String returnFields) throws Exception {
		return PubUtils.ConvertJsonList(super.querySimple(tableName, keyField, keyFieldValue, orderBy, returnFields));
	}

	/**
	 * 简单查询数据（重载方法）
	 * 
	 * @param tableName 查询数据的表名
	 * @param keyField 查询条件字段，多个字段请使用用半角逗号或者分号分隔。如“id”、“department_id,emp_id”
	 * @param keyFieldValue 查询条件字段值，多个字段请使用用半角逗号或者分号分隔。如“0001”、“0001,0002”
	 * @param orderBy 排序字段，格式如：abc desc, efg, xyz asc
	 * @return 返回查询结果，通过status=1表示更新成功，否则请检查msg参数
	 * @throws Exception
	 */
	public ParaMap querySimpleFormat(String tableName, String keyField, String keyFieldValue, String orderBy) throws Exception {
		return PubUtils.ConvertJsonList(super.querySimple(tableName, keyField, keyFieldValue, orderBy));
	}

	/**
	 * 简单查询数据（重载方法）
	 * 
	 * @param tableName 查询数据的表名
	 * @param keyField 查询条件字段，多个字段请使用用半角逗号或者分号分隔。如“id”、“department_id,emp_id”
	 * @param keyFieldValue 查询条件字段值，多个字段请使用用半角逗号或者分号分隔。如“0001”、“0001,0002”
	 * @return 返回查询结果，通过status=1表示更新成功，否则请检查msg参数
	 * @throws Exception
	 */
	public ParaMap querySimpleFormat(String tableName, String keyField, String keyFieldValue) throws Exception {
		return PubUtils.ConvertJsonList(super.querySimple(tableName, keyField, keyFieldValue));
	}

	/**
	 * 简单查询数据（重载方法）
	 * 
	 * @param tableName 查询数据的表名
	 * @param idFieldValue id字段值
	 * @param orderBy 排序字段，格式如：abc desc, efg, xyz asc
	 * @param returnFields 返回结果集的字段列表
	 * @return 返回查询结果，通过status=1表示更新成功，否则请检查msg参数
	 * @throws Exception
	 */
	public ParaMap querySimpleByIdFormat(String tableName, String idFieldValue, String orderBy, String returnFields) throws Exception {
		return PubUtils.ConvertJsonMap(super.querySimpleById(tableName, idFieldValue, orderBy, returnFields));
	}

	/**
	 * 简单查询数据（重载方法）
	 * 
	 * @param tableName 查询数据的表名
	 * @param idFieldValue id字段值
	 * @param orderBy 排序字段，格式如：abc desc, efg, xyz asc
	 * @return 返回查询结果，通过status=1表示更新成功，否则请检查msg参数
	 * @throws Exception
	 */
	public ParaMap querySimpleByIdFormat(String tableName, String idFieldValue, String orderBy) throws Exception {
		return PubUtils.ConvertJsonMap(super.querySimpleById(tableName, idFieldValue, orderBy));
	}

	/**
	 * 简单查询数据（重载方法）
	 * 
	 * @param tableName 查询数据的表名
	 * @param idFieldValue id字段值
	 * @return 返回查询结果，通过status=1表示更新成功，否则请检查msg参数
	 * @throws Exception
	 */
	public ParaMap querySimpleByIdFormat(String tableName, String idFieldValue) throws Exception {
		return PubUtils.ConvertJsonMap(super.querySimpleById(tableName, idFieldValue));
	}
	
	public ParaMap attachData(SQLMap sqlMap, int total, String URL, String sqlName, int pageSize, int pageIndex)throws Exception{
		sqlMap.setSQL(URL ,sqlName);
		sqlMap.addParam(pageIndex > 0 ? (pageIndex-1)*pageSize : pageIndex*pageSize);
		sqlMap.addParam(pageSize);
		sqlMap.setPlaceHolder("$limit", " limit ?,? ");
		ParaMap outMap = query(sqlMap);
		outMap.put("pageCount",  Math.round(Math.ceil(total / (pageSize + 0.0))));
		outMap.put("pageIndex", pageIndex);
		outMap.put("pageSize", pageSize);
		outMap.put("totalCount", total);
		return outMap;
	}
	
	public static void main(String[] args) throws Exception {
		//SimpleDataSetDao dao = new SimpleDataSetDao();
		//SQLMap sqlMap = new SQLMap();
		//sqlMap.setSQL("SELECT #currenttime# ctime1, UNIX_TIMESTAMP() * 1000 ctime2, u.* FROM fa_sys_user u");
		//sqlMap.setSQL("update fa_sys_user set remark = #currenttime#");
		//System.out.println(dao.queryForPage(sqlMap));
	}
}
