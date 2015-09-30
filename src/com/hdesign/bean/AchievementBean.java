package com.hdesign.bean;

import java.io.Serializable;

public class AchievementBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int achievementMaserId;
	String name,imageUrl,achievementName,achievementCreated;
	int totalAchieveMentRecords,viewAchieveMentRecords,achieveUserId;
	
	
	public String getAchievementName() {
		return achievementName;
	}
	public void setAchievementName(String achievementName) {
		this.achievementName = achievementName;
	}
	
	public int getAchievementMaserId() {
		return achievementMaserId;
	}
	public void setAchievementMaserId(int achievementMaserId) {
		this.achievementMaserId = achievementMaserId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAchievementImageUrl() {
		return imageUrl;
	}
	public void setAchievementImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getAchievementCreated() {
		return achievementCreated;
	}
	public void setAchievementCreated(String achievementCreated) {
		this.achievementCreated = achievementCreated;
	}
	
	public int getTotalAchieveMentRecords() {
		return totalAchieveMentRecords;
	}
	public void setTotalAchieveMentRecords(int totalAchieveMentRecords) {
		this.totalAchieveMentRecords = totalAchieveMentRecords;
	}
	
	public int getViewAchieveMentRecords() {
		return viewAchieveMentRecords;
	}
	public void setViewAchieveMentRecords(int viewAchieveMentRecords) {
		this.viewAchieveMentRecords = viewAchieveMentRecords;
	}
	
	public int getAchieveUserId() {
		return achieveUserId;
	}
	public void setAchieveUserId(int achieveUserId) {
		this.achieveUserId = achieveUserId;
	}
}
