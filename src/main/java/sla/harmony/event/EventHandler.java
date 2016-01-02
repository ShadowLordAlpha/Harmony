package sla.harmony.event;

import sla.harmony.Harmony;

// @FunctionalInterface // Not marked as this to prevent problems
public interface EventHandler<E extends Event> {

	public void onEvent(Harmony harmony, E event);
	
	public EventType getEventType();
}
