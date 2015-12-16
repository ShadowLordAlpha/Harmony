package sla.harmony.user;

import org.json.JSONObject;

public class User {

	private String username;
	private String discriminator;
	private String id;
	private String avatar;

	public User() {

	}

	public String getUsername() {
		return username;
	}

	public String getDiscriminator() {
		return discriminator;
	}

	public String getId() {
		return id;
	}

	public String getAvatar() {
		return avatar;
	}

	public User readObject(JSONObject jobj) {
		username = jobj.optString("username", username);
		discriminator = jobj.optString("discriminator", discriminator);
		id = jobj.optString("id", id);
		avatar = jobj.optString("avatar", avatar);
		return this;
	}

	public String writeObject() {
		JSONObject jobj = new JSONObject();
		jobj.putOpt("id", id);
		jobj.putOpt("discriminator", discriminator);
		jobj.putOpt("username", username);
		jobj.putOpt("avatar", (avatar == null) ? JSONObject.NULL : avatar);
		return jobj.toString();
	}
}
