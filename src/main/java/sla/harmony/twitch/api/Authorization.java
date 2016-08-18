package sla.harmony.twitch.api;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Authorization {

	@JsonProperty("scopes")
	private String[] scopes;

	@JsonProperty("created_at")
	private String createdAt; // this is a date string

	@JsonProperty("updated_at")
	private String updatedAt; // this is a date string

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + Arrays.hashCode(scopes);
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Authorization other = (Authorization) obj;
		if(createdAt == null) {
			if(other.createdAt != null) return false;
		} else if(!createdAt.equals(other.createdAt)) return false;
		if(!Arrays.equals(scopes, other.scopes)) return false;
		if(updatedAt == null) {
			if(other.updatedAt != null) return false;
		} else if(!updatedAt.equals(other.updatedAt)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "Authorization [scopes=" + Arrays.toString(scopes) + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
}
