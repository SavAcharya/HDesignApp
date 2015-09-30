package com.hdesignapp.prefs;

import com.hdesignapp.utils.AlertUtility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class FacebookPrefs {

	private static final String CLASS_NAME = "FacebookPrefs";
	private static String FB_SHARED_PREFS;
	private static final String KEY_PREFIX = "FacebookPrefs";
	
	private static final String FACEBOOK_LOGIN = KEY_PREFIX+"fb_login";
	private static final String FACEBOOK_ACCESS_TOKEN = KEY_PREFIX+"fb_access_token";	
	private static final String FACEBOOK_EXPIRES_TOKEN = KEY_PREFIX+"fb_expire_token";
	
	private static final String FACEBOOK_USER_ID = KEY_PREFIX+"fb_user_id";
	private static final String FACEBOOK_NAME = KEY_PREFIX+"fb_name";
	private static final String FACEBOOK_BIRTHDAY = KEY_PREFIX+"fb_birthday";
	private static final String FACEBOOK_COVER_IMAGE = KEY_PREFIX+"fb_cover_image";
	private static final String FACEBOOK_EDUCATION = KEY_PREFIX+"fb_education";
	private static final String FACEBOOK_EMAIL = KEY_PREFIX+"fb_nemail";
	private static final String FACEBOOK_PROFILE_IMAGE = KEY_PREFIX+"fb_profile_image";
	private static final String FACEBOOK_RELATIONSHIP = KEY_PREFIX+"fb_relationship";
	private static final String FACEBOOK_WORK = KEY_PREFIX+"fb_work";
	private static final String FACEBOOK_EVENTS = KEY_PREFIX+"fb_events";
	private static final String FACEBOOK_CHECKINS = KEY_PREFIX+"fb_checkins";
	private static final String FACEBOOK_GENDER = KEY_PREFIX+"fb_gender";
	private static final String FACEBOOK_MUSIC = KEY_PREFIX+"fb_music";
	
	private SharedPreferences mPrefs;
	private Editor mPrefsEditor;

	public FacebookPrefs(Context context) {

		FB_SHARED_PREFS = context.getApplicationContext().getPackageName();
		this.mPrefs = context.getSharedPreferences(FB_SHARED_PREFS,Activity.MODE_PRIVATE);
		this.mPrefsEditor = mPrefs.edit();
	}
	
	public void setFacebookLogin(Boolean value) {
		mPrefsEditor.putBoolean(FACEBOOK_LOGIN, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookLogin=" + value);
	}

	public boolean getFacebookLogin() {
		boolean value = mPrefs.getBoolean(FACEBOOK_LOGIN, false);
		AlertUtility.printStatement(CLASS_NAME, "getFacebookLogin=" + value);
		return value;
	}
	
	public void setFacebookAccessToken(String value) {
		mPrefsEditor.putString(FACEBOOK_ACCESS_TOKEN, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookAccessToken = " + value);
	}

	public String getFacebookAccessToken() {
		String value = mPrefs.getString(FACEBOOK_ACCESS_TOKEN, "");
		AlertUtility.printStatement(CLASS_NAME, "getFacebookAccessToken=" + value);
		return value;
	}
	
	public void setFacebookTokenExpires(long value) {
		mPrefsEditor.putLong(FACEBOOK_EXPIRES_TOKEN, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookTokenExpires = " + value);
	}

	public long getFacebookTokenExpires() {
		long value = mPrefs.getLong(FACEBOOK_EXPIRES_TOKEN, 0);
		AlertUtility.printStatement(CLASS_NAME, "getFacebookTokenExpires=" + value);
		return value;
	}
	
	public void setFacebookID(long value) {
		mPrefsEditor.putLong(FACEBOOK_USER_ID, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookID = " + value);
	}

	public long getFacebookID() {
		long value = mPrefs.getLong(FACEBOOK_USER_ID, 0);
		AlertUtility.printStatement(CLASS_NAME, "getFacebookID=" + value);
		return value;
	}
	
	public void setFacebookName(String value) {
		mPrefsEditor.putString(FACEBOOK_NAME, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookName = " + value);
	}

	public String getFacebookName() {
		String value = mPrefs.getString(FACEBOOK_NAME, "");
		AlertUtility.printStatement(CLASS_NAME, "getFacebookName=" + value);
		return value;
	}
	
	public void setFacebookBirthday(String value) {
		mPrefsEditor.putString(FACEBOOK_BIRTHDAY, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookBirthday = " + value);
	}

	public String getFacebookBirthday() {
		String value = mPrefs.getString(FACEBOOK_BIRTHDAY, "");
		AlertUtility.printStatement(CLASS_NAME, "getFacebookBirthday=" + value);
		return value;
	}
	
	public void setFacebookCoverImage(String value) {
		mPrefsEditor.putString(FACEBOOK_COVER_IMAGE, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookCoverImage = " + value);
	}

	public String getFacebookCoverImage() {
		String value = mPrefs.getString(FACEBOOK_COVER_IMAGE, "");
		AlertUtility.printStatement(CLASS_NAME, "getFacebookCoverImage=" + value);
		return value;
	}
	
	public void setFacebookEducation(String value) {
		mPrefsEditor.putString(FACEBOOK_EDUCATION, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookEducation = " + value);
	}

	public String getFacebookEducation() {
		String value = mPrefs.getString(FACEBOOK_EDUCATION, "");
		AlertUtility.printStatement(CLASS_NAME, "getFacebookEducation=" + value);
		return value;
	}
	
	public void setFacebookEmail(String value) {
		mPrefsEditor.putString(FACEBOOK_EMAIL, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookEmail = " + value);
	}

	public String getFacebookEmail() {
		String value = mPrefs.getString(FACEBOOK_EMAIL, "");
		AlertUtility.printStatement(CLASS_NAME, "getFacebookEmail=" + value);
		return value;
	}
	
	public void setFacebookProfileImage(String value) {
		mPrefsEditor.putString(FACEBOOK_PROFILE_IMAGE, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookProfileImage = " + value);
	}

	public String getFacebookProfileImage() {
		String value = mPrefs.getString(FACEBOOK_PROFILE_IMAGE, "");
		AlertUtility.printStatement(CLASS_NAME, "getFacebookProfileImage=" + value);
		return value;
	}
	
	public void setFacebookRelationShip(String value) {
		mPrefsEditor.putString(FACEBOOK_RELATIONSHIP, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookRelationShip = " + value);
	}

	public String getFacebookRelationShip() {
		String value = mPrefs.getString(FACEBOOK_RELATIONSHIP, "");
		AlertUtility.printStatement(CLASS_NAME, "getFacebookRelationShip=" + value);
		return value;
	}
	
	public void setFacebookWork(String value) {
		mPrefsEditor.putString(FACEBOOK_WORK, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookWork = " + value);
	}

	public String getFacebookWork() {
		String value = mPrefs.getString(FACEBOOK_WORK, "");
		AlertUtility.printStatement(CLASS_NAME, "getFacebookWork=" + value);
		return value;
	}
	
	public void setFacebookEvents(String value) {
		mPrefsEditor.putString(FACEBOOK_EVENTS, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookEvents = " + value);
	}

	public String getFacebookEvents() {
		String value = mPrefs.getString(FACEBOOK_EVENTS, "");
		AlertUtility.printStatement(CLASS_NAME, "getFacebookEvents=" + value);
		return value;
	}
	
	public void setFacebookCheckins(String value) {
		mPrefsEditor.putString(FACEBOOK_CHECKINS, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookCheckins = " + value);
	}

	public String getFacebookCheckins() {
		String value = mPrefs.getString(FACEBOOK_CHECKINS, "");
		AlertUtility.printStatement(CLASS_NAME, "getFacebookCheckins=" + value);
		return value;
	}
	
	public void setFacebookGender(String value) {
		mPrefsEditor.putString(FACEBOOK_GENDER, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookGender = " + value);
	}

	public String getFacebookGender() {
		String value = mPrefs.getString(FACEBOOK_GENDER, "");
		AlertUtility.printStatement(CLASS_NAME, "getFacebookGender=" + value);
		return value;
	}
	
	public void setFacebookMusic(String value) {
		mPrefsEditor.putString(FACEBOOK_MUSIC, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookMusic = " + value);
	}

	public String getFacebookMusic() {
		String value = mPrefs.getString(FACEBOOK_MUSIC, "");
		AlertUtility.printStatement(CLASS_NAME, "getFacebookMusic=" + value);
		return value;
	}
	
	
}
