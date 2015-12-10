package sla.harmony.websocket;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Heartbeat implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(Heartbeat.class);

	private boolean isBeating = false;
	private long heartbeat;
	private HarmonyWebSocketClient client;
	private JSONObject json = new JSONObject().put("op", 1);
	
	public Heartbeat(long heartbeat, HarmonyWebSocketClient client) {
		this.heartbeat = heartbeat;
		this.client = client;
	}
	
	public void stop() {
		isBeating = false;
	}
	
	@Override
	public void run() {
		isBeating = true;
		while(isBeating) {
			
			if(isBeating) {
				logger.debug("Sending Heartbeat.");
				client.send(json.put("d", System.currentTimeMillis()));
			}
			
			try {
				Thread.sleep(heartbeat);
			} catch (InterruptedException e) {
				logger.warn("Heartbeat Sleep Interrupted: ", e);
			}
		}
	}
}
