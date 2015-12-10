package sla.harmony.bot;

import java.io.File;

import sla.harmony.Harmony;
import sla.harmony.event.EventHandler;
import sla.harmony.event.HarmonyReadyEvent;
import sla.harmony.event.MessageCreateEvent;

public class PingPongBot {

	private static Harmony harmony;
	
	public static void main(String[] args) {
		harmony = new Harmony(new File("login.json"));
		harmony.getEventManager().registerListener(new PingPongBot());
		harmony.login();
	}
	
	@EventHandler
	public void onMessageCreate(MessageCreateEvent event) {
		System.out.println("[" + event.getMessage().getChannelId() + "] <" + event.getMessage().getAuthor().getUsername() + "> " + event.getMessage().getContent());
		 if(event.getMessage().getContent().contains("$*ping")) {
			harmony.sendMessage(event.getMessage().getChannelId(), "pong");
		 } else if(event.getMessage().getContent().contains("$*pong")) {
			harmony.sendMessage(event.getMessage().getChannelId(), "ping");
		 }
	}
	
	@EventHandler
	public void onHarmonyReady(HarmonyReadyEvent event) {
		System.out.println("Harmony Ready!");
		
		System.out.println("PingPongBot Ready!");
	}
}
