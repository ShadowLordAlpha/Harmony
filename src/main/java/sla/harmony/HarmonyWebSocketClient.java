package sla.harmony;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sla.harmony.event.HarmonyReadyEvent;
import sla.harmony.event.MessageCreateEvent;
import sla.harmony.event.PresenceUpdateEvent;
import sla.harmony.event.TypingStartEvent;

public class HarmonyWebSocketClient extends WebSocketClient {
	
	private static final Logger logger = LoggerFactory.getLogger(HarmonyWebSocketClient.class);

	private Harmony harmony;
	private HarmonyHeartbeat heart;
	private long sequence = 1;
	
	public HarmonyWebSocketClient(Harmony harmony, URI serverURI) {
		super(serverURI);
		this.harmony = harmony;
		this.connect();
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		logger.debug("Handshake Confirmed, Sending Client Metadata");
		this.send(harmony.getMetadata());
	}

	@Override
	public void onMessage(String message) {
		
		try {
			JSONObject jobj = new JSONObject(message);
			
			if(jobj.getInt("op") != 0) {
				logger.warn("Non-Zero op: {} - {}", jobj.getInt("op"), message);
			}
			
			if(sequence != jobj.getLong("s")) {
				logger.warn("Message out of order: {} - {}", jobj.getLong("s"), message);
				sequence = jobj.getLong("s");
			}
			sequence++;
			
			JSONObject data = jobj.getJSONObject("d");
			switch(jobj.getString("t")) {
				case "READY": {
					// TODO Processes the whole ready message
					
					// setup the heartbeat first
					heart = new HarmonyHeartbeat(data.getLong("heartbeat_interval"), this);
					new Thread(heart).start();
					
					harmony.setUserSettings(new UserSettings(data.getJSONObject("user_settings")));
					
					System.out.println(data);
					
					// TODO private channels
					
					// TODO more guild stuff
					
					JSONObject user = data.getJSONObject("user");
					harmony.setUserId(user.getString("id"));
					harmony.setAvatar(user.optString("avatar", null));
					harmony.setUsername(user.getString("username"));
					harmony.setDiscriminator(user.getString("discriminator"));
					harmony.setSessionId(data.getString("session_id"));
					harmony.getEventManager().throwEvent(new HarmonyReadyEvent(harmony));
					break;
				}
				case "MESSAGE_CREATE": {
					harmony.getEventManager().throwEvent(new MessageCreateEvent());
					break;
				}
				case "TYPING_START": {
					harmony.getEventManager().throwEvent(new TypingStartEvent(harmony, data));
					break;
				}
				case "PRESENCE_UPDATE": {
					harmony.getEventManager().throwEvent(new PresenceUpdateEvent(harmony, data));
					break;
				}
				default: {
					logger.info("Unknown Event: {}", message);
				}
				
			}
			
		} catch(Exception e) {
			logger.warn("Problem processing message:", e);
		}
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		logger.info("Close Code: {} Reason: {} Remote: {}", code, reason, remote);
		heart.stop();
		logger.info("Attempting Reconnect!");
		harmony.login();
	}

	@Override
	public void onError(Exception ex) {
		logger.error("Exception Thrown: ", ex);	
	}
	
	public void send(JSONObject jobj) {
		send(jobj.toString());
	}
	
	
	public static final String USER_AGENT = String.format("Harmony DiscordBot(%s, %s)", "N/A", "0.1.0");
	/**
	 * 
	 * @return
	 */
	public static JSONObject sendPostObj(String url, String token, JSONObject jobj) {
		
		try {
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
	
			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Harmony");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			if(token != null && !token.isEmpty()) {
				con.setRequestProperty("Authorization", token);
			}
			con.setRequestProperty("content-type", "application/json");
			
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(jobj.toString());
			wr.flush();
			wr.close();
	
			int responseCode = con.getResponseCode(); // TODO stuff with this later
	
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			return new JSONObject(response.toString());
		} catch(Exception e) {
			
		}
		
		return null;
	}
	
	public static JSONObject sendGetObj(String url, String token) {
		
		try {
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
	
			//add reuqest header
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Harmony");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			if(token != null && !token.isEmpty()) {
				con.setRequestProperty("Authorization", token);
			}
			con.setRequestProperty("content-type", "application/json");
	
			int responseCode = con.getResponseCode(); // TODO stuff with this later
	
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			return new JSONObject(response.toString());
		} catch(Exception e) {
			
		}
		
		return null;
	}
}
