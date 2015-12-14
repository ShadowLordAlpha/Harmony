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
package sla.harmony.rest;

/**
 * Endpoint represents the different Endpoints that Discord has
 * TODO make this a formatter type class?
 * 
 * @since Harmony v0.2.0
 * @version v1.0.0
 * @author Josh "ShadowLordAlpha"
 */
public final class Endpoint {

	/**
	 * Core URL for Discord
	 */
	public static final String DISCORD = "https://discordapp.com/";
	
	/**
	 * Discord API URL
	 */
	public static final String API = DISCORD + "api/";
	
	/**
	 * Gateway Endpoint, Used for getting the gateway the the web socket should connect to
	 */
	public static final String GATEWAY = API + "gateway";
	
	/**
	 * Login Endpoint, Used to send login info to Discord
	 */
	public static final String LOGIN = API + "auth/login";
	
	/**
	 * Logout Endpoint, Used to send logout info to Discord
	 */
	public static final String LOGOUT = API + "auth/logout";
	
	/**
	 * Channel Endpoint, Used to Edit or Delete channels. Format
	 */
	public static final String CHANNEL = API + "channels/%s";
	
	/**
	 * Typing Endpoint, Used to send the user typing message, Format
	 */
	public static final String TYPING = CHANNEL + "/typing";
	
	/**
	 * Message Endpoint, Used to request messages or send new ones, Format
	 */
	public static final String MESSAGE = CHANNEL + "/messages";
	
	/**
	 * Edit Message Endpoint, Used to edit or delete a message, Format
	 */
	public static final String EDIT_MESSAGE = MESSAGE + "/%s";
	
	
	/**
	 * Acknowledge Message Endpoint, Used to send message acknowledgement, Format
	 */
	public static final String ACK_MESSAGE = EDIT_MESSAGE + "/ack";
	
	/**
	 * Permission Endpoint, Used to create, edit, or delete permissions, Format
	 */
	public static final String PERMISSION = CHANNEL + "/permissions/%s";
	
	/**
	 * Guild Endpoint, Used in the creation of new Guilds
	 */
	public static final String GUILD = API + "guilds";
	
	/**
	 * Get Channels Endpoint, Used to create new channels or get a guilds channels, Format
	 */
	public static final String GET_CHANNELS = GUILD + "/%s/channels";
	
	/**
	 * Edit Guild Endpoint, Used to edit or delete/leave a guild, Format
	 */
	public static final String EDIT_GUILD = GUILD + "/%s";
	
	/**
	 * Members Endpoint, Used to get a list of a guilds members, Format
	 * <b>!This is being removed!</b>
	 */
	@Deprecated
	public static final String MEMBERS = EDIT_GUILD + "/members";
	
	/**
	 * Edit Member Endpoint, Used to edit a guild members roles or kick a member, Format
	 */
	public static final String EDIT_MEMBER = MEMBERS + "/%s";
	
	/**
	 * Get Bans Endpoint, Used to get a guilds banned users, Format
	 */
	public static final String GET_BANS = EDIT_GUILD + "/bans";
	
	/**
	 * Set Ban Endpoint, Used to add or remove a users ban, Format
	 */
	public static final String SET_BAN = GET_BANS + "/%s";
	
	/**
	 * Get Guilds Endpoint, Used to get your 
	 */
	public static final String GET_GUILDS = API + "@me/guilds";
	
	/**
	 * Role Endpoint, Used to get?, create, or reorder a roll, Format
	 */
	public static final String ROLE = EDIT_GUILD + "/roles";
	
	/**
	 * Edit Role Endpoint, Used to edit or delete a role, Format
	 */
	public static final String EDIT_ROLE = ROLE + "/%s";
	
	/**
	 * Invite Endpoint, Used to Get, accept, or delete invites, Format
	 */
	public static final String INVITE = API + "invite/%s";
	
	/**
	 * Create Channel Endpoint, Used to create an invite for a channel, Format
	 */
	public static final String CREATE_INVITE = CHANNEL + "/invites";
	
	/**
	 * User Endpoint, Used for UNKNOWN, Format
	 */
	public static final String USER = API + "users/%s";
	
	/**
	 * Create Private Endpoint, Used to create private chats with another user, Format
	 */
	public static final String CREATE_PRIVATE = USER + "/channels";
	
	/**
	 * Get Avatar Endpoint, Used to get the avatar of a user, Format
	 */
	public static final String GET_AVATAR = USER + "/avatars/%s.jpg";
	
	/**
	 * Active Maintenance Endpoint, used to get if there are any current maintencance
	 */
	public static final String ACTIVE_MAINT = API + "v2/scheduled-maintenances/active.json";
	
	/**
	 * Upcoming Maintenance Endpoint, used to get if there are any upcoming maintencance
	 */
	public static final String UPCOMING_MAINT = API + "v2/scheduled-maintenances/upcoming.json";
	
	// TODO voice endpoints
	
	/**
	 * Private constructor to keep others from instanceing this class
	 */
	private Endpoint() {
		
	}
}
