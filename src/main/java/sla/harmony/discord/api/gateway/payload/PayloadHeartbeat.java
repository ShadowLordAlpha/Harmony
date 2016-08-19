/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 ShadowLordAlpha
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
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
