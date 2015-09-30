package com.hdesign.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hdesign.bean.UserDataBean;

public class ChapterParser {

	public static ArrayList<UserDataBean> getParsedChaptersList(String jsonResponse) throws JSONException {

		try {
			ArrayList<UserDataBean> arrayList = new ArrayList<UserDataBean>();
			JSONObject mainJsonObject = new JSONObject(jsonResponse);
			String success = mainJsonObject.getJSONObject("status").getString("type");
			if (success.equalsIgnoreCase("Success")) {
				JSONArray array = null;
				array = mainJsonObject.getJSONArray("chapters");
				if (array != null) {
					for (int i = 0; i < array.length(); i++) {
						UserDataBean mapData = getParsedChaptersResult((JSONObject) array.get(i));
						arrayList.add(mapData);
					}
				}	
				
				return arrayList;
			} else {
				String error = mainJsonObject.getString("error").trim();
				System.out.println("error = " + error);
			}
		} catch (JSONException ex) {
		}
		return null;
	}
	
	public static UserDataBean getParsedChaptersResult(JSONObject chapterJsonResult){

		UserDataBean chapterBean = new UserDataBean();
		try {	
			  chapterBean.setVinChapterId(chapterJsonResult.getInt("chapter_id"));
			  chapterBean.setVinChapterName(chapterJsonResult.getString("chapter_name"));
			  chapterBean.setVinIsActive(chapterJsonResult.getInt("is_active"));
			  chapterBean.setVinCreatedChapterTime(chapterJsonResult.getString("created"));
			  chapterBean.setChapterCoverImageURL(chapterJsonResult.getString("cover_image"));
			  chapterBean.setChapterProfileImageURL(chapterJsonResult.getString("profile_image"));
			  
			 return chapterBean;
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
