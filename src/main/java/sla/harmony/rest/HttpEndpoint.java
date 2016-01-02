package sla.harmony.rest;

public class HttpEndpoint {

	public static final HttpEndpoint DISCORD = new HttpEndpoint("https://discordapp.com");
	
	public static final HttpEndpoint API = new HttpEndpoint("https://discordapp.com/api");
	
	public static final HttpEndpoint GATEWAY = new HttpEndpoint("https://discordapp.com/api/gateway");
	
	public static final HttpEndpoint LOGIN = new HttpEndpoint("https://discordapp.com/api/auth/login");
	
	public static final HttpEndpoint LOGOUT = new HttpEndpoint("https://discordapp.com/api/auth/logout");
	
	private String text;
	private boolean format;
	
	private HttpEndpoint(String text) {
		this(text, false);
	}
	
	private HttpEndpoint(String text, boolean format) {
		this.text = text;
		this.format = format;
	}
	
	public String getEndpoint(Object... args) {
		if(format) {
			return String.format(text, args);
		} else {
			return text;
		}
	}
}
