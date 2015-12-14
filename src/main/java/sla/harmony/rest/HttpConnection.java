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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static sla.harmony.util.Utils.*; // TODO remove this import and go though and place Utils where it needs to go

import sla.harmony.Harmony;
import sla.harmony.exception.AuthenticationException;

/**
 * HttpConnection handles most of the messages that need to be sent to the Discord Server. If it needs an endpoint it is
 * going through this class. Convenience methods are provided for converting from Strings to JSONObject's or
 * JSONArray's. If the method to send a protocol doesn't exist the message can still be send using the
 * {@link sendRequest} method.
 * 
 * @since Harmony v0.1.4
 * @version 2.0.0
 * @author Josh "ShadowLordAlpha"
 */
public class HttpConnection {

	/**
	 * SLF4J logger instance for this class.
	 */
	private static final Logger logger = LoggerFactory.getLogger(HttpConnection.class);

	/**
	 * Harmony user agent as requested by Discord Devs.
	 */
	private static final String USER_AGENT = String.format("Harmony DiscordBot(%s, v%d.%d.%d)", Harmony.GITHUB_URL,
			Harmony.VERSION_MAJOR, Harmony.VERSION_MINOR, Harmony.VERSION_PATCH);

	/**
	 * The HttpClient that sends all requests to Discord.
	 */
	private static HttpClient client = HttpClients.createDefault();

	/**
	 * Private constructor to prevent instances of this class.
	 */
	private HttpConnection() {

	}

	/**
	 * JSONObject Convince version of {@link sendGetStr}.
	 * 
	 * @param endpoint The endpoint where the Get request should be sent. May not be {@code null}.
	 * @param token The sending users token or {@code null} if not token required.
	 * @param data The data to send with the request or {@code null} if there is no data.
	 * @return a JSONObject constructed from the request's response.
	 * @throws AuthenticationException If and only if Discord returns a 401 status code in the reply message. This
	 * should be used to attempt to login or relog as needed.
	 */
	public static JSONObject sendGetObj(String endpoint, String token, String data) throws AuthenticationException {
		return new JSONObject(checkObjString(sendGetStr(endpoint, token, data)));
	}

	/**
	 * JSONArray Convince version of {@link sendGetStr}.
	 * 
	 * @param endpoint The endpoint where the Get request should be sent. May not be {@code null}.
	 * @param token The sending users tokenor {@code null} if not token required.
	 * @param data The data to send with the request or {@code null} if there is no data.
	 * @return a JSONArray constructed from the request's response.
	 * @throws AuthenticationException If and only if Discord returns a 401 status code in the reply message. This
	 * should be used to attempt to login or relog as needed.
	 */
	public static JSONArray sendGetArr(String endpoint, String token, String data) throws AuthenticationException {
		return new JSONArray(checkArrString(sendGetStr(endpoint, token, data)));
	}

	/**
	 * Send a Get Request to Discord.
	 * 
	 * @param endpoint The endpoint where the Get request should be sent. May not be {@code null}.
	 * @param token The sending users token or {@code null} if not token required.
	 * @param data The data to send with the request or {@code null} if there is no data.
	 * @return the request's response message or {@code null} if there was no response message.
	 * @throws AuthenticationException If and only if Discord returns a 401 status code in the reply message. This
	 * should be used to attempt to login or relog as needed.
	 */
	public static String sendGetStr(String endpoint, String token, String data) throws AuthenticationException {
		return sendRequest(endpoint, HttpProtocol.GET, token, data);
	}

	/**
	 * JSONObject Convince version of {@link sendPostStr}.
	 * 
	 * @param endpoint The endpoint where the Post request should be sent. May not be {@code null}.
	 * @param token The sending users token or {@code null} if not token required.
	 * @param data The data to send with the request or {@code null} if there is no data.
	 * @return a JSONObject constructed from the request's response.
	 * @throws AuthenticationException If and only if Discord returns a 401 status code in the reply message. This
	 * should be used to attempt to login or relog as needed.
	 */
	public static JSONObject sendPostObj(String endpoint, String token, String data) throws AuthenticationException {
		return new JSONObject(checkObjString(sendPostStr(endpoint, token, data)));
	}

