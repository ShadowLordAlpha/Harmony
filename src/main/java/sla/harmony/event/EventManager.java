package sla.harmony.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sla.harmony.event.adv.MessageReceivedEvent;

public class EventManager {

	private static final Logger logger = LoggerFactory.getLogger(EventManager.class);
	
	private ConcurrentHashMap<Class<? extends Event>, ConcurrentHashMap<Method, Object>> methodMap = new ConcurrentHashMap<Class<? extends Event>, ConcurrentHashMap<Method, Object>>();

	private ArrayList<EventListener<MessageReceivedEvent>> messageReveivedListeners = new ArrayList<EventListener<MessageReceivedEvent>>();
	
	public void addListener(Object o) {
		for(Method m: o.getClass().getMethods()) {
			if(m.isAnnotationPresent(EventHandler.class)) {
				if(m.getParameterCount() == 1) {
					if(Event.class.isAssignableFrom(m.getParameterTypes()[0])) {
						logger.info("Added Method: {}", m);
						methodMap.getOrDefault(m.getParameterTypes()[0], new ConcurrentHashMap<Method, Object>()).put(m, o);
					}
				}
			}
		}
	}
	
	public void addListener(EventListener<MessageReceivedEvent> eventListener) {
		messageReveivedListeners.add(eventListener);
	}
	
	public void throwEvent(Event event) {
		
		// Bullshit magic to get lambdas to play nice
		switch(event.getClass().getSimpleName()) {
			case "MessageReceivedEvent": {
				for(EventListener<MessageReceivedEvent> el: messageReveivedListeners) {
					el.eventListner((MessageReceivedEvent) event);
				}
			}
		}
			
		
		// logger.info("Throwing event {}", event.getClass().getName());
		ConcurrentHashMap<Method, Object> methods = methodMap.get(event.getClass());
		if(methods != null) {
			for(Method m: methods.keySet()) {
				try {
					m.invoke(methods.get(m), event);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					logger.warn("Event Manager Error:", e);
				}
			}
		}
	}
	
	@FunctionalInterface
	public interface EventListener<E extends Event> {
		
		public void eventListner(E event);
	}
}
