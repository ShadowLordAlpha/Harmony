package sla.harmony.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface Guild {

	@JsonProperty("afk_timeout")
	public int getAfkTimeout();
	
	@JsonProperty("joined_at")
	public String getJoinedAt(); // TODO j8 timestamp
	
	@JsonProperty("afk_channel_id")
	public String getAfkChannelId();
	
	@JsonProperty("id")
	public String getId();
	
	@JsonProperty("icon")
	public String getIcon();
	
	@JsonProperty("name")
	public String getName();
	
	@JsonProperty("roles")
	public Role[] getRoles();
	
	@JsonProperty("region")
	public String getRegion();
	
	@JsonProperty("embed_channel_id")
	public String getEmbedChannelId();
	
	@JsonProperty("embed_enabled")
	public boolean getEmbedEnabled();
	
	@JsonProperty("owner_id")
	public String getOwnerId();
}
