package sla.harmony.bot;

import java.io.File;

import sla.harmony.Harmony;
import sla.harmony.event.adv.MessageReceivedEvent;
import sla.harmony.util.Utils;

public class LoggerBot {

	public static void main(String[] args) {
		Harmony harmony = new Harmony();
		harmony.getEventManager().addListener((MessageReceivedEvent event) -> Utils.appendFile(new File("WebsocketLogs.log"), event.getMessage() + "\n"));
		harmony.connect();
	}
}
