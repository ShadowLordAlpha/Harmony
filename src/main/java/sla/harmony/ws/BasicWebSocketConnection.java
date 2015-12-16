package sla.harmony.ws;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neovisionaries.ws.client.WebSocket;

import sla.harmony.Harmony;
import sla.harmony.event.HarmonyReadyEvent;
import sla.harmony.event.adv.MessageReceivedEvent;

public class BasicWebSocketConnection extends WebSocketConnection {
	
	private static final Logger logger = LoggerFactory.getLogger(BasicWebSocketConnection.class);

	private long sequence;
	
	public BasicWebSocketConnection(Harmony harmony, String serverURI) {
		super(harmony, serverURI);
	}
	
	@Override
	public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
		websocket.sendText(harmony.getMetadata());
		// harmony.getEventManager().throwEvent(new HarmonyReadyEvent());
	}
	
	@Override
	public void onTextMessage(WebSocket websocket, String text) throws Exception {
		
		JSONObject jobj = new JSONObject(text);
		
		if(jobj.getLong("s") != sequence++) {
			logger.warn("Message out of sequence! {} - {}", sequence, jobj.getLong("s"));
			sequence = jobj.getLong("s") + 1;
		}
		
		if(jobj.getInt("op") != 0) {
			logger.warn("Unknown opcode", jobj.getLong("op"));
			return;
		}
		
		JSONObject data = jobj.getJSONObject("d");
		
		switch(jobj.getString("t")) {
			case "READY": {
				// TODO stuff
				harmony.getEventManager().throwEvent(new HarmonyReadyEvent());
				break;
			}
			default:
				logger.warn("UNKNOWN SERVER EVENT: {}", jobj.toString(1));
				break;
		}
		
		if(text.contains("READY")) {
			JSONObject json = new JSONObject(text);
			startHeartbeat(json.getJSONObject("d").getInt("heartbeat_interval")); // this is needed to start the heartbeat thread
		}
		
		harmony.getEventManager().throwEvent(new MessageReceivedEvent(text));
	}
}
