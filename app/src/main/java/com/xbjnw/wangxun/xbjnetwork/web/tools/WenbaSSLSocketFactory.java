package com.xbjnw.wangxun.xbjnetwork.web.tools;


import com.xbjnw.wangxun.xbjnetwork.BBLog;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class WenbaSSLSocketFactory extends SSLSocketFactory {
	private SSLContext sslContext = SSLContext.getInstance("TLS");

	public WenbaSSLSocketFactory(InputStream truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
		super(KeyStore.getInstance(KeyStore.getDefaultType()));
		TrustManager tm = null;
		try {
			tm = new WenbaX509TrustManager(truststore,"");
		} catch (GeneralSecurityException e) {
			BBLog.w("wenba", e);
		}
		sslContext.init(null, new TrustManager[] { tm }, null);
	}

	@Override
	public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
		return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
	}

	@Override
	public Socket createSocket() throws IOException {
		return sslContext.getSocketFactory().createSocket();
	}
}
