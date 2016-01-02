/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 ShadowLordAlpha
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package sla.harmony.websocket;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import sla.harmony.Harmony;

/**
 * Basic Web-Socket connection. This is the default and is the same for both the basic and advanced modes. Both
 * connections should be subclasses of this class.
 * 
 * @author Josh "ShadowLordAlpha"
 */
public abstract class WebSocketConnection extends WebSocketAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketConnection.class);

	protected Harmony harmony;
	private WebSocket webSocket;

	public WebSocketConnection(Harmony harmony, String serverUri) {

		this.harmony = harmony;

		try {
			webSocket = new WebSocketFactory().createSocket(serverUri).addHeader("Accept-Encoding", "gzip")
					.addListener(this);
		} catch (IOException e) {
			LOGGER.error("Web-Socket Exception:", e);
		}

		try {
			LOGGER.info("Web-Socket Connecting.");
			webSocket.connect();
		} catch (WebSocketException e) {
			LOGGER.error("Web-Socket Failed to Connect:", e);
		}
	}

	/**
	 * Send a text message to the server.
	 */
	public void sendTextMessage(String text) {
		webSocket.sendText(text);
	}
	
	public void close() {
		webSocket.disconnect("Application Shutdown");
	}

	private void setupHeartbeat(int heatbreatInterval) {
		Runnable heartbeat = () -> {
			LOGGER.info("Connection Heatbeat Started");

			while (webSocket.isOpen()) {
				LOGGER.info("Sending Heartbeat");
				sendTextMessage(String.format("{\"op\":1,\"d\":%d}", System.currentTimeMillis()));

				try {
					Thread.sleep(heatbreatInterval);
				} catch (InterruptedException e) {
					LOGGER.warn("Heatbeat Interrupted:", e);
				}
			}
		};

		new Thread(heartbeat, "HeartbeatThread").start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neovisionaries.ws.client.WebSocketAdapter#onConnected(com.neovisionaries.ws.client.WebSocket,
	 * java.util.Map)
	 */
	@Override
	public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
		// TODO make this easier to edit
		LOGGER.info("Sending Handshake");
		sendTextMessage(String.format(
				"{\"op\":2,\"d\":{\"token\":\"%s\",\"v\":3,\"properties\":{\"$os\":\"%s\",\"$browser\":\"Harmony\","
						+ "\"$device\": \"\",\"$referrer\":\"https://discordapp.com/@me\",\"$referring_domain\":\"discordapp.com\"}"
						+ ",\"large_threshold\":100,\"compress\":true}}",
				harmony.getAuthToken(), System.getProperty("os.name")));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neovisionaries.ws.client.WebSocketAdapter#onDisconnected(com.neovisionaries.ws.client.WebSocket,
	 * com.neovisionaries.ws.client.WebSocketFrame, com.neovisionaries.ws.client.WebSocketFrame, boolean)
	 */
	@Override
	public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame,
			boolean closedByServer) throws Exception {
		// TODO exponential retry x number of times
		// TODO figure out what is sent if Internet cuts out and if it goes to this method
		LOGGER.warn("Web-Socket Closed. By Server: {}, Reason: {}", closedByServer, serverCloseFrame.getCloseReason());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neovisionaries.ws.client.WebSocketAdapter#onTextMessage(com.neovisionaries.ws.client.WebSocket,
	 * java.lang.String)
	 */
	@Override
	public abstract void onTextMessage(WebSocket websocket, String text) throws Exception;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neovisionaries.ws.client.WebSocketAdapter#onBinaryMessage(com.neovisionaries.ws.client.WebSocket,
	 * byte[])
	 */
	@Override
	public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {
		LOGGER.debug("Binary Message Recived!");

		// We assume all Binary messages are GZIP so we simply decode them all and pass them to the normal message
		// method.
		StringBuffer data = new StringBuffer();
		Inflater decompresser = new Inflater();
		decompresser.setInput(binary, 0, binary.length);
		byte[] result = new byte[100];
		while (!decompresser.finished()) {
			int resultLength = decompresser.inflate(result);
			data.append(new String(result, 0, resultLength, "UTF-8")); // TODO is there a better way of decoding this?
		}
		decompresser.end();

		setupHeartbeat(Harmony.mapper.readTree(data.toString()).get("d").get("heartbeat_interval").asInt(41250));
		
		// send the inflated message to the TextMessage method
		onTextMessage(websocket, data.toString());
	}

	/*
	 * We log all Web-Socket errors/exceptions with a warning as none of them should cause everything to crash though it
	 * is possible as which point the whole application is probably already crashing because of memory problems or the
	 * like.
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neovisionaries.ws.client.WebSocketAdapter#onError(com.neovisionaries.ws.client.WebSocket,
	 * com.neovisionaries.ws.client.WebSocketException)
	 */
	@Override
	public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
		LOGGER.warn("Error:", cause);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neovisionaries.ws.client.WebSocketAdapter#onFrameError(com.neovisionaries.ws.client.WebSocket,
	 * com.neovisionaries.ws.client.WebSocketException, com.neovisionaries.ws.client.WebSocketFrame)
	 */
	@Override
	public void onFrameError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {
		LOGGER.warn("Frame Error:\nFrame: {}:", frame, cause);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neovisionaries.ws.client.WebSocketAdapter#onMessageError(com.neovisionaries.ws.client.WebSocket,
	 * com.neovisionaries.ws.client.WebSocketException, java.util.List)
	 */
	@Override
	public void onMessageError(WebSocket websocket, WebSocketException cause, List<WebSocketFrame> frames)
			throws Exception {
		LOGGER.warn("Message Error:\nFrames: {}:", frames, cause);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neovisionaries.ws.client.WebSocketAdapter#onMessageDecompressionError(com.neovisionaries.ws.client.WebSocket,
	 * com.neovisionaries.ws.client.WebSocketException, byte[])
	 */
	@Override
	public void onMessageDecompressionError(WebSocket websocket, WebSocketException cause, byte[] compressed)
			throws Exception {
		LOGGER.warn("Message Decompression Error:\nData: {}:", compressed, cause);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neovisionaries.ws.client.WebSocketAdapter#onTextMessageError(com.neovisionaries.ws.client.WebSocket,
	 * com.neovisionaries.ws.client.WebSocketException, byte[])
	 */
	@Override
	public void onTextMessageError(WebSocket websocket, WebSocketException cause, byte[] data) throws Exception {
		LOGGER.warn("Text Message Error:\nMessage: {}:", data, cause);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neovisionaries.ws.client.WebSocketAdapter#onSendError(com.neovisionaries.ws.client.WebSocket,
	 * com.neovisionaries.ws.client.WebSocketException, com.neovisionaries.ws.client.WebSocketFrame)
	 */
	@Override
	public void onSendError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {
		LOGGER.warn("Send Error:\nFrame: {}:", frame, cause);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neovisionaries.ws.client.WebSocketAdapter#onUnexpectedError(com.neovisionaries.ws.client.WebSocket,
	 * com.neovisionaries.ws.client.WebSocketException)
	 */
	@Override
	public void onUnexpectedError(WebSocket websocket, WebSocketException cause) throws Exception {
		LOGGER.warn("Unexpected Error:", cause);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neovisionaries.ws.client.WebSocketAdapter#handleCallbackError(com.neovisionaries.ws.client.WebSocket,
	 * java.lang.Throwable)
	 */
	@Override
	public void handleCallbackError(WebSocket websocket, Throwable cause) throws Exception {
		LOGGER.warn("Callback Error:", cause);
	}
}
