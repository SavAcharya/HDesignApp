package com.hdesign.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.hdesignapp.utils.AlertUtility;

public class URLNameValuePairBuilder {

	private static String TAG = "URLNameValuePairBuilder";

	
	public static String functionVinLogin = "vinLogin";
	public static List<NameValuePair> setVinLoginParam(String Vin_Number) {

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.clear();
		
		postParameters.add(new BasicNameValuePair("vin_number", Vin_Number));		
		AlertUtility.printStatement(TAG,"setVinLoginParameter ---Vin_Number = " + Vin_Number);

		return postParameters;
	}
	
	public static String functionUpdateInfoParam = "/updateuser";
	public static List<NameValuePair> setUpdateUserParam(String authCode,String name, String modelName,int chapterId,
			String avatarName,String imageUrl) {

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.clear();
		postParameters.add(new BasicNameValuePair("auth_code",authCode));
		postParameters.add(new BasicNameValuePair("name", name));
		postParameters.add(new BasicNameValuePair("model_name", modelName));
		postParameters.add(new BasicNameValuePair("chapter_id", ""+chapterId));
		postParameters.add(new BasicNameValuePair("avtar_name", avatarName));
		postParameters.add(new BasicNameValuePair("profile_image", imageUrl));
		
		AlertUtility.printStatement(TAG,"setUpdateUserParam ---authcode = " + authCode+ ", name = " + name+", ModelName = "+modelName+", Chapter Id = "+chapterId+
				", Avatar Name = "+avatarName+",ImageUrl - "+imageUrl);

		return postParameters;
	}
	
	public static String functionSocialSyncParam = "/syncSocial";
	public static List<NameValuePair> setSocialSyncParam(String userId,String vinNumber,String name, String modelName, 
			String socialType, String avatarName, long socialId) {

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.clear();
		postParameters.add(new BasicNameValuePair("user_id",userId));
		postParameters.add(new BasicNameValuePair("vin_number", vinNumber));
		postParameters.add(new BasicNameValuePair("name", name));
		postParameters.add(new BasicNameValuePair("model_name", modelName));
		postParameters.add(new BasicNameValuePair("social_type", socialType));
		postParameters.add(new BasicNameValuePair("avtar_name", avatarName));
		postParameters.add(new BasicNameValuePair("social_id", ""+socialId));
		
		AlertUtility.printStatement(TAG,"setSocialSyncParam ---userId = " + userId+ ", vinNumber = " + vinNumber+", name = "+name+", ModelName = "+modelName+
				", social_type = "+socialType+", avtar_name = "+avatarName+" , social_id = "+socialId);

		return postParameters;
	}
	
	
	public static String functionGetChaptersParam = "/getchapters";
	public static List<NameValuePair> setGetChaptersParam(String authCode) {

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.clear();
		postParameters.add(new BasicNameValuePair("auth_code",authCode));
		
		AlertUtility.printStatement(TAG,"setGetChaptersParam ---authCode = " + authCode);

		return postParameters;
	}
	
	
	public static String functionListAllAchievementParam = "/listAllAchievements";
	public static List<NameValuePair> setListAllAchievementParam(String authCode) {

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.clear();
		postParameters.add(new BasicNameValuePair("auth_code",authCode));
		
		AlertUtility.printStatement(TAG,"setListAllAchievementParam ---authCode = " + authCode);

		return postParameters;
	}
	
	public static String functionManageAchievementParam = "/manageAchivements";
	public static List<NameValuePair> setManageAchievementParam(String authCode,int achievementID) {

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.clear();
		postParameters.add(new BasicNameValuePair("auth_code",authCode));
		postParameters.add(new BasicNameValuePair("action_type","add"));
		postParameters.add(new BasicNameValuePair("achivement_id",""+achievementID));
		
		AlertUtility.printStatement(TAG,"setManageAchievementParam ---auth_code = " + authCode+" , achievementID - "+achievementID);

		return postParameters;
	}
	
	public static String functionViewAchievementParam = "/viewAchivements";
	public static List<NameValuePair> setViewAchievementParam(String authCode) {

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.clear();
		postParameters.add(new BasicNameValuePair("auth_code",authCode));
		
		AlertUtility.printStatement(TAG,"setViewAchievementParam ---authCode = " + authCode);

		return postParameters;
	}
	
}

