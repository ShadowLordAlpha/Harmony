package sla.harmony.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"permission_overwrites"})
public interface Channel {

	@JsonProperty("guild_id")
	public String getGuildId();
	
	@JsonProperty("id")
	public String getId();
	
	@JsonProperty("name")
	public String getName();
	
	// TODO permissionOverwrites
	
	@JsonProperty("topic")
	public String getTopic();
	
	@JsonProperty("position")
	public int getPosition();
	
	@JsonProperty("last_message_id")
	public String getLastMessageId();
	
	@JsonProperty("type")
	public String getType();
	
	@JsonProperty("is_private")
	public boolean isPrivate();
	
	@JsonProperty("recipient")
	public User getRecipient();
}
