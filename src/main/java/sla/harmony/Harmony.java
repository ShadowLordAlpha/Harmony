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
package sla.harmony;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sla.harmony.event.EventManager;
import sla.harmony.exception.AuthenticationException;
import sla.harmony.rest.Endpoint;
import sla.harmony.rest.HttpConnection;
import sla.harmony.util.Utils;
import sla.harmony.ws.WebSocketConnection;

/**
 * TODO most of this class needs to be readied up for 0.2.0 TODO documentation
 * 
 * @author Josh "ShadowLordAlpha"
 */
public class Harmony {

	/**
	 * SLF4J logger instance for this class.
	 */
	private static final Logger logger = LoggerFactory.getLogger(Harmony.class);

	/**
	 * The major version number of Harmony. This is incremented when the API is changed in non-compatible ways.
	 */
	public static final int VERSION_MAJOR = 0;

	/**
	 * The minor version number of Harmony. This is incremented when features are added to the API but it remains
	 * backwards compatible.
	 */
	public static final int VERSION_MINOR = 2;

	/**
	 * The patch version number of Harmony. This is incremented when a bug fix release is made that does not contain any
	 * API changes.
	 */
	public static final int VERSION_PATCH = 0;

	/**
	 * The Github URL of this project
	 */
	public static final String GITHUB_URL = "https://github.com/ShadowLordAlpha/Harmony";

	private String email;
	private String password;
	private File dataFile;
	private String token;
	private WebSocketConnection conn;
	private EventManager manager;

	private boolean connected;

	public Harmony() {
		this(null);
	}

	public Harmony(String password) {
		this(null, password);
	}

	public Harmony(String email, String password) {
		this.email = email;
		this.password = password;
		manager = new EventManager();
	}

	public Harmony setDataFile(String filePath) {
		if (filePath != null) {
			return setDataFile(new File(filePath));
		} else {
			return setDataFile((File) null);
		}
	}

	public Harmony setDataFile(File file) {
		this.dataFile = file;
		return this;
	}

	/**
	 * Establishes a connection to the Discord servers first checking if there is a valid token it can reuse from a
	 * previous session.
	 * 
	 * @return
	 * @throws AuthenticationException If the Discord Servers send back a 401 with login or after we have logged in.
	 * @throws JSONException If there is a syntax error in the source string or a duplicated key in one of the response
	 * messages.
	 */
	public Harmony connect() {

		logger.info("Attempting Login!");
		// TODO This is where it will check and set settings for each instance of Harmony
		// Currently only Advanced mode is ready

		// no data file set
		if (dataFile == null) {
			dataFile = new File("auth.json");
		}

		JSONObject json;

		try {
			json = new JSONObject(Utils.checkObjString(Utils.readFile(dataFile)));
		} catch (JSONException e) {
			json = new JSONObject();
		}

		// No password provided so look in data file for one.
		if (password == null) {
			password = json.optString("password", null);

			if (password == null) {
				throw new NullPointerException(
						"Password may not be null! Provide one in the data file or to Harmony directly.");
			}

			logger.warn("Password Loaded From File.");
		}

		// check to make sure emails are the same or given email is null/empty
		String fileEmail = json.optString("email"); // this will never return null and should not be empty
		if (fileEmail.equalsIgnoreCase(email) || email == null || email.isEmpty()) {
			try {
				email = fileEmail; // they are the same or email is wrong
				token = Utils.decrypt(password, json.optString("token_enc", null));
			} catch (NullPointerException e) {
				token = null;
			}
		}

		String gatewayUrl = null;
		// Attempt to access the gateway with the loaded token
		if (token != null) {
			try {
				json = HttpConnection.sendGetObj(Endpoint.GATEWAY, token, null);

				gatewayUrl = json.optString("url", null);
			} catch (AuthenticationException e) {
				token = null;
			}
		}

		// token is null or we failed to get a gateway
		if (token == null || gatewayUrl == null) {
			logger.info("Failed to reuse Token, Attempting full login.");

			// Send login info to Discord
			json = HttpConnection.sendPostObj(Endpoint.LOGIN, null,	new JSONObject().put("email", email).put("password", password).toString());
			token = json.getString("token");
			
			// Try to get the gateway again
			json = HttpConnection.sendGetObj(Endpoint.GATEWAY, token, null);
			gatewayUrl = json.getString("url");
		}

		connected = true;
		conn = new WebSocketConnection(this, gatewayUrl);

		logger.info("Login Successful");
		saveData();
		return this;
	}

	public void disconnect() {
		if (!connected) {
			return;
		}

		connected = false;
		conn.close();

		try {
			HttpConnection.sendPostStr(Endpoint.LOGOUT, token, String.format("{\"token\":\"%s\"}", token));
		} catch (Exception e) {
			logger.warn("Exception while Logging out: ", e);
		}

		saveData();
	}

	/*
	 * We need to store data but we also need to be able to get it later. If you password for Discord is compromised so
	 * is this data though if it is this should be the least of your worries.
	 */
	private void saveData() {

		JSONObject jobj;
		try {
			// This is to save any other data that might be in the file, must be JSON format though
			jobj = new JSONObject(Utils.checkObjString(Utils.readFile(dataFile)));
		} catch (JSONException e) {
			jobj = new JSONObject();
		}

		jobj.put("email", email);

		Object tokenEnc;
		try {
			tokenEnc = Utils.encrypt(password, token);
		} catch (Exception e) {
			logger.warn("Failed to Encrypt Token:", e);
			tokenEnc = JSONObject.NULL;
		}

		jobj.put("token_enc", tokenEnc);

		Utils.writeFile(dataFile, jobj.toString(1));
	}

	public boolean isConnected() {
		return this.connected;
	}

	public String getMetadata() {
		return String
				.format("{\"op\":2,\"d\":{\"token\":\"%s\",\"v\":3,\"properties\":{\"$os\":\"%s\",\"$browser\":\"Harmony\","
						+ "\"$device\": \"\",\"$referrer\":\"https://discordapp.com/@me\",\"$referring_domain\":\"discordapp.com\"}"
						+ ",\"large_threshold\":100,\"compress\":true}}", token, System.getProperty("os.name"));
	}

	public EventManager getEventManager() {
		return manager;
	}
}
