package sla.harmony;

import org.json.JSONObject;

public class UserSettings {

	private boolean inlineEmbedMedia;
	private boolean renderEmbeds;
	private boolean inlineAttachmentMedia;
	// private ? mutedChannels;
	private boolean showCurrentGame;
	private String theme;
	private boolean enableTtsCommand;
	private String locale;
	private boolean converEmoticons;
	private boolean messageDisplayCompact;
	
	public UserSettings(JSONObject jobj) {
		inlineEmbedMedia = jobj.optBoolean("inline_embed_media", true);
		renderEmbeds = jobj.optBoolean("render_embeds", true);
		inlineAttachmentMedia = jobj.optBoolean("inline_attachment_media", true);
		// Muted channels
		showCurrentGame = jobj.optBoolean("show_current_game", true);
		theme = jobj.optString("theme", "light");
		enableTtsCommand = jobj.optBoolean("enable_tts_command", true);
		locale = jobj.optString("locale", "en-US");
		converEmoticons = jobj.optBoolean("convert_emoticons", true);
		messageDisplayCompact = jobj.optBoolean("message_display_compact", true);
	}

	public boolean isInlineEmbedMedia() {
		return inlineEmbedMedia;
	}

	public boolean isRenderEmbeds() {
		return renderEmbeds;
	}

	public boolean isInlineAttachmentMedia() {
		return inlineAttachmentMedia;
	}

	public boolean isShowCurrentGame() {
		return showCurrentGame;
	}

	public String getTheme() {
		return theme;
	}

	public boolean isEnableTtsCommand() {
		return enableTtsCommand;
	}

	public String getLocale() {
		return locale;
	}

	public boolean isConverEmoticons() {
		return converEmoticons;
	}

	public boolean isMessageDisplayCompact() {
		return messageDisplayCompact;
	}
	
	
}
