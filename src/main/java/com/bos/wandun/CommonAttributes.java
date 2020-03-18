package com.bos.wandun;

/**
 * 公共参数
 * 
 * @author Hello King
 * @version 4.0
 */
public final class CommonAttributes {

	/** 日期格式配比 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

	/** ecs.xml文件路径 */
	public static final String ECS_XML_PATH = "/ecs.xml";

	/** ecs.properties文件路径 */
	public static final String ECS_PROPERTIES_PATH = "/ecs.properties";

	/**
	 * 不可实例化
	 */
	private CommonAttributes() {
	}

}