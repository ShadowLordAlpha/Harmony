package sla.harmony.discord.api.gateway.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Used to maintain an active gateway connection. Must be sent every heartbeat_interval milliseconds after the ready
 * payload is received. Note that this interval already has room for error, and that client implementations do not need
 * to send a heartbeat faster than what's specified. The inner d key must be set to the last seq (s) received by the
 * client. If none has yet been received you should send null (you cannot send a heartbeat before authenticating,
 * however).
 * 
 * @author Josh "ShadowLordAlpha"
 *
 */
@JsonTypeName("1")
public class PayloadHeartbeat extends Payload {

	@JsonProperty("d")
	private int d;
	
	public PayloadHeartbeat() {
		this.opcode = 1;
	}
	
	public void setSequence(int sequence) {
		this.d = sequence;
	}

	@Override
	public String toString() {
		return "PayloadHeartbeat [d=" + d + ", opcode=" + opcode + "]";
	}
}
