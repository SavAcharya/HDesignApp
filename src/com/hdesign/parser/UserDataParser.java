package com.hdesign.parser;

import org.json.JSONException;
import org.json.JSONObject;
import com.hdesign.bean.UserDataBean;
import com.hdesignapp.utils.StringUtility;

public class UserDataParser {

	public static UserDataBean getParsedFbProfileResult(JSONObject userProfileJsonResult) {
		UserDataBean userFbProfileData = new UserDataBean();

		try {

			userFbProfileData.setFbId(userProfileJsonResult.getLong("id"));
			
			String fname = "";
			String lname = "";

			if (userProfileJsonResult.has("first_name")) {
				if (StringUtility.isNotNullOrEmpty(userProfileJsonResult.getString("first_name")))
					fname = StringUtility.FirstLetterInUpperCase(userProfileJsonResult.getString("first_name")).trim();
			}

			if (userProfileJsonResult.has("last_name")) {
				if (StringUtility.isNotNullOrEmpty(userProfileJsonResult.getString("last_name")))
					lname = StringUtility.FirstLetterInUpperCase(userProfileJsonResult.getString("last_name")).trim();
			}

			userFbProfileData.setFbFirstName(fname);
			userFbProfileData.setFbLastName(lname);
			if (lname.length() > 0) {
				userFbProfileData.setFbName(fname + " " + lname);
			} else {
				userFbProfileData.setFbName(fname);
			
			}

			if (userProfileJsonResult.has("gender")) {
				if (StringUtility.isNotNullOrEmpty(userProfileJsonResult.getString("gender"))) {
					userFbProfileData.setFbGender(userProfileJsonResult.getString("gender"));
				} else
					userFbProfileData.setFbGender("");
			} else
				userFbProfileData.setFbGender("");

			return userFbProfileData;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static UserDataBean getParsedVinLoginProfileResult(JSONObject userVinProfileJsonResult) {
		UserDataBean userVinLoginProfileData = new UserDataBean();
		JSONObject chapterJsonObject;
		
		try {

			userVinLoginProfileData.setVinLoginUserid(userVinProfileJsonResult.getString("user_id"));
			
			if (userVinProfileJsonResult.has("vin_number")) {
				if (StringUtility.isNotNullOrEmpty(userVinProfileJsonResult.getString("vin_number"))) {
					userVinLoginProfileData.setVinNumber(userVinProfileJsonResult.getString("vin_number"));
				} else
					userVinLoginProfileData.setVinNumber("");
			} else
				userVinLoginProfileData.setVinNumber("");
			
			
			if (userVinProfileJsonResult.has("name")) {
				if (StringUtility.isNotNullOrEmpty(userVinProfileJsonResult.getString("name"))) {
					userVinLoginProfileData.setVinName(userVinProfileJsonResult.getString("name"));
				} else
					userVinLoginProfileData.setVinName("");
			} else
				userVinLoginProfileData.setVinName("");

			
			if (userVinProfileJsonResult.has("model_name")) {
				if (StringUtility.isNotNullOrEmpty(userVinProfileJsonResult.getString("model_name"))) {
					userVinLoginProfileData.setVinModelName(userVinProfileJsonResult.getString("model_name"));
				} else
					userVinLoginProfileData.setVinModelName("");
			} else
				userVinLoginProfileData.setVinModelName("");
			
			
			if (userVinProfileJsonResult.has("chapter_id")) {
				if (StringUtility.isNotNullOrEmpty(userVinProfileJsonResult.getString("chapter_id"))) {
					userVinLoginProfileData.setVinChapterId(userVinProfileJsonResult.getInt("chapter_id"));
				} else
					userVinLoginProfileData.setVinChapterId(0);
			} else
				userVinLoginProfileData.setVinChapterId(0);
			
           
			if (userVinProfileJsonResult.has("avtar_name")) {
				if (StringUtility.isNotNullOrEmpty(userVinProfileJsonResult.getString("avtar_name"))) {
					userVinLoginProfileData.setVinAvatarName(userVinProfileJsonResult.getString("avtar_name"));
				} else
					userVinLoginProfileData.setVinAvatarName("");
			} else
				userVinLoginProfileData.setVinAvatarName("");
			
			
			if (userVinProfileJsonResult.has("profile_image")) {
				if (StringUtility.isNotNullOrEmpty(userVinProfileJsonResult.getString("profile_image"))) {
					userVinLoginProfileData.setVinProfileImage(userVinProfileJsonResult.getString("profile_image"));
				} else
					userVinLoginProfileData.setVinProfileImage("");
			} else
				userVinLoginProfileData.setVinProfileImage("");
		
			if (userVinProfileJsonResult.has("chapter")) {
			      chapterJsonObject = userVinProfileJsonResult.getJSONObject("chapter");
			     
			      if(chapterJsonObject.has("chapter_id")) {
						if (StringUtility.isNotNullOrEmpty(chapterJsonObject.getString("chapter_id"))) {
							userVinLoginProfileData.setVinChapterId(chapterJsonObject.getInt("chapter_id"));
						} else
							userVinLoginProfileData.setVinChapterId(0);
				  } else
						userVinLoginProfileData.setVinChapterId(0);
			      
			      
			      if(chapterJsonObject.has("chapter_name")) {
						if (StringUtility.isNotNullOrEmpty(chapterJsonObject.getString("chapter_name"))) {
							userVinLoginProfileData.setVinChapterName(chapterJsonObject.getString("chapter_name"));
						} else
							userVinLoginProfileData.setVinChapterName("");
				  } else
						userVinLoginProfileData.setVinChapterName("");
			      
			      
			      if(chapterJsonObject.has("is_active")) {
						if (StringUtility.isNotNullOrEmpty(chapterJsonObject.getString("is_active"))) {
							userVinLoginProfileData.setVinIsActive(chapterJsonObject.getInt("is_active"));
						} else
							userVinLoginProfileData.setVinIsActive(0);
				  } else
						userVinLoginProfileData.setVinIsActive(0);

			      
			      if(chapterJsonObject.has("created")) {
						if (StringUtility.isNotNullOrEmpty(chapterJsonObject.getString("created"))) {
							userVinLoginProfileData.setVinCreatedChapterTime(userVinProfileJsonResult.getString("created"));
						} else
							userVinLoginProfileData.setVinCreatedChapterTime("");
				  } else
						userVinLoginProfileData.setVinCreatedChapterTime("");
			      
			}
			
			
			return userVinLoginProfileData;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
