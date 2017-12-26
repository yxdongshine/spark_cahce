package com.common.conf;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;

import com.base.log.Logging;

/**
 * 
 * @author OL
 *
 */
public class MsgConfig {

	private MsgConfig() {}

	private static Logging log = Logging.getLogging("common_msg");

	private static Properties properties = new Properties();

	private final static String propResource = "/message.properties";

	static {
		InputStream in = MsgConfig.class.getResourceAsStream(propResource);
		try {
			properties.load(new InputStreamReader(in, "UTF-8"));
		} catch (IOException e) {
			// log.error("读取message.properties出错:" + IOUtil.getStackTrace(e, true));
		}
	}

	/**
	 * 读取配置文件中的值
	 * 
	 * @param key
	 * @return
	 * @author OL
	 */
	public static String get(String key) {
		return properties.getProperty(key);
	}

	/**
	 * 读取配置文件中的值
	 * 
	 * @param key
	 * @param values 需要被替换的占位符{0}参数
	 * @return
	 * @author OL
	 */
	public static String get(String key, Object... values) {
		String msg = get(key);
		return msg == null ? null : MessageFormat.format(msg, values);
	}

	public static void main(String[] args) {
		System.out.println(MsgConfig.get("common.message.config.demo1"));
		System.out.println(MsgConfig.get("common.message.config.demo2"));
		System.out.println(MsgConfig.get("common.message.config.demo2", "a", "b"));
		System.out.println(MsgConfig.get("common.message.config.demo2", "a", "b",	"c"));
		System.out.println(MsgConfig.get("common.message.config.demo2", "a", "b",	"c", "d"));
	}
}
