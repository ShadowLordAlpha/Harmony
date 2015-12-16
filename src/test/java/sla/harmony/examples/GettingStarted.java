package sla.harmony.examples;

import java.util.Scanner;

import sla.harmony.Harmony;
import sla.harmony.event.adv.MessageReceivedEvent;
import sla.harmony.event.EventHandler;
import sla.harmony.event.HarmonyReadyEvent;

public class GettingStarted {

	public static void main(String[] args) {
		Harmony harmony = new Harmony();
		harmony.getEventManager().addListener((HarmonyReadyEvent event) -> System.out.println("Harmony Ready!"));
		// While in 0.2.0 Harmony is locked in Advanced mode so we have to use this event
		harmony.getEventManager().addListener(new GettingStarted());
		harmony.connect();
		
		Scanner in = new Scanner(System.in);
		while(harmony.isConnected()) {
			String command = in.nextLine();
			if(command.equalsIgnoreCase("logout")) {
				harmony.disconnect();
			}
		}
		in.close();
	}
	
	@EventHandler
	public void handle(MessageReceivedEvent event) {
		System.out.println(event.getMessage());
	}
}
