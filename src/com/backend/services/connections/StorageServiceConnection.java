package com.backend.services.connections;

import java.util.List;

import com.backend.services.ServiceData;
import com.backend.services.StorageServiceCall;
import com.backend.services.events.IServiceEventListener;
import com.backend.services.events.IServiceEvents;
import com.backend.services.events.ServiceEvent;
import com.backend.services.events.ServiceEventDispatcher;
import com.backend.services.utils.EventUtils;

import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.RemoteException;

/**
 * Storage Service Connection
 * 
 * <p>It is used from the UI thread to create a connection to the underlying 
 * storage service and send Async. requests to that service and forward the 
 * response to the event dispatcher.</p>
 */
public final class StorageServiceConnection implements IServiceConnection {
	
	//private static final String TAG = StorageServiceConnection.class.getSimpleName();
	
	private IServiceEvents mEventDispatcher;
	
	private GenericServiceConnection<StorageServiceCall> mStorageCall = 
			new GenericServiceConnection<StorageServiceCall>(StorageServiceCall.class);
	
	public StorageServiceConnection() {
		mEventDispatcher = ServiceEventDispatcher.newInstance();
	}
	
	@Override
	public ServiceConnection getServiceConnection() {
		return mStorageCall;
	}

	@Override
	public boolean isServiceConnectionCreated() {
		return mStorageCall.getInterface() != null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void triggerEvent(final ServiceEvent event, final List<ServiceData> data) {
		final StorageServiceCall serviceCall = mStorageCall.getInterface();
		if(serviceCall != null) {
			AsyncTask<List<ServiceData>, Void, List<ServiceData>> task =
				new AsyncTask<List<ServiceData>, Void, List<ServiceData>>() {
				
					@Override
					protected List<ServiceData> doInBackground(final List<ServiceData>... params) {
						List<ServiceData> response = null;
						try {
							response = serviceCall.storageCall(event.stringValue(), params[0]);
						} catch(final RemoteException e) {
							e.printStackTrace();
						}
						return response;
					}
				
					@Override
					protected void onPostExecute(final List<ServiceData> result) {
						ServiceEvent newEvent = EventUtils.getEvent(result);
						if(newEvent != null) {
							EventUtils.popEvent(result);
							mEventDispatcher.handle(newEvent, result);
						} else {
							mEventDispatcher.handle(event, result);
						}
					}
				};
			
			task.execute(data);
		}
	}

	@Override
	public List<ServiceData> triggerSyncEvent(ServiceEvent event, List<ServiceData> data) {
		return null;
	}
	
	@Override
	public void attachEventListener(IServiceEventListener serviceEventListener) {
		mEventDispatcher.attach(serviceEventListener);
	}

	@Override
	public void detachEventListener(IServiceEventListener serviceEventListener) {
		mEventDispatcher.detach(serviceEventListener);
	}
	
	@Override
	public final Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
