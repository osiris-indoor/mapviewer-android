package com.fhc25.percepcion.osiris.mapviewer.common.kuasars;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.PhoneNumberUtils;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;

/**
 * General utility methods.
 */
public class Utils {
	private static final String TAG = "Utils";
	
	public static void CopyStream(InputStream is, OutputStream os)    {
		final int buffer_size=1024;
		try{
			byte[] bytes=new byte[buffer_size];
			for(;;){
				int count=is.read(bytes, 0, buffer_size);
				if(count==-1)
					break;
				os.write(bytes, 0, count);
			}
		}
		catch(Exception ex){
			//ex.printStackTrace();
		}    
	}
	
	/**
	 * Returns if device has network connection active.
	 * @param ctx the context of the application.
	 * @return true or false based on if user has net and is connected.
	 */
	public static boolean isOnline(final Context ctx) {
		ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		Toast.makeText(ctx, "Connection error", Toast.LENGTH_LONG).show();
		return false;
	}
	
	/**
	 * Returns if device has network connection active.
	 * @param ctx the context of the application.
	 * @return true or false based on if user has net and is connected.
	 */
	public static boolean isOnlineNoToast(final Context ctx) {
		ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the input string is formatted correctly as an email
	 * @param target the sequence to check format
	 * @return boolean indicating if matches as a valid email
	 */
	public static boolean isValidEmail(CharSequence target) {
			
		final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
		          "\\@" +
		          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
		          "(" +
		          "\\." +
		          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
		          ")+"
		         );
		if(target == null)
			return false;
		
		else return EMAIL_ADDRESS_PATTERN.matcher(target).matches();
	}
	
	/**
	 * Checks if the input string is formatted correctly as a phone.
	 * <p>Allowed formats:
	 * <UL>
	 * <LI><3 digits prefix>-<7 digits phone_number>.
	 * <LI><7 digits phone_number>
	 * <LI><10 digits phone_number>
	 * </UL>
	 * @param phone the phone to check format.
	 * @return boolean indicating if matches a valid phone.
	 */
	public static boolean isValidPhone(CharSequence phone) {
		
		if (phone == null || phone.length() == 0)
			return false;
		String mobile = PhoneNumberUtils.stripSeparators((String)phone);
		mobile = mobile.replaceAll("\\+", "");
		
		//Logger.v(Utils.class.getName(), mobile);

		final Pattern PHONE_PATTERN = Pattern.compile("\\d{7}|\\d{8}|\\d{9}|\\d{10}|\\d{11}|\\d{12}");
		
		boolean result = PHONE_PATTERN.matcher(mobile).matches();
		if (result == true)
			return result;
		return false;
	}
	
	/**
	 * Returns if strings are valid (ie not null, empty, "null"...)
	 * @param value the string to check.
	 * @return true or false based on validation
	 */
	public static boolean isValidString(String value) {
		return (value != null && !value.equals("") && !value.equals("null"));
	}


}
