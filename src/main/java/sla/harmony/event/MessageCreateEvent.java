package sla.harmony.event;

import org.json.JSONObject;

import sla.harmony.Harmony;
import sla.harmony.Message;

public class MessageCreateEvent implements Event {
	
	private Message message;
	
	public MessageCreateEvent(Harmony harmony, JSONObject jobj) {
		 message = new Message(harmony, jobj);
	}

	public Message getMessage() {
		return message;
	}
}
