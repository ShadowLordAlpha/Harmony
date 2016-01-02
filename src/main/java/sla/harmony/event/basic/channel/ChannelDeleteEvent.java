package sla.harmony.event.basic.channel;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;

import sla.harmony.Harmony;
import sla.harmony.entity.Channel;
import sla.harmony.event.Event;
import sla.harmony.event.EventHandler;
import sla.harmony.event.EventType;

public class ChannelDeleteEvent implements Event {

	private Channel channel;

	public ChannelDeleteEvent(Harmony harmony, String json) {
		try {
			JsonNode jNode = Harmony.mapper.readTree(json);

			channel = harmony.getChannelList().getIfPresent(jNode.get("id").asText());
			harmony.getChannelList().invalidate(jNode.get("id").asText());

			if (channel != null) {
				// Final update of object though strictly not needed
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
		return EventType.CHANNEL_DELETE;
	}
	
	@FunctionalInterface
	public interface ChannelDeleteEventHandler extends EventHandler<ChannelDeleteEvent> {

		@Override
		public void onEvent(Harmony harmony, ChannelDeleteEvent event);
		
		@Override
		public default EventType getEventType() {
			return EventType.CHANNEL_DELETE;
		}
	}
}