	/**
	 * JSONArray Convince version of {@link sendPostStr}.
	 * 
	 * @param endpoint The endpoint where the Post request should be sent. May not be {@code null}.
	 * @param token The sending users token or {@code null} if not token required.
	 * @param data The data to send with the request or {@code null} if there is no data.
	 * @return a JSONArray constructed from the request's response.
	 * @throws AuthenticationException If and only if Discord returns a 401 status code in the reply message. This
	 * should be used to attempt to login or relog as needed.
	 */
	public static JSONArray sendPostArr(String endpoint, String token, String data) throws AuthenticationException {
		return new JSONArray(checkArrString(sendPostStr(endpoint, token, data)));
	}

	/**
	 * Send a Post Request to Discord.
	 * 
	 * @param endpoint The endpoint where the Post request should be sent. May not be {@code null}.
	 * @param token The sending users token or {@code null} if not token required.
	 * @param data The data to send with the request or {@code null} if there is no data.
	 * @return the request's response message or {@code null} if there was no response message.
	 * @throws AuthenticationException If and only if Discord returns a 401 status code in the reply message. This
	 * should be used to attempt to login or relog as needed.
	 */
	public static String sendPostStr(String endpoint, String token, String data) throws AuthenticationException {
		return sendRequest(endpoint, HttpProtocol.POST, token, data);
	}

	/**
	 * JSONObject Convince version of {@link sendPatchStr}.
	 * 
	 * @param endpoint The endpoint where the Patch request should be sent. May not be {@code null}.
	 * @param token The sending users token or {@code null} if not token required.
	 * @param data The data to send with the request or {@code null} if there is no data.
	 * @return a JSONObject constructed from the request's response.
	 * @throws AuthenticationException If and only if Discord returns a 401 status code in the reply message. This
	 * should be used to attempt to login or relog as needed.
	 */
	public static JSONObject sendPatchObj(String endpoint, String token, String data) throws AuthenticationException {
		return new JSONObject(checkObjString(sendPatchStr(endpoint, token, data)));
	}

	/**
	 * JSONArray Convince version of {@link sendPatchStr}.
	 * 
	 * @param endpoint The endpoint where the Patch request should be sent. May not be {@code null}.
	 * @param token The sending users token or {@code null} if not token required.
	 * @param data The data to send with the request or {@code null} if there is no data.
	 * @return a JSONArray constructed from the request's response.
	 * @throws AuthenticationException If and only if Discord returns a 401 status code in the reply message. This
	 * should be used to attempt to login or relog as needed.
	 */
	public static JSONArray sendPatchArr(String endpoint, String token, String data) throws AuthenticationException {
		return new JSONArray(checkArrString(sendPatchStr(endpoint, token, data)));
	}

	/**
	 * Send a Patch Request to Discord.
	 * 
	 * @param endpoint The endpoint where the Patch request should be sent. May not be {@code null}.
	 * @param token The sending users token or {@code null} if not token required.
	 * @param data The data to send with the request or {@code null} if there is no data.
	 * @return the request's response message or {@code null} if there was no response message.
	 * @throws AuthenticationException If and only if Discord returns a 401 status code in the reply message. This
	 * should be used to attempt to login or relog as needed.
	 */
	public static String sendPatchStr(String endpoint, String token, String data) throws AuthenticationException {
		return sendRequest(endpoint, HttpProtocol.PATCH, token, data);
	}

	/**
	 * JSONObject Convince version of {@link sendDeleteStr}.
	 * 
	 * @param endpoint The endpoint where the Delete request should be sent. May not be {@code null}.
	 * @param token The sending users token or {@code null} if not token required.
	 * @param data The data to send with the request or {@code null} if there is no data.
	 * @return a JSONObject constructed from the request's response.
	 * @throws AuthenticationException If and only if Discord returns a 401 status code in the reply message. This
	 * should be used to attempt to login or relog as needed.
	 */
	public static JSONObject sendDeleteObj(String endpoint, String token, String data) throws AuthenticationException {
		return new JSONObject(checkObjString(sendDeleteStr(endpoint, token, data)));
	}

