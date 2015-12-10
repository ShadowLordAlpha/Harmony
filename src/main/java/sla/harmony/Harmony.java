package sla.harmony;

import java.io.File;
import java.net.URI;
import java.util.HashMap;

import org.json.JSONObject;

import sla.harmony.event.EventManager;

public class Harmony {
	
	private String email;
	private String password;
	private String token;
	private HarmonyWebSocketClient client;
	private UserSettings settings;
	
	private EventManager manager = new EventManager();
	
	private HashMap<String, User> userList = new HashMap<String, User>();
	
	public Harmony(File jsonFile) {
		
	}
	
	public Harmony(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public Harmony login() {
		JSONObject jobj = new JSONObject().put("email", email).put("password", password);
		jobj = HarmonyWebSocketClient.sendPostObj("https://discordapp.com/api/auth/login", null, jobj);
		
		token = jobj.getString("token");
		
		jobj = HarmonyWebSocketClient.sendGetObj("https://discordapp.com/api/gateway", token);
		
		client = new HarmonyWebSocketClient(this, URI.create(jobj.getString("url").replace("wss", "ws")));
		return this;
	}
	
	public void logout() {
		JSONObject jobj = new JSONObject().put("token", token);
		HarmonyWebSocketClient.sendPostObj("https://discordapp.com/api/auth/logout", token, jobj);
		
		token = null;
	}
	
	public EventManager getEventManager() {
		return manager;
	}
	
	void setUserSettings(UserSettings settings) {
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
	
	JSONObject getMetadata() {
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
}
