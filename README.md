## Android Backend Services

It is necessary to decouple different functionalities, 
responsibilities from each other in order to create a clean, 
reusable implementation. And what it all 
boils down if modification is needed in some parts a project that 
must not have any effect on the others.

Three parts are distinguished *(layering)*

1. The User Interface capabilities that handles what user can do. 
It must not kown anything about what goes in the background.

2. The way as data is processed and stored.

3. The way of communication as the device connects to one or more 
other devices.

This project aims to separate the last two responsibilities and 
collect useful implementations and tools that can help to make 
development more structured, reusable and testable. 
It aims to be a Framework by which developers can focuse more 
on business logic rather then writing boilerplate codes.

Example application will be provided in order to see how to 
use the *Framework*.

*The project is built on the top of an event driven solution.*

### Remarks

The current state contains only the basic idea.

### Description

... soon

#### Terminologies

- **Storage vs. Networking** services. The term *Storage* is chosen because 
reusable data must be persisted and with other word that must be *stored* 
even if we talk about persisting data in files or in database and so on. 
*Networking* may be confusing if we talk about e.g., Bluetooth connection 
but if we re-think in case of at least two connected devices we may say 
they form a network even if it contains only two devices.

- **Events** are intentions that may be triggered and dispatched.

- **Event Holder** is an object that can store the String value of an event 
and it is a kind of *Service Data*. It aims to exchange event type between 
components. E.g., Event-A is triggered and during the process something went 
wrong hence an error response should be return and some message shown to the 
user. But how to change the triggered event to another one? The semantic 
of this object is the answer for the question. *Event Holder* can be placed 
into the list of *Service Data* and dispatched if needed.

- **Service Data** is what can be attached to events and passed between 
Services even if it must be marshalled and de-marshalled if IPC is used.

- **Utils** are components by which some sort of reusable functionality 
can be achieved.

### Milestones

Here is a list about the goals to achieve.

- [x] Create a public GitHub repo with the existing structure.

- [ ] Create a stable AIDL-based Storage-Network communication library.

- [ ] Introduce unit tests for existing functionalities.

- [ ] Fixtures or mock data possibility for *debug* and *test* versions.

- [ ] Improve security.

- [ ] Improve functionality with a Android HaMeR framework as a choice in 
service connection establishment and communication in order to support 
loosely coupled class hierarchy that 
enables developers to implement more flexible and optimised solutions.

- [ ] Include libraries for RESTful frameworks *(e.g., Retrofit)* to be able to 
implement more coherent and clean solutions

- [ ] Improve networking functionalities with Bluetooth, Wifi-Direct, NFC, etc. 
connection capabilities.

- [ ] Providing documentation togethet with usecase, component, sequence, 
activity and class diagrams to demonstrate how components work together to 
give a coherent solution.

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
  
    private IServiceEventListener mExampleEventListener;

    private UserModel mUser;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        // Do things

        mExampleEventListener = new ExampleEventListener();
        mStorageServiceConnection.attachEventListener(mExampleEventListener);
    }
    
    @Override
    public void onStart() {
    	super.onStart();
    	connectService();
    }
    
    @Override
    public void onDestroy() {
        mStorageServiceConnection.detachEventListener(mExampleEventListener);
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
