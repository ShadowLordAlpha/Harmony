package sla.harmony.bot;

import java.io.File;

import org.json.JSONArray;

import sla.harmony.Harmony;
import sla.harmony.event.EventHandler;
import sla.harmony.event.MessageCreateEvent;

public class BasicBot {

	private static Harmony harmony;
	
	public static void main(String[] args) {
		
		harmony = new Harmony(new File("login.json"));
		harmony.getEventManager().registerListener(new BasicBot());
		// harmony.getEventManager().registerListener((HarmonyReadyEvent event) -> System.out.println("Harmoy Ready"));
		harmony.login();
		
	}
	
	@EventHandler
	public void onMessageCreate(MessageCreateEvent event) {
		System.out.println(event.getMessage().getTimestamp() + "] " + event.getMessage().getContent());
		if(event.getMessage().getChannelId().equalsIgnoreCase("106972441074483200")) {
			if(event.getMessage().getContent().startsWith("|deleteall")) {
				System.out.println(event.getMessage().getAuthor());
				JSONArray jarr = harmony.getMessagesBefore(event.getMessage(), 150);
				
				while(jarr != null && jarr.length() > 0) {
				
					for(int i = 0; i < jarr.length(); i++) {
						harmony.deleteMessage(jarr.getJSONObject(i).getString("channel_id"), jarr.getJSONObject(i).getString("id"));
					}
					jarr = harmony.getMessagesBefore(event.getMessage(), 150);
				}
				harmony.deleteMessage(event.getMessage());
			}
		} /*else {*/
			if(event.getMessage().getContent().startsWith("|google")) {
				String message = event.getMessage().getContent();
				if(event.getMessage().getMentions() != null) {
					
				}
				
				harmony.sendMessage(event.getMessage().getChannelId(), "http://lmgtfy.com/?q=" + message.replace("|google ", "").replace(" ", "+").trim());
			} else if(event.getMessage().getContent().startsWith("|updateinfo")) {
				
				harmony.deleteMessage(event.getMessage());
				
			} else if(event.getMessage().getContent().startsWith("|info")) {
				String message = event.getMessage().getAuthor().getUsername() + "\n```Java\nName: ???\n" + 
				"Servers: " + harmony.guildCache.estimatedSize() + "\n" +
				"Users: " + harmony.userCache.estimatedSize() + "\n" +
				"```";
				harmony.sendMessage(event.getMessage().getChannelId(), message);
			}
		/*}*/
	}
}
