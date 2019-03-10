package com.zach.safety.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.common.utils.DateTimeUtils;
import com.miaodiyun.httpApiDemo.IndustrySMS;
import com.zach.safety.service.MsgService;

/**
 * 发送短信服务
 * 
 * @author Administrator
 * 
 */
@Service("msgService")
public class MsgServiceImpl implements MsgService {

	private Logger logger = Logger.getLogger(getClass());

	public String createSendMsg(String projectName, String area, String location, String equipId, String alermStatus) {
		StringBuilder smsText = new StringBuilder();
		// 尊敬的用户,截止到{1},项目:{2},区域:{3},位置:{4}发生告警,{5}值为{6}
		// smsText.append("【拓客科技】尊敬的用户,截止到").append(DateTimeUtils.toCurrentDateTime("yyyy-MM-dd
		// HH:mm:ss")).append(",项目:")
		// .append(projectName).append(",区域:").append(area).append(",位置:").append(location).append("发生告警,");
		// smsText.append(alermStatus).append("值为").append(checkValue);

		// 尊敬的用户,于{1},项目:{2},区域:{3},位置:{4},设备:{5}发生{6}告警
		smsText.append("【拓客科技】尊敬的用户,于").append(DateTimeUtils.toCurrentDateTime("yyyy-MM-dd HH:mm:ss")).append(",项目:")
				.append(projectName).append(",区域:").append(area).append(",位置:").append(location).append(",设备:")
				.append(equipId).append("发生").append(alermStatus).append("告警");

		return smsText.toString();
	}

	public static void main(String[] args) {
		StringBuilder smsText = new StringBuilder();
		smsText.append("【拓客科技】尊敬的用户，您的验证码为").append("1212").append("，请于5分钟内正确输入，如非本人操作，请忽略此短信。");
		IndustrySMS.setParameter("13923469163", smsText.toString());
		IndustrySMS.execute();
	}

	@Override
	public void sendMsg(String phone, String text) {
		StringBuilder smsText = new StringBuilder();
		smsText.append("【拓客科技】尊敬的用户，您的验证码为").append(text).append("，请于5分钟内正确输入，如非本人操作，请忽略此短信。");
		// String msgText = "【拓客科技】尊敬的客户，您的验证码为4567，请于5分钟内正确输入，如非本人操作，请忽略此短信。";
		IndustrySMS.setParameter(phone, smsText.toString());
		IndustrySMS.execute();
	}

}
