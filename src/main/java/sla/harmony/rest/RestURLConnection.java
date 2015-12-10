/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 ShadowLordAlpha
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package sla.harmony.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.NetPermission;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.security.Permission;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.security.cert.X509Certificate;

import sun.net.www.protocol.http.HttpURLConnection.TunnelState;

/**
 * A {@link URLConnection} that allows for use of more RESTful Web Services than the default provided by Java.
 * 
 * @since Harmony v0.1.4
 * @version v1.0.0
 * @author Josh "ShadowLordAlpha"
 */
public class RestURLConnection extends HttpsURLConnection {

	private static final String[] REST_METHODS = { "GET", "POST", "HEAD", "OPTIONS", "PUT", "PATCH", "DELETE",
			"TRACE" };

	private String method = "GET";
	private DelegateRestURLConnection delegate;
	
	// DO NOT USE it doesn't work like you think it does
	protected RestURLConnection(URL url) throws IOException {
		super(url);
		
		
	}

	public RestURLConnection(URL u, Proxy p, RestURLStreamHandler handler) throws IOException {
		super(u);
		
		delegate = new DelegateRestURLConnection(u, handler, this);
	}

	/**
	 * Set the method for the URL request, one of:
	 * <li>GET
	 * <li>POST
	 * <li>HEAD
	 * <li>OPTIONS
	 * <li>PUT
	 * <li>PATCH
	 * <li>DELETE
	 * <li>TRACE <br>
	 * are legal, subject to protocol restrictions. The default method is GET.
	 * 
	 * @param method - the HTTP method
	 * 
	 * @throws ProtocolException if the method cannot be reset or if the requested method isn't valid for HTTP.
	 * @see #getRequestMethod()
	 */
	@Override
	public void setRequestMethod(String method) throws ProtocolException {
		if (connected) {
			throw new ProtocolException("Can't reset method: already connected");
		}

		for (String methods : REST_METHODS) {
			if (methods.equals(method)) {
				if (method.equals("TRACE")) {
					SecurityManager s = System.getSecurityManager();
					if (s != null) {
						s.checkPermission(new NetPermission("allowHttpTrace"));
					}
				}
				method = methods;
				return;
			}
		}
		throw new ProtocolException("Invalid HTTP method: " + method);
	}

	/**
	 * Get the request method.
	 * 
	 * @return the HTTP request method
	 * @see #setRequestMethod(String)
	 */
	@Override
	public String getRequestMethod() {
		return method;
	}

	public void addRequestProperty(String arg0, String arg1) {
		delegate.addRequestProperty(arg0, arg1);
	}

	public Object authObj() {
		return delegate.authObj();
	}

	public void authObj(Object arg0) {
		delegate.authObj(arg0);
	}

	public void connect() throws IOException {
		delegate.connect();
	}

	public void disconnect() {
		delegate.disconnect();
	}

