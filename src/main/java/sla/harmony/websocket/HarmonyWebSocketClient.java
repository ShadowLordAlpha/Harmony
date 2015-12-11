package sla.harmony.websocket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sla.harmony.Harmony;
import sla.harmony.UserSettings;
import sla.harmony.event.HarmonyReadyEvent;
import sla.harmony.event.MessageAcknowledgeEvent;
import sla.harmony.event.MessageCreateEvent;
import sla.harmony.event.MessageDeleteEvent;
import sla.harmony.event.MessageUpdateEvent;
import sla.harmony.event.PresenceUpdateEvent;
import sla.harmony.event.TypingStartEvent;

public class HarmonyWebSocketClient extends WebSocketClient {
	
	private static final Logger logger = LoggerFactory.getLogger(HarmonyWebSocketClient.class);

	private Harmony harmony;
	private Heartbeat heart;
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
					heart = new Heartbeat(data.getLong("heartbeat_interval"), this);
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
					harmony.getEventManager().throwEvent(new MessageCreateEvent(harmony, data));
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
				case "MESSAGE_ACK": {
					harmony.getEventManager().throwEvent(new MessageAcknowledgeEvent(data));
					break;
				}
				case "MESSAGE_DELETE": {
					harmony.getEventManager().throwEvent(new MessageDeleteEvent(data));
					break;
				}
				case "MESSAGE_UPDATE": {
					harmony.getEventManager().throwEvent(new MessageUpdateEvent(harmony, data));
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
}
