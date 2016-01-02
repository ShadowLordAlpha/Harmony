package sla.harmony.test;

import java.io.IOException;

import org.junit.Test;

import sla.harmony.Harmony;
import sla.harmony.HarmonyBuilder;
import sla.harmony.entity.Guild;
import sla.harmony.entity.Message;
import sla.harmony.entity.User;

public class Basic {

	@Test
	public void test() {
		HarmonyBuilder hb = new HarmonyBuilder("", "");
		Harmony harmony = hb.build();
	}
	
	//@Test
	public void test2() {
		String content = "{\r\n    \"nonce\": \"1453949470692605952\",\r\n    \"attachments\": [],\r\n    \"tts\": false,\r\n    \"embeds\": [],\r\n    \"timestamp\": \"2015-10-07T20:12:45.743000+00:00\",\r\n    \"mention_everyone\": false,\r\n    \"id\": \"111222333444555666\",\r\n    \"edited_timestamp\": null,\r\n    \"author\": {\r\n        \"username\": \"Test Account\",\r\n        \"discriminator\": \"1234\",\r\n        \"id\": \"111222333444555666\",\r\n        \"avatar\": \"31171c07640015bbc5aed21b28ea2408\"\r\n    },\r\n    \"content\": \"I'm a test message~\",\r\n    \"channel_id\": \"81384788765712384\",\r\n    \"mentions\": []\r\n}";
		
		Message message = null;
		
		try {
			message = Harmony.mapper.readValue(content, Message.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(message.getAuthor());
		
		content = "{\"afk_timeout\": 300,\r\n    \"joined_at\": \"2012-12-21T12:34:56.789012+00:00\",\r\n    \"afk_channel_id\": null,\r\n    \"id\": \"111222333444555666\",\r\n    \"icon\": null,\r\n    \"name\": \"Name\",\r\n    \"roles\": [\r\n        {\r\n            \"managed\": false,\r\n            \"name\": \"@everyone\",\r\n            \"color\": 0,\r\n            \"hoist\": false,\r\n            \"position\": -1,\r\n            \"id\": \"111222333444555666\",\r\n            \"permissions\": 12345678\r\n        }\r\n    ],\r\n    \"region\": \"us-west\",\r\n    \"embed_channel_id\": null,\r\n    \"embed_enabled\": false,\r\n    \"owner_id\": \"111222333444555666\"\r\n}";
		Guild guild = null;
		
		try {
			guild = Harmony.mapper.readValue(content, Guild.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(guild);
	}
}