	public void doTunneling() throws IOException {
		delegate.doTunneling();
	}

	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}

	public boolean getAllowUserInteraction() {
		return delegate.getAllowUserInteraction();
	}

	public String getCipherSuite() {
		return delegate.getCipherSuite();
	}

	public int getConnectTimeout() {
		return delegate.getConnectTimeout();
	}

	public Object getContent() throws IOException {
		return delegate.getContent();
	}

	public Object getContent(Class[] classes) throws IOException {
		return delegate.getContent(classes);
	}

	public String getContentEncoding() {
		return delegate.getContentEncoding();
	}

	public int getContentLength() {
		return delegate.getContentLength();
	}

	public long getContentLengthLong() {
		return delegate.getContentLengthLong();
	}

	public String getContentType() {
		return delegate.getContentType();
	}

	public CookieHandler getCookieHandler() {
		return delegate.getCookieHandler();
	}

	public long getDate() {
		return delegate.getDate();
	}

	public boolean getDefaultUseCaches() {
		return delegate.getDefaultUseCaches();
	}

	public boolean getDoInput() {
		return delegate.getDoInput();
	}

	public boolean getDoOutput() {
		return delegate.getDoOutput();
	}

	public InputStream getErrorStream() {
		return delegate.getErrorStream();
	}

	public long getExpiration() {
		return delegate.getExpiration();
	}

	public String getHeaderField(int arg0) {
		return delegate.getHeaderField(arg0);
	}

	public String getHeaderField(String arg0) {
		return delegate.getHeaderField(arg0);
	}

	public long getHeaderFieldDate(String name, long Default) {
		return delegate.getHeaderFieldDate(name, Default);
	}

	public int getHeaderFieldInt(String name, int Default) {
		return delegate.getHeaderFieldInt(name, Default);
	}

	public String getHeaderFieldKey(int arg0) {
		return delegate.getHeaderFieldKey(arg0);
	}

	public long getHeaderFieldLong(String name, long Default) {
		return delegate.getHeaderFieldLong(name, Default);
	}

	public Map<String, List<String>> getHeaderFields() {
		return delegate.getHeaderFields();
	}

	public long getIfModifiedSince() {
		return delegate.getIfModifiedSince();
	}

	public InputStream getInputStream() throws IOException {
		return delegate.getInputStream();
	}

	public boolean getInstanceFollowRedirects() {
		return delegate.getInstanceFollowRedirects();
	}

	public long getLastModified() {
		return delegate.getLastModified();
	}

	public Certificate[] getLocalCertificates() {
		return delegate.getLocalCertificates();
	}

	public OutputStream getOutputStream() throws IOException {
		return delegate.getOutputStream();
	}

	public Permission getPermission() throws IOException {
		return delegate.getPermission();
	}

	public int getReadTimeout() {
		return delegate.getReadTimeout();
	}

	public Map<String, List<String>> getRequestProperties() {
		return delegate.getRequestProperties();
	}

	public String getRequestProperty(String arg0) {
		return delegate.getRequestProperty(arg0);
	}

	public int getResponseCode() throws IOException {
		return delegate.getResponseCode();
	}

	public String getResponseMessage() throws IOException {
		return delegate.getResponseMessage();
	}

	public X509Certificate[] getServerCertificateChain() throws SSLPeerUnverifiedException {
		return delegate.getServerCertificateChain();
	}

	public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
		return delegate.getServerCertificates();
	}

	public URL getURL() {
		return delegate.getURL();
	}

	public boolean getUseCaches() {
		return delegate.getUseCaches();
	}

	public int hashCode() {
		return delegate.hashCode();
	}

	public boolean isConnected() {
		return delegate.isConnected();
	}

	public void setAllowUserInteraction(boolean allowuserinteraction) {
		delegate.setAllowUserInteraction(allowuserinteraction);
	}

	public void setAuthenticationProperty(String arg0, String arg1) {
		delegate.setAuthenticationProperty(arg0, arg1);
	}

	public void setChunkedStreamingMode(int chunklen) {
		delegate.setChunkedStreamingMode(chunklen);
	}

	public void setConnectTimeout(int arg0) {
		delegate.setConnectTimeout(arg0);
	}

	public void setConnected(boolean arg0) {
		delegate.setConnected(arg0);
	}

	public void setDefaultUseCaches(boolean defaultusecaches) {
		delegate.setDefaultUseCaches(defaultusecaches);
	}

	public void setDoInput(boolean doinput) {
		delegate.setDoInput(doinput);
	}

	public void setDoOutput(boolean dooutput) {
		delegate.setDoOutput(dooutput);
	}

	public void setFixedLengthStreamingMode(int contentLength) {
		delegate.setFixedLengthStreamingMode(contentLength);
	}

	public void setFixedLengthStreamingMode(long contentLength) {
		delegate.setFixedLengthStreamingMode(contentLength);
	}

	public void setIfModifiedSince(long ifmodifiedsince) {
		delegate.setIfModifiedSince(ifmodifiedsince);
	}

	public void setInstanceFollowRedirects(boolean followRedirects) {
		delegate.setInstanceFollowRedirects(followRedirects);
	}

	public void setNewClient(URL arg0, boolean arg1) throws IOException {
		delegate.setNewClient(arg0, arg1);
	}

	public void setNewClient(URL arg0) throws IOException {
		delegate.setNewClient(arg0);
	}

	public void setProxiedClient(URL arg0, String arg1, int arg2, boolean arg3) throws IOException {
		delegate.setProxiedClient(arg0, arg1, arg2, arg3);
	}

	public void setProxiedClient(URL arg0, String arg1, int arg2) throws IOException {
		delegate.setProxiedClient(arg0, arg1, arg2);
	}

	public void setReadTimeout(int arg0) {
		delegate.setReadTimeout(arg0);
	}

	public void setRequestProperty(String arg0, String arg1) {
		delegate.setRequestProperty(arg0, arg1);
	}

	public void setTunnelState(TunnelState arg0) {
		delegate.setTunnelState(arg0);
	}

	public void setUseCaches(boolean usecaches) {
		delegate.setUseCaches(usecaches);
	}

	public boolean streaming() {
		return delegate.streaming();
	}

	public String toString() {
		return delegate.toString();
	}

	public boolean usingProxy() {
		return delegate.usingProxy();
	}
}
