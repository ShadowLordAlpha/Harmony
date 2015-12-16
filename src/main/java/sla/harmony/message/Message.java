package sla.harmony.message;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import sla.harmony.rest.Endpoint;
import sla.harmony.rest.HttpConnection;
import sla.harmony.util.Utils;

public class Message {

	private static Random rand = new Random(); // TODO change to a faster random method
	
	private String nonce;
	// private Attachment[] attachments;
	private boolean tts;
	// private Embed embeds;
	// private ___ timestamp;
	private boolean mentionEveryone;
	private String id;
	// private ___ editedTimestamp;
	// private User author;
	private String content;
	private String channelId;
	// private User[] mentions; //??is this also being removed??
	
	public Message() {
		
	}
	
	public Message(String message) {
		this(message, false);
	}
	
	public Message(String message, boolean tts) {
		this(message, tts, "" + rand.nextLong());
	}
	
	public Message(String message, boolean tts, String nonce) {
		this.content = message;
		this.tts = tts;
		this.nonce = nonce;
	}
	
	public static Random getRand() {
		return rand;
	}

	public String getNonce() {
		return nonce;
	}

	public boolean isTts() {
		return tts;
	}

	public boolean isMentionEveryone() {
		return mentionEveryone;
	}

	public String getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public String getChannelId() {
		return channelId;
	}

	public Message readObject(JSONObject jobj) {
		nonce = jobj.optString("nonce", nonce);
		// TODO attachment stuff
		tts = jobj.optBoolean("tts", tts);
		// TODO embed stuff
		// TODO timestamp stuff
		mentionEveryone = jobj.optBoolean("mention_everyone", mentionEveryone);
		id = jobj.optString("id", id);
		// TODO editedTimestamp stuff
		// TODO author stuff
		content = jobj.optString("content", content);
		channelId = jobj.optString("channel_id", channelId);
		// TODO mention stuff
		return this;
	}
	
	public String writeObject() {
		JSONObject jobj = new JSONObject();
		jobj.putOpt("content", content); // The content of the message
		jobj.putOpt("nonce", nonce); // this nonce of the message
		jobj.putOpt("tts", tts); // Should the message be tts
		return jobj.toString();
	}
	
	// Static objects for sending editing deleting and getting messages from Discord.
	public static Message[] getMessages(String token, String channelId, String beforeId, String afterId, int limit) {
		JSONArray jarr = getMessagesArr(token, channelId, beforeId, afterId, limit);
		Message[] messages = new Message[jarr.length()];
		for(int i = 0; i < jarr.length(); i++) {
			messages[i] = new Message().readObject(jarr.getJSONObject(i));
		}
		return messages;
	}
	
	public static JSONArray getMessagesArr(String token, String channelId, String beforeId, String afterId, int limit) {
		return new JSONArray(Utils.checkArrString(getMessagesStr(token, channelId, beforeId, afterId, limit)));
	}
	
	public static String getMessagesStr(String token, String channelId, String beforeId, String afterId, int limit) {
		String endpoint = Endpoint.MESSAGE + "?" + (beforeId != null && !beforeId.isEmpty() ? "before=" + beforeId + "&" : "") + ((afterId != null && !afterId.isEmpty()) ? "after=" + afterId + "&":"") + "limit=" + Math.max(1, Math.min(100, limit));
		return HttpConnection.sendGetStr(String.format(endpoint, channelId), token, null);
	}
	
	public static Message createMessage(String token, String channelId, Message message) {
		return message.readObject(createMessageObj(token, channelId, message));
	}
	
	public static JSONObject createMessageObj(String token, String channelId, Message message) {
		return new JSONObject(Utils.checkObjString(createMessageStr(token, channelId, message)));
	}
	
	public static String createMessageStr(String token, String channelId, Message message) {
		String endpoint = String.format(Endpoint.MESSAGE, channelId);
		return HttpConnection.sendPostStr(endpoint, token, message.writeObject());
	}
	
	// TODO these need to change to something . . . else
	public static Message editMessage(String token, Message message, String newContent) {
		if(message.channelId == null || message.id == null) {
			throw new RuntimeException("Message had no id/channel id are you sure its an actual message?");
		}
		return message.readObject(editMessageObj(token, message.channelId, message.id, newContent));
	}
	
	public static JSONObject editMessageObj(String token, String channelId, String messageId, String content) {
		return new JSONObject(Utils.checkObjString(editMessageStr(token, channelId, messageId, content)));
	}
	
	public static String editMessageStr(String token, String channelId, String messageId, String content) {
		String endpoint = String.format(Endpoint.EDIT_MESSAGE, channelId, messageId);
		content = String.format("\"content\":\"%s\"", content);
		return HttpConnection.sendPatchStr(endpoint, token, content);
	}
	
	public static void deleteMessage(String token, String channelId, String messageId) {
		String endpoint = String.format(Endpoint.EDIT_MESSAGE, channelId, messageId);
		HttpConnection.sendDeleteStr(endpoint, token, null);
	}
	
	public static void ackMessage(String token, String channelId, String messageId) {
		String endpoint = String.format(Endpoint.ACK_MESSAGE, channelId, messageId);
		HttpConnection.sendPostStr(endpoint, token, null);
	}
}
