package com.hdesign.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hdesign.bean.AchievementBean;

public class AchievementParser {

	public static ArrayList<AchievementBean> getParsedAchievementList(String jsonResponse) throws JSONException {

		try {
			ArrayList<AchievementBean> arrayList = new ArrayList<AchievementBean>();
			JSONObject mainJsonObject = new JSONObject(jsonResponse);
			String success = mainJsonObject.getJSONObject("status").getString("type");
			if (success.equalsIgnoreCase("Success")) {
				JSONArray array = null;
				array = mainJsonObject.getJSONArray("achivements");
				if (array != null) {
					for (int i = 0; i < array.length(); i++) {
						AchievementBean achieveBean = getParsedAchievementResult((JSONObject) array.get(i));
						arrayList.add(achieveBean);
					}
				}
				
				JSONObject jsonObject = mainJsonObject.getJSONObject("data").getJSONObject("pagination");
				AchievementBean achieveBean = new AchievementBean();
				achieveBean.setTotalAchieveMentRecords(jsonObject.getInt("total_records"));
				return arrayList;
			} else {
				String error = mainJsonObject.getString("error").trim();
				System.out.println("error = " + error);
			}
		} catch (JSONException ex) {
		}
		return null;
	}
	
	public static AchievementBean getParsedAchievementResult(JSONObject achievementJsonResult){

		AchievementBean achieveBean = new AchievementBean();
		try {	
			
			   achieveBean.setAchievementMaserId(achievementJsonResult.getInt("achivement_master_id"));
			   achieveBean.setAchievementName(achievementJsonResult.getString("name"));
			   achieveBean.setAchievementImageUrl(achievementJsonResult.getString("image"));
			   achieveBean.setAchievementCreated(achievementJsonResult.getString("created"));
			 return achieveBean;
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<AchievementBean> getParsedViewAchievementList(String jsonResponse) throws JSONException {

		try {
			ArrayList<AchievementBean> arrayList = new ArrayList<AchievementBean>();
			JSONObject mainJsonObject = new JSONObject(jsonResponse);
			String success = mainJsonObject.getJSONObject("status").getString("type");
			if (success.equalsIgnoreCase("Success")) {
				JSONArray array = null;
				array = mainJsonObject.getJSONArray("achivements");
				if (array != null) {
					for (int i = 0; i < array.length(); i++) {
						AchievementBean mapData = getParsedViewAchievementResult((JSONObject) array.get(i));
						arrayList.add(mapData);
					}
				}	
				
				JSONObject jsonObject = mainJsonObject.getJSONObject("data").getJSONObject("pagination");
				AchievementBean achieveBean = new AchievementBean();
				achieveBean.setViewAchieveMentRecords(jsonObject.getInt("total_records"));
				
				return arrayList;
			} else {
				String error = mainJsonObject.getString("error").trim();
				System.out.println("error = " + error);
			}
		} catch (JSONException ex) {
		}
		return null;
	}
	
	public static AchievementBean getParsedViewAchievementResult(JSONObject achievementJsonResult){

		AchievementBean achieveBean = new AchievementBean();
		try {	
			
			   achieveBean.setAchievementMaserId(achievementJsonResult.getInt("achivement_master_id"));
			   achieveBean.setAchievementName(achievementJsonResult.getString("achivement_name"));
			   achieveBean.setName(achievementJsonResult.getString("name"));
			   achieveBean.setAchievementImageUrl(achievementJsonResult.getString("achivement_image"));
			   achieveBean.setAchieveUserId(achievementJsonResult.getInt("user_id"));
			 return achieveBean;
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
