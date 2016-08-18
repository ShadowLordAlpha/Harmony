package sla.harmony.discord.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {

	@JsonProperty("token")
	private String token;

	private Token() {

	}

	public String getToken() {
		return token;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Token other = (Token) obj;
		if(token == null) {
			if(other.token != null) return false;
		} else if(!token.equals(other.token)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "Token [token=" + token + "]";
	}

}
