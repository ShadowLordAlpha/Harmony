package sla.harmony.discord.api.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Identify {

	@JsonProperty("token")
	private String token;
	
	@JsonProperty("properties")
	private Properties properties;
	
	@JsonProperty("compress")
	private boolean compress;
	
	@JsonProperty("large_threshold")
	private int largeThreshold;
	
	@JsonProperty("shard")
	private int[] shard;

	public Identify() {
		
	}
	
	public String getToken() {
		return token;
	}

	public Identify setToken(String token) {
		this.token = token;
		return this;
	}

	public Properties getProperties() {
		return properties;
	}

	public Identify setProperties(Properties properties) {
		this.properties = properties;
		return this;
	}

	public boolean isCompress() {
		return compress;
	}

	public Identify setCompress(boolean compress) {
		this.compress = compress;
		return this;
	}

	public int getLargeThreshold() {
		return largeThreshold;
	}

	public Identify setLargeThreshold(int largeThreshold) {
		this.largeThreshold = largeThreshold;
		return this;
	}

	public int[] getShard() {
		return shard;
	}

	public Identify setShard(int shardId, int numShards) {
		if(shard == null) {
			shard = new int[2];
		}
		this.shard[0] = shardId;
		this.shard[1] = numShards;
		return this;
	}

	public static class Properties {

		@JsonProperty("$os")
		private String os;
		
		@JsonProperty("$browser")
		private String browser;
		
		@JsonProperty("$device")
		private String device;
		
		@JsonProperty("$referrer")
		private String referrer;
		
		@JsonProperty("$referring_domain")
		private String referringDomain;

		public Properties() {

		}

		public String getOs() {
			return os;
		}

		public Properties setOs(String os) {
			this.os = os;
			return this;
		}

		public String getBrowser() {
			return browser;
		}

		public Properties setBrowser(String browser) {
			this.browser = browser;
			return this;
		}

		public String getDevice() {
			return device;
		}

		public Properties setDevice(String device) {
			this.device = device;
			return this;
		}

		public String getReferrer() {
			return referrer;
		}

		public Properties setReferrer(String referrer) {
			this.referrer = referrer;
			return this;
		}

		public String getReferringDomain() {
			return referringDomain;
		}

		public Properties setReferringDomain(String referringDomain) {
			this.referringDomain = referringDomain;
			return this;
		}

	}
}
