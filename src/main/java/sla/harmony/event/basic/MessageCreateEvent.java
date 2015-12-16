package sla.harmony.event.basic;

import org.json.JSONObject;

import sla.harmony.event.Event;
import sla.harmony.message.Message;

public class MessageCreateEvent implements Event {

	private Message message;
	
	public MessageCreateEvent(JSONObject data) {
		message = new Message().readObject(data);
	}
	
	public Message getMessage() {
		return message;
	}
	
	@FunctionalInterface
	public interface MessageCreateListener {
		
		public void listen(MessageCreateEvent event);
	}
}
