package sla.harmony.bot;

import java.io.File;

import sla.harmony.Harmony;
import sla.harmony.event.EventHandler;
import sla.harmony.event.HarmonyReadyEvent;
import sla.harmony.event.MessageCreateEvent;
import sla.harmony.event.PresenceUpdateEvent;
import sla.harmony.event.TypingStartEvent;

public class PingPongBot {

	public static void main(String[] args) {
		Harmony harmony = new Harmony(new File("login.json"));
		harmony.getEventManager().registerListener(new PingPongBot());
		harmony.login();
	}
	
	@EventHandler
	public void onMessageCreate(MessageCreateEvent event) {
		// System.out.println("PING-PONG");
	}
	
	@EventHandler
	public void onHarmonyReady(HarmonyReadyEvent event) {
		System.out.println("Bot Ready!");
	}
	
	@EventHandler
	public void onTypingStart(TypingStartEvent event) {
		System.out.println("[" + event.getTimestamp() + "] <cid:" + event.getChannelId() + "> <" + event.getUser().getUsername() + "> is typing.");
	}
	
	@EventHandler
	public void onPresenceUpdate(PresenceUpdateEvent event) {
		System.out.println("<" + event.getUser().getUsername() + ":" + event.getUser().getId() + "> Status: " + event.getUser().getStatus() + " Game: " + event.getUser().getGame() + " Guild: " + event.getGuildId());
	}
}
