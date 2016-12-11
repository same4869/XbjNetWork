package com.xbjnw.wangxun.xbjnetwork.web.tools;


import com.xbjnw.wangxun.xbjnetwork.BBLog;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class SecureSSLSocketFactory extends SSLSocketFactory {
	private SSLContext mSSLContext;

	public SecureSSLSocketFactory(InputStream keyStore, String keyStorePassword)
			throws NoSuchAlgorithmException, KeyManagementException {
		try {
			mSSLContext = SSLContext.getInstance("TLS");
			// Log.w("sslcont", sslcontext.getProtocol() + ":" +
			// sslcontext.getProtocol().toString());
			mSSLContext.init(null, new TrustManager[] { new SsX509TrustManager(keyStore, keyStorePassword) }, null);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Failure initializing default SSL context", e);
		} catch (KeyManagementException e) {
			throw new IllegalStateException("Failure initializing default SSL context", e);
		} catch (GeneralSecurityException e) {
			BBLog.w("wenba", e);
		}
	}

	@Override
	public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
		SSLSocket socket = (SSLSocket) mSSLContext.getSocketFactory().createSocket(s, host, port, autoClose);
		socket.setEnabledProtocols(new String[] { "TLSv1.2" });
		return socket;
	}

	@Override
	public String[] getDefaultCipherSuites() {
		return mSSLContext.getSocketFactory().getDefaultCipherSuites();
	}

	@Override
	public String[] getSupportedCipherSuites() {
		return mSSLContext.getSocketFactory().getSupportedCipherSuites();
	}

	@Override
	public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
		SSLSocket socket = (SSLSocket) mSSLContext.getSocketFactory().createSocket(host, port);
		socket.setEnabledProtocols(new String[] { "TLSv1.2" });
		return socket;
	}

	@Override
	public Socket createSocket(InetAddress host, int port) throws IOException {
		SSLSocket socket = (SSLSocket) mSSLContext.getSocketFactory().createSocket(host, port);
		socket.setEnabledProtocols(new String[] { "TLSv1.2" });
		return socket;
	}

	@Override
	public Socket createSocket(String host, int port, InetAddress localHost, int localPort)
			throws IOException, UnknownHostException {
		SSLSocket socket = (SSLSocket) mSSLContext.getSocketFactory().createSocket(host, port, localHost, localPort);
		socket.setEnabledProtocols(new String[] { "TLSv1.2" });
		return socket;
	}

	@Override
	public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort)
			throws IOException {
		SSLSocket socket = (SSLSocket) mSSLContext.getSocketFactory().createSocket(address, port, localAddress,
				localPort);
		socket.setEnabledProtocols(new String[] { "TLSv1.2" });
		return socket;
	}

}
