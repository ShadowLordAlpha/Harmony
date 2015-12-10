package sla.harmony.bot;

import java.io.File;

import org.json.JSONArray;

import sla.harmony.Harmony;
import sla.harmony.event.EventHandler;
import sla.harmony.event.HarmonyReadyEvent;
import sla.harmony.event.MessageCreateEvent;

public class DeleteBot {

	private static Harmony harmony;
	
	public static void main(String[] args) {
		harmony = new Harmony(new File("login.json"));
		harmony.getEventManager().registerListener(new DeleteBot());
		harmony.login();
	}
	
	@EventHandler
	public void onMessageCreate(MessageCreateEvent event) {
		System.out.println("[" + event.getMessage().getChannelId() + "] <" + event.getMessage().getAuthor().getUsername() + "> " + event.getMessage().getContent());
		if(event.getMessage().getContent().contains("$*delete mass")) {
			JSONArray jarr = harmony.getMessagesBefore(event.getMessage(), 150);
			
			while(jarr != null && jarr.length() > 0) {
			
				for(int i = 0; i < jarr.length(); i++) {
					harmony.deleteMessage(jarr.getJSONObject(i).getString("channel_id"), jarr.getJSONObject(i).getString("id"));
				}
				jarr = harmony.getMessagesBefore(event.getMessage(), 150);
			}
			harmony.deleteMessage(event.getMessage());
		} else if(event.getMessage().getContent().contains("$*delete")) {
			harmony.deleteMessage(event.getMessage());
		}
	}
	
	@EventHandler
	public void onHarmonyReady(HarmonyReadyEvent event) {
		System.out.println("Harmony Ready!");
		
		System.out.println("DeleteBot Ready!");
	}
}
