package com.backend.services.connections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Generic Service Connection
 * 
 * <p>In order to start a service and build a communication channel a service 
 * connection interface must be implemented. Please note that it is for AIDL. 
 * The class intends to wrap around the connection procedure by Reflection API. 
 * Its benefit is that the AIDL Java interface classes can be discovered and 
 * handled globally.</p>
 * 
 * @param <AIDLInterface>
 */
public class GenericServiceConnection
				<AIDLInterface extends android.os.IInterface> 
										implements ServiceConnection {
	
	private static final String STUB = "Stub";
	private static final String AS_INTERFACE = "asInterface";
	private static final Class<?>[] AI_PARAMS = {IBinder.class};
	
	private AIDLInterface mInterface;
	private final Class<?> mStub;
	private final Method mAsInterface;
	
	/**
	 * Creates the service connection with the given AIDL interface that is used for 
	 * the communication.
	 *  
	 * @param aidl
	 */
	public GenericServiceConnection(final Class<AIDLInterface> aidl) {
		Class<?> stub = null;
		Method method = null;
		for(final Class<?> c : aidl.getDeclaredClasses()) {
			if(c.getSimpleName().equals(STUB)) {
				try {
					stub = c;
					method = stub.getMethod(AS_INTERFACE, AI_PARAMS);
					break;
				} catch(final NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		}
		mStub = stub;
		mAsInterface = method;
	}
	
	/**
	 * Retrieves the AIDL interface for the communication.
	 * 
	 * @return The AIDL interface.
	 */
	public AIDLInterface getInterface() {
		return mInterface;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		try{
			mInterface = (AIDLInterface) mAsInterface.invoke(mStub, new Object[]{service});
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		} catch(InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		mInterface = null;
	}
}
