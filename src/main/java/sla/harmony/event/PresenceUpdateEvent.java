package sla.harmony.event;

import org.json.JSONArray;
import org.json.JSONObject;

import sla.harmony.Harmony;
import sla.harmony.User;

public class PresenceUpdateEvent implements Event {

	private User user;
	private JSONArray role;
	private String guildId;
	
	public PresenceUpdateEvent(Harmony harmony, JSONObject data) {
		JSONObject user = data.getJSONObject("user");
		this.user = harmony.getUser(user.getString("id"));
		this.user.update(user);
		
		this.user.setStatus(data.getString("status"));
		role = data.optJSONArray("roles");
		guildId = data.getString("guild_id");
		this.user.setGame(data.optInt("game_id", -1)); // use -1 instead of null as a number can not be null
	}

	public User getUser() {
		return user;
	}

	public JSONArray getRole() {
		return role;
	}

	public String getGuildId() {
		return guildId;
	}
}
