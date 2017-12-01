package com.yxd.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

import com.yxd.netty.example.server.TimeServer;

/**
 * Java 开发的话必须用maven 不然jar太难管理了  我已经放弃spark Java ant 方式开发、
 * 该项目Java版本放弃
 * @author YXD
 *
 */
public class SparkTask {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SparkConf conf = new SparkConf()
		.setAppName("SparkTask")
		//.setMaster("hadoop1:7070");
		.setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);
		//启用spark sql
		SQLContext sqlContext = new org.apache.spark.sql.SQLContext(sc);
		sqlContext.sql("create table weblogs (day string,date string,r_no string,service string,log_level string,data_info string)");
		DataFrame df = sqlContext.sql("select * from weblogs");
		df.show();  
		
		TimeServer ts = new TimeServer();
		//开启服务器
		try {
			ts.bind(50864);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
