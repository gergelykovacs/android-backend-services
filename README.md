## Android Backend Services

Introduction

### Description

Description / Technical details

### Milestones

Here is a list about the goals to achieve.

- [ ] Create a stable AIDL-based Storage-Network communication library.

- [ ] Introduce unit tests for existing functionalities.

- [ ] Fixture and mock data handling option for DEBUG, TEST versions.

- [ ] Improve security.

- [ ] Improve functionality with a Android HaMeR framework as a choice in 
service connection in order to support loosely coupled class hierarchy that 
enables developers to implement more flexible and optimised solutions.

- [ ] Include libraries for RESTful frameworks *(e.g., Retrofit)* to be able to 
implement more coherent and clean solutions

- [ ] Improve networking functionalities with Bluetooth, Wifi-Direct, NFC, ... 
connection capabilities.

### Project Management

Project integration and management with Maven and Gradle.

### Usage

Services can be registerd in different ways: *(the model is that the UI communicates 
with storage service only while storage service communicates with the networking service)*

1. If `android:process="..."` attribute is missing then the services are started 
in the process of the application's UI. IPC is not used but garbage collection by the 
VM may effect UI's performance. (It is not the recommended way of use.)

2. If `android:process="..."` attribute is e.g., `":worker_process"` the same for 
both services. In this case services are started in a different process from the 
application's UI hence garbage collection does not pollute UI's performance but 
IPC is used to push data across process poundaries (UI <=> Storage Service). 
It is the proposed way of use if storage and networking cooperates often or 
e.g., in repeated chain.

3. If `android:process="..."` attributes are different e.g., `android:process=":process_1"` 
and `android:process="process_2"` then 3 processes are started and communication between 
processes using IPC may be expensive. Never the less if networking and data handling 
a huge amount of power then it may be useful to separate VMs. But, on mobile, handheld 
devices if such situation occures then it makes more sense to implement power consuming 
operations using NDK and C++ or simply re-think the application's functionality otherwise 
battery will be drained rapidly. And a huge amout of IPC usage is also not welcomed.

4. If `android:process="..."` attribute is set for networking service only may make sense 
if only small amount of data should be handled continuously while networking may be recursive.

Proper choice of configuration can make things better, devil is always in the details.

The *AndroidManifest.xml* should contain the followings.

```xml
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

    // Get/Set the user object.
    private void setUser() {
        UserModel user = new UserModel();
        user.setId(/* ID may come from e.g., private shared preferences */);
        // Here we have to fetch the user the same way as the list of questions is requested.
        // An event listener will be responsible to set it properly. 
    }

    // Activity connects to the service.
    private void connectService() {
    	if(!mStorageServiceConnection.isServiceConnectionCreated()) {
    		bindService(StorageService.makeIntent(this), 
    						mStorageServiceConnection.getServiceConnection(), 
    															BIND_AUTO_CREATE);
    	}
    }
    
    // Activity is disconnected from the service.
    private void disconnectService() {
    	if(mStorageServiceConnection.isServiceConnectionCreated()) {
    		unbindService(mStorageServiceConnection.getServiceConnection());
    	}
    }

    // E.g., a button is pressed that was pre-set in the layout file.
    public void onButtonPressedTriggerEvent(View view) {

        // ServiceData is Parcelable and can be sent across process boundaries if needed.
    	List<ServiceData> data = new ArrayList<ServiceData>();
    	data.add(mUser);
    	
        // Request is sent to the service together with the user object.
    	mStorageServiceConnection.triggerEvent(ServiceEvent.EVENT_GET_QUESTIONS, data);
    }
    
    // Event handlers may be separated from the Activity itself.
    private class ExampleEventListener implements IServiceEventListener {

		@Override
		public void handleEvent(ServiceEvent event, List<ServiceData> data) {
			if(event == ServiceEvent.EVENT_GET_QUESTIONS) {
                // Handle event and process data
			}
		} 	
    }
}
```
