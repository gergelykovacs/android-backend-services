package com.backend.services.services;

import java.util.List;

import com.backend.services.ServiceData;
import com.backend.services.StorageServiceCall;
import com.backend.services.connections.IServiceConnection;
import com.backend.services.connections.NetworkingServiceConnection;
import com.backend.services.strategies.IServiceStrategy;
import com.backend.services.strategies.ServiceStrategy;
import com.backend.services.strategies.storage.StorageStrategyFactory;
import com.backend.services.utils.EventUtils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * The Storage service base class that wraps around 
 * the underlying concrete functionalities.
 */
public class StorageService extends Service {
	
	private static final String TAG = StorageService.class.getSimpleName();
	
	private IServiceConnection mNetworkingServiceConnection = new NetworkingServiceConnection();
	
	/**
	 * Stub Implementation
	 * 
	 * <p>Creates an implementation of the Stub on the current service side to 
	 * be called by the Proxy on the client side. 
	 * This implementation represents the logic of the service should perform when a 
	 * request is made by the client.</p> 
	 */
	private StorageServiceCall.Stub mUiStorageServiceRequest = new StorageServiceCall.Stub() {

		@Override
		public List<ServiceData> storageCall(String event, List<ServiceData> data) throws RemoteException {

			List<ServiceData> result = null;
			
			IServiceStrategy strategy = new ServiceStrategy();
			
			result = strategy.setStrategy(
						StorageStrategyFactory.getStrategy(
								EventUtils.findEventBy(event), 
								data, 
								mNetworkingServiceConnection, 
								getApplicationContext()
						)
					).commit();
	
			return result;
		}
	};
	
	/**
	 * Prepares an intent for the present service to be created.
	 *  
	 * @param context The context in which the service should be created.
	 * @return The intent to be used to create this service.
	 */
	public static Intent makeIntent(Context context) {
		return new Intent(context, StorageService.class);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "Storage service/process is started.");
		
		if(!mNetworkingServiceConnection.isServiceConnectionCreated()) {
			bindService(NetworkingService.makeIntent(getApplicationContext()), 
							mNetworkingServiceConnection.getServiceConnection(), 
																	BIND_AUTO_CREATE);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mUiStorageServiceRequest;
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		return false;
	}
	
	@Override
	public void onDestroy() {
		if(mNetworkingServiceConnection.isServiceConnectionCreated()) {
			unbindService(mNetworkingServiceConnection.getServiceConnection());
		}
		
		super.onDestroy();
	}
}
