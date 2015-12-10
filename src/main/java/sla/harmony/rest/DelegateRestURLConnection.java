package sla.harmony.rest;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import sun.net.www.protocol.http.Handler;
import sun.net.www.protocol.https.AbstractDelegateHttpsURLConnection;

public class DelegateRestURLConnection extends AbstractDelegateHttpsURLConnection {
	
	protected RestURLConnection rest;

	protected DelegateRestURLConnection(URL arg0, Handler arg1, RestURLConnection rest) throws IOException {
		super(arg0, arg1);
		
		this.rest = rest;
	}

	@Override
	protected HostnameVerifier getHostnameVerifier() {
		return rest.getHostnameVerifier();
	}

	@Override
	protected SSLSocketFactory getSSLSocketFactory() {
		return rest.getSSLSocketFactory();
	}

	protected void dispose() throws Throwable {
		super.finalize();
	}
}
