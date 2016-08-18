package sla.harmony.twitch.api.blocks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import sla.harmony.twitch.api.User;

@JsonIgnoreProperties({ "_links" })
public class Block {

	@JsonProperty("_id")
	private int id;

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("user")
	private User user;

	private Block() {

	}

	public int getId() {
		return id;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public User getUser() {
		return user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Block other = (Block) obj;
		if(id != other.id) return false;
		if(updatedAt == null) {
			if(other.updatedAt != null) return false;
		} else if(!updatedAt.equals(other.updatedAt)) return false;
		if(user == null) {
			if(other.user != null) return false;
		} else if(!user.equals(other.user)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "Block [id=" + id + ", updatedAt=" + updatedAt + ", user=" + user + "]";
	}

}
