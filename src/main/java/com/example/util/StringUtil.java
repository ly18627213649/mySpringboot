package com.example.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	protected static Logger log = LoggerFactory.getLogger(StringUtil.class);

	// 对象锁
	public static final Object lockObj = new Object();

	/**
	 * 生成流水号
	 * @return
	 */
	public static String createSerialNumber(int serialLength, int currentNum){
		String str = String.valueOf((++currentNum));
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < serialLength - str.length(); i++){
			buf.append("0");
		}
		return buf.append(str).toString();
	}

	/**
	 *  生成指定长度随机数
	 *
	 * @author liyang
	 * @since  2019/11/12 17:49
	 */
	public static String getRandomCode(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(((int)(Math.random()*100))%10);
		}
		return sb.toString();
	}

	/**
	 * 随机生成指定长度的验证码
	 */
	private static String generateNumCode(int length) {
		String val = "";
		String charStr = "char";
		String numStr = "num";
		Random random = new Random();

		//参数length，表示生成几位随机数
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? charStr : numStr;
			//输出字母还是数字
			if (charStr.equalsIgnoreCase(charOrNum)) {
				//输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (random.nextInt(26) + temp);
			}
			else if (numStr.equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	/**
	 * 生成唯一msgId
	 *
	 * @author liyang
	 * @since  2019/11/21 10:54
	 */
	public static String getMsgId(String resource){

		synchronized (lockObj) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				log.error("生成msgID时出错！");
			}
			return resource + "_" + System.currentTimeMillis();
		}
	}

	/**
	 * 生成指定长度的随机字符串
	 */
	public static String getRandomString(Integer length){

		String str="abcdefghjkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ123456789";
		Random random=new Random();
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<length;i++){
			int number=random.nextInt(56);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * RandomStringUtils工具类
	 */
	public static void random(){
		//产生5位长度的随机字符串，中文环境下是乱码
		String random = RandomStringUtils.random(5);
		System.out.println(random);

		//使用指定的字符生成5位长度的随机字符串
		String random1 = RandomStringUtils.random(5, new char[]{'a', 'b', 'c', 'd', 'e', 'f', '1', '2', '3'});
		System.out.println(random1);

		//生成指定长度的字母和数字的随机组合字符串
		String s = RandomStringUtils.randomAlphanumeric(5);
		System.out.println(s);

		//生成随机数字字符串
		String s1 = RandomStringUtils.randomNumeric(5);
		System.out.println(s1);

		//生成随机[a-z]字符串，包含大小写
		String s2 = RandomStringUtils.randomAlphabetic(5);
		System.out.println(s2);

		//生成从ASCII 32到126组成的随机字符串
		String s3 = RandomStringUtils.randomAscii(4);
		System.out.println(s3);
	}

	/**
	 *  过滤特殊字符
	 *  @author liyang
	 *  @since  2020/3/27 18:49
	 */
	public static String stringFilter(String str){
		// 清除掉所有特殊字符和空格
		String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(str);
		return matcher.replaceAll("").trim();
	}
	
}
