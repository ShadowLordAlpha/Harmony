package sla.harmony.event.basic.channel;

import java.io.IOException;

import sla.harmony.Harmony;
import sla.harmony.entity.Channel;
import sla.harmony.event.Event;
import sla.harmony.event.EventHandler;
import sla.harmony.event.EventType;

public class ChannelCreateEvent implements Event {
	
	private Channel channel;
	
	public ChannelCreateEvent(Harmony harmony, String json) {
		Channel channel = null;
		try {
			channel = Harmony.mapper.readValue(json, Channel.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.channel = channel;
		harmony.getChannelList().put(channel.getId(), channel);
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	@Override
	public EventType getEventType() {
		return EventType.CHANNEL_CREATE;
	}

	@FunctionalInterface
	public interface ChannelCreateEventHandler extends EventHandler<ChannelCreateEvent> {
		
		@Override
		public void onEvent(Harmony harmony, ChannelCreateEvent event);
		
		@Override
		public default EventType getEventType() {
			return EventType.CHANNEL_CREATE;
		}
	}
}
