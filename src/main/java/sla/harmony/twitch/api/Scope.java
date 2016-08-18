/**
 * 
 */
package sla.harmony.twitch.api;

/**
 * When requesting authorization from users, the scope parameter allows you to specify which permissions your app
 * requires. These scopes are ties to the access token you receive upon a successful authorization. Without specifying
 * scopes, your app only has access to basic information about the authenticated user. You may specify any or all of the
 * following scopes:
 * 
 * @author Josh "ShadowLordAlpha"
 *
 */
public enum Scope {

	/**
	 * Read access to non-public user information, such as email address.
	 */
	USER_READ("user_read"),
	
	/**
	 * Ability to ignore or unignore on behalf of a user.
	 */
	USER_BLOCKS_EDIT("user_blocks_edit"),
	
	/**
	 * Read access to a user's list of ignored users.
	 */
	USER_BLOCKS_READ("user_blocks_read"),
	
	/**
	 * Access to manage a user's followed channels.
	 */
	USER_FOLLOWS_EDIT("user_follows_edit"),
	
	/**
	 * Read access to non-public channel information, including email address and stream key.
	 */
	CHANNEL_READ("channel_read"),
	
	/**
	 * Write access to channel metadata (game, status, etc).
	 */
	CHANNEL_EDITOR("channel_editor"),
	
	/**
	 * Access to trigger commercials on channel.
	 */
	CHANNEL_COMMERCIAL("channel_commercial"),
	
	/**
	 * Ability to reset a channel's stream key.
	 */
	CHANNEL_STREAM("channel_stream"),
	
	/**
	 * Read access to all subscribers to your channel.
	 */
	CHANNEL_SUBSCRIPTIONS("channel_subscriptions"),
	
	/**
	 * Read access to subscriptions of a user.
	 */
	USER_SUBSCRIPTIONS("user_subscriptions"),
	
	/**
	 * Read access to check if a user is subscribed to your channel.
	 */
	CHANNEL_CHECK_SUBSCRIPTIONS("channel_check_subscriptions"),
	
	/**
	 * Ability to log into chat and send messages.
	 */
	CHAT_LOGIN("chat_login"),
	
	/**
	 * Ability to view to a channel feed.
	 */
	CHANNEL_FEED_READ("channel_feed_read"),
	
	/**
	 * Ability to add posts and reactions to a channel feed.
	 */
	CHANNEL_FEED_EDIT("channel_feed_edit");

	private Scope(String name) {

	}
}
