package sla.harmony.twitch;

import org.junit.Test;
import sla.harmony.discord.api.DiscordApi;
import sla.harmony.twitch.api.TwitchApi;

public class TwitchTest {

	@Test
	public void auth() {
		
		TwitchApi twitch = new TwitchApi.Builder().setAccessToken("").buid();
	}
	
	@Test
	public void ingests() {
		TwitchApi twitch = new TwitchApi.Builder().setAccessToken("").buid();
		
		System.out.println(twitch.getIngests());
	}
	
	@Test
	public void auth2() {
		
		DiscordApi twitch = new DiscordApi.Builder().build();
	}
}
