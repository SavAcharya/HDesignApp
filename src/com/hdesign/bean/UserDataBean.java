package com.hdesign.bean;

import java.io.Serializable;

public class UserDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long fbId = 0;
	private String fbName = "";
	private String fbFirstName = "";
	private String fbLastName = "";
	private String fbGender = "";
	
	private String vinLoginUserid;
	private String vinNumber;
	private String vinName;
	private String vinModelName;
	private String vinAvatarName;	
	private String vinProfileImage;
	private String vinCreatedTime;
	
	private int vinChapterId;
	private String vinChapterName,chapterCoverImageURL,chapterProfileImageURL;
	private int isActive;
	private String createdChapterTime;
	
	public UserDataBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDataBean(long fbId, String fbName, String fbFirstName,String fbLastName, String fbGender) {
		super();
		this.fbId = fbId;
		this.fbName = fbName;
		this.fbFirstName = fbFirstName;
		this.fbLastName = fbLastName;
		this.fbGender = fbGender;
	}

	public long getFbId() {
		return fbId;
	}

	public void setFbId(long fbId) {
		this.fbId = fbId;
	}

	public String getFbName() {
		return fbName;
	}

	public void setFbName(String fbName) {
		this.fbName = fbName;
	}

	public String getFbFirstName() {
		return fbFirstName;
	}

	public void setFbFirstName(String fbFirstName) {
		this.fbFirstName = fbFirstName;
	}

	public String getFbLastName() {
		return fbLastName;
	}

	public void setFbLastName(String fbLastName) {
		this.fbLastName = fbLastName;
	}

	public String getFbGender() {
		return fbGender;
	}

	public void setFbGender(String fbGender) {
		this.fbGender = fbGender;
	}

	public String getVinLoginUserid() {
		return vinLoginUserid;
	}

	public void setVinLoginUserid(String vinLoginUserid) {
		this.vinLoginUserid = vinLoginUserid;
	}

	public String getVinNumber() {
		return vinNumber;
	}

	public void setVinNumber(String vinNumber) {
		this.vinNumber = vinNumber;
	}

	public String getVinName() {
		return vinName;
	}

	public void setVinName(String vinName) {
		this.vinName = vinName;
	}

	public String getVinModelName() {
		return vinModelName;
	}

	public void setVinModelName(String vinModelName) {
		this.vinModelName = vinModelName;
	}

	public String getVinAvatarName() {
		return vinAvatarName;
	}

	public void setVinAvatarName(String vinAvatarName) {
		this.vinAvatarName = vinAvatarName;
	}

	
	public String getVinProfileImage() {
		return vinProfileImage;
	}

	public void setVinProfileImage(String vinProfileImage) {
		this.vinProfileImage = vinProfileImage;
	}

	public String getVinCreatedTime() {
		return vinCreatedTime;
	}

	public void setVinCreatedTime(String vinCreatedTime) {
		this.vinCreatedTime = vinCreatedTime;
	}

	public int getVinChapterId() {
		return vinChapterId;
	}

	public void setVinChapterId(int vinChapterId) {
		this.vinChapterId = vinChapterId;
	}

	public String getVinChapterName() {
		return vinChapterName;
	}

	public void setVinChapterName(String vinChapterName) {
		this.vinChapterName = vinChapterName;
	}

	public int getVinIsActive() {
		return isActive;
	}

	public void setVinIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getVinCreatedChapterTime() {
		return createdChapterTime;
	}

	public void setVinCreatedChapterTime(String createdChapterTime) {
		this.createdChapterTime = createdChapterTime;
	}

	public String getChapterCoverImageURL() {
		return chapterCoverImageURL;
	}

	public void setChapterCoverImageURL(String chapterCoverImageURL) {
		this.chapterCoverImageURL = chapterCoverImageURL;
	}

	public String getChapterProfileImageURL() {
		return chapterProfileImageURL;
	}

	public void setChapterProfileImageURL(String chapterProfileImageURL) {
		this.chapterProfileImageURL = chapterProfileImageURL;
	}
	
	
}
