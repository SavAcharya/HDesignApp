package com.hdesignapp.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdesign.BaseActivity;
import com.hdesign.HDesignApp;
import com.hdesign.bean.AchievementBean;
import com.hdesign.net.URLNameValuePairBuilder;
import com.hdesign.parser.AchievementParser;
import com.hdesign.ui.AddAchievementDialog;
import com.hdesign.ui.FooterBar;
import com.hdesignapp.R;
import com.hdesignapp.utils.AlertUtility;
import com.hdesignapp.utils.AppConstants;
import com.hdesignapp.utils.CommonUtility;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class MyProfileActivity extends BaseActivity implements OnClickListener{

	private Activity context;
	private TextView nameText,avatarNameText,bikeOwnedNameText,vinNumberText,chapterNameText;
	private Button addAchievementBtn,editProfileBtn;
	private ImageButton achievementThumbnailImageBtn;
	private ImageView profileImageView;
	private DisplayImageOptions options;
	private FooterBar footerBar;
	
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		context = (MyProfileActivity) this;
		setContentView(R.layout.activity_my_profile);
		
		setupViews();
		setupData();
			
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.cacheInMemory().cacheOnDisc().build();
	}
	
	private void setupViews()
	{
		nameText = (TextView) findViewById(R.id.vinNameTextView);
		avatarNameText = (TextView) findViewById(R.id.avatarNameTextView);
		bikeOwnedNameText = (TextView) findViewById(R.id.vinModelNameTextView);
		vinNumberText = (TextView) findViewById(R.id.vinNumberTextView);
		chapterNameText = (TextView) findViewById(R.id.chapterNameTextView);
		
		editProfileBtn = (Button) findViewById(R.id.edit_my_profile_btn);
		addAchievementBtn = (Button) findViewById(R.id.addAchievementBtn);
		achievementThumbnailImageBtn = (ImageButton) findViewById(R.id.achievementThumbnailImageBtn);
		profileImageView = (ImageView) findViewById(R.id.profileImageView);
		
		footerBar = (FooterBar) findViewById(R.id.layoutFooterBar);	
		footerBar.setProfileSelected();
		
		editProfileBtn.setOnClickListener(this);
		addAchievementBtn.setOnClickListener(this);
		achievementThumbnailImageBtn.setOnClickListener(this);
	}
	
	private void setupData()
	{
		nameText.setText(HDesignApp.getInstanceAppPreferences().getName().toUpperCase());
		avatarNameText.setText(HDesignApp.getInstanceAppPreferences().getAvatarName().toUpperCase());
		bikeOwnedNameText.setText(HDesignApp.getInstanceAppPreferences().getModelName().toUpperCase());
		vinNumberText.setText(HDesignApp.getInstanceAppPreferences().getVinNumber().toUpperCase());
		chapterNameText.setText(HDesignApp.getInstanceAppPreferences().getChapterName().toUpperCase());
		
		
		if(HDesignApp.getInstanceFbPreferences().getFacebookLogin())
		{
			profileImageView.setImageBitmap(CommonUtility.getRoundedShape(AppConstants.fbAvatarBitmap));
		}
		else
		{
			profileImageView.setImageBitmap(CommonUtility.getRoundedShape(CommonUtility.StringToBitMap(HDesignApp.getInstanceAppPreferences().getImageUrl())));
			//downloadAvatar();
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			
		case R.id.edit_my_profile_btn:
			Intent intent = new Intent(context,EditProfileActivity.class);
			startActivity(intent);
			break;

        case R.id.addAchievementBtn:
        	handleAddAchieveAction();
			break;
			
        case R.id.achievementThumbnailImageBtn:       	
        	new ViewAchieveAsyncTask().execute();
	        break;
		default:
			break;
		}
	}
	
	private void handleAddAchieveAction()
	{
		
		Intent intent = new Intent(context,AddAchievementActivity.class);
		startActivity(intent);
		finish();
	}
	
	
	
	   private class ViewAchieveAsyncTask extends AsyncTask<Void, Void, String>
		{

			ProgressDialog progressDialog;
			
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				progressDialog = new ProgressDialog(context);
				progressDialog.setMessage("View Achievement...");
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setCancelable(false);
				progressDialog.setCanceledOnTouchOutside(false);
				
				progressDialog.setOnCancelListener(new OnCancelListener() {				
					@Override
					public void onCancel(DialogInterface dialog) {
						cancel(true);
					}
				});	
				progressDialog.show();	
			}

			@Override
			protected String doInBackground(Void... arg0) {
				String response = "";
				response = HDesignApp.getInstanceWebService().postData(URLNameValuePairBuilder.functionViewAchievementParam, 
						URLNameValuePairBuilder.setViewAchievementParam("11_AAAA"));
				return response;
			}
			
			@Override
			protected void onCancelled() {
				super.onCancelled();
				progressDialog.dismiss();
			}
			
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				
				if(result.equals(HDesignApp.getInstanceWebService().SERVER_BUSY)){
					progressDialog.dismiss();				
					AlertUtility.showToast(context,context.getResources().getString(R.string.server_busy));				
					return;
				}
				else if(result.equals(HDesignApp.getInstanceWebService().NO_INTERNET)){
					progressDialog.dismiss();
					AlertUtility.showToast(context,context.getResources().getString(R.string.no_internet));		
					return;
				}
			
				
				getViewAchievementResult(result);
				AlertUtility.printStatement("AchievementDialog", "View Achieve API Result - "+result);
				progressDialog.dismiss();
			}
			
			
		}
	   private void getViewAchievementResult(String result)
	    {
	    	ArrayList<AchievementBean> viewAchieveList = new ArrayList<AchievementBean>();
	    	try {
				viewAchieveList = AchievementParser.getParsedViewAchievementList(result);
				if(viewAchieveList.size() > 0)
				{
					AddAchievementDialog achieveDialog = new AddAchievementDialog(context, imageLoader, viewAchieveList);
					achieveDialog.show();
				}
				else
				{
					AlertUtility.showToast(context, "No AchieveList");
				}
				 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }
	   
	   private synchronized void downloadAvatar() 
	   {
			AsyncTask<Void, Void, Bitmap> task = new AsyncTask<Void, Void, Bitmap>() {
			
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				
			}

			
			@Override
			public Bitmap doInBackground(Void... params) {
				URL fbAvatarUrl = null;
				Bitmap fbAvatarBitmap = null;
				try {
					fbAvatarUrl = new URL(HDesignApp.getInstanceAppPreferences().getImageUrl());
					fbAvatarBitmap = BitmapFactory.decodeStream(fbAvatarUrl.openConnection().getInputStream());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return fbAvatarBitmap;
			}
			
			@Override
			protected void onCancelled() {
				super.onCancelled();
				
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				profileImageView.setImageBitmap(CommonUtility.getRoundedShape(result));
			}
		};
		task.execute();
	}
	   
	@Override
	public void onBackPressed() {	    
		    return;
	}
}
