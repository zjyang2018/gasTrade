package com.common.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

public class HttpTransfer {
	private static Logger logger = Logger.getLogger(HttpTransfer.class);
	protected CloseableHttpClient httpClient;
	protected int maxTotal = 200;
	protected int defaultMaxPerRoute = 50;
	protected int connectTimeout = 60;
	protected int socketTimeout = 60;
	protected String encoding = "utf-8";

	public void init() {
		try {
			SSLContext sslcontext = SSLContexts.custom()
					.loadTrustMaterial(new TrustStrategy() {
						public boolean isTrusted(X509Certificate[] chain,
								String authType) throws CertificateException {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslcontext, NoopHostnameVerifier.INSTANCE);
			Registry<ConnectionSocketFactory> registry = RegistryBuilder
					.<ConnectionSocketFactory> create()
					.register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", sslsf).build();
			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
					registry);
			cm.setMaxTotal(maxTotal);
			cm.setDefaultMaxPerRoute(defaultMaxPerRoute);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(socketTimeout * 1000)
					.setConnectTimeout(connectTimeout * 1000).build();
			httpClient = HttpClients.custom()
					.setDefaultRequestConfig(requestConfig)
					.setConnectionManager(cm).build();
		} catch (Exception e) {
			logger.error("fail to initial httptransfer: ", e);
			throw new RuntimeException(e);
		}
	}

	public HttpResult doPost(String url, String data) throws IOException {
		return doPost(url, data, ContentType.create(
				ContentType.TEXT_PLAIN.getMimeType(), encoding));
	}

	public HttpResult doPost(String url, String data, ContentType contentTtype)
			throws IOException {
		HttpPost post = new HttpPost(url);
		if (StringUtils.isNotEmpty(data)) {
			post.setEntity(new StringEntity(data, contentTtype));
		}
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(post);
			return new HttpResult().setStatusCode(
					String.valueOf(response.getStatusLine().getStatusCode()))
					.setContent(
							EntityUtils.toString(response.getEntity(),
									contentTtype.getCharset()));
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	public HttpResult doGet(String url, Map<String, String> data)
			throws IOException, Exception {
		CloseableHttpResponse response = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			for (String key : data.keySet()) {
				builder.setParameter(key, data.get(key));
			}
			HttpGet httpGet = new HttpGet(builder.build());
			response = httpClient.execute(httpGet);
			return new HttpResult()
					.setStatusCode(
							String.valueOf(response.getStatusLine()
									.getStatusCode()))
					.setContent(
							EntityUtils.toString(response.getEntity(), encoding));
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	public HttpResult doPost(String url, Map<String, String> data)
			throws IOException {
		return doPost(url, data, encoding);
	}

	public HttpResult doPost(String url, Map<String, String> data, String encode)
			throws IOException {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (Entry<String, String> entry : data.entrySet()) {
			if (entry.getValue() instanceof String) {
				list.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			} else {
				list.add(new BasicNameValuePair(entry.getKey(), JSON
						.toJSONString(entry.getValue())));
			}

		}
		post.setEntity(new UrlEncodedFormEntity(list, Charset.forName(encode)));
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(post);
			return new HttpResult().setStatusCode(
					String.valueOf(response.getStatusLine().getStatusCode()))
					.setContent(
							EntityUtils.toString(response.getEntity(), encode));
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	/**
	 * spring 配置在destroy-method
	 * 
	 * @throws IOException
	 */
	public void destroy() throws IOException {
		httpClient.close();
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
