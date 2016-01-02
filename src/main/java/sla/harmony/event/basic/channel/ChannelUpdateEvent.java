package sla.harmony.event.basic.channel;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;

import sla.harmony.Harmony;
import sla.harmony.entity.Channel;
import sla.harmony.event.Event;
import sla.harmony.event.EventHandler;
import sla.harmony.event.EventType;

public class ChannelUpdateEvent implements Event {

	private Channel channel;
	
	public ChannelUpdateEvent(Harmony harmony, String json) {
		try {
			JsonNode jNode = Harmony.mapper.readTree(json);
			
			channel = harmony.getChannelList().getIfPresent(jNode.get("id").asText());
			
			if(channel != null) {
				Harmony.mapper.readerForUpdating(channel).readValue(json);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	@Override
	public EventType getEventType() {
		return EventType.CHANNEL_UPDATE;
	}
	
	@FunctionalInterface
	public interface ChannelUpdateEventHandler extends EventHandler<ChannelUpdateEvent> {
		
		@Override
		public void onEvent(Harmony harmony, ChannelUpdateEvent event);
		
		@Override
		public default EventType getEventType() {
			return EventType.CHANNEL_UPDATE;
		}
	}
}
