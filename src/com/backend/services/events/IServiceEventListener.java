package com.backend.services.events;

import java.util.List;

import com.backend.services.ServiceData;

/**
 * Interface for subscribing service events.
 * 
 * <p>If an event is triggered then this interface is responsible 
 * or should be used to capture the result.</p>
 */
public interface IServiceEventListener {

	/**
	 * The event dispatcher will call this function in order to notify the caller 
	 * that an event occurred.
	 * 
	 * <p>Note that all the events are handled through this method's implementation 
	 * hence you should always test against the event parameter which corresponds to 
	 * the actually dispatched event.</p>
	 * 
	 * @param event The actual dispatched event.
	 * @param data The general service data list that may contain the result.
	 */
	public void handleEvent(ServiceEvent event, List<ServiceData> data);
}
