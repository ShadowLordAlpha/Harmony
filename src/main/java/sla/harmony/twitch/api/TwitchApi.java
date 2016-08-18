package sla.harmony.twitch.api;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.TreeNode;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sla.harmony.base.Harmony;
import sla.harmony.twitch.api.blocks.Block;
import sla.harmony.twitch.api.blocks.Blocks;
import sla.harmony.twitch.api.ingests.Ingests;

// TODO: possibly add more convenience methods

public class TwitchApi {

	// this is the 'Accept' string to send to accept version 3 of the twitch api only
	private static final String apiVersion = "application/vnd.twitchtv.v3+json";
	private static final String baseUrl = "https://api.twitch.tv/kraken";
		
	private Token token;
	private String accessToken; // why do I have these separated
	
	// TODO: links to api that are generated with the token call
	
	private TwitchApi(Token token, String accessToken) {
		this.token = token;
		this.accessToken = accessToken;
	}
	
	// Blocks
	// TODO: these all need scope and authenticated checks
	private String getBlocks = "/users/%s/blocks";
	private String putBlock = "/users/%s/blocks/%s";
	
	// Get users block list
	public Blocks getBlocks() {
		
		Request.Builder rBuilder = new Request.Builder();
		rBuilder.header("Accept", apiVersion); // we only want to use version 3 for now
		rBuilder.header("Clinet-ID", token.getClientId()); // we don't want to be rate limited
		rBuilder.header("Authorization", "OAuth " + accessToken);
		rBuilder.url(baseUrl + String.format(getBlocks, token.getUserName())); // URL we are sending the request to
		rBuilder.get(); // we are sending a get type request
		
		try {
			Response response = Harmony.twitchClient.newCall(rBuilder.build()).execute();
			
			// TODO: check response code
			
			return Harmony.mapper.readValue(response.body().string(), Blocks.class);
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	// Add target to users block list
	public Block addBlock(String target) {
		
		Request.Builder rBuilder = new Request.Builder();
		rBuilder.header("Accept", apiVersion); // we only want to use version 3 for now
		rBuilder.header("Clinet-ID", token.getClientId()); // we don't want to be rate limited
		rBuilder.header("Authorization", "OAuth " + accessToken);
		rBuilder.url(baseUrl + String.format(putBlock, token.getUserName(), target)); // URL we are sending the request to
		rBuilder.put(RequestBody.create(Harmony.JSON, (String) null)); // we are sending a put type request
		
		try {
			Response response = Harmony.twitchClient.newCall(rBuilder.build()).execute();
			
			// TODO: check response code
			
			return Harmony.mapper.readValue(response.body().string(), Block.class);
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	// Delete target from users block list
	public void deleteBlock(String target) {
		
		Request.Builder rBuilder = new Request.Builder();
		rBuilder.header("Accept", apiVersion); // we only want to use version 3 for now
		rBuilder.header("Clinet-ID", token.getClientId()); // we don't want to be rate limited
		rBuilder.header("Authorization", "OAuth " + accessToken);
		rBuilder.url(baseUrl + String.format(putBlock, token.getUserName(), target)); // URL we are sending the request to
		rBuilder.delete(); // we are sending a delete type request
		
		try {
			Response response = Harmony.twitchClient.newCall(rBuilder.build()).execute();
			
			// TODO: check response code
			
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Channels
	
	// Get channel object
	// Get channel object
	// Get channels list of videos
	// Get channels list of following users
	// Get channels list of editors
	// Update channel object
	// Reset channels stream key
	// Start a commercial on channel
	// Get list of teams channel belongs to
	
	// Channel Feed
	
	// Chat
	
	// Follows
	
	// Games
	
	private String getGames = "/games/top";
	
	// Ingests (DONE)
	
	private String getIngests = "/ingests/";
	
	/**
	 * Get list of ingests.
	 * 
	 * @return a list of ingest objects.
	 */
	public Ingests getIngests() {
		
		Request.Builder rBuilder = new Request.Builder();
		rBuilder.header("Accept", apiVersion); // we only want to use version 3 for now
		rBuilder.header("Clinet-ID", token.getClientId()); // we don't want to be rate limited
		// no need to send authorization header here as we don't need to be authorized to use this
		rBuilder.url(baseUrl + getIngests); // URL we are sending the request to
		rBuilder.get(); // we are sending a get type request
		
		try {
			Response response = Harmony.twitchClient.newCall(rBuilder.build()).execute();
			// TODO: check for errors
			return Harmony.mapper.readValue(response.body().string(), Ingests.class);
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO: add failure null to docs
		return null;
	}
	
	// Root
	
	// Get top level links object and authorization status
	
	// Search
	
	// Streams
	
	// Subscriptions
	
	// Teams
	
	// Users
	
	// Videos
	
	
	
	
	
	
	
	
	
	
	
	
	

	// TODO: most of this class
	public static class Builder {
		
		private static final Logger LOG = LoggerFactory.getLogger(Builder.class);

		private String clientId;
		private String clientSecret;
		private String redirectUri;
		private String code;
		private String state;
		private List<Scope> scope;

		private String username; // used in Password Grant flow
		private String password; // used in Password Grant flow

		private String accessToken;

		public Builder() {
			// might need some things setup
		}

		public Builder setAccessToken(String token) {
			this.accessToken = token;
			return this;
		}

		public TwitchApi buid() {
			// We allow for all 3 different types of Grant Flow as well as cached token login

			// If we have been given a token we try and verify it first before a new login is attempted
			if(accessToken != null && !accessToken.isEmpty()) {

				Request request = new Request.Builder().header("Accept", apiVersion).header("Authorization", "OAuth " + accessToken).url("https://api.twitch.tv/kraken/").get().build();
				try {
					Response response = Harmony.twitchClient.newCall(request).execute();

					TreeNode node = Harmony.mapper.readTree(response.body().string()).get("token");
					Token token = Harmony.mapper.treeToValue(node, Token.class);
					
					if(token.isValid()) {
						LOG.info("Token Valid");
						return new TwitchApi(token, accessToken);
					}
				} catch(IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// TODO: the rest of this build class
			
			return null;
		}
	}
}