	/**
	 * JSONArray Convince version of {@link sendDeleteStr}.
	 * 
	 * @param endpoint The endpoint where the Delete request should be sent. May not be {@code null}.
	 * @param token The sending users token or {@code null} if not token required.
	 * @param data The data to send with the request or {@code null} if there is no data.
	 * @return a JSONArray constructed from the request's response.
	 * @throws AuthenticationException If and only if Discord returns a 401 status code in the reply message. This
	 * should be used to attempt to login or relog as needed.
	 */
	public static JSONArray sendDeleteArr(String endpoint, String token, String data) throws AuthenticationException {
		return new JSONArray(checkArrString(sendDeleteStr(endpoint, token, data)));
	}

	/**
	 * Send a Delete Request to Discord.
	 * 
	 * @param endpoint The endpoint where the Delete request should be sent. May not be {@code null}.
	 * @param token The sending users token or {@code null} if not token required.
	 * @param data The data to send with the request or {@code null} if there is no data.
	 * @return the request's response message or {@code null} if there was no response message.
	 * @throws AuthenticationException If and only if Discord returns a 401 status code in the reply message. This
	 * should be used to attempt to login or relog as needed.
	 */
	public static String sendDeleteStr(String endpoint, String token, String data) throws AuthenticationException {
		return sendRequest(endpoint, HttpProtocol.DELETE, token, data);
	}

	/**
	 * Sends a Request to Discord.
	 * <p>
	 * This method handles all normal execution exceptions, not all unhandled exceptions, internally other than the
	 * AuthenticationException which can be used by the client to signal that it needs to log in or re-log. When an
	 * Exception is encountered the code will log an exception and return null if it can not keep going or log a warning
	 * and keep running.
	 * 
	 * @param endpoint The endpoint where the request should be sent. May not be {@code null}.
	 * @param protocol The protocol being used to send the message. May not be {@code null}.
	 * @param token The sending users token or {@code null} if not token required.
	 * @param data The data to send with the request or {@code null} if there is no data.
	 * @return the request's response message or {@code null} if there was no response message or an error that could
	 * not be safely ignored.
	 * @throws AuthenticationException If and only if Discord returns a 401 status code in the reply message. This
	 * should be used to attempt to login or relog as needed.
	 */
	public static String sendRequest(String endpoint, HttpProtocol protocol, String token, String data)
			throws AuthenticationException {

		if (endpoint == null) {
			// Throw a null pointer for dev's
			throw new NullPointerException("Endpoint can not be null!");
		}

		if (protocol == null) {
			// Throw a null pointer for dev's
			throw new NullPointerException("Protocol can not be null!");
		}

		HttpRequestBase request = null;

		try {
			request = protocol.getRequestClass().getConstructor(String.class).newInstance(endpoint);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			logger.error("Failed to create class instance! Endpoint: {}, Protocol: {}:", endpoint,
					protocol.getReadableName(), e);
			return null;
		}

		request.setHeader("User-Agent", USER_AGENT); // Requested User-Agent by Discord Devs
		request.setHeader("Content-Type", "application/json"); // We always use JSON to send data
		request.setHeader("Accept-Encoding", "gzip"); // We allow gzipped things!
		if (token != null && !token.isEmpty()) {
			request.setHeader("Authorization", token); // The authorization token from login
		}

		if (data != null && !data.isEmpty() && ((protocol == HttpProtocol.POST) || (protocol == HttpProtocol.PATCH)
				|| (protocol == HttpProtocol.PUT))) { // we have extra data to send with the request
			((HttpEntityEnclosingRequestBase) request).setEntity(new StringEntity(data, ContentType.APPLICATION_JSON));
		}

		HttpResponse response = null;

		try {
			response = client.execute(request);
		} catch (IOException e) {
			logger.error("Failed to send request:", e);
			return null;
		}

		int code = response.getStatusLine().getStatusCode();

		if (code >= 400) {
			if (code == 401) {
				throw new AuthenticationException(code + " " + response.getStatusLine().getReasonPhrase());
			} else {
				logger.warn("Code: {}, Reason: {}.", code, response.getStatusLine().getReasonPhrase());
			}
		}

		System.out.println(response.getEntity().getClass()); // TODO remove this later
		if (response.getEntity() != null) {
			StringBuffer buffer = new StringBuffer();
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8))) {
				String line;
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}

				return buffer.toString();
			} catch (IOException e) {
				logger.error("Failed to Read Message Input Stream:", e);
				return null;
			}
		}

		return null;
	}
}
