package sla.harmony.discord.api.gateway.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonIgnoreProperties({ "s", "t", "d" })
@JsonTypeName("11")
public class PayloadHeartbeatAcknowledge extends Payload {

	private PayloadHeartbeatAcknowledge() {
		
	}
	
	@Override
	public String toString() {
		return "PayloadHeartbeatAcknowledge [opcode=" + opcode + "]";
	}

}
