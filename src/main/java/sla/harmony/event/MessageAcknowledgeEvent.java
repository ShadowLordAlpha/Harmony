package sla.harmony.event;

import org.json.JSONObject;

public class MessageAcknowledgeEvent implements Event {

	private String channelId;
	private String messageId;
	
	public MessageAcknowledgeEvent(JSONObject jobj) {
		channelId = jobj.getString("channel_id");
		messageId = jobj.getString("message_id");
	}

	public String getChannelId() {
		return channelId;
	}

	public String getMessageId() {
		return messageId;
	}
}
