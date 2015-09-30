package com.hdesignapp.activity;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.hdesign.BaseActivity;
import com.hdesign.HDesignApp;
import com.hdesign.adapter.AddAchieveListAdapter;
import com.hdesign.bean.AchievementBean;
import com.hdesign.net.URLNameValuePairBuilder;
import com.hdesign.parser.AchievementParser;
import com.hdesign.ui.FooterBar;
import com.hdesignapp.R;
import com.hdesignapp.utils.AlertUtility;

public class AddAchievementActivity extends BaseActivity{

	private Activity context;
	private ListView achieveListView;
	private ImageButton backBtn;
	private FooterBar footerBar;
	private ArrayList<AchievementBean> achieveList = new ArrayList<AchievementBean>();
	private AddAchieveListAdapter adapter;
	
	private static int PROCESS_LIST_ALL_ACHIEVEMENTS = 100;
	
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		context = (AddAchievementActivity) this;
		setContentView(R.layout.activity_add_achieve);
		
		backBtn = (ImageButton) findViewById(R.id.backAchieveImageView);
		achieveListView = (ListView) findViewById(R.id.achieveFragmentListView);
		
		footerBar = (FooterBar) findViewById(R.id.layoutFooterBar);	
		footerBar.setProfileSelected();
        
        new AllAchieveAsynTask(PROCESS_LIST_ALL_ACHIEVEMENTS).execute();
		
        backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				handleBackAchieveAction();
			}
		});
	}
	
	private void handleBackAchieveAction()
	{
		Intent intent = new Intent(context,MyProfileActivity.class);
		startActivity(intent);
		finish();
	}
	
	private class AllAchieveAsynTask extends AsyncTask<Void, Void, String>
	{

		int ProcessCode;
		ProgressDialog progressDialog;
		
		public AllAchieveAsynTask(int ProcessCode) {
			super();
			this.ProcessCode = ProcessCode;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("Get Achievement...");
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
		protected String doInBackground(Void... params) {
			String response = "";
			if(ProcessCode == PROCESS_LIST_ALL_ACHIEVEMENTS)
			{
				response = HDesignApp.getInstanceWebService().postData(URLNameValuePairBuilder.functionListAllAchievementParam,
						URLNameValuePairBuilder.setListAllAchievementParam("11_AAAA"));
			}
			
			return response;
		}
		
		@Override
		protected void onCancelled() {
			super.onCancelled();
			progressDialog.dismiss();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(result.equals(HDesignApp.getInstanceWebService().SERVER_BUSY)){
				progressDialog.dismiss();				
				AlertUtility.showToast(context,getString(R.string.server_busy));				
				return;
			}
			else if(result.equals(HDesignApp.getInstanceWebService().NO_INTERNET)){
				progressDialog.dismiss();
				AlertUtility.showToast(context,getString(R.string.no_internet));		
				return;
			}
			
			if(ProcessCode == PROCESS_LIST_ALL_ACHIEVEMENTS)
			{
				listAllAchievementResult(result);
				progressDialog.dismiss();
				
			}
			
		}
		
		private void listAllAchievementResult(String result)
		{
			
			try {
				achieveList = AchievementParser.getParsedAchievementList(result);
				AlertUtility.printStatement("AddAchievementFragment", "ListAchieve Result - "+result);
				adapter = new AddAchieveListAdapter(context, achieveList,imageLoader);
				achieveListView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void onBackPressed() {	    
	    return;
	}
}
