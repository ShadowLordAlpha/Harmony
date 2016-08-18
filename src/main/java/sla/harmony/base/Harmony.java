package sla.harmony.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class Harmony {
	
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	public static OkHttpClient twitchClient = new OkHttpClient();
	public static ObjectMapper mapper = new ObjectMapper();
	public static String version = "0.3.0";
	
	public static void checkResponse(Response response) {
		if(response.isSuccessful()) {
			
		} else if(response.isRedirect()) {
			// TODO: i need to figure out how to handle this
		} else {
			throw new RuntimeException(String.format("Http Request Failed Code: %s - %s", response.code(), response.message()));
		}
	}
}
