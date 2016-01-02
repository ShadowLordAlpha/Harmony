package sla.harmony.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"attachments", "embeds", "mentions"})
public interface Message {
	
	@JsonProperty("nonce")
	public String getNonce();
	
	// TODO attachments
	
	@JsonProperty("tts")
	public boolean getTts();
	
	// TODO embeds
	
	@JsonProperty("timestamp")
	public String getTimestamp(); // TODO maybe add the J8 timestamp modal as well?
	
	@JsonProperty("mention_everyone")
	public boolean getMentionEveryone();
	
	@JsonProperty("id")
	public String getId();
	
	@JsonProperty("edited_timestamp")
	public String getEditedTimestamp();
	
	@JsonProperty("author")
	public User getAuthor();
	
	@JsonProperty("content")
	public String getContent();
	
	@JsonProperty("channel_id")
	public String getChannelId();
	
	// TODO mentions???
}
