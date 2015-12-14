package sla.harmony.ws;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import sla.harmony.Harmony;
import sla.harmony.event.HarmonyReadyEvent;
import sla.harmony.event.adv.MessageReceivedEvent;

public class WebSocketConnection extends WebSocketAdapter {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketConnection.class);

	private Harmony harmony;
	private WebSocket webSocket;
	private Thread heartThread;

	public WebSocketConnection(Harmony harmony, String serverURI) {
		this.harmony = harmony;
		try {
			webSocket = new WebSocketFactory().createSocket(URI.create(serverURI)).addHeader("Accept-Encoding", "gzip")
					.addListener(this).connect();
		} catch (WebSocketException | IOException e) {
			logger.warn("WebSocket Exception: ", e);
		}
	}

	public void close() {
		webSocket.disconnect("Client Logout");
	}

	// TODO are these two even needed?
	public void send(JSONObject jobj) {
		send(jobj.toString());
	}

	public void send(String text) {
		webSocket.sendText(text);
	}
	
	public void startHeartbeat(int time) {
		
		// I can't use lambda here :'(
		(heartThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				logger.debug("Starting Connection Heartbeat.");
				while(harmony.isConnected()) {
					logger.debug("Sending Heartbeat.");
					send(String.format("{\"op\":1,\"d\":%d}", System.currentTimeMillis()));
					
					try {
						Thread.sleep(time);
					} catch (InterruptedException e) {
						logger.warn("Heartbeat Interrupted:", e);
					}
				}
			}
		}, "HeartbeatThread")).start();
	}

	@Override
	public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
		websocket.sendText(harmony.getMetadata());
		harmony.getEventManager().throwEvent(new HarmonyReadyEvent());
	}

	@Override
	public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame,
			boolean closedByServer) throws Exception {
		if (closedByServer && harmony.isConnected()) {
			// attempt to reconnect using exponential time x times
		}

		logger.info("Websocket Connection Closed. By Server: ", closedByServer);
	}

	@Override
	public void onTextMessage(WebSocket websocket, String text) throws Exception {
		// We assume Advanced mode for Harmony v0.2.0 this WILL change in later versions
		
		if(heartThread == null && text.contains("READY")) {
			JSONObject json = new JSONObject(text);
			startHeartbeat(json.getJSONObject("d").getInt("heartbeat_interval")); // this is needed to start the heartbeat thread
		}
		
		harmony.getEventManager().throwEvent(new MessageReceivedEvent(text));
	}

	@Override
	public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {

		// Get the compressed message and inflate it
		String data = "";
		Inflater decompresser = new Inflater();
		decompresser.setInput(binary, 0, binary.length);
		byte[] result = new byte[100];
		while(!decompresser.finished()) {
			int resultLength = decompresser.inflate(result);
			data += new String(result, 0, resultLength, "UTF-8");
		}
		decompresser.end();
		
		// send the inflated message to the TextMessage method
		onTextMessage(websocket, data);
	}

	@Override
	public void onUnexpectedError(WebSocket websocket, WebSocketException cause) throws Exception {
		logger.warn("Unexpected Exception in WebSocket: ", cause);
	}

	@Override
	public void handleCallbackError(WebSocket websocket, Throwable cause) throws Exception {
		logger.warn("WebSocket Exception: ", cause);
	}
}
