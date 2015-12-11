package sla.harmony.event;

import org.json.JSONObject;

import sla.harmony.Harmony;
import sla.harmony.Message;

public class MessageUpdateEvent implements Event {
	
	private Message message;

	public MessageUpdateEvent(Harmony harmony, JSONObject data) {
		message = new Message(harmony, data);
	}

	public Message getMessage() {
		return message;
	}
}
