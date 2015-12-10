package sla.harmony;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import sla.harmony.event.EventManager;
import sla.harmony.rest.DiscordRequest;
import sla.harmony.websocket.HarmonyWebSocketClient;

public class Harmony {
	
	public static final String VERSION = "v0.1.4";
	public static final String GITHUB_URL = "https://github.com/ShadowLordAlpha/Harmony";
	
	private String email;
	private String password;
	private String token;
	private HarmonyWebSocketClient client;
	private UserSettings settings;
	
	private EventManager manager = new EventManager();
	
	private HashMap<String, User> userList = new HashMap<String, User>();
	
	public Harmony(File jsonFile) {
		
		String file = "";
		try(Scanner in = new Scanner(jsonFile)) {
			while(in.hasNextLine()) {
				file += in.nextLine();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		JSONObject jobj = new JSONObject(file);
		
		this.email = jobj.getString("email");
		this.password = jobj.getString("password");
	}
	
	public Harmony(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public Harmony login() {
		JSONObject jobj = new JSONObject().put("email", email).put("password", password);
		jobj = DiscordRequest.sendPostObj(DiscordRequest.DISCORD_URL_EXTENTION_LOGIN, null, jobj.toString());
		
		token = jobj.getString("token");
		
		jobj = DiscordRequest.sendGetObj("gateway", this, null);
		
		client = new HarmonyWebSocketClient(this, URI.create(jobj.getString("url").replace("wss", "ws")));
		return this;
	}
	
	public void logout() {
		JSONObject jobj = new JSONObject().put("token", token);
		DiscordRequest.sendPostObj(DiscordRequest.DISCORD_URL_EXTENTION_LOGOUT, this, jobj.toString());
		
		token = null;
	}
	
	public EventManager getEventManager() {
		return manager;
	}
	
	public void setUserSettings(UserSettings settings) {
		this.settings = settings;
	}
	
	public UserSettings getUserSettings() {
		return this.settings;
	}
	
	public User getUser(String userId) {
		User temp = userList.get(userId);
		if(temp == null) {
			temp = new User(new JSONObject().put("id", userId));
			userList.put(userId, temp);
		}
		return temp;
	}
	
	public JSONObject getMetadata() {
		return new JSONObject()
				.put("op", 2)
				.put("d", new JSONObject()
						.put("token", token)
						.put("v", 3)
						.put("properties", new JSONObject()
								.put("$os", System.getProperty("os.name"))
								.put("$browser", "Harmony")
								.put("$device", "Harmony")
								.put("$referring_domain", "")
								.put("$referrer", "")));
	}

	public void setUserId(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setAvatar(String optString) {
		// TODO Auto-generated method stub
		
	}

	public void setUsername(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setDiscriminator(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setSessionId(String string) {
		// TODO Auto-generated method stub
		
	}
	
	public JSONObject sendMessage(String channelId, String message) {
		JSONObject jobj = new JSONObject().put("content", message)/*.put("mentions", ???)*/.put("nonce", "HARMONYCLIENT").put("tts", false);
		return DiscordRequest.sendPostObj(String.format("channels/%s/messages", channelId), this, jobj.toString());
	}
	
	public void deleteMessage(Message message) {
		deleteMessage(message.getChannelId(), message.getId());
	}
	
	public JSONObject deleteMessage(String channelId, String messageId) {
		return DiscordRequest.sendDeleteObj(String.format("channels/%s/messages/%s", channelId, messageId), this, null);
	}
	
	public JSONArray getMessagesBefore(Message message) {
		return getMessagesBefore(message, 50);
	}
	
	public JSONArray getMessagesBefore(Message message, int limit) {
		return getMessagesBefore(message.getChannelId(), message.getId(), limit);
	}
	
	public JSONArray getMessagesBefore(String channelId, String messageId, int limit) {
		return DiscordRequest.sendGetArr(String.format("channels/%s/messages?before=%s&limit=%d", channelId, messageId, limit), this, null);
	}

	// TODO might not want this to be a method
	public String getToken() {
		return token;
	}
}
