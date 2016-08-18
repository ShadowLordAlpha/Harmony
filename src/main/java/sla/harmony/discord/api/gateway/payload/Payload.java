package sla.harmony.discord.api.gateway.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties({"s", "t"})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "op", defaultImpl = PayloadNull.class, visible = true)
@JsonSubTypes({
	@Type(PayloadDispatch.class),
	@Type(PayloadHeartbeat.class),
	@Type(PayloadIdentify.class),
	@Type(PayloadStatusUpdate.class),
	@Type(PayloadVoiceStateUpdate.class),
	@Type(PayloadVoiceServerPing.class),
	@Type(PayloadResume.class),
	@Type(PayloadReconnect.class),
	@Type(PayloadRequestGuildMembers.class),
	@Type(PayloadInvalidSession.class),	
	@Type(PayloadHello.class),
	@Type(PayloadHeartbeatAcknowledge.class)
	})
public abstract class Payload {

	@JsonProperty("op")
	protected int opcode;

	protected Payload() {

	}

	public int getOpcode() {
		return this.opcode;
	}

	@Override
	public String toString() {
		return "Payload [opcode=" + opcode + "]";
	}
}
