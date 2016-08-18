package sla.harmony.discord.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.neovisionaries.ws.client.WebSocketState;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sla.harmony.base.Harmony;
import sla.harmony.discord.api.gateway.payload.Payload;
import sla.harmony.discord.api.gateway.payload.PayloadDispatch;
import sla.harmony.discord.api.gateway.payload.PayloadHeartbeat;
import sla.harmony.discord.api.gateway.payload.PayloadHello;

public class DiscordApi {

	private static final String BASE_URL = "https://discordapp.com/api";
	private static final String USER_AGENT = "DiscordBot (https://github.com/ShadowLordAlpha/Harmony, 0.3.0)";

	private static final String GATEWAY = "%s/gateway";

	private Token token;
	private String gatewayUrl;
	private WebSocket webSocket;

	private DiscordApi(Token token) {
		
		this.token = token;

		// Grab the gateway to connect to
		Request.Builder rBuilder = new Request.Builder();
		rBuilder.header("Authorization", token.getToken());
		rBuilder.header("Content-Type", "application/json");
		rBuilder.header("User-Agent", USER_AGENT);
		// rBuilder.header("Accept-Encoding", "gzip"); // TODO: accept gzip encoding
		rBuilder.url(String.format(GATEWAY, BASE_URL));
		rBuilder.get();

		try {
			Response response = Harmony.twitchClient.newCall(rBuilder.build()).execute();

			Harmony.checkResponse(response);
			gatewayUrl = Harmony.mapper.readTree(response.body().string()).findValue("url").asText() + "?encoding=json&v=6";
			System.out.println(gatewayUrl);
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			webSocket = new WebSocketFactory().setConnectionTimeout(60000).createSocket(gatewayUrl);
			webSocket.addListener(new DiscordWSListener());
			webSocket.addExtension(WebSocketExtension.PERMESSAGE_DEFLATE);
			webSocket.addHeader("Accept-Encoding", "gzip");
			webSocket.addHeader("Content-Type", "application/json");
			webSocket.connect();

		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(WebSocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void disconnect() {

	}

	private class DiscordWSListener extends WebSocketAdapter {
		
		private PayloadHeartbeat heartbeat = new PayloadHeartbeat();

		@Override
		public void onStateChanged(WebSocket websocket, WebSocketState newState) throws Exception {
			// System.out.println(newState);
		}

		@Override
		public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
			
			StringBuilder builder = new StringBuilder();
			builder.append("{\"op\":2,\"d\":{\"token\":\"");
			builder.append(token.getToken());
			builder.append("\",\"v\":5,\"properties\":{\"$os\":\"");
			builder.append(System.getProperty("os.name"));
			builder.append("\",\"$browser\":\"Harmony\",\"$device\": \"\",\"$referrer\":\"https://discordapp.com/@me\",\"$referring_domain\":\"discordapp.com\"}" + ",\"large_threshold\":100,\"compress\":false}}");
			
			websocket.sendText(builder.toString());
		}

		@Override
		public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {}

		@Override
		public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
			System.out.println("Connection closed" + clientCloseFrame.getCloseReason());
		}

		@Override
		public void onTextMessage(WebSocket websocket, String text) throws Exception {
			// System.out.println("< " + text);
			
			Payload payload = Harmony.mapper.readValue(text, Payload.class);
			
			System.out.println(payload);
			
			switch(payload.getOpcode()) {
				case 0: // Dispatch
					PayloadDispatch pDispatch = (PayloadDispatch) payload;
					heartbeat.setSequence(pDispatch.getSequence());
					break;
				case 1: // Heartbeat
					// Included for completion. Should never get
					break;
				case 2: // Identify
					// Included for completion. Should never get
					break;
				case 3: // Status Update
					break;
				case 4: // Voice State Update
					
					break;
				case 5: // Voice Server Ping
					
					break;
				case 6: // Resume
					// Included for completion. Should never get
					break;
				case 7: // Reconnect
					break;
				case 8: // Request Guild Members
					// Included for completion. Should never get
					break;
				case 9: // Invalid Session
					break;
				case 10: // Hello
					PayloadHello pHello = (PayloadHello) payload;
					
					Runnable heartbeatThread = () -> {
						while(websocket.isOpen()) {
							try {
								Thread.sleep(pHello.getHello().getHeartbeatInterval());
							} catch(InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							try {
								websocket.sendText(Harmony.mapper.writer().writeValueAsString(heartbeat));
							} catch(JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					new Thread(heartbeatThread, "heartbeat").start();
					
					break;
				case 11: // Heartbeat Acknowledge
					// We don't really need to do anything with this
					break;
				default:
					System.out.println("ERROR - Unknown opcode");
					break;
			}
		}

		@Override
		public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {
			
			System.out.println("Binary Message " + Arrays.toString(binary));
			
			/*BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new ByteArrayInputStream(binary))));
			StringBuffer buffer = new StringBuffer();
			String line;
			while((line = br.readLine()) != null) {
				buffer.append(line);
			}
			System.out.println(buffer.toString());
			onTextMessage(websocket, buffer.toString());*/
		}

		@Override
		public void onFrameSent(WebSocket websocket, WebSocketFrame frame) throws Exception {
			System.out.println("> " + frame.getPayloadText());
		}

		@Override
		public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
			System.out.println(cause);
		}

		@Override
		public void onFrameError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {}

		@Override
		public void onMessageError(WebSocket websocket, WebSocketException cause, List<WebSocketFrame> frames) throws Exception {}

		@Override
		public void onMessageDecompressionError(WebSocket websocket, WebSocketException cause, byte[] compressed) throws Exception {
			System.out.println("This?");
		}

		@Override
		public void onTextMessageError(WebSocket websocket, WebSocketException cause, byte[] data) throws Exception {
			System.out.println("Message Error");
		}

		@Override
		public void onSendError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {
			System.out.println("Send Error");
		}

		@Override
		public void onUnexpectedError(WebSocket websocket, WebSocketException cause) throws Exception {
			System.out.println("Error");
		}

		@Override
		public void handleCallbackError(WebSocket websocket, Throwable cause) throws Exception {
			System.out.println("Exception" + cause);
		}

		@Override
		public void onSendingHandshake(WebSocket websocket, String requestLine, List<String[]> headers) throws Exception {
			// System.out.println("Sending Handshake");
		}
	}

	public static class Builder {

		private String token;
		private String email;
		private String password;

		public Builder() {

		}

		public void setBotToken(String botToken) {
			this.token = "Bot " + botToken;
		}

		public void setUserToken(String userToken) {
			this.token = "Bearer " + userToken;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public DiscordApi build() {

			Request.Builder rBuilder = new Request.Builder();
			rBuilder.header("Authorization", token);
			rBuilder.header("Content-Type", "application/json");
			rBuilder.header("User-Agent", USER_AGENT);
			// rBuilder.header("Accept-Encoding", "gzip"); // TODO: accept gzip encoding

			rBuilder.url("https://discordapp.com/api/auth/login");
			rBuilder.get(); /*.post(RequestBody.create(Harmony.JSON, "{\"email\":\"\",\"password\":\"\"}"));*/

			try {
				Response response = Harmony.twitchClient.newCall(rBuilder.build()).execute();

				Harmony.checkResponse(response);

				String responseString = response.body().string();
				System.out.println(responseString);
				Token token = Harmony.mapper.readValue(responseString, Token.class);

				return new DiscordApi(token);

			} catch(IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
	}
}
