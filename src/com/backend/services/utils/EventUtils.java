package com.backend.services.utils;

import java.util.List;

import com.backend.services.ServiceData;
import com.backend.services.events.EventHolder;
import com.backend.services.events.ServiceEvent;

/**
 * Event Utils
 * 
 * <p>Its methods used to perform operations on service 
 * events and event related data only.
 * It has an intention that is to support the Event Exchange 
 * procedure that means e.g., an event E1 is triggered but during 
 * the background procedure a logic say that in a case not E1 should 
 * be sent back instead an E2 event such a meaning occurs when the 
 * returning E1 means OK and read the result data set but in case of 
 * errors E2 means e.g., oops that operation can't be done do to e.g., 
 * the network is down.</p>
 */
public class EventUtils {

	private EventUtils() {
	}
	
	/**
	 * Puts (or rewrites if exists) an event holder into the data set 
	 * to modify the resulting event.
	 *  
	 * @param event To be set.
	 * @param data The response data set.
	 */
	public static void setEvent(ServiceEvent event, List<ServiceData> data) {
		int idx = indexOfEventHolder(data);
		if(idx > -1) {
			EventHolder eh = (EventHolder) data.get(idx);
			eh.setEventString(event.stringValue());
		} else {
			data.add(new EventHolder(event.stringValue()));
		}
	}
	
	/**
	 * Extracts the event from the data set if exists.
	 * 
	 * @param data The seed.
	 * @return The event if exists otherwise null.
	 */
	public static ServiceEvent getEvent(List<ServiceData> data) {
		int idx = indexOfEventHolder(data);
		if(idx > -1) {
			EventHolder eh = (EventHolder) data.get(idx);
			String event = eh.getEventString();
			return findEventBy(event);
		}
		return null;
	}
	
	/**
	 * Gives back the index of the event if exists in the given data set.
	 * 
	 * @param data The seed.
	 * @return The index or -1 if event not found.
	 */
	public static int indexOfEventHolder(List<ServiceData> data) {
		if(data != null) {
			for(ServiceData d : data) {
				if(d instanceof EventHolder) {
					return data.indexOf(d);
				}
			}
		}
		return -1;
	}
	
	/**
	 * Gives back the event and removes it from the data set if exists.
	 * 
	 * @param data The seed.
	 * @return The event if exists otherwise null.
	 */
	public static EventHolder popEvent(List<ServiceData> data) {
		int idx = indexOfEventHolder(data);
		if(idx > -1) {
			return (EventHolder)data.remove(idx);
		}
		return null;
	}
	
	/**
	 * Performs a search for the event given by its string value.
	 * 
	 * @param event The event's String value.
	 * @return The event if exists otherwise null.
	 */
	public static ServiceEvent findEventBy(String event) {
		for(ServiceEvent e : ServiceEvent.values()) {
			if(event.equals(e.stringValue())) {
				return e;
			}
		}
		return null;
	}
	
	@Override
	public final Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
