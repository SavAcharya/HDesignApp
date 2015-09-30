package com.hdesignapp.prefs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.hdesignapp.utils.AlertUtility;

public class AppPreferences {

	private static final String CLASS_NAME = "AppPreferences";
	private static String APP_SHARED_PREFS;
	private static final String KEY_PREFIX = "APP_PREFERENCE_";

	private static final String IS_FIRST = KEY_PREFIX+"is_first";
	private static final String IS_LOGIN = KEY_PREFIX+"is_login";
	private static final String IS_TUTORIAL_FIRST = KEY_PREFIX+"is_tutorial_first";
	
	private static final String NAME = KEY_PREFIX+"user_name";
	private static final String GENDER = KEY_PREFIX+"gender";
	private static final String EMAIL = KEY_PREFIX+"email";
	private static final String VIN_ID = KEY_PREFIX+"vin_id";
	private static final String VIN_NUMBER = KEY_PREFIX+"vin_number";
	private static final String IMAGE_URL = KEY_PREFIX+"image_url";
	private static final String MODEL_NAME = KEY_PREFIX+"model_name";
	private static final String MODEL_NUMBER = KEY_PREFIX+"model_number";
	private static final String CHAPTER_ID = KEY_PREFIX+"chapter_id";
	private static final String CHAPTER_NAME = KEY_PREFIX+"chapter_name";
	private static final String CHAPTER_CREATED_TIME = KEY_PREFIX+"chapter_created_name";
	private static final String IS_ACTIVE = KEY_PREFIX+"is_chapter_active";
	private static final String AVATAR_NAME = KEY_PREFIX+"avatar_name";
	
	
	private SharedPreferences mPrefs;
	private Editor mPrefsEditor;

	public AppPreferences(Context context) {

		APP_SHARED_PREFS = context.getApplicationContext().getPackageName();
		this.mPrefs = context.getSharedPreferences(APP_SHARED_PREFS,Activity.MODE_PRIVATE);
		this.mPrefsEditor = mPrefs.edit();
	}
	
	public void setFirst(boolean value)
	{
		mPrefsEditor.putBoolean(IS_FIRST, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFirst=" + value);
	}
	
	public boolean isFirst() {
		boolean value = mPrefs.getBoolean(IS_FIRST, true);
		AlertUtility.printStatement(CLASS_NAME, "isFirst=" + value);
		return value;
	}
	
	public void setName(String value) {
		mPrefsEditor.putString(NAME, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setName = " + value);
	}

	public String getName() {
		String value = mPrefs.getString(NAME, "");
		AlertUtility.printStatement(CLASS_NAME, "getName=" + value);
		return value;
	}
	
	public void setGender(String value) {
		mPrefsEditor.putString(GENDER, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setGender = " + value);
	}

	public String getGender() {
		String value = mPrefs.getString(GENDER, "");
		AlertUtility.printStatement(CLASS_NAME, "getGender=" + value);
		return value;
	}

	public void setEmail(String value) {
		mPrefsEditor.putString(EMAIL, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setEmail = " + value);
	}

	public String getEmail() {
		String value = mPrefs.getString(EMAIL, "");
		AlertUtility.printStatement(CLASS_NAME, "getEmail=" + value);
		return value;
	}
	
	public void setVinID(String value) {
		mPrefsEditor.putString(VIN_ID, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setVinID = " + value);
	}

	public String getVinID() {
		String value = mPrefs.getString(VIN_ID, "");
		AlertUtility.printStatement(CLASS_NAME, "getVinID=" + value);
		return value;
	}
	
	
	public void setVinNumber(String value) {
		mPrefsEditor.putString(VIN_NUMBER, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setVinNumber = " + value);
	}

	public String getVinNumber() {
		String value = mPrefs.getString(VIN_NUMBER, "");
		AlertUtility.printStatement(CLASS_NAME, "getVinNumber=" + value);
		return value;
	}
	
	public void setImageUrl(String value) {
		mPrefsEditor.putString(IMAGE_URL, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setImageUrl = " + value);
	}

	public String getImageUrl() {
		String value = mPrefs.getString(IMAGE_URL, "");
		AlertUtility.printStatement(CLASS_NAME, "getImageUrl=" + value);
		return value;
	}
	
	public void setModelName(String value) {
		mPrefsEditor.putString(MODEL_NAME, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setModelName = " + value);
	}

	public String getModelName() {
		String value = mPrefs.getString(MODEL_NAME, "");
		AlertUtility.printStatement(CLASS_NAME, "getModelName=" + value);
		return value;
	}
	
	public void setModelNumber(String value) {
		mPrefsEditor.putString(MODEL_NUMBER, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setModelNumber = " + value);
	}

	public String getModelNumber() {
		String value = mPrefs.getString(MODEL_NUMBER, "");
		AlertUtility.printStatement(CLASS_NAME, "getModelNumber=" + value);
		return value;
	}
	
	public void setChapterID(int value) {
		mPrefsEditor.putInt(CHAPTER_ID, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setChapterID = " + value);
	}

	public int getChapterID() {
		int value = mPrefs.getInt(CHAPTER_ID, 0);
		AlertUtility.printStatement(CLASS_NAME, "getChapterID=" + value);
		return value;
	}
	
	public void setChapterName(String value) {
		mPrefsEditor.putString(CHAPTER_NAME, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setChapterName = " + value);
	}

	public String getChapterName() {
		String value = mPrefs.getString(CHAPTER_NAME, "");
		AlertUtility.printStatement(CLASS_NAME, "getChapterName=" + value);
		return value;
	}
	
	
	public void setChapterActive(int value) {
		mPrefsEditor.putInt(IS_ACTIVE, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setChapterName = " + value);
	}

	public int getChapterActive() {
		int value = mPrefs.getInt(IS_ACTIVE, 0);
		AlertUtility.printStatement(CLASS_NAME, "getChapterName=" + value);
		return value;
	}
	
	public void setChapterCreatedTime(String value) {
		mPrefsEditor.putString(CHAPTER_CREATED_TIME, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setChapterCreatedTime = " + value);
	}

	public String getChapterCreatedTime() {
		String value = mPrefs.getString(CHAPTER_CREATED_TIME, "");
		AlertUtility.printStatement(CLASS_NAME, "getChapterCreatedTime=" + value);
		return value;
	}
	
	public void setAvatarName(String value) {
		mPrefsEditor.putString(AVATAR_NAME, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setAvatarName = " + value);
	}

	public String getAvatarName() {
		String value = mPrefs.getString(AVATAR_NAME, "");
		AlertUtility.printStatement(CLASS_NAME, "getAvatarName=" + value);
		return value;
	}
	
	public boolean isLogin() {
		boolean value = mPrefs.getBoolean(IS_LOGIN, false);
		AlertUtility.printStatement(CLASS_NAME, "isLogin=" + value);
		return value;
	}
	
	public void setLogin(boolean value) {
		mPrefsEditor.putBoolean(IS_LOGIN, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setLogin = " + value);
	}
	
	public boolean isTutorialFirst() {
		boolean value = mPrefs.getBoolean(IS_TUTORIAL_FIRST, true);
		AlertUtility.printStatement(CLASS_NAME, "isTutorialFirst=" + value);
		return value;
	}
	
	public void setTutorialFirst(boolean value) {
		mPrefsEditor.putBoolean(IS_TUTORIAL_FIRST, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setTutorialFirst = " + value);
	}
	

	
}
