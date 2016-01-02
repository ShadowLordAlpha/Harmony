package sla.harmony.event;

import sla.harmony.Harmony;

public class HarmonyReadyEvent implements Event {
	
	public HarmonyReadyEvent(String json) {
		
	}
	
	@Override
	public EventType getEventType() {
		return EventType.READY;
	}
	
	@FunctionalInterface
	public interface HarmonyReadyEventHandler extends EventHandler<HarmonyReadyEvent> {
		
		@Override
		public void onEvent(Harmony harmony, HarmonyReadyEvent event);
		
		@Override
		public default EventType getEventType() {
			return EventType.READY;
		}
	}
}
