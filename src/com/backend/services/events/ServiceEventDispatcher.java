package com.backend.services.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.backend.services.ServiceData;

/**
 * Event Subscriber-Dispatcher
 * 
 * <p>This class implements the concrete logic for subscribing events by 
 * the use of event listeners that are used to notify subscribers about 
 * incoming and dispatched events.</p>
 * 
 * @see com.backend.services.events.IServiceEvents
 * @see com.backend.services.events.IServiceEventListener
 */
public class ServiceEventDispatcher implements IServiceEvents {
	
	/**
	 * The pool of event listeners.
	 */
	private List<IServiceEventListener> mEventListenerPool = new ArrayList<IServiceEventListener>();
	
	private ServiceEventDispatcher() {
	}
	
	/**
	 * Factory  method to create a new event subscriber-dispatcher infrastructure. 
	 * 
	 * @return A new event dispatcher is returned.
	 */
	public static IServiceEvents newInstance() {
		return new ServiceEventDispatcher();
	}

	@Override
	public void attach(IServiceEventListener serviceEventListener) {
		if(mEventListenerPool.indexOf(serviceEventListener) < 0) {
			mEventListenerPool.add(serviceEventListener);
		}
	}

	@Override
	public void detach(IServiceEventListener serviceEventHandler) {
		if(mEventListenerPool.indexOf(serviceEventHandler) >= 0) {
			mEventListenerPool.remove(serviceEventHandler);
		}
	}

	@Override
	public synchronized void handle(ServiceEvent event, List<ServiceData> data) {
		if(!mEventListenerPool.isEmpty()) {
			Iterator<IServiceEventListener> it = mEventListenerPool.iterator();
			while(it.hasNext()) {
				it.next().handleEvent(event, data);
			}
		}
	}
	
	@Override
	public final Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
