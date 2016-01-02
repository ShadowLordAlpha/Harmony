package sla.harmony.websocket;

import com.neovisionaries.ws.client.WebSocket;

import sla.harmony.Harmony;

public class BasicWebSocketConnection extends WebSocketConnection {
	
	public BasicWebSocketConnection(Harmony harmony, String serverUri) {
		super(harmony, serverUri);
	}

	@Override
	public void onTextMessage(WebSocket websocket, String text) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
