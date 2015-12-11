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
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import sla.harmony.Harmony;

/**
 * 
 * 
 * @since Harmony v0.1.4
 * @version 1.0.0
 * @author Josh "ShadowLordAlpha"
 */
public class DiscordRequest {

	private static final String PROTOCOL = "https";
	private static final String DISCORD_URL_API = "https://discordapp.com/api/";
	public static final String DISCORD_URL_EXTENTION_LOGIN = "auth/login";
	public static final String DISCORD_URL_EXTENTION_LOGOUT = "auth/logout";
	private static final String USER_AGENT = String.format("Harmony DiscordBot(%s, %s)", Harmony.GITHUB_URL,
			Harmony.VERSION);
	

	private DiscordRequest() {

	}
	
	public static JSONArray sendGetArr(String ext, Harmony harmony, String data) {
		try {
			return new JSONArray(sendRequest(ext, "GET", harmony, data));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static JSONObject sendGetObj(String ext, Harmony harmony, String data) {
		try {
			return new JSONObject(sendRequest(ext, "GET", harmony, data));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static JSONArray sendPostArr(String ext, Harmony harmony, String data) {
		try {
			return new JSONArray(sendRequest(ext, "POST", harmony, data));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static JSONObject sendPostObj(String ext, Harmony harmony, String data) {
		try {
			return new JSONObject(sendRequest(ext, "POST", harmony, data));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void sendPost(String ext, Harmony harmony, String data) {
		try {
			sendRequest(ext, "POST", harmony, data);
		} catch (Exception e) {
			
		}
	}
	
	public static JSONArray sendPatchArr(String ext, Harmony harmony, String data) {
		try {
			return new JSONArray(sendRequest(ext, "PATCH", harmony, data));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static JSONObject sendPatchObj(String ext, Harmony harmony, String data) {
		try {
			return new JSONObject(sendRequest(ext, "PATCH", harmony, data));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static JSONArray sendDeleteArr(String ext, Harmony harmony, String data) {
		try {
			return new JSONArray(sendRequest(ext, "DELETE", harmony, data));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static JSONObject sendDeleteObj(String ext, Harmony harmony, String data) {
		try {
			return new JSONObject(sendRequest(ext, "DELETE", harmony, data));
		} catch (Exception e) {
			return null;
		}
	}

	private static HttpClient client = HttpClients.createDefault();
	
	/**
	 * 
	 * 
	 * @param ext The extension to discords api url (discordapp.com/api/) for example auth/login
	 * @param requestType The type of request to send, one of "GET", "POST", "HEAD", "OPTIONS", "PUT", "PATCH", "DELETE", "TRACE"
	 * @param harmony the main api object
	 * @param data The data to send, if any, with the request. This should be in JSON format
	 * @return a string containing the reply from Discord
	 * @throws IOException
	 */
	private static String sendRequest(String ext, String requestType, Harmony harmony, String data) throws IOException {

		if(requestType.equals("PATCH")) {
			
			HttpPatch method = new HttpPatch(DISCORD_URL_API + ext);
			
			method.setHeader("User-Agent", USER_AGENT);
			method.setHeader("Content-Type", "application/json");
			method.setHeader("Authorization", harmony.getToken());
			
			method.setEntity(EntityBuilder.create().setText(data).build());
			
			HttpResponse response = client.execute(method);
			
			int code = response.getStatusLine().getStatusCode();
			System.out.println(code);
			BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
			String inputLine;
			StringBuilder response1 = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response1.append(inputLine);
			}
			
			in.close();
			return response1.toString();
		}
		
		URL url = new URL(DISCORD_URL_API + ext);
		// The built in URL version does not work here so use the direct one.
		RestURLConnection rcon = (RestURLConnection) new RestURLStreamHandler().openConnection(url);
		// Setup the REST header
		rcon.setRequestProperty("User-Agent", USER_AGENT);
		rcon.setRequestProperty("Content-Type", "application/json");
		if(!ext.equals(DISCORD_URL_EXTENTION_LOGIN)) {
			rcon.setRequestProperty("Authorization", harmony.getToken());
		}
		
		// Send data if there is any to send
		if(data != null && !data.isEmpty()) {
			rcon.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(rcon.getOutputStream());
			wr.write(data.getBytes(StandardCharsets.UTF_8));
			wr.flush();
			wr.close();
		}
		
		rcon.getResponseCode();
		// TODO may need to handle errors here?
		
		BufferedReader in = new BufferedReader(new InputStreamReader(rcon.getInputStream(), StandardCharsets.UTF_8));
		String inputLine;
		StringBuilder response = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		
		in.close();
		return response.toString();
	}
}
