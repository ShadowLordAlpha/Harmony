package sla.harmony.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sla.harmony.Harmony;
import sla.harmony.event.adv.MessageReceivedEvent;

public class StartBot {

	private static final Logger logger = LoggerFactory.getLogger(StartBot.class);
	
	public static void main(String[] args) {
		Harmony harmony = new Harmony();
		harmony.getEventManager().addListener((MessageReceivedEvent event) -> logger.info(event.getMessage()));
		harmony.connect();
	}
}
