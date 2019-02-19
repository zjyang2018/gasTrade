package com.common.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;

public abstract class HttpsKeyTransfer extends HttpTransfer {
	private static Logger	logger			= Logger.getLogger( HttpsKeyTransfer.class );
	protected String		keyPath			= null;
	protected String		trustPath		= null;
	protected String		storePassword	= null;
	protected String		keyPassword		= null;
	protected String		trustPassword	= null;

	@Override
	public void init() {
		try {
			SSLConnectionSocketFactory sslsf = loadKey();
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
					.register( "http", PlainConnectionSocketFactory.INSTANCE ).register( "https", sslsf ).build();
			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager( registry );
			cm.setMaxTotal( maxTotal );
			cm.setDefaultMaxPerRoute( defaultMaxPerRoute );
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout( socketTimeout * 1000 ).setConnectTimeout( connectTimeout * 1000 )
					.build();
			httpClient = HttpClients.custom().setDefaultRequestConfig( requestConfig ).setConnectionManager( cm ).build();
		} catch (Exception e) {
			logger.error( "fail to initial httptransfer: ", e );
			throw new RuntimeException( e );
		}
	}

	protected abstract SSLConnectionSocketFactory loadKey() throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
			FileNotFoundException, IOException, KeyManagementException, UnrecoverableKeyException;

	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}

	public void setTrustPath(String trustPath) {
		this.trustPath = trustPath;
	}

	public void setStorePassword(String storePassword) {
		this.storePassword = storePassword;
	}

	public void setKeyPassword(String keyPassword) {
		this.keyPassword = keyPassword;
	}

	public void setTrustPassword(String trustPassword) {
		this.trustPassword = trustPassword;
	}

}
