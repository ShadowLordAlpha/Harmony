package sla.harmony.event;

import sla.harmony.Harmony;

/**
 * An almost useless event that is thrown after Harmony as finished processing the READY event from the Discord WebServer. This event contains
 * a reference to the Harmony instance that finished and nothing else.
 * 
 * @author Josh "ShadowLordAlpha"
 */
public class HarmonyReadyEvent implements Event {

	private Harmony harmony;
	
	public HarmonyReadyEvent(Harmony harmony) {
		this.harmony = harmony;
	}

	/**
	 * Get the Harmony instance
	 * @return The harmony instance
	 */
	public Harmony getHarmony() {
		return harmony;
	}
}
