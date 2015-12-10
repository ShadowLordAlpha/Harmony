package sla.harmony.event;

import java.util.Date;

import org.json.JSONObject;

import sla.harmony.Harmony;
import sla.harmony.User;

public class TypingStartEvent implements Event {

	private User user;
	private Date timestamp;
	private String channelId;
	
	public TypingStartEvent(Harmony harmony, JSONObject jobj) {
		user = harmony.getUser(jobj.getString("user_id"));
		timestamp = new Date(jobj.getLong("timestamp") * 1000); // by default this timestamp is in seconds convert it to milis
		channelId = jobj.getString("channel_id");
	}

	public User getUser() {
		return user;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getChannelId() {
		return channelId;
	}
	
	
}
