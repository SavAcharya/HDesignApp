package com.hdesignapp.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.util.LruCache;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hdesign.BaseActivity;
import com.hdesign.HDesignApp;
import com.hdesign.bean.UserDataBean;
import com.hdesign.net.URLNameValuePairBuilder;
import com.hdesign.parser.ChapterParser;
import com.hdesign.parser.UserDataParser;
import com.hdesign.ui.FooterBar;
import com.hdesignapp.R;
import com.hdesignapp.extras.ImageCropOption;
import com.hdesignapp.extras.ImageCropOptionAdapter;
import com.hdesignapp.utils.AlertUtility;
import com.hdesignapp.utils.AppConstants;
import com.hdesignapp.utils.CommonUtility;
import com.hdesignapp.utils.StringUtility;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class EditProfileActivity extends BaseActivity implements OnClickListener {

	private Activity context;
	private Button confirmBtn,selectChapterBtn;
	private EditText usernameText,modelNameText,modelNumberText,avatarNameText;
	private ImageView imgUserPic;
	private FooterBar footerBar;
	private String chapterStr,avatarStr,usernameStr,modelNameStr;
	
	private DisplayImageOptions options;
	
	private static final int PICK_FROM_FILE = 1;
	private static final int CROP_FROM_CAMERA = 2;
	
	private Uri mImageCaptureUri;
	private Bitmap bitmapImage = null ;
	private String FB_LOGIN, TWIT_LOGIN, VIN_LOGIN, GOOGLE_LOGIN;
	
	private LruCache<String, Bitmap> mMemoryCache;
	private String strImageEncodeToBase64 = "";
	
	private static int PROCESS_UPDATE_USER = 100;
	private static int PROCESS_GET_CHAPTERS = 101;
	
	private ArrayList<UserDataBean> chapterList = new ArrayList<UserDataBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		context = (EditProfileActivity) this;
		CommonUtility.fullScreenActivity(context);
		setContentView(R.layout.activity_edit_profile);
		
		setupViews();
		setupData();
		
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.cacheInMemory().cacheOnDisc().build();
	}
	
	private void setupViews()
	{
		imgUserPic = (ImageView) findViewById(R.id.add_hoto_btn);
		usernameText = (EditText) findViewById(R.id.username_text_view);
		modelNameText = (EditText) findViewById(R.id.model_name_text_view);
		modelNumberText = (EditText) findViewById(R.id.model_number_text_view);
		selectChapterBtn = (Button) findViewById(R.id.select_chapter_btn);
		avatarNameText = (EditText) findViewById(R.id.avatar_name_text_view);
		confirmBtn = (Button) findViewById(R.id.update_user_btn);

		footerBar = (FooterBar) findViewById(R.id.layoutFooterBar);	
		footerBar.setProfileSelected();
		if(HDesignApp.getInstanceAppPreferences().isLogin())
		{
			footerBar.setVisibility(View.VISIBLE);
		}
		else
		{
			footerBar.setVisibility(View.GONE);
		}
		
		imgUserPic.setOnClickListener(this);
		selectChapterBtn.setOnClickListener(this);
		confirmBtn.setOnClickListener(this);
		
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		// Use 1/8th of the available memory for this memory cache.
		final int cacheSize = maxMemory / 8;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize);
	}
	
	private void setupData()
	{
		
	
		if(HDesignApp.getInstanceFbPreferences().getFacebookLogin())
		{
			imgUserPic.setImageBitmap(CommonUtility.getRoundedShape(AppConstants.fbAvatarBitmap));
		}
		else
		{
			imgUserPic.setImageBitmap(CommonUtility.getRoundedShape(CommonUtility.StringToBitMap(HDesignApp.getInstanceAppPreferences().getImageUrl())));

		}
		
		if(getIntent().hasExtra("google_login")){
			GOOGLE_LOGIN = getIntent().getExtras().getString("google_login");
			if(GOOGLE_LOGIN.equalsIgnoreCase("GoogleImage"))
			{
				Intent intent = getIntent();
				HDesignApp.getInstanceAppPreferences().setEmail(intent.getStringExtra("email_id"));
				
			}	
		}
		
		AlertUtility.printStatement("EditProfileActivity", "ImageUrl - "+(HDesignApp.getInstanceAppPreferences().getImageUrl()));
		usernameText.setText(HDesignApp.getInstanceAppPreferences().getName());
		modelNameText.setText(HDesignApp.getInstanceAppPreferences().getModelName());
		modelNumberText.setText(HDesignApp.getInstanceAppPreferences().getModelNumber());
		avatarNameText.setText(HDesignApp.getInstanceAppPreferences().getAvatarName());
		selectChapterBtn.setText(HDesignApp.getInstanceAppPreferences().getChapterName());
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		  case R.id.add_hoto_btn:
			  openPictureIntent();
			  break;
		
		  case R.id.select_chapter_btn:
			  // showChapterAlert();
			  new AsyncTaskUpdateUserProfile(PROCESS_GET_CHAPTERS).execute(); 
			  break;
			  
		  case R.id.update_user_btn:
			  handleUpdateBtnClick();
		      break;
		      
		  default:
			  break;
		}
	}
	
	private void openPictureIntent()
	{
		Intent pickIntent = new Intent();
		pickIntent.setType("image/*");
		pickIntent.setAction(Intent.ACTION_GET_CONTENT);

		Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		String pickTitle = "Complete action using"; // Or get from strings.xml
		Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
		chooserIntent.putExtra
		(
		  Intent.EXTRA_INITIAL_INTENTS, 
		  new Intent[] { takePhotoIntent }
		);

		startActivityForResult(chooserIntent, PICK_FROM_FILE);
	}
	
	private boolean isValidInput()
	{
		boolean isValid = true;
		
		usernameStr = usernameText.getText().toString();
		modelNameStr = modelNameText.getText().toString();
		chapterStr = selectChapterBtn.getText().toString();
		avatarStr = avatarNameText.getText().toString();
		
		
		if(! StringUtility.isNotNullOrEmpty(chapterStr))
		{
			isValid = false;
			AlertUtility.showToast(context, getString(R.string.chapter_field_empty));
		}
		else if( ! StringUtility.isNotNullOrEmpty(avatarStr))
		{
			isValid = false;
			AlertUtility.showToast(context, getString(R.string.avatar_field_empty));
		}
		
		return isValid;
	}
	
	private void handleUpdateBtnClick()
	{
		if(isValidInput())
		{			
			new AsyncTaskUpdateUserProfile(PROCESS_UPDATE_USER,usernameStr,modelNameStr,avatarStr).execute();
		}
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode != RESULT_OK) return;
		   
	    switch (requestCode) {

		    case PICK_FROM_FILE: 
		    	mImageCaptureUri = data.getData();	    	
		    	doCrop();	    
		    	break;	    	
	    
		    case CROP_FROM_CAMERA:	    	
		        Bundle extras = data.getExtras();	
		        if (extras != null) {	        	
		        	bitmapImage = extras.getParcelable("data");
		        	if(bitmapImage!=null){
		        		HDesignApp.getInstanceAppPreferences().setImageUrl(CommonUtility.BitMapToString(bitmapImage));
		        		imgUserPic.setImageBitmap(CommonUtility.getRoundedShape(bitmapImage));				        	
		        	}		                
		        }	
		        File f = new File(mImageCaptureUri.getPath());            
		        if (f.exists()) f.delete();	
		        break;		  		  
	    }
	}
	
    private void doCrop() {
		final ArrayList<ImageCropOption> cropOptions = new ArrayList<ImageCropOption>();  	
    	Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        
        List<ResolveInfo> list = getPackageManager().queryIntentActivities( intent, 0 );       
        int size = list.size();       
        if (size == 0) {	        
        	AlertUtility.showToast(context,getString(R.string.profile_not_crop_image));	       	
            return;
        } else {
        	intent.setData(mImageCaptureUri);           
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            
        	if (size == 1) {
        		Intent i 		= new Intent(intent);
	        	ResolveInfo res	= list.get(0);	        	
	        	i.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));	        	
	        	startActivityForResult(i, CROP_FROM_CAMERA);
        	} else {
		        for (ResolveInfo res : list) {
		        	final ImageCropOption co = new ImageCropOption();	        	
		        	co.title 	= getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
		        	co.icon		= getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
		        	co.appIntent= new Intent(intent);		        	
		        	co.appIntent.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));		        	
		            cropOptions.add(co);
		        }
	        
		        ImageCropOptionAdapter adapter = new ImageCropOptionAdapter(getApplicationContext(), cropOptions);
		        
		        AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setTitle("Choose Crop App");
		        builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
		            public void onClick( DialogInterface dialog, int item ) {
		                startActivityForResult( cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
		            }
		        });
	        
		        builder.setOnCancelListener( new DialogInterface.OnCancelListener() {
		            @Override
		            public void onCancel( DialogInterface dialog ) {
		               
		                if (mImageCaptureUri != null ) {
		                    getContentResolver().delete(mImageCaptureUri, null, null );
		                    mImageCaptureUri = null;
		                }
		            }
		        } );
		        
		        AlertDialog alert = builder.create();		        
		        alert.show();
        	}
        }
	}

    
	/* Use to Create Marker with Image... */

	private class BitmapWorkerTask extends AsyncTask<Void, Void, Bitmap> {

		BitmapFactory.Options op = new BitmapFactory.Options();
		String imageUrl;

		public BitmapWorkerTask(String imageUrl) {
			super();
			this.imageUrl = imageUrl;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}

		@Override
		protected Bitmap doInBackground(Void... params) {

			Bitmap bitmap = null;
			op.inPurgeable = true;
			if (StringUtility.isNotNullOrEmpty(imageUrl)) {
				if (mMemoryCache.get(imageUrl) != null) {
					bitmap = mMemoryCache.get(imageUrl);
				} else {
					URL url = null;
					try {
						url = new URL(imageUrl);
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setDoInput(true);
						conn.connect();
						InputStream is = conn.getInputStream();
						bitmap = BitmapFactory.decodeStream(is, null, op);
						if (bitmap != null)
							mMemoryCache.put(imageUrl, bitmap);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			encodeImageToBase64(result);
			imgUserPic.setImageBitmap(CommonUtility.getRoundedShape(result));

   	    }

	}
	
	private class AsyncTaskUpdateUserProfile extends AsyncTask<Void, Void, String> {

		ProgressDialog progressDialog;
		String usernameStr, modelNameStr, avatarStr;
		int ProcessCode;
	
		public AsyncTaskUpdateUserProfile(int ProcessCode,String usernameStr, String modelNameStr,String avatarStr) {
			super();
			this.ProcessCode = ProcessCode;
			this.usernameStr = usernameStr;
			this.modelNameStr = modelNameStr;
			this.avatarStr = avatarStr;
		}
		
		public AsyncTaskUpdateUserProfile(int ProcessCode)
		{
		   this.ProcessCode = ProcessCode;	
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(context);
			
			if(ProcessCode == PROCESS_UPDATE_USER){
				progressDialog.setMessage("Update User Profile...");
			}
			
			if(ProcessCode == PROCESS_GET_CHAPTERS){
				progressDialog.setMessage("Get Chapters...");
			}
			
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setCancelable(false);
			progressDialog.setCanceledOnTouchOutside(false);			
			progressDialog.setOnCancelListener(new OnCancelListener() {				
				@Override
				public void onCancel(DialogInterface dialog) {
					cancel(true);
				}
			});	
			progressDialog.show();		}

		@Override
		protected String doInBackground(Void... params) {
			
			String response = "";			
			
			if(ProcessCode == PROCESS_UPDATE_USER){
				response = HDesignApp.getInstanceWebService().postData(URLNameValuePairBuilder.functionUpdateInfoParam,
						URLNameValuePairBuilder.setUpdateUserParam("11_AAAA",usernameStr,
								modelNameStr, 
								HDesignApp.getInstanceAppPreferences().getChapterID(),
								avatarStr,encodeImageToBase64(bitmapImage)));	
			}
			
            if(ProcessCode == PROCESS_GET_CHAPTERS){
            	response = HDesignApp.getInstanceWebService().postData(URLNameValuePairBuilder.functionGetChaptersParam,URLNameValuePairBuilder.setGetChaptersParam("11_AAAA"));
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
			
			if(ProcessCode == PROCESS_UPDATE_USER){
				   handleUpdateUserResult(result);
					progressDialog.dismiss();
			}
			
            if(ProcessCode == PROCESS_GET_CHAPTERS){
					handleChapterResult(result);
					progressDialog.dismiss();
			 }
			
		}
		
		private void handleChapterResult(String result)
		{
			
			try {
				chapterList = ChapterParser.getParsedChaptersList(result);
				showChapterAlert(chapterList);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
	    private void showChapterAlert(final ArrayList<UserDataBean> chapterList){
				
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				
				final String chapterNames[] = new String[chapterList.size()];
				for(int i=0;i<chapterList.size();i++)
				{
					chapterNames[i] = chapterList.get(i).getVinChapterName();
				}
				builder.setTitle("Select Chapter");
				builder.setItems(chapterNames, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int pos) {
				    	selectChapterBtn.setText(chapterNames[pos]);
				    	HDesignApp.getInstanceAppPreferences().setChapterID(chapterList.get(pos).getVinChapterId());
				    }
				});
				AlertDialog alert = builder.create();
				alert.show();
		}
		
		private void handleUpdateUserResult(String result) {
			JSONObject mainJsonObject = null;
			try {
				mainJsonObject = new JSONObject(result);
				String success = mainJsonObject.getJSONObject("status").getString("type");
				
				if (success.equalsIgnoreCase("Success")) {	
					UserDataBean userDataBean = UserDataParser.getParsedVinLoginProfileResult(mainJsonObject);
					setVinPrefValue(userDataBean);
					AlertUtility.showToast(context, getString(R.string.update_profile_successfully));
					
					if(HDesignApp.getInstanceAppPreferences().isLogin()) 
					{
						Intent intent = new Intent(context,HomeActivity.class);
						startActivity(intent);
					}
					else 
					{
						Intent intent = new Intent(context,GetStartedActivity.class);
						startActivity(intent);
					}
					
				} 
				else {
					String error = mainJsonObject.getString("error").trim();
					AlertUtility.showToast(context, error);
				}
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		private void setVinPrefValue(UserDataBean userDataBean)
		{
			HDesignApp.getInstanceAppPreferences().setVinID(userDataBean.getVinLoginUserid());
			HDesignApp.getInstanceAppPreferences().setName(userDataBean.getVinName());
			HDesignApp.getInstanceAppPreferences().setVinNumber(userDataBean.getVinNumber());
		//	HDesignApp.getInstanceAppPreferences().setImageUrl(userDataBean.getVinProfileImage());
			HDesignApp.getInstanceAppPreferences().setModelName(userDataBean.getVinModelName());
			HDesignApp.getInstanceAppPreferences().setModelNumber(userDataBean.getVinNumber());
			HDesignApp.getInstanceAppPreferences().setChapterActive(userDataBean.getVinIsActive());
			HDesignApp.getInstanceAppPreferences().setChapterName(userDataBean.getVinChapterName());
			HDesignApp.getInstanceAppPreferences().setChapterID(userDataBean.getVinChapterId());
			HDesignApp.getInstanceAppPreferences().setChapterCreatedTime(userDataBean.getVinCreatedChapterTime());
			HDesignApp.getInstanceAppPreferences().setAvatarName(userDataBean.getVinAvatarName());
		}
		
	}
	
	private String encodeImageToBase64(Bitmap bitmapImage) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		if (bitmapImage != null) {
			bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		} else {
			Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.hog_profile_img);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		}
		byte[] byteArray = stream.toByteArray();
		strImageEncodeToBase64 = Base64.encodeToString(byteArray,Base64.DEFAULT);
     //   AlertUtility.printStatement("ProfileActivity","base64 code of -"+strImageEncodeToBase64);
		return strImageEncodeToBase64;
	}
	
	@Override
	public void onBackPressed() {	    
	    return;
	}
	
	private synchronized void downloadAvatar() {
			AsyncTask<Void, Void, Bitmap> task = new AsyncTask<Void, Void, Bitmap>() {
			ProgressDialog progressDialog;
			
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				progressDialog = new ProgressDialog(context);
				progressDialog.setMessage("Sync With Facebook...");
				progressDialog.show();
				progressDialog.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						cancel(true);
					}
				});
			}

			
			@Override
			public Bitmap doInBackground(Void... params) {
				URL fbAvatarUrl = null;
				Bitmap fbAvatarBitmap = null;
				try {
					AlertUtility.printStatement("+++++++++++ FacebookLoginActivity", "Facebook Id - "+HDesignApp.getInstanceFbPreferences().getFacebookID());
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
				finish();
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				progressDialog.dismiss();
				HDesignApp.getInstanceAppPreferences().setImageUrl(encodeImageToBase64(result));
				imgUserPic.setImageBitmap(CommonUtility.getRoundedShape(result));
			}
		};
		task.execute();
	}
}
