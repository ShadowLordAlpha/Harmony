package sla.harmony.twitch.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({ "_links" })
public class User {

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("display_name")
	private String displayName;

	@JsonProperty("type")
	private String type;

	@JsonProperty("bio")
	private String bio;

	@JsonProperty("name")
	private String name;

	@JsonProperty("_id")
	private int id;

	@JsonProperty("logo")
	private String logo;

	@JsonProperty("created_at")
	private String createdAt;

	private User() {

	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getType() {
		return type;
	}

	public String getBio() {
		return bio;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public String getLogo() {
		return logo;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bio == null) ? 0 : bio.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
		result = prime * result + id;
		result = prime * result + ((logo == null) ? 0 : logo.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		User other = (User) obj;
		if(bio == null) {
			if(other.bio != null) return false;
		} else if(!bio.equals(other.bio)) return false;
		if(createdAt == null) {
			if(other.createdAt != null) return false;
		} else if(!createdAt.equals(other.createdAt)) return false;
		if(displayName == null) {
			if(other.displayName != null) return false;
		} else if(!displayName.equals(other.displayName)) return false;
		if(id != other.id) return false;
		if(logo == null) {
			if(other.logo != null) return false;
		} else if(!logo.equals(other.logo)) return false;
		if(name == null) {
			if(other.name != null) return false;
		} else if(!name.equals(other.name)) return false;
		if(type == null) {
			if(other.type != null) return false;
		} else if(!type.equals(other.type)) return false;
		if(updatedAt == null) {
			if(other.updatedAt != null) return false;
		} else if(!updatedAt.equals(other.updatedAt)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [updatedAt=" + updatedAt + ", displayName=" + displayName + ", type=" + type + ", bio=" + bio + ", name=" + name + ", id=" + id + ", logo=" + logo + ", createdAt=" + createdAt + "]";
	}

}
