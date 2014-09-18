package com.backend.services.events;

import java.util.List;

import com.backend.services.ServiceData;

/**
 * Interface for implementing an event dispatcher or listener.
 * 
 * <p>It is an Observer pattern so look up the belonging part 
 * that is the event listener interface. This interface can be used to 
 * to implement the subscriber-dispatcher logic.</p>
 * 
 * @see com.backend.services.events.IServiceEventListener
 */
public interface IServiceEvents {

	/**
	 * The implementation should attach the given listener.
	 * 
	 * @param serviceEventListener
	 */
	public void attach(IServiceEventListener serviceEventListener);
	
	/**
	 * The implementation should remove the event listener given by the 
	 * parameter if exists.
	 * 
	 * @param serviceEventListener
	 */
	public void detach(IServiceEventListener serviceEventListener);
	
	/**
	 * The implementation should iterate over the attached listeners 
	 * and call their hook method to notify the subscribers.
	 * 
	 * @see com.backend.services.events.IServiceEventListener
	 * 
	 * @param event The event to be dispatched.
	 * @param data The data to be forwarded as the result of some sort of procedures. 
	 */
	public void handle(ServiceEvent event, List<ServiceData> data);
}
