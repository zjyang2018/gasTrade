package com.common.filter.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.common.constant.CommonConstant;

/**
 * 
 * @Project payment-web
 * @Description:
 * @Company youku
 * @Create 2014-11-10下午10:39:57
 * @author guochao
 * @version 1.0 Copyright (c) 2014 youku, All Rights Reserved.
 */
public class HttpUtil {

	protected static final Logger	log						= Logger.getLogger( HttpUtil.class );

	public static final String		IP_REG					= "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

	public static final int			DEFAULT_READ_TIMEOUT	= 20000;

	public static final int			DEFAULT_CONNECT_TIMEOUT	= 5000;

	public static final String		DEFAULT_CHARSET			= "UTF-8";

	public static final int			DEFAULT_RETRY_TIME		= 1;																							// 打开URL失败默认重新连接的次数

	public static String openUrlReturnMoreMessage(String strUrl, String charSet) {
		return openUrlReturnMoreMessage( strUrl, charSet, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT );
	}

	public static String openUrlReturnMoreMessage(String strUrl) {
		return openUrlReturnMoreMessage( strUrl, DEFAULT_CHARSET, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT );
	}

	public static String openUrlReturnMoreMessage(String strUrl, int connectTimeout, int readTimeout) {
		return openUrlReturnMoreMessage( strUrl, DEFAULT_CHARSET, connectTimeout, readTimeout );
	}

