package com.zach.safety.service;

public interface MsgService {

	public void sendMsg(String phone, String text);

	public void sendMsgText(String phone, String text);

}
