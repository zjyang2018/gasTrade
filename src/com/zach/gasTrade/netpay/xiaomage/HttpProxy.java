package com.zach.gasTrade.netpay.xiaomage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

public class HttpProxy {
	/**
	 * 发送post请求
	 * 
	 * @author liangsi 18/08/08 13:28
	 * @param params
	 *            参数
	 * @param requestUrl
	 *            请求地址
	 * @return 返回结果
	 * @throws IOException
	 */
	public static String HttpPost(String params, String requestUrl) throws IOException {
		System.out.println("请求参数:" + params);
		byte[] requestBytes = params.getBytes("utf-8"); // 将参数转为二进制流
		HttpClient httpClient = new HttpClient();// 客户端实例化
		PostMethod postMethod = new PostMethod(requestUrl);
		/*
		 * 设置请求头 Authorization（这个东西是验证部分，一般做法是参数和验证分离，小马哥的参数尾部自带签名）
		 * postMethod.setRequestHeader("Authorization", "Basic " + authorization);
		 */

		// 设置请求头
		postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		postMethod.setRequestHeader("Accept-Charset", "utf-8");
		// postMethod.setRequestHeader("Content-length",
		// String.valueOf(requestBytes.length));
		// System.out.println("请求参数长度:" + String.valueOf(requestBytes.length));

		InputStream inputStream = new ByteArrayInputStream(requestBytes, 0, requestBytes.length);

		RequestEntity requestEntity = new InputStreamRequestEntity(inputStream, requestBytes.length,
				"application/x-www-form-urlencoded; charset=utf-8"); // 请求体
		postMethod.setRequestEntity(requestEntity);

		httpClient.executeMethod(postMethod);// 执行请求
		InputStream soapResponseStream = postMethod.getResponseBodyAsStream();// 获取返回的流
		byte[] datas = null;
		try {
			datas = readInputStream(soapResponseStream);// 从输入流中读取数据
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = new String(datas, "UTF-8");// 将二进制流转为String
		// 打印返回结果
		if ((result == null) || ("".equals(result.trim()))) {
			System.out.println("很遗憾，响应结果为空，请重试。");
		} else
			System.out.println("响应参数：" + result);

		return result;

	}

	/**
	 * 从输入流中读取数据
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;
	}
}