	/**
	 * 本方法用于调用远程的接口
	 * 
	 * @param String
	 *            接口的url
	 * @return 返回调用接收Url后的返回值
	 */
	public static String openUrlReturnMoreMessage(String strUrl, String charSet, int connectTimeout, int readTimeout) {
		InputStream is = null;

		try {

			URL webUrl = new URL( strUrl );
			HttpURLConnection httpConn = (HttpURLConnection) webUrl.openConnection();
			httpConn.setConnectTimeout( connectTimeout );
			httpConn.setReadTimeout( readTimeout );
			if (httpConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				log.warn( strUrl + "|ResponseCode=" + httpConn.getResponseCode() );
				return null;
			}
			is = httpConn.getInputStream();
			return getReturnValueFromInputStream( is, charSet );
		} catch (Exception e) {
			String url = strUrl.indexOf( "?" ) > 0 ? strUrl.substring( 0, strUrl.indexOf( "?" ) ) : strUrl;
			log.fatal( url + ";" + e.getMessage(), e );
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e1) {
					log.error( "openUrl(String strUrl)", e1 );
				}
			}
		}
	}

	/**
	 * 本方法用于调用远程的接口
	 * 
	 * @param String
	 *            接口的url
	 * @return 返回调用接收Url后的返回值
	 */
	public static String postToUrlReturnMoreMessage(String strUrl, Map<String, String> params, String charSet, int connectTimeout, int readTimeout) {
		InputStream is = null;
		OutputStream os = null;
		StringBuilder postParams = new StringBuilder();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				postParams.append( "&" ).append( entry.getKey() ).append( "=" ).append( urlEncode( entry.getValue() ) );
			}
		}

		try {
			URL webURL = new URL( strUrl );
			HttpURLConnection httpURLConnection = (HttpURLConnection) webURL.openConnection();
			httpURLConnection.setDoOutput( true );// 打开写入属性
			httpURLConnection.setDoInput( true );// 打开读取属性
			httpURLConnection.setRequestMethod( "POST" );// 设置提交方法
			httpURLConnection.setConnectTimeout( connectTimeout );// 连接超时时间
			httpURLConnection.setReadTimeout( readTimeout );
			httpURLConnection.connect();
			os = httpURLConnection.getOutputStream();
			os.write( postParams.toString().getBytes( CommonConstant.UTF_8 ) );
			os.flush();

			if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				log.warn( strUrl + "|ResponseCode=" + httpURLConnection.getResponseCode() );
				return null;
			}

			is = httpURLConnection.getInputStream();
			return getReturnValueFromInputStream( is, charSet );
		} catch (Exception e) {
			log.fatal( strUrl + ";" + e.getMessage(), e );
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e1) {
					log.error( "openUrl(String strUrl)", e1 );
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e1) {
					log.error( "openUrl(String strUrl)", e1 );
				}
			}
		}
	}

	public static String httpsUrlRequestPost(String url) {
		return httpsUrlRequestPost( url, "" );
	}

	public static String httpsUrlRequestPost(String url, String content) {
		return httpsUrlRequestPost( url, content, DEFAULT_CHARSET, DEFAULT_CONNECT_TIMEOUT );
	}

	/**
	 * post形式将请求提交到一个https地址，无需配置SSL证书
	 * 
	 * @param url
	 * @param content
	 * @param charSet
	 * @param timeOut
	 * @return
	 */
	public static String httpsUrlRequestPost(String url, String content, String charSet, int timeOut) {
		if (0 == timeOut) {
			timeOut = 5000;
		}
		String ret = null;
		HostnameVerifier hv = new HostnameVerifier() {

			public boolean verify(String hostname, SSLSession session) {
				return hostname.equals( session.getPeerHost() );
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier( hv );
		HttpURLConnection urlConnect = null;
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) {
					// System.out.println("checkServerTrusted");
				}

				public void checkClientTrusted(X509Certificate[] chain, String authType) {
					// System.out.println("checkClientTrusted");
				}
			} };

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance( "TLS", "SunJSSE" );
			sc.init( null, trustAllCerts, new java.security.SecureRandom() );
			HttpsURLConnection.setDefaultSSLSocketFactory( sc.getSocketFactory() );
			HttpsURLConnection.setDefaultHostnameVerifier( hv );

			// Now you can access an https URL without having the certificate in
			// the truststore
			URL urlClass = new URL( url );
			urlConnect = (HttpURLConnection) urlClass.openConnection();
			urlConnect.setRequestMethod( "POST" );
			urlConnect.setConnectTimeout( timeOut );
			urlConnect.setDoOutput( true );

			urlConnect.setRequestProperty( "Content-Type", "application/stream" ); // 设置请求类型
			urlConnect.setRequestProperty( "Content-Length", Integer.toString( content.length() ) ); // 设置请求数据长度

			byte[] byteContent = content.getBytes( charSet );
			urlConnect.getOutputStream().write( byteContent, 0, byteContent.length );
			urlConnect.getOutputStream().flush();
			urlConnect.getOutputStream().close();

			InputStream in = urlConnect.getInputStream();
			ret = getReturnValueFromInputStream( in, charSet );
			in.close();
		} catch (Exception e) {
			log.fatal( "请求https异常", e );
			return null;
		} finally {
			if (urlConnect != null) {
				urlConnect.disconnect();
			}
		}
		return ret;
	}

	/**
	 * 从输入流获取所有数据并以指定字符集拼装成字符串
	 * 
	 * @param is
	 * @param charSet
	 * @return
	 */
	public static String getReturnValueFromInputStream(InputStream is, String charSet) {
		if (StringUtils.isBlank( charSet )) {
			charSet = DEFAULT_CHARSET;
		}

		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader( new InputStreamReader( is, charSet ) );
			char[] temp = new char[1024];
			int charCount = 0;
			while ((charCount = br.read( temp )) != -1) {
				sb.append( new String( temp, 0, charCount ) );
			}
			return sb.toString();
		} catch (Exception e) {
			log.fatal( e.getMessage(), e );
			return null;
		}
	}

	/**
	 * 获取远程用户IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static final String getRemoteAddr(HttpServletRequest request) {
		String rip = request.getRemoteAddr();
		String xff = request.getHeader( "X-Forwarded-For" );
		String ip;
		if (xff != null && xff.length() != 0) {
			int px = xff.indexOf( ',' );
			if (px != -1) {
				ip = xff.substring( 0, px );
			} else {
				ip = xff;
			}
			if ("unknown".equalsIgnoreCase( ip )) {
				ip = rip;
			} else if (!ip.matches( IP_REG )) {
				log.fatal( "Wrong format IP from header, X-Forwarded-For:" + xff + ", IP:" + rip );
				ip = rip;
			}
		} else {
			ip = rip;
		}
		return ip.trim();
	}

	/**
	 * 
	 * 方法用途: 默认限制同域，传了allowed则根据allowed判断<br>
	 * 实现步骤: <br>
	 * 
	 * @param request
	 * @param allowedOrigins
	 * @return
	 */
	public static boolean isValidOrigin(HttpServletRequest request, Collection<String> allowedOrigins) {
		Assert.notNull( request, "Request must not be null" );
		Assert.notNull( allowedOrigins, "Allowed origins must not be null" );

		String origin = getRequestOrigin( request );
		if (origin == null || allowedOrigins.contains( "*" )) {
			return true;
		} else if (allowedOrigins.isEmpty()) {
			UriComponents originComponents;
			try {
				originComponents = UriComponentsBuilder.fromHttpUrl( origin ).build();
			} catch (IllegalArgumentException ex) {
				if (log.isEnabledFor( Level.WARN )) {
					log.warn( "Failed to parse Origin header value [" + origin + "]" );
				}
				return false;
			}
			UriComponents requestComponents = HttpUtil.fromHttpRequest( request ).build();
			// 由于目前系统tomcat开放的是http协议，https是从nginx外层做的，然后origin是https的，所以这里暂且只校验host
			return originComponents.getHost().equals( requestComponents.getHost() );
		} else {
			return allowedOrigins.contains( origin );
		}
	}

	public static String getRequestOrigin(HttpServletRequest req) {
		String origin = req.getHeader( "Origin" );
		if (origin == null) {
			return null;
		}
		UriComponents originComponents;
		try {
			originComponents = UriComponentsBuilder.fromHttpUrl( origin ).build();
		} catch (IllegalArgumentException ex) {
			if (log.isEnabledFor( Level.WARN )) {
				log.warn( "Failed to parse Origin header value [" + origin + "]" );
			}
			return null;
		}
		return originComponents.getScheme() + "://" + originComponents.getHost();
	}

	private static UriComponentsBuilder fromHttpRequest(HttpServletRequest request) {
		URI uri;
		try {
			uri = new URI( request.getRequestURL().toString() );
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException( request.getRequestURI(), e );
		}
		UriComponentsBuilder builder = UriComponentsBuilder.fromUri( uri );

		String scheme = uri.getScheme();
		String host = uri.getHost();
		int port = uri.getPort();

		String hostHeader = request.getHeader( "X-Forwarded-Host" );
		if (StringUtils.isNotBlank( hostHeader )) {
			String[] hosts = StringUtils.split( hostHeader, "," );
			String hostToUse = hosts[ 0 ];
			if (hostToUse.contains( ":" )) {
				String[] hostAndPort = StringUtils.split( hostToUse, ":" );
				host = hostAndPort[ 0 ];
				port = Integer.parseInt( hostAndPort[ 1 ] );
			} else {
				host = hostToUse;
				port = -1;
			}
		}

		String portHeader = request.getHeader( "X-Forwarded-Port" );
		if (StringUtils.isNotBlank( portHeader )) {
			port = Integer.parseInt( portHeader );
		}

		String protocolHeader = request.getHeader( "X-Forwarded-Proto" );
		if (StringUtils.isNotBlank( protocolHeader )) {
			scheme = protocolHeader;
		}

		builder.scheme( scheme );
		builder.host( host );
		if (port == -1) {
			if ("http".equals( scheme )) {
				port = 80;
			} else if ("https".equals( scheme )) {
				port = 443;
			}
		}
		if (scheme.equals( "http" ) && port != 80 || scheme.equals( "https" ) && port != 443) {
			builder.port( port );
		}

		return builder;
	}

	public static String urlEncode(String source) {
		return urlEncode( source, null );
	}

	public static String urlEncode(String source, String encode) {
		if (StringUtils.isBlank( source )) {
			return StringUtils.EMPTY;
		}
		if (StringUtils.isBlank( encode )) {
			encode = DEFAULT_CHARSET;
		}
		try {
			return URLEncoder.encode( source, encode );
		} catch (UnsupportedEncodingException e) {
			log.fatal( "不支持的编码，source:" + source + ",encode:" + encode, e );
			return StringUtils.EMPTY;
		}
	}

	public static String urlDecode(String source) {
		return urlDecode( source, null );
	}

	public static String urlDecode(String source, String encode) {
		if (StringUtils.isBlank( source )) {
			return StringUtils.EMPTY;
		}
		if (StringUtils.isBlank( encode )) {
			encode = DEFAULT_CHARSET;
		}
		try {
			return URLDecoder.decode( source, encode );
		} catch (UnsupportedEncodingException e) {
			log.fatal( "不支持的编码，source:" + source + ",encode:" + encode, e );
			return StringUtils.EMPTY;
		}
	}

	/**
	 * URL检查<br>
	 * <br>
	 * 
	 * @param pInput
	 *            要检查的字符串<br>
	 * @return boolean 返回检查结果<br>
	 */
	public static boolean isUrl(String pInput) {
		if (pInput == null) {
			return false;
		}
		String regEx = "^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-\\_]+(\\:[a-zA-" + "Z0-9\\.&%\\$\\-\\_]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"
				+ "2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}" + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|"
				+ "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-" + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"
				+ "-9\\-]+\\.)*[a-zA-Z0-9\\-\\_]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/" + "[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*$";
		Pattern p = Pattern.compile( regEx );

		java.util.regex.Matcher matcher = p.matcher( pInput );
		return matcher.matches();
	}

	/**
	 * 
	 * 方法用途: 判断是否是ajax请求<br>
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		if (request == null) {
			return false;
		}
		String msg = request.getHeader( "X-Requested-With" );
		if (StringUtils.isNotBlank( msg ) && StringUtils.equalsIgnoreCase( msg, "XMLHttpRequest" ))// 识别ajax请求
		{
			return true;
		}
		return false;
	}

}
