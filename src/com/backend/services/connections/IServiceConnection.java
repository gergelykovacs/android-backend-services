package com.backend.services.connections;

import java.util.List;

import com.backend.services.ServiceData;
import com.backend.services.events.IServiceEventListener;
import com.backend.services.events.ServiceEvent;

import android.content.ServiceConnection;

/**
 * A generic service connection interface.
 * 
 * <p>Implement this interface in order to hide underlying complexity of the 
 * service connection and communication logic used by the event driven logic 
 * of the present framework.</p>
 */
public interface IServiceConnection {

	/**
	 * Returns the service connection.
	 * 
	 * <p>It is used by startService() and bindService() methods to 
	 * start or bind to a service.</p>
	 * 
	 * @return The service connection.
	 */
	public ServiceConnection getServiceConnection();
	
	/**
	 * Returns whether the service connection was created successfully or not.
	 *  
	 * @return boolean
	 */
	public boolean isServiceConnectionCreated();
	
	/**
	 * Non-blocking Call
	 * 
	 * <p>Performs an Async. or non-blocking call to the current service 
	 * with the given event and data. 
	 * You should use this method from the UI thread.</p> 
	 * 
	 * @param event The event you may wish to trigger.
	 * @param data The data you may wish to send together with the event.
	 */
	public void triggerEvent(ServiceEvent event, List<ServiceData> data);
	
	/**
	 * Blocking Call
	 * 
	 * <p>Performs a Sync. or blocking call to the current service 
	 * with the given event and data. 
	 * You can use this method from any non UI thread.</p> 
	 * 
	 * @param event The event you may wish to trigger.
	 * @param data The data you may wish to send together with the event.
	 */
	public List<ServiceData> triggerSyncEvent(ServiceEvent event, List<ServiceData> data);
	
	/**
	 * Wrapper method to hide the underlying Observer pattern and forward the 
	 * attachment request to the appropriate implementation.
	 * 
	 * @param serviceEventListener The listener to be attached.
	 */
	public void attachEventListener(IServiceEventListener serviceEventListener);
	
	/**
	 * Wrapper method to hide the underlying Observer pattern and forward the 
	 * removal intention to the appropriate implementation.
	 * 
	 * @param serviceEventListener The listener to be removed.
	 */
	public void detachEventListener(IServiceEventListener serviceEventListener);
}
