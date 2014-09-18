package com.backend.services.connections;

import java.util.List;

import com.backend.services.NetworkingServiceCall;
import com.backend.services.ServiceData;
import com.backend.services.events.IServiceEventListener;
import com.backend.services.events.ServiceEvent;

import android.content.ServiceConnection;
import android.os.RemoteException;

/**
 * Networking Service Connection
 * 
 * <p>It is used from non-UI thread to create a connection to the underlying 
 * networking service and send Sync. requests to that service and forward the 
 * response back to the caller directly.</p>
 */
public final class NetworkingServiceConnection implements IServiceConnection {
	
	//private static final String TAG = NetworkingServiceConnection.class.getSimpleName();
	
	//private IServiceEvents mEventDispatcher;
	
	private GenericServiceConnection<NetworkingServiceCall> mNetworkingCall = 
			new GenericServiceConnection<NetworkingServiceCall>(NetworkingServiceCall.class);
	
	public NetworkingServiceConnection() {
		//mEventDispatcher = ServiceEventDispatcher.newInstance();
	}
	
	@Override
	public ServiceConnection getServiceConnection() {
		return mNetworkingCall;
	}

	@Override
	public boolean isServiceConnectionCreated() {
		return mNetworkingCall.getInterface() != null;
	}
	
	@Override
	public void triggerEvent(ServiceEvent event, List<ServiceData> data) {
	}
	
	@Override
	public List<ServiceData> triggerSyncEvent(ServiceEvent event, List<ServiceData> data) {
		NetworkingServiceCall serviceCall = mNetworkingCall.getInterface();
		List<ServiceData> eventResponse = null;
		if(serviceCall != null) {
			try { 
				eventResponse = serviceCall.networkingCall(event.stringValue(), data);
			} catch(RemoteException e) {
				e.printStackTrace();
			}
		}
		return eventResponse;
	}
	
	@Override
	public void attachEventListener(IServiceEventListener serviceEventListener) {
		//mEventDispatcher.attach(serviceEventListener);
	}

	@Override
	public void detachEventListener(IServiceEventListener serviceEventListener) {
		//mEventDispatcher.detach(serviceEventListener);
	}
	
	@Override
	public final Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
