package com.common.wx.bean;

import java.util.Map;

/**
 * 通知模板消息实体类
 */
public class TemplateMessage {
	private String openId; // 用户OpenID
	private String templateId; // 模板消息ID
	private String url; // URL置空，在发送后，点模板消息进入一个空白页面（ios），或无法点击（android）。
	private String topcolor; // 标题颜色
	private Map<String, TemplateData> templateData; // 模板详细信息

	public static TemplateMessage New() {
		return new TemplateMessage();
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the topcolor
	 */
	public String getTopcolor() {
		return topcolor;
	}

	/**
	 * @param topcolor
	 *            the topcolor to set
	 */
	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	/**
	 * @return the templateData
	 */
	public Map<String, TemplateData> getTemplateData() {
		return templateData;
	}

	/**
	 * @param templateData
	 *            the templateData to set
	 */
	public void setTemplateData(Map<String, TemplateData> templateData) {
		this.templateData = templateData;
	}

}
