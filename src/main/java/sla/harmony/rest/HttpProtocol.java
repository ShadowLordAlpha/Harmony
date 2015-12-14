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

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;

/**
 * The HttpProtocol class represents the protocols that HttpConnection can use.
 * 
 * @version 1.0.0
 * @since Harmony v0.2.0
 * @author Josh "ShadowLordAlpha"
 */
public enum HttpProtocol {
	
	/**
	 * Enum constant representing the Get protocol.
	 */
	GET("Get", "GET", HttpGet.class),
	
	/**
	 * Enum constant representing the Post protocol.
	 */
	POST("Post", "POST", HttpPost.class),
	
	/**
	 * Enum constant representing the Head protocol.
	 */
	HEAD("Head", "HEAD", HttpHead.class),
	
	/**
	 * Enum constant representing the Options protocol.
	 */
	OPTIONS("Options", "OPTIONS", HttpOptions.class),
	
	/**
	 * Enum constant representing the Put protocol.
	 */
	PUT("Put", "PUT", HttpPut.class),
	
	/**
	 * Enum constant representing the Patch protocol.
	 */
	PATCH("Patch", "PATCH", HttpPatch.class),
	
	/**
	 * Enum constant representing the Delete protocol.
	 */
	DELETE("Delete", "DELETE", HttpDelete.class),
	
	/**
	 * Enum constant representing the Trace protocol.
	 */
	TRACE("Trace", "TRACE", HttpTrace.class);
	
	/**
	 * A nicely formatted for this protocol. This should be used for output only
	 */
	private String readableName;
	
	/**
	 * The actual name of this protocol.
	 */
	private String methodName;
	
	/**
	 * The class used to send this protocol.
	 */
	private Class<? extends HttpRequestBase> clazz;
	
	/**
	 * Private enum constructor for HttpProtocol
	 * 
	 * @param readableName A nicely formatted name for the protocol
	 * @param methodName The name of the protocol when using Http
	 */
	private HttpProtocol(String readableName, String methodName, Class<? extends HttpRequestBase> clazz) {
		this.readableName = readableName;
		this.methodName = methodName;
		this.clazz = clazz;
	}

	public String getReadableName() {
		return readableName;
	}

	public String getMethodName() {
		return methodName;
	}
	
	public Class<? extends HttpRequestBase> getRequestClass() {
		return clazz;
	}
	
	@Override
	public String toString() {
		return getReadableName();
	}
}
