package sla.harmony.event;

public interface Event {

	public default EventType getEventType() {
		return EventType.UNKNOWN;
	}
}
