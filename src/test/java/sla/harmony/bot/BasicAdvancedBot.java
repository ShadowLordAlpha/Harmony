package sla.harmony.bot;

import sla.harmony.Harmony;
import sla.harmony.event.EventHandler;
import sla.harmony.event.adv.MessageReceivedEvent;
import sla.harmony.message.Message;

public class BasicAdvancedBot {

	private static Harmony harmony;
	
	public static void main(String[] args) {
		harmony = new Harmony();
		harmony.getEventManager().addListener(new BasicAdvancedBot());
		harmony.connect();
	}
	
	@EventHandler
	public void onMessageRecevedEvent(MessageReceivedEvent event) {
		
		if(event.getMessage().contains("<#125132763870396417>")) {
			System.out.println("Message recieved, Attempt send");
			System.out.println(Message.createMessageStr(harmony.getToken(), "125132763870396417", new Message("Test message", true)));
		}
	}
}
