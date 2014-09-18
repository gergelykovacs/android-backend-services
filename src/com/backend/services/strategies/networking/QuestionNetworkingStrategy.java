package com.backend.services.strategies.networking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.backend.services.ServiceData;
import com.backend.services.connections.IServiceConnection;
import com.backend.services.events.ServiceEvent;
import com.backend.services.settings.Api;
import com.backend.services.settings.HttpStatusCode;
import com.backend.services.strategies.IStrategy;
import com.backend.services.utils.CommonUtils;
import com.backend.services.utils.EventUtils;
import com.backend.services.utils.NetworkingUtils;
import com.backend.services.utils.SettingsUtils;

public class QuestionNetworkingStrategy implements IStrategy {
	
	private static final String TAG = QuestionNetworkingStrategy.class.getSimpleName();
		
	@SuppressWarnings("unused")
	private ServiceEvent mEvent;
	private List<ServiceData> mData;
	@SuppressWarnings("unused")
	private IServiceConnection mServiceConnection;
	private Context mContext;
	
	public QuestionNetworkingStrategy() {
	}
	
	public QuestionNetworkingStrategy(ServiceEvent event, List<ServiceData> data, 
							IServiceConnection serviceConnection, Context context) {
		mEvent = event;
		mData = data;
		mServiceConnection = serviceConnection;
		mContext = context;
	}

	@Override
	public List<ServiceData> process() {
		if(!NetworkingUtils.isNetworkConnected(mContext)) {
			EventUtils.setEvent(ServiceEvent.EVENT_ERROR_NETWORK_DOWN, mData);
			return mData;
		}
		JSONObject json = new JSONObject();
		CommonUtils.StringHolder url = new CommonUtils.StringHolder();
		if(setRequest(url, json)) {
			return NetworkingUtils.makeHttpPostRequest(Uri.parse(url.getString()), json, new JSONResponseHandler());
		} else {
			EventUtils.setEvent(ServiceEvent.EVENT_ERROR_SYSTEM, mData);
			return mData;
		}	
	}
	
	private class JSONResponseHandler implements ResponseHandler<List<ServiceData>> {

		@Override
		public List<ServiceData> handleResponse(HttpResponse response) 
													throws ClientProtocolException, IOException {
			List<ServiceData> result = new ArrayList<ServiceData>();
			HttpStatusCode statusCode = SettingsUtils.findHttpStatusCodeBy(
														response.getStatusLine().getStatusCode());
			if(statusCode == HttpStatusCode.HTTP_STATUS_OK) {
				try {
					String JSONResponse = (new BasicResponseHandler()).handleResponse(response);
					JSONObject json = (JSONObject) (new JSONTokener(JSONResponse)).nextValue();
					if(!json.isNull(Api.API_FLAG_CONTENT.stringValue())) {
						JSONObject content = json.getJSONObject(Api.API_FLAG_CONTENT.stringValue());
						setResult(result, content);
					} else {
						JSONObject error = json.optJSONObject(Api.API_FLAG_ERROR.stringValue());
						int errorCode = error.optInt(Api.API_FLAG_ERROR_CODE.stringValue());
						Log.e(TAG, "code: "+ errorCode);
					}
				} catch(JSONException e) {
					e.printStackTrace();
					EventUtils.setEvent(ServiceEvent.EVENT_ERROR_SYSTEM, mData);
					result = mData;
				}
			} else {
				EventUtils.setEvent(ServiceEvent.EVENT_ERROR_IN_COMMUNICATION, mData);
				result = mData;
			}
			return result;
		}
	}
	
	private boolean setRequest(CommonUtils.StringHolder url, JSONObject json) {
		// Do things
		return false;
	}
	
	private void setResult(List<ServiceData> result, JSONObject content) {
		// Do things
	}
}
