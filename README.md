## Android Backend Services

*Library Project*

### Description


### Usage

The *AndroidManifest.xml* should contain the followings.

```
<service 
  android:name="com.backend.services.services.StorageService" 
  android:process=":worker_process"
  android:exported="false"
  android:enabled="true"/>
    
<service 
  android:name="com.backend.services.services.NetworkingService" 
  android:process=":worker_process"
  android:exported="false"
  android:enabled="true"/>
```

An example Activity may look like as follows.

```java
package tld.example.app.activity;

// import android packages

import com.backend.services.events.IServiceEventListener;
import com.backend.services.events.ServiceEvent;
import com.backend.services.services.StorageService;
import com.backend.services.connections.StorageServiceConnection;
import com.backend.services.connections.IServiceConnection;
import com.backend.services.ServiceData;
import com.backend.services.datamodels.QuestionModel;
import com.backend.services.datamodels.UserModel;

public class ExampleActivity extends Activity {
	
	private IServiceConnection mStorageServiceConnection = new StorageServiceConnection();

    private UserModel mUser;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        // Do things

        mUser = getUser();

        mStorageServiceConnection.attachEventListener(new ExampleEventListener());
    }
    
    @Override
    public void onStart() {
    	super.onStart();
    	connectService();
    }
    
    @Override
    public void onDestroy() {
    	disconnectService();
    	super.onDestroy();
    }

    private UserModel getUser() {
        UserModel user;
        // Fetch the user
        return user;
    }
        
    private void connectService() {
    	if(!mStorageServiceConnection.isServiceConnectionCreated()) {
    		bindService(StorageService.makeIntent(this), 
    						mStorageServiceConnection.getServiceConnection(), 
    															BIND_AUTO_CREATE);
    	}
    }
    
    private void disconnectService() {
    	if(mStorageServiceConnection.isServiceConnectionCreated()) {
    		unbindService(mStorageServiceConnection.getServiceConnection());
    	}
    }

    // If a button is pressed.
    public void onButtonPressedTriggerEvent(View view) {
    	List<ServiceData> data = new ArrayList<ServiceData>();

    	data.add(mUser);
    	
    	mStorageServiceConnection.triggerEvent(ServiceEvent.EVENT_GET_QUESTIONS, data);
    }
    
    private class ExampleEventListener implements IServiceEventListener {

		@Override
		public void handleEvent(ServiceEvent event, List<ServiceData> data) {
			if(event == ServiceEvent.EVENT_GET_QUESTIONS) {
                // Handle event
			}
		} 	
    }
}
```
