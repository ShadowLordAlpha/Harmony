/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 ShadowLordAlpha
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
package sla.harmony.event.adv;

import sla.harmony.event.Event;
import sla.harmony.event.HarmonyReadyEvent;

/**
 * The HarmonyReadyEvent is the event that is thrown when Harmony is fully ready for user interaction. This is thrown at
 * different times depending on what mode Harmony is in.
 * <p>
 * <b>Basic Mode</b>
 * <p>
 * This event will not be thrown in Basic Mode
 * <p>
 * <b>Advanced Mode</b>
 * <p>
 * In advanced mode this event will be thrown whenever Harmony receives a message from Discord. Harmony will first
 * Inflate a message if needed before passing it on to this event.
 * 
 * @since Harmony v0.2.0
 * @version 1.0.0
 * @author Josh "ShadowLordAlpha"
 */
public class MessageReceivedEvent implements Event {

	private String message;
	/**
	 * Creates a new Event that can be thrown by the EventManager
	 */
	public MessageReceivedEvent(String message) {
		this.message = message;
	}
	
	/**
	 * Get the message received from Discord.
	 * @return The message that was received from Discord.
	 */
	public String getMessage() {
		return this.message;
	}
	
	@FunctionalInterface
	public interface MessageReveivedListener {
		
		public void listen(MessageReceivedEvent event);
	}
}
