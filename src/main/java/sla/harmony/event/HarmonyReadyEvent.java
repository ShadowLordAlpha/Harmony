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
package sla.harmony.event;

/**
 * The HarmonyReadyEvent is the event that is thrown when Harmony is fully ready for user interaction. This is thrown at
 * different times depending on what mode Harmony is in.
 * <p>
 * <b>Basic Mode</b>
 * <p>
 * In basic mode this event is thrown only after Harmony has fully made its connections with Discord and fully processed
 * the READY event sent by Discord. Processing the READY event is when the majority of objects that are not messages are
 * created by Harmony.
 * <p>
 * <b>Advanced Mode</b>
 * <p>
 * In advanced mode this event will be thrown directly after Harmony has finished connecting with Discord. The READY
 * event will not be processed by Harmony and so most of its memory overhead will not exist.
 * 
 * @since Harmony v0.1.1
 * @version 1.0.0
 * @author Josh "ShadowLordAlpha"
 */
public class HarmonyReadyEvent implements Event {

	/**
	 * Creates a new HarmonyReadyEvent that can be thrown by the EventManager
	 */
	public HarmonyReadyEvent() {

	}
}
