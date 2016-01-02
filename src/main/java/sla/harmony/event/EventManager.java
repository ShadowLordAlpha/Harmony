package sla.harmony.event;

import java.util.ArrayList;
import java.util.HashMap;

import sla.harmony.Harmony;
import sla.harmony.event.HarmonyReadyEvent.HarmonyReadyEventHandler;
import sla.harmony.event.adv.MessageReceivedEvent;
import sla.harmony.event.adv.MessageReceivedEvent.MessageReceivedEventHandler;

public class EventManager {

	// TODO readd annotation based version if possible
	
	// I don't have a better way of doing this :(
	private HashMap<EventType, ArrayList<EventHandler<?>>> listeners = new HashMap<EventType, ArrayList<EventHandler<?>>>();
	
	private EventManager addListenerInter(EventHandler<?> listener) {
		if(listeners.get(listener.getEventType()) == null) {
			listeners.put(listener.getEventType(), new ArrayList<EventHandler<?>>());
		}
		
		listeners.get(listener.getEventType()).add(listener);
		
		return this;
	}
	
	public EventManager addListener(HarmonyReadyEventHandler listener) {
		return addListenerInter(listener);
	}
	
	public EventManager addListener(MessageReceivedEventHandler listener) {
		return addListenerInter(listener);
	}
	
	public void throwEvent(Harmony harmony, Event event) {
		
		switch(event.getEventType()) {
		case READY: {
			for(EventHandler<?> eh :listeners.get(EventType.READY)) {
				((HarmonyReadyEventHandler) eh).onEvent(harmony, (HarmonyReadyEvent) event);
			}
			break;
		}
		case MESSAGE_RECEIVED: {
			for(EventHandler<?> eh :listeners.get(EventType.MESSAGE_RECEIVED)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case CHANNEL_CREATE: {
			for(EventHandler<?> eh :listeners.get(EventType.CHANNEL_CREATE)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case CHANNEL_DELETE: {
			for(EventHandler<?> eh :listeners.get(EventType.CHANNEL_DELETE)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case CHANNEL_UPDATE: {
			for(EventHandler<?> eh :listeners.get(EventType.CHANNEL_UPDATE)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case GUILD_BAN_ADD: {
			for(EventHandler<?> eh :listeners.get(EventType.GUILD_BAN_ADD)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case GUILD_BAN_REMOVE: {
			for(EventHandler<?> eh :listeners.get(EventType.GUILD_BAN_REMOVE)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case GUILD_CREATE: {
			for(EventHandler<?> eh :listeners.get(EventType.GUILD_CREATE)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case GUILD_DELETE: {
			for(EventHandler<?> eh :listeners.get(EventType.GUILD_DELETE)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case GUILD_INTEGRATIONS_UPDATE: {
			for(EventHandler<?> eh :listeners.get(EventType.GUILD_INTEGRATIONS_UPDATE)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case GUILD_MEMBER_ADD: {
			for(EventHandler<?> eh :listeners.get(EventType.GUILD_MEMBER_ADD)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case GUILD_MEMBER_REMOVE: {
			for(EventHandler<?> eh :listeners.get(EventType.GUILD_MEMBER_REMOVE)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case GUILD_MEMBER_UPDATE: {
			for(EventHandler<?> eh :listeners.get(EventType.GUILD_MEMBER_UPDATE)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case GUILD_ROLE_CREATE: {
			for(EventHandler<?> eh :listeners.get(EventType.GUILD_ROLE_CREATE)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case GUILD_ROLE_DELETE: {
			for(EventHandler<?> eh :listeners.get(EventType.GUILD_ROLE_DELETE)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case GUILD_ROLE_UPDATE: {
			for(EventHandler<?> eh :listeners.get(EventType.GUILD_ROLE_UPDATE)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case GUILD_UPDATE: {
			for(EventHandler<?> eh :listeners.get(EventType.GUILD_UPDATE)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case MESSAGE_CREATE: {
			for(EventHandler<?> eh :listeners.get(EventType.MESSAGE_CREATE)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case MESSAGE_DELETE: {
			for(EventHandler<?> eh :listeners.get(EventType.MESSAGE_DELETE)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case MESSAGE_UPDATE: {
			for(EventHandler<?> eh :listeners.get(EventType.MESSAGE_UPDATE)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case PRESENCE_UPDATE: {
			for(EventHandler<?> eh :listeners.get(EventType.PRESENCE_UPDATE)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case TYPING_START: {
			for(EventHandler<?> eh :listeners.get(EventType.TYPING_START)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case USER_SETTINGS_UPDATE: {
			for(EventHandler<?> eh :listeners.get(EventType.USER_SETTINGS_UPDATE)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case VOICE_STATE_UPDATE: {
			for(EventHandler<?> eh :listeners.get(EventType.VOICE_STATE_UPDATE)) {
				((MessageReceivedEventHandler) eh).onEvent(harmony, (MessageReceivedEvent) event);
			}
			break;
		}
		case UNKNOWN:
		default:
			System.out.println("Unknown event");
			break;			
		}
	}
}
