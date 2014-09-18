package com.backend.services.services;

import java.util.List;

import com.backend.services.NetworkingServiceCall;
import com.backend.services.ServiceData;
import com.backend.services.strategies.IServiceStrategy;
import com.backend.services.strategies.ServiceStrategy;
import com.backend.services.strategies.networking.NetworkingStrategyFactory;
import com.backend.services.utils.EventUtils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * The Networking service base class that wraps around 
 * the underlying concrete functionalities.
 */
public class NetworkingService extends Service {
	
	private static final String TAG = NetworkingService.class.getSimpleName();
	
	/**
	 * Stub Implementation
	 * 
	 * <p>Creates an implementation of the Stub on the current service side to 
	 * be called by the Proxy on the client side. 
	 * This implementation represents the logic of the service should perform when a 
	 * request is made by the client.</p> 
	 */
	private NetworkingServiceCall.Stub mNetworkingServiceRequest = new NetworkingServiceCall.Stub() {

		@Override
		public List<ServiceData> networkingCall(String event, List<ServiceData> data) throws RemoteException {

			List<ServiceData> result = null;
			
			IServiceStrategy strategy = new ServiceStrategy();
			
			result = strategy.setStrategy(
						NetworkingStrategyFactory.getStrategy(
								EventUtils.findEventBy(event), 
								data, 
								null, getApplicationContext()
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
		return new Intent(context, NetworkingService.class);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "Networking service/process is started.");
	}
		
	@Override
	public IBinder onBind(Intent intent) {
		return mNetworkingServiceRequest;
	}
		
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
