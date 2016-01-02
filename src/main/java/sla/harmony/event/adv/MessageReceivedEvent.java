package sla.harmony.event.adv;

import sla.harmony.Harmony;
import sla.harmony.event.Event;
import sla.harmony.event.EventHandler;
import sla.harmony.event.EventType;

public class MessageReceivedEvent implements Event {

	private String message;
	
	public MessageReceivedEvent(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	@Override
	public EventType getEventType() {
		return EventType.MESSAGE_RECEIVED;
	}
	
	@FunctionalInterface
	public interface MessageReceivedEventHandler extends EventHandler<MessageReceivedEvent> {
		
		@Override
		public void onEvent(Harmony harmony, MessageReceivedEvent event);
		
		@Override
		public default EventType getEventType() {
			return EventType.MESSAGE_RECEIVED;
		}
	}
}
