package sla.harmony.websocket;

import com.neovisionaries.ws.client.WebSocket;

import sla.harmony.Harmony;
import sla.harmony.event.adv.MessageReceivedEvent;

public class AdvWebSocketConnection extends WebSocketConnection {

	public AdvWebSocketConnection(Harmony harmony, String serverUri) {
		super(harmony, serverUri);
	}

	@Override
	public void onTextMessage(WebSocket websocket, String text) throws Exception {
		// We just throw everything as this event
		harmony.getEventManager().throwEvent(harmony, new MessageReceivedEvent(text));
	}
}
