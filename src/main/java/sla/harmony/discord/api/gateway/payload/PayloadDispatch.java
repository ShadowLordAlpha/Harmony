package sla.harmony.discord.api.gateway.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonIgnoreProperties({ "d" })
@JsonTypeName("0")
public class PayloadDispatch extends Payload {

	@JsonProperty("s")
	protected int sequence;

	@JsonProperty("t")
	protected String eventName;

	private PayloadDispatch() {

	}

	public int getSequence() {
		return sequence;
	}

	public String getEventName() {
		return eventName;
	}

	@Override
	public String toString() {
		return "PayloadDispatch [sequence=" + sequence + ", eventName=" + eventName + ", opcode=" + opcode + "]";
	}

}
