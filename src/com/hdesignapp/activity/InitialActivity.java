package com.hdesignapp.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.hdesign.BaseActivity;
import com.hdesign.HDesignApp;
import com.hdesign.bean.UserDataBean;
import com.hdesign.net.URLNameValuePairBuilder;
import com.hdesign.parser.UserDataParser;
import com.hdesign.ui.CustomDialog;
import com.hdesignapp.R;
import com.hdesignapp.extras.Installation;
import com.hdesignapp.utils.AlertUtility;
import com.hdesignapp.utils.AppConstants;
import com.hdesignapp.utils.CommonUtility;
import com.hdesignapp.utils.StringUtility;

public class InitialActivity extends BaseActivity implements OnClickListener{
	
	private static String TAG = "InitialActivity";
	private EditText vidNumberText;
	private Button forgotVINBtn,checkValidVinBtn;
	
	private Activity context;
	private String vinNumberStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		context = (InitialActivity) this;
		CommonUtility.fullScreenActivity(context);
		setContentView(R.layout.activity_initial);
		
		setupViews();
		AlertUtility.printStatement(TAG,"IMEI Number - "+Installation.getIMEINumber(context));
	}

	private void setupViews()
	{
		vidNumberText = (EditText) findViewById(R.id.enterVIDNumberTextView);
		forgotVINBtn = (Button) findViewById(R.id.forgotVINBtn);
		checkValidVinBtn = (Button) findViewById(R.id.checkVINValidationBtn);
		
		forgotVINBtn.setOnClickListener(this);
		checkValidVinBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.forgotVINBtn:
			openForgotVINAlert(context,AppConstants.OPEN_FORGET_DIALOG);
			break;
		case R.id.checkVINValidationBtn:
			checkValidityOfVIN();
			break;

		default:
			break;
		}
	}
	
	private void openForgotVINAlert(Activity context,int FLAG)
	{
		CustomDialog forgetDialog = new CustomDialog(context,FLAG);
		forgetDialog.show();  
	}
	
	private void checkValidityOfVIN()
	{
		if(StringUtility.isNotNullOrEmpty(vidNumberText.getText().toString()))
		{
			vinNumberStr = vidNumberText.getText().toString();
			
			if(vinNumberStr.equalsIgnoreCase("98756"))
			{
				new AsyncTaskVinLogin(vinNumberStr).execute();			
			}
			else
			{
				openForgotVINAlert(context,AppConstants.WRONG_VIN_DIALOG);
			}
			
		}
		else
		{
			AlertUtility.showToast(context, getString(R.string.enter_vin_number));
		}
	}
	
	private class AsyncTaskVinLogin extends AsyncTask<Void, Void, String> {

		String vin_number;
		ProgressDialog progressDialog;
	
		public AsyncTaskVinLogin(String vin_number) {
			super();
			this.vin_number = vin_number;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("Sync VIN Number...");
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
			
			response = HDesignApp.getInstanceWebService().postData(URLNameValuePairBuilder.functionVinLogin,
						URLNameValuePairBuilder.setVinLoginParam("98756_AAAA"));				
			
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
				AlertUtility.showToast(context,getString(R.string.server_busy));				
				return;
			}
			else if(result.equals(HDesignApp.getInstanceWebService().NO_INTERNET)){
				progressDialog.dismiss();
				AlertUtility.showToast(context,getString(R.string.no_internet));		
				return;
			}
			
			AlertUtility.printStatement("Initial Activity", "API Result - "+result);
			    handleVinLoginResult(result);
			    HDesignApp.getInstanceAppPreferences().setFirst(false);
				progressDialog.dismiss();
			
				Intent intent = new Intent(context,SocialSyncActivity.class);
				intent.putExtra("vin_login", "VinImage");
				startActivity(intent);
			
		}
		
		private boolean handleVinLoginResult(String result) {
			Boolean isSuccess = true;
			JSONObject mainJsonObject = null;
			try {
				mainJsonObject = new JSONObject(result);
				JSONObject jsonObject = mainJsonObject.getJSONObject("Status");
				String success = jsonObject.getString("type");
				if (success.equalsIgnoreCase("Success")) {	
					UserDataBean userDataBean = UserDataParser.getParsedVinLoginProfileResult(mainJsonObject);
					setVinPrefValue(userDataBean);
				} 
				else {
					String error = mainJsonObject.getString("error").trim();
					AlertUtility.showToast(context, error);
					isSuccess = false;
				}
			}
			catch (JSONException e) {
				e.printStackTrace();
				isSuccess = false;
			}
			return isSuccess;
		}
		
		private void setVinPrefValue(UserDataBean userDataBean)
		{
			HDesignApp.getInstanceAppPreferences().setVinID(userDataBean.getVinLoginUserid());
			HDesignApp.getInstanceAppPreferences().setName(userDataBean.getVinName());
			HDesignApp.getInstanceAppPreferences().setVinNumber(userDataBean.getVinNumber());
			HDesignApp.getInstanceAppPreferences().setImageUrl(userDataBean.getVinProfileImage());
			HDesignApp.getInstanceAppPreferences().setModelName(userDataBean.getVinModelName());
			HDesignApp.getInstanceAppPreferences().setModelNumber(userDataBean.getVinNumber());
			HDesignApp.getInstanceAppPreferences().setChapterActive(userDataBean.getVinIsActive());
			HDesignApp.getInstanceAppPreferences().setChapterName(userDataBean.getVinChapterName());
			HDesignApp.getInstanceAppPreferences().setChapterID(userDataBean.getVinChapterId());
			HDesignApp.getInstanceAppPreferences().setChapterCreatedTime(userDataBean.getVinCreatedChapterTime());
			HDesignApp.getInstanceAppPreferences().setAvatarName(userDataBean.getVinAvatarName());
			
			Intent intent = new Intent(context,SocialSyncActivity.class);
			startActivity(intent);
		}
		
	}
	
	@Override
	public void onBackPressed() {	    
	    return;
	}
}
