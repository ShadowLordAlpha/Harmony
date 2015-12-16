package sla.harmony.guild;

import java.time.temporal.TemporalAccessor;

import org.json.JSONArray;
import org.json.JSONObject;

import sla.harmony.Harmony;
import sla.harmony.rest.Endpoint;
import sla.harmony.rest.HttpConnection;
import sla.harmony.user.User;
import sla.harmony.util.Utils;

public class Guild {

	private int afkTimeout;
	private TemporalAccessor joinedAt;
	private String afkChannelId;
	private String id;
	private String icon;
	private String name;
	// private Role[] roles;
	private String region;
	private String embedChannelId;
	private boolean embedEnabled;
	private String ownerId;
	
	public Guild() {
		
	}
		
	public int getAfkTimeout() {
		return afkTimeout;
	}


	public String getAfkChannelId() {
		return afkChannelId;
	}


	public String getId() {
		return id;
	}


	public String getIcon() {
		return icon;
	}


	public String getName() {
		return name;
	}


	public String getRegion() {
		return region;
	}


	public String getEmbedChannelId() {
		return embedChannelId;
	}


	public boolean isEmbedEnabled() {
		return embedEnabled;
	}


	public String getOwnerId() {
		return ownerId;
	}


	public Guild readObject(JSONObject jobj) {
		afkTimeout = jobj.optInt("afk_timeout", afkTimeout);
		String rawTime = jobj.optString("timestamp", null);
		if(rawTime != null || !rawTime.isEmpty()) {
			joinedAt = Harmony.TIME_FORMAT.parse(rawTime);
		}
		afkChannelId = jobj.optString("afk_channel_id", afkChannelId);
		id = jobj.optString("id", id);
		icon = jobj.optString("icon", icon);
		name = jobj.optString("name", name);
		// TODO roles stuff
		region = jobj.optString("region", region);
		embedChannelId = jobj.optString("embed_channel_id", embedChannelId);
		embedEnabled = jobj.optBoolean("embed_enabled", embedEnabled);
		ownerId = jobj.optString("owner_id", ownerId);
		return this;
	}
	
	public String writeObject() {
		// TODO figure this out
		return null;
	}
	
	public static Guild[] getGuilds(String token) {
		JSONArray jarr = getGuildsArr(token);
		Guild[] guilds = new Guild[jarr.length()];
		for(int i = 0; i < jarr.length(); i++) {
			guilds[i] = new Guild().readObject(jarr.getJSONObject(i));
		}
		return guilds;
	}
	
	public static JSONArray getGuildsArr(String token) {
		return new JSONArray(Utils.checkArrString(getGuildsStr(token)));
	}
	
	public static String getGuildsStr(String token) {
		return HttpConnection.sendGetStr(Endpoint.GET_GUILDS, token, null);
	}
	
	public static Guild createGuild(String token, String name) {
		return new Guild().readObject(createGuildObj(token, name));
	}
	
	public static JSONObject createGuildObj(String token, String name) {
		return new JSONObject(Utils.checkObjString(createGuildStr(token, name)));
	}

	public static String createGuildStr(String token, String name) {
		String data = String.format("{\"name\":\"%s\"}", name);
		return HttpConnection.sendPostStr(Endpoint.GUILD, token, data);
	}
	
	// TODO edit guild stuff
	
	public static void deleteGuild(String token, String guildId) {
		String endpoint = String.format(Endpoint.EDIT_GUILD, guildId);
		HttpConnection.sendDeleteStr(endpoint, token, null);
	}
	
	public static User[] getBans(String token, String guildId) {
		JSONArray jarr = getBansArr(token, guildId);
		User[] users = new User[jarr.length()];
		for(int i = 0; i < jarr.length(); i++) {
			users[i] = new User().readObject(jarr.getJSONObject(i)); // TODO pass harmony in and use that?
		}
		return users;
	}
	
	public static JSONArray getBansArr(String token, String guildId) {
		return new JSONArray(Utils.checkArrString(getBansStr(token, guildId)));
	}

	public static String getBansStr(String token, String guildId) {
		String endpoint = String.format(Endpoint.GET_BANS, guildId);
		return HttpConnection.sendGetStr(endpoint, token, null);
	}
	
	public static void addBan(String token, String guildId, String userId, int deleteMessageDays) {
		deleteMessageDays = Math.max(0, deleteMessageDays);
		String endpoint = String.format(Endpoint.SET_BAN + "?delete-message-days=%d", guildId, userId, deleteMessageDays);
		HttpConnection.sendPutStr(endpoint, token, null);
	}
	
	public static void removeBan(String token, String guildId, String userId) {
		String endpoint = String.format(Endpoint.SET_BAN, guildId, userId);
		HttpConnection.sendDeleteStr(endpoint, token, null);
	}
}
