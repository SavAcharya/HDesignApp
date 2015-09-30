package com.hdesignapp.prefs;

import com.hdesignapp.utils.AlertUtility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class GooglePrefs {

	private static final String CLASS_NAME = "AppPreferences";
	private static String GOOGLE_SHARED_PREFS;
	private static final String KEY_PREFIX = "APP_PREFERENCE_";

    private static final String GOOGLE_USER_NAME = KEY_PREFIX+"google_user_name";
    private static final String GOOGLE_EMAIL = KEY_PREFIX+"google_email";
    private static final String GOOGLE_IMAGE_URL = KEY_PREFIX+"google_image_url";
    
    private SharedPreferences mPrefs;
	private Editor mPrefsEditor;

	public GooglePrefs(Context context) {

		GOOGLE_SHARED_PREFS = context.getApplicationContext().getPackageName();
		this.mPrefs = context.getSharedPreferences(GOOGLE_SHARED_PREFS,Activity.MODE_PRIVATE);
		this.mPrefsEditor = mPrefs.edit();
	}
	
	public void setGoogleName(String value) {
		mPrefsEditor.putString(GOOGLE_USER_NAME, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setName = " + value);
	}

	public String getGoogleName() {
		String value = mPrefs.getString(GOOGLE_USER_NAME, "");
		AlertUtility.printStatement(CLASS_NAME, "getName=" + value);
		return value;
	}
	
	public void setGoogleEmail(String value) {
		mPrefsEditor.putString(GOOGLE_EMAIL, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setGoogleEmail = " + value);
	}

	public String getGoogleEmail() {
		String value = mPrefs.getString(GOOGLE_EMAIL, "");
		AlertUtility.printStatement(CLASS_NAME, "getGoogleEmail=" + value);
		return value;
	}
	
	public void setGoogleProfileImage(String value) {
		mPrefsEditor.putString(GOOGLE_IMAGE_URL, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setGoogleProfileImage = " + value);
	}

	public String getGoogleProfileImage() {
		String value = mPrefs.getString(GOOGLE_IMAGE_URL, "");
		AlertUtility.printStatement(CLASS_NAME, "getGoogleProfileImage=" + value);
		return value;
	}
	
	public void saveTwitterPreferences(String name,String email, String imageUrl) {

		setGoogleName(name);
		setGoogleEmail(email);
		setGoogleProfileImage(imageUrl);
	}
}
