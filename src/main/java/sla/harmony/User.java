package sla.harmony;

import org.json.JSONObject;

public class User {
	
	private String username;
	private String id;
	private String discriminator;
	private String avatar;
	
	// these are per guild? maybe more per guild things stored in the guild channels?
	private String status;
	private int game;

	public User(JSONObject jobj) {
		id = jobj.getString("id");
		username = jobj.optString("username", null);
		discriminator = jobj.optString("discriminator", null);
		avatar = jobj.optString("avatar", null);
	}
	

	public String getUsername() {
		return username;
	}

	public String getId() {
		return id;
	}

	public String getDiscriminator() {
		return discriminator;
	}

	public String getAvatar() {
		return avatar;
	}
	
	public void update(JSONObject jobj) {
		// ID can never change for the same user
		username = jobj.optString("username", null);
		discriminator = jobj.optString("discriminator", null);
		avatar = jobj.optString("avatar", null);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return new JSONObject().put("user", new JSONObject().put("username", username).put("id", id).put("discriminator", discriminator).put("avatar", avatar)).toString();
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public int getGame() {
		return game;
	}


	public void setGame(int game) {
		this.game = game;
	}
}
