package sla.harmony.event;

import org.json.JSONObject;

public class MessageDeleteEvent implements Event {

	private String channelId;
	private String id; // I assume this is the message ID that was deleted
	
	
	public MessageDeleteEvent(JSONObject jobj) {
		channelId = jobj.getString("channel_id");
		id = jobj.getString("id");
	}

	public String getChannelId() {
		return channelId;
	}

	public String getId() {
		return id;
	}
}
