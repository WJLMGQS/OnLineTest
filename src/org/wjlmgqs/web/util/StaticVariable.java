/**
**作者：翁加林
**时间：2012-8-3
**文件名：StaticVariable.java
**包名：org.wjlmgqs.web.util
**工程名：OnLineTest04
*/


package org.wjlmgqs.web.util;

public class StaticVariable {
	private static String defaultImageFormat = ".JPEG";
	private static String QuestBankImage="defaultQuestBank.jpg";
	private static String studentPhoto="defaultStudentPhoto.jpg";
	private static String studentSex= "1";
	private static String studentTelephote="00000000000";
	public static String getDefaultImageFormat() {
		return defaultImageFormat;
	}
	public static String getQuestBankImage() {
		return QuestBankImage;
	}
	public static String getStudentPhoto() {
		return studentPhoto;
	}
	public static String getStudentSex() {
		return studentSex;
	}
	public static String getStudentTelephote() {
		return studentTelephote;
	}
	public static void setQuestBankImage(String questBankImage) {
		QuestBankImage = questBankImage;
	}
	
}
