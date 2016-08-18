package sla.harmony.twitch.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {

	@JsonProperty("valid")
	private boolean valid;

	@JsonProperty("authorization")
	private Authorization authorization;

	@JsonProperty("user_name")
	private String userName;

	@JsonProperty("client_id")
	private String clientId;

	private Token() {

	}

	public boolean isValid() {
		return valid;
	}

	public Authorization getAuthorization() {
		return authorization;
	}

	public String getUserName() {
		return userName;
	}

	public String getClientId() {
		return clientId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorization == null) ? 0 : authorization.hashCode());
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + (valid ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Token other = (Token) obj;
		if(authorization == null) {
			if(other.authorization != null) return false;
		} else if(!authorization.equals(other.authorization)) return false;
		if(clientId == null) {
			if(other.clientId != null) return false;
		} else if(!clientId.equals(other.clientId)) return false;
		if(userName == null) {
			if(other.userName != null) return false;
		} else if(!userName.equals(other.userName)) return false;
		if(valid != other.valid) return false;
		return true;
	}

	@Override
	public String toString() {
		return "Token [valid=" + valid + ", authorization=" + authorization + ", userName=" + userName + ", clientId=" + clientId + "]";
	}
}
