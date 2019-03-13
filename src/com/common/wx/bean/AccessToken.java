package com.common.wx.bean;

import java.io.Serializable;

/*
*AccessToken 对象
*/
public class AccessToken implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 630820355595608596L;
	// 获取到的凭证
	private String accessToken;
	// 凭证有效时间，单位：秒
	private int expiresin;

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken
	 *            the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the expiresin
	 */
	public int getExpiresin() {
		return expiresin;
	}

	/**
	 * @param expiresin
	 *            the expiresin to set
	 */
	public void setExpiresin(int expiresin) {
		this.expiresin = expiresin;
	}

	public AccessToken() {
		// TODO Auto-generated constructor stub
	}

}
