package sla.harmony.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sla.harmony.event.HarmonyReadyEvent.HarmonyReadyListener;
import sla.harmony.event.adv.MessageReceivedEvent;
import sla.harmony.event.adv.MessageReceivedEvent.MessageReveivedListener;

public class EventManager {

	private static final Logger logger = LoggerFactory.getLogger(EventManager.class);
	
	// I can't think of a better way to do this so its a bunch of lists
	private ArrayList<HarmonyReadyListener> harmonyReadyListeners = new ArrayList<HarmonyReadyListener>();
	private ArrayList<MessageReveivedListener> messageReveivedListeners = new ArrayList<MessageReveivedListener>();
	
	public void addListener(Object o) {
		for(Method m: o.getClass().getMethods()) {
			if(m.isAnnotationPresent(EventHandler.class)) {
				if(m.getParameterCount() == 1) {
					if(Event.class.isAssignableFrom(m.getParameterTypes()[0])) {
						createListenerWrapper(m.getParameterTypes()[0].getName(), o, m);
					}
				}
			}
		}
	}
	
	/**
	 * Wraps a method in a lambda function in order to call it later without needing another list for them
	 * 
	 * @param type The type of event, basically class.getName()
	 * @param o The object that is being used to throw the event
	 * @param m The method receiving the event
	 */
	private void createListenerWrapper(String type, Object o, Method m) {
		switch(m.getParameterTypes()[0].getName()) {
			case "sla.harmony.event.adv.MessageReceivedEvent": {
				addListener((MessageReceivedEvent event) -> this.forceInvoke(o, m, event));
				break;
			}
			case "sla.harmony.event.HarmonyReadyEvent": {
				addListener((HarmonyReadyEvent event) -> this.forceInvoke(o, m, event));
				break;
			}
			default:
				logger.warn("UNKNOWN EVENT LISTENER: {}", m);
				break;
		}
	}
	
	public void addListener(HarmonyReadyListener eventListener) {
		harmonyReadyListeners.add(eventListener);
	}
	
	public void addListener(MessageReveivedListener eventListener) {
		messageReveivedListeners.add(eventListener);
	}
	
	public void throwEvent(Event event) {
		// System.out.println(event.getClass().getName());
		// we can't switch on a class but we can on a classes names
		switch(event.getClass().getName()) {
			case "sla.harmony.event.adv.MessageReceivedEvent": {
				for(MessageReveivedListener e: messageReveivedListeners) {
					e.listen((MessageReceivedEvent) event);
				}
				break;
			}
			case "sla.harmony.event.HarmonyReadyEvent": {
				for(HarmonyReadyListener e: harmonyReadyListeners) {
					e.listen((HarmonyReadyEvent) event);
				}
				break;
			}
			default:
				logger.warn("UNKNOWN EVENT: {}", event.getClass().getName());
				break;
		}
	}
	
	private void forceInvoke(Object o, Method m, Event e) {
		try {
			m.invoke(o, e);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			logger.warn("Failed to invoke method: {}:", m, e);
		}
	}
}
