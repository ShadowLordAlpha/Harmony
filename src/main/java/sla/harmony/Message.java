package sla.harmony;

import org.json.JSONArray;
import org.json.JSONObject;

public class Message {

	private boolean tts;
	private String timestamp; // I'm not decoding this one -_-
	private String nonce; // not needed and not used by most bots
	private User[] mentions;
	private boolean mentionEveryone;
	private String id;
	// I'm ignoring these embeds for now
	private String editedTimestamp;
	private String content; // this is the message
	private String channelId;
	private User author;
	private JSONArray attachments;
	
	public Message(Harmony harmony, JSONObject jobj) {
		tts = jobj.getBoolean("tts");
		timestamp = jobj.getString("timestamp");
		nonce = jobj.optString("nonce", null);
		JSONArray jarr = jobj.getJSONArray("mentions");
		mentions = new User[jarr.length()];
		for(int i = 0; i < jarr.length(); i++) {
			mentions[i] = harmony.getUser(jarr.getJSONObject(i).getString("id"));
			mentions[i].update(jarr.getJSONObject(i));
		}
		mentionEveryone = jobj.getBoolean("mention_everyone");
		id = jobj.getString("id");
		// embeds
		editedTimestamp = jobj.optString("edited_timestamp", null);
		content = jobj.getString("content");
		channelId = jobj.getString("channel_id");
		author = harmony.getUser(jobj.getJSONObject("author").getString("id"));
		author.update(jobj.getJSONObject("author"));
		attachments = jobj.getJSONArray("attachments");
		
		// Acknowledge that we have the message to discord (maybe let user do this?)
		HarmonyWebSocketClient.sendPostObj(String.format("https://discordapp.com/api/channels/%s/messages/%s/ack", channelId, id), harmony.getToken(), jobj);
	}

	public boolean isTts() {
		return tts;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getNonce() {
		return nonce;
	}

	public User[] getMentions() {
		return mentions;
	}

	public boolean isMentionEveryone() {
		return mentionEveryone;
	}

	public String getId() {
		return id;
	}

	public String getEditedTimestamp() {
		return editedTimestamp;
	}

	public String getContent() {
		return content;
	}

	public String getChannelId() {
		return channelId;
	}

	public User getAuthor() {
		return author;
	}

	public JSONArray getAttachments() {
		return attachments;
	}
}
