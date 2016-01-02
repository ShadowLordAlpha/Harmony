package sla.harmony;

import java.io.IOException;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.module.mrbean.MrBeanModule;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import sla.harmony.entity.Channel;
import sla.harmony.entity.Guild;
import sla.harmony.entity.User;
import sla.harmony.event.EventManager;
import sla.harmony.exception.AuthenticationException;
import sla.harmony.rest.HttpConnection;
import sla.harmony.rest.HttpEndpoint;
import sla.harmony.util.Utils;
import sla.harmony.websocket.AdvWebSocketConnection;
import sla.harmony.websocket.BasicWebSocketConnection;
import sla.harmony.websocket.WebSocketConnection;

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
	 * The version string of Harmony. This changes every time any of the version numbers change.
	 */
	public static final String VERSION_STRING = String.format("v%d.%d.%d", VERSION_MAJOR, VERSION_MINOR, VERSION_PATCH);

	/**
	 * The Github URL of this project
	 */
	public static final String GITHUB_URL = "https://github.com/ShadowLordAlpha/Harmony";

	// TODO
	public static ObjectMapper mapper = new ObjectMapper().registerModule(new MrBeanModule());

	private HttpConnection rest;
	private WebSocketConnection ws;
	private EventManager eventManager = new EventManager();

	private Cache<String, Guild> guildList;
	private Cache<String, Channel> channelList;
	private Cache<String, User> userList;

	private String authToken;

	Harmony(String email, String password, Path dataPath, boolean advancedMode, boolean dataFileOverried) {

		rest = new HttpConnection(this);

		JsonNode jNode = null;
		try {
			jNode = mapper.readTree(dataPath.toFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (dataFileOverried || password == null) {
			password = jNode.get("password").asText(password);
		}

		if (dataFileOverried || email == null) {
			email = jNode.get("email").asText(email);
		}
		
		authToken = Utils.decrypt(password, jNode.get("token_enc").asText(null));

		String gatewayUrl = null;
		
		if (authToken != null) {
			logger.info("Attempting Token reuse.");
			try {
				gatewayUrl = mapper.readTree(rest.sendGetStr(HttpEndpoint.GATEWAY.getEndpoint(), null)).get("url").asText();
			} catch (AuthenticationException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(authToken == null || gatewayUrl == null) {
			logger.info("Token reuse failed! Attempting login.");
			try {
				authToken = mapper.readTree(rest.sendPostStr(HttpEndpoint.LOGIN.getEndpoint(), String.format("\"email\":\"%s\",\"password\":\"%s\"", email, password))).get("token").asText(null);
				gatewayUrl = mapper.readTree(rest.sendGetStr(HttpEndpoint.GATEWAY.getEndpoint(), null)).get("url").asText(null);
			} catch (AuthenticationException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(advancedMode) {
			ws = new AdvWebSocketConnection(this, gatewayUrl);
		} else {
			ws = new BasicWebSocketConnection(this, gatewayUrl);
		}
		
		try {
			((ObjectNode) jNode).put("token_enc", Utils.encrypt(password, authToken));
			mapper.writerWithDefaultPrettyPrinter().writeValue(dataPath.toFile(), jNode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getAuthToken() {
		return this.authToken;
	}

	public Cache<String, Guild> getGuildList() {
		if (guildList == null) {
			guildList = Caffeine.newBuilder().build();
		}
		return guildList;
	}
	
	public Cache<String, Channel> getChannelList() {
		if (channelList == null) {
			channelList = Caffeine.newBuilder().build();
		}
		return channelList;
	}
	
	public Cache<String, User> getUserList() {
		if (userList == null) {
			userList = Caffeine.newBuilder().build();
		}
		return userList;
	}

	public EventManager getEventManager() {
		return eventManager;
	}
}
