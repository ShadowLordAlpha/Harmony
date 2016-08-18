package sla.harmony.discord.api.gateway;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Hello {

	@JsonProperty("heartbeat_interval")
	private int heartbeatInterval;

	@JsonProperty("_trace")
	private String[] trace;

	private Hello() {

	}

	public int getHeartbeatInterval() {
		return heartbeatInterval;
	}

	public String[] getTrace() {
		return trace;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + heartbeatInterval;
		result = prime * result + Arrays.hashCode(trace);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Hello other = (Hello) obj;
		if(heartbeatInterval != other.heartbeatInterval) return false;
		if(!Arrays.equals(trace, other.trace)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "Hello [heartbeatInterval=" + heartbeatInterval + ", trace=" + Arrays.toString(trace) + "]";
	}

}
