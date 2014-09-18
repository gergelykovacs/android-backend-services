package com.backend.services.events;

import com.backend.services.ServiceData;

/**
 * Service event wrapper or holder class that is intended to use for 
 * modifying events.
 *  
 * <p>Events as Enums can not be pushed through process boundaries hence 
 * an event holder service data is used to circumvent this issue. 
 * By this way events can be changed on the fly and dispatched 
 * appropriately.</p>
 *  
 * @see com.backend.services.utils.EventUtils
 */
public class EventHolder extends ServiceData {
	
	private String mEventString;
	
	public EventHolder() {
	}
	
	public EventHolder(String event) {
		mEventString = event;
	}

	public void setEventString(String event) {
		mEventString = event;
	}
	
	public String getEventString() {
		return mEventString;
	}
}
