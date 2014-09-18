package com.backend.services.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.provider.Settings.Secure;
import android.util.Base64;

public class StorageUtils {
	
	public static class Crypto {
		
		private static final String KEY_GENERATOR_ALGORITHM = "AES";
		private static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";
		
		private static final String HEX = "0123456789ABCDEF";
		
		private static String getAndroidId(Context context) {
			String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
			return androidId;
		}
		
		public static String encrypt(Context context, String plainText) throws Exception {	
			return encrypt(getAndroidId(context), plainText);
		}
		
		public static String decrypt(Context context, String cipherText) throws Exception {
			return decrypt(getAndroidId(context), cipherText);
		}
		
		public static String encrypt(String seed, String cleartext) throws Exception {
			PRNGFixes.apply();
			byte[] rawKey = getRawKey(seed.getBytes());
			byte[] result = encrypt(rawKey, cleartext.getBytes());
			return toHex(result);
		}
		
		public static String decrypt(String seed, String encrypted) throws Exception {
			PRNGFixes.apply();
			byte[] rawKey = getRawKey(seed.getBytes());
			byte[] enc = toByte(encrypted);
			byte[] result = decrypt(rawKey, enc);
			return new String(result);
		}
		
		@SuppressLint("TrulyRandom")
		private static byte[] getRawKey(byte[] seed) throws Exception {
			KeyGenerator kgen = KeyGenerator.getInstance(KEY_GENERATOR_ALGORITHM);
			SecureRandom sr = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM);
			sr.setSeed(seed);
			kgen.init(128, sr);
			SecretKey skey = kgen.generateKey();
			byte[] raw = skey.getEncoded();
			return raw;
		}
		
		private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_GENERATOR_ALGORITHM);
			Cipher cipher = Cipher.getInstance(KEY_GENERATOR_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(clear);
			return encrypted;
		}
		
		private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_GENERATOR_ALGORITHM);
			Cipher cipher = Cipher.getInstance(KEY_GENERATOR_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] decrypted = cipher.doFinal(encrypted);
			return decrypted;
		}
		
		public static String toHex(String txt) {
			return toHex(txt.getBytes());
		}
		
		public static String fromHex(String hex) {
			return new String(toByte(hex));
		}
		
		public static byte[] toByte(String hexString) {
			int len = hexString.length()/2;
			byte[] result = new byte[len];
			for (int i = 0; i < len; i++) {
				result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
			}
			return result;
		}
		
		public static String toHex(byte[] buf) {
			if (buf == null) {
				return "";
			}
			StringBuffer result = new StringBuffer(2*buf.length);
			for(int i = 0; i < buf.length; i++) {
				appendHex(result, buf[i]);
			}
			return result.toString();
		}
		
		private static void appendHex(StringBuffer sb, byte b) {
			sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
		}
	}
	
	/**
	 * Shared Preferences handler to store, hold PRIVATE user session data. 
	 * User's unique ID is used for this purpose. 
	 */
	public static class UserSessionManager {
		
		private static final String PREFERENCE_NAME = "HelloHolnap";
		private static final String USER_ID = "user_id";
		
		private SharedPreferences mSharedPreferences;
		private Editor mEditor;
		
		public UserSessionManager(Context context) {
			mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
			mEditor = mSharedPreferences.edit();
		}
		
		public boolean applyId(int i) {
			mEditor.putInt(USER_ID, i);
			return mEditor.commit();
		}
		
		public boolean removeId(int i) {
			mEditor.remove(USER_ID);
			return mEditor.commit();
		}
		
		public int getId() {
			return mSharedPreferences.getInt(USER_ID, 0);
		}
	}
	
	/**
	 * Collection of file I/O handlers. 
	 */
	public static class FileIO {
		
		private static final int BUFFER_SIZE = 1024;
		
		private FileIO() {
		}
		
		/**
		 * Gets the stream File object with the same-file=same-location  idea.
		 * 
		 * @param context In which context the file will be handled. 
		 * @param uri Specifies the file resource and used as name. 
		 * @return Gives back the File object.
		 * @throws IOException
		 */
		public static File getStreamedFile(final Context context, final String uri) throws IOException {
			return context.getFileStreamPath(Base64.encodeToString(uri.getBytes(), Base64.NO_WRAP));
		}
		
		/**
		 * IO stream copy to place InputStream into an OutputStream.
		 * 
		 * @param is The InputStream object to be read.
		 * @param os The OutputStrea object to be written.
		 * @return The total read bytes.
		 * @throws IOException
		 */
		public static int ioCopy(final InputStream is, final OutputStream os) throws IOException {
			final byte[] buffer = new byte[BUFFER_SIZE];
			int total = 0, read = 0;
			while((read = is.read(buffer)) != -1) {
				os.write(buffer, 0, read);
				total += read;
			}
			return total;
		}
		
		/**
		 * Write text file to the internal PRIVATE storage.
		 * 
		 * @param context
		 * @param fileName
		 * @param text
		 * @return true if success otherwise false. 
		 */
		public static boolean writeTextFile(Context context, String fileName, String text) {
			try {
				FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
				fos.write(text.getBytes());
				fos.close();
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		/**
		 * Reads text from file.
		 * 
		 * @param context
		 * @param fileName
		 * @return String content or null on error.
		 */
		public static String readTextFile(Context context, String fileName) {
			try {
				FileInputStream fis = context.openFileInput(fileName);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				StringBuilder sb = new StringBuilder();
				String line;
				while((line = br.readLine()) != null) {
					sb.append(line);
				}
				return sb.toString();
			} catch(FileNotFoundException e) {
				e.printStackTrace();
				return null;
			} catch(IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		public final Object clone() throws CloneNotSupportedException {
			throw new CloneNotSupportedException();
		}
	}
}
