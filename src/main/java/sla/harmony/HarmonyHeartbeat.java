package sla.harmony;

import org.json.JSONObject;

public class HarmonyHeartbeat implements Runnable {

	private boolean isBeating = false;
	private long heartbeat;
	private HarmonyWebSocketClient client;
	
	public HarmonyHeartbeat(long heartbeat, HarmonyWebSocketClient client) {
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
			
			try {
				Thread.sleep(heartbeat);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(isBeating) {
				//System.out.println("Sending Heartbeat");
				client.send(new JSONObject().put("op", 1).put("d", System.currentTimeMillis()));
			}
		}
	}
}
