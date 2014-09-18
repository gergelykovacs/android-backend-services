package com.backend.services.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

/**
 * Networking Utils
 * 
 * <p>Intends to collect reusable tools to give support for networking 
 * jobs and to hide underlying complexity.</p>
 */
public class NetworkingUtils {
	
	//private static final String TAG = NetworkingUtils.class.getSimpleName();
	
	private static final String HTTP_ACCEPT = "Accept";
	private static final String MIME_TYPE = "*/*";
	private static final String HTTP_POST_FIELD = "body";
	
	private static final boolean RAW_POST = true;

	private NetworkingUtils() {
	}
	
	/**
	 * Checks out the network connection to be connected. 
	 * 
	 * @param context The system resource in which context the network connectivity is checked.
	 * @return Gives back whether the network is connected or not.
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Download and store the file specified by the given URI.
	 *  
	 * @param context Specifies the system resources that can be used. 
	 * @param uri Specifies the resource to be downloaded.
	 * @return The absolute file path to the downloaded file.
	 */
	public static String downloadFile(Context context, Uri uri) {
		try {
			File file = StorageUtils.FileIO.getStreamedFile(context, uri.toString());
			final InputStream is = (InputStream) new URL(uri.toString()).getContent();
			final OutputStream os = new FileOutputStream(file);
			StorageUtils.FileIO.ioCopy(is, os);
			is.close();
			os.close();
			return file.getAbsolutePath();
		} catch(Exception e) {
			return null;
		}
	}
	
	/**
	 * Send an HTTP POST request to the server specified by the URI and attach JSON data. 
	 * Returns with the result processed by the implementation of the ResponseHandler<T> 
	 * interface. Raw POST data can be an option to find e.g., for PHP under php://input or 
	 * under $_POST['name'] otherwise. 
	 *  
	 * @param uri The resource identifier.
	 * @param json The HTTP POST data.
	 * @param responseHandler Processes the HTTP response.
	 * @return T The response processed by the response handler.
	 */
	public static <T> T makeHttpPostRequest(Uri uri, JSONObject json, ResponseHandler<T> responseHandler) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(uri.toString());
		try {
			if(RAW_POST) {
				httpPost.setEntity(new StringEntity(json.toString()));
			} else {
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
				nameValuePair.add(new BasicNameValuePair(HTTP_POST_FIELD, json.toString()));
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			}
			httpPost.setHeader(HTTP_ACCEPT, MIME_TYPE);
			return httpClient.execute(httpPost, responseHandler);
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch(ClientProtocolException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Send an HTTP POST request to the server specified by the URI and attach JSON data and or files. 
	 * Returns with the result processed by the implementation of the ResponseHandler<T> 
	 * interface. Raw POST data is not an option for the current implementation.
	 * 
	 * @param uri The resource identifier.
	 * @param json The HTTP POST data.
	 * @param fileAttachments The mapping of file attachments.
	 * @param responseHandler Processes the HTTP response.
	 * @return T The response processed by the response handler.
	 */
	public static <T> T makeHttpPostRequest(Uri uri, JSONObject json, 
												Map<String, String> fileAttachments, 
													ResponseHandler<T> responseHandler) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(uri.toString());
		try {
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			if(json != null)
				builder.addTextBody(HTTP_POST_FIELD, json.toString());
			if(fileAttachments != null) {
				for(Map.Entry<String, String> e : fileAttachments.entrySet()) {
					builder.addPart(e.getKey(), new FileBody(new File(e.getValue())));
				}
			}
			httpPost.setEntity(builder.build());
			httpPost.setHeader(HTTP_ACCEPT, MIME_TYPE);
			return httpClient.execute(httpPost, responseHandler);
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch(ClientProtocolException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Url
	 * 
	 * <p>Url as String can not be passed by reference. To cir</p>
	 */
	public static class Url {
		
		private String mUrl;
		
		public Url() {
		}
		
		public Url(String mUrl) {
			this.setUrl(mUrl);
		}

		public String getUrl() {
			return mUrl;
		}

		public void setUrl(String mUrl) {
			this.mUrl = mUrl;
		}
	}
	
	@Override
	public final Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
