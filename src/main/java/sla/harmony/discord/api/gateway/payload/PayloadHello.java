package sla.harmony.discord.api.gateway.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import sla.harmony.discord.api.gateway.Hello;

@JsonTypeName("10")
public class PayloadHello extends Payload {

	@JsonProperty("d")
	private Hello hello;

	private PayloadHello() {
		
	}

	public Hello getHello() {
		return hello;
	}

	@Override
	public String toString() {
		return "PayloadHello [hello=" + hello + ", opcode=" + opcode + "]";
	}
}
