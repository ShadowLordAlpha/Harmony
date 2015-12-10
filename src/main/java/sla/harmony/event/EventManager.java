package sla.harmony.event;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventManager {
	
	private static final Logger logger = LoggerFactory.getLogger(EventManager.class);

	private HashMap<Method, Object> methodList = new HashMap<Method, Object>();

	/**
	 * Checks a given class for methods containing the {@link EventHandler @EventHandler} annotation. If a method is
	 * found that contains this annotation and takes only one parameter that implements Event it is added to the list of
	 * methods to check when a new event is thrown.
	 * @param clazz the class to check
	 */
	public void registerListener(Object clazz) {
		for (Method m : clazz.getClass().getMethods()) {
			if (m.isAnnotationPresent(EventHandler.class) && m.getParameterTypes().length == 1 && Event.class.isAssignableFrom(m.getParameterTypes()[0])) {
				logger.debug("Method added: " + m.getName());
				methodList.put(m, clazz);
			}
		}
	}
	
	public void throwEvent(Event event) {
		for(Method m: methodList.keySet()) {
			try {
				if(m.getParameterTypes()[0].equals(event.getClass())) {
					try {
						logger.debug("Sending Event: " + event.getClass().getName() + " to: " + m.getName());
						m.setAccessible(true);
						m.invoke(methodList.get(m), event);
					} catch(Exception e) {
						logger.warn("Failed to Properly invoke method:", e);
					}
				}
			} catch(Exception e) {
				logger.warn("Failed to Properly throw Event:", e);
			}
		}
	}
}
