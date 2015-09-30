package com.hdesignapp.prefs;

import com.hdesignapp.utils.AlertUtility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class TwitterPrefs {

	private static final String CLASS_NAME = "TwitterPrefs";
	private static String TWITTER_SHARED_PREFS;
	private static final String KEY_PREFIX = "TWITTER_PREFERENCES";
	
	private static final String TWITTER_OAUTH_TOKEN = KEY_PREFIX+"oauth_token";
    private static final String TWITTER_OAUTH_SECRET_TOKEN = KEY_PREFIX+"oauth_token_secret";
    private static final String TWITTER_LOGIN = KEY_PREFIX+"isTwitterLogedIn";
    
    private static final String TWITTER_USER_ID = KEY_PREFIX+"twitter_user_id";
    private static final String TWITTER_USER_NAME = KEY_PREFIX+"twitter_user_name";
    private static final String TWITTER_IMAGEURL = KEY_PREFIX+"twitter_image_url";
        
    private SharedPreferences mPrefs;
	private Editor mPrefsEditor;

	public TwitterPrefs(Context context) {

		TWITTER_SHARED_PREFS = context.getApplicationContext().getPackageName();
		this.mPrefs = context.getSharedPreferences(TWITTER_SHARED_PREFS,Activity.MODE_PRIVATE);
		this.mPrefsEditor = mPrefs.edit();
	}
	
	public void setTwitterID(long value) {
		mPrefsEditor.putLong(TWITTER_USER_ID, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setTwitterID = " + value);
	}

	public long getTwitterID() {
		long value = mPrefs.getLong(TWITTER_USER_ID, 0);
		AlertUtility.printStatement(CLASS_NAME, "getTwitterID=" + value);
		return value;
	}
	
	public void setTwitterName(String value) {
		mPrefsEditor.putString(TWITTER_USER_NAME, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setTwitterName = " + value);
	}

	public String getTwitterName() {
		String value = mPrefs.getString(TWITTER_USER_NAME, "");
		AlertUtility.printStatement(CLASS_NAME, "getTwitterName=" + value);
		return value;
	}
	
	public void setTwitterLogin(Boolean value) {
		mPrefsEditor.putBoolean(TWITTER_LOGIN, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setTwitterLogin=" + value);
	}

	public boolean getTwitterLogin() {
		boolean value = mPrefs.getBoolean(TWITTER_LOGIN, false);
		AlertUtility.printStatement(CLASS_NAME, "getTwitterLogin=" + value);
		return value;
	}
	
	public void setTwitterOAuthToken(String value) {
		mPrefsEditor.putString(TWITTER_OAUTH_TOKEN, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setTwitterOAuthToken = " + value);
	}

	public String getTwitterOAuthToken() {
		String value = mPrefs.getString(TWITTER_OAUTH_TOKEN, "");
		AlertUtility.printStatement(CLASS_NAME, "getTwitterOAuthToken=" + value);
		return value;
	}
	
	public void setTwitterOAuthSecretToken(String value) {
		mPrefsEditor.putString(TWITTER_OAUTH_SECRET_TOKEN, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setTwitterOAuthSecretToken = " + value);
	}

	public String getTwitterOAuthSecretToken() {
		String value = mPrefs.getString(TWITTER_OAUTH_SECRET_TOKEN, "");
		AlertUtility.printStatement(CLASS_NAME, "getTwitterOAuthSecretToken=" + value);
		return value;
	}
	
	public void setTwitterImageUrl(String value) {
		mPrefsEditor.putString(TWITTER_IMAGEURL, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setTwitterImageUrl = " + value);
	}

	public String getTwitterImageUrl() {
		String value = mPrefs.getString(TWITTER_IMAGEURL, "");
		AlertUtility.printStatement(CLASS_NAME, "setTwitterImageUrl=" + value);
		return value;
	}
	
	
	public void saveTwitterPreferences(long id,String name, String imageUrl,boolean isTwitterLogin) {

		setTwitterID(id);
		setTwitterName(name);
		setTwitterImageUrl(imageUrl);
		setTwitterLogin(isTwitterLogin);
	}
	
}
