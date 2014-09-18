package com.backend.services.strategies;

import java.util.List;

import android.content.Context;

import com.backend.services.ServiceData;
import com.backend.services.connections.IServiceConnection;
import com.backend.services.events.ServiceEvent;
import com.backend.services.utils.EventUtils;

/**
 * Default Strategy
 * 
 * <p>The default strategy is used to represent a default NOTHING TO DO 
 * logic and stop further spreading of the unhandled event more over its 
 * usage avoids null pointer exception during the strategy logics.</p>
 */
public class DefaultStrategy implements IStrategy {
	
	//private static final String TAG = DefaultStrategy.class.getSimpleName();
	
	@SuppressWarnings("unused")
	private ServiceEvent mEvent;
	private List<ServiceData> mData;
	@SuppressWarnings("unused")
	private IServiceConnection mServiceConnection;
	@SuppressWarnings("unused")
	private Context mContext;
	
	public DefaultStrategy() {
	}
	
	public DefaultStrategy(ServiceEvent event, List<ServiceData> data, 
						IServiceConnection serviceConnection, Context context) {
		mEvent = event;
		mData = data;
		mServiceConnection = serviceConnection;
		mContext = context;
	}

	@Override
	public List<ServiceData> process() {
		EventUtils.setEvent(ServiceEvent.EVENT_NULL, mData);
		return mData;
	}
}
