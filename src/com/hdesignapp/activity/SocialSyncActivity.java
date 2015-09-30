package com.hdesignapp.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AppEventsLogger;
import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.hdesign.BaseActivity;
import com.hdesign.HDesignApp;
import com.hdesign.bean.UserDataBean;
import com.hdesign.google.AbstractGetNameTask;
import com.hdesign.google.GetNameInForeground;
import com.hdesign.net.URLNameValuePairBuilder;
import com.hdesign.parser.UserDataParser;
import com.hdesignapp.R;
import com.hdesignapp.utils.AlertUtility;
import com.hdesignapp.utils.AppConstants;
import com.hdesignapp.utils.CommonUtility;
import com.hdesignapp.utils.TwitterUtils;

public class SocialSyncActivity extends BaseActivity implements OnClickListener {
    
	private final static int PROCESS_CODE_SYNC_FACEBOOK = 100;
	private final static int PROCESS_CODE_SOCIAL_SYNC = 101;
	
	private Button btnLoginTwitter, btnLoginFacebook, btnLoginGoogle, btnEditProfile;
	private TextView usernameText;
 
	private static String TAG = "SocialSyncActivity";
    private static Twitter twitter;
    private static RequestToken requestToken;
   
    private Activity context;
    
    private AccountManager mAccountManager;
	
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	private Bundle savedInstance;
	private boolean isFacebookLogin = false;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstance = savedInstanceState;
        context = (SocialSyncActivity) this;
        CommonUtility.fullScreenActivity(context);
	    
        setContentView(R.layout.activity_social_sync);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        getFbHashKey();
        checkTwitterSession();  
        setupViews();       
        checkFbSession();
		processTwitterToken();
   }
    
    private void setupViews()
    {
        btnEditProfile = (Button) findViewById(R.id.edit_profile_btn);
        btnLoginTwitter = (Button) findViewById(R.id.twitter_btn);
        btnLoginFacebook = (Button) findViewById(R.id.facebook_btn);
        btnLoginGoogle = (Button) findViewById(R.id.google_btn);
        usernameText = (TextView) findViewById(R.id.usernameTextView);
        
        usernameText.setText("HI "+HDesignApp.getInstanceAppPreferences().getName().toUpperCase());
        
		btnEditProfile.setOnClickListener(this);		
        btnLoginTwitter.setOnClickListener(this);       
        btnLoginFacebook.setOnClickListener(this);        
        btnLoginGoogle.setOnClickListener(this);
        
    }

    
    @Override
	public void onClick(View v) {
		switch (v.getId()) {
		
        case R.id.edit_profile_btn:
        	Intent intent = new Intent(context,EditProfileActivity.class);
			context.startActivity(intent);
			break;

		case R.id.facebook_btn:
			isFacebookLogin = true;
			loginToFacebook();
		//	new AsyncFacebookTask(PROCESS_CODE_SOCIAL_SYNC, "Facebook",HDesignApp.getInstanceFbPreferences().getFacebookID(),"fb_login","FbImage").execute();
			break;

        case R.id.twitter_btn:
        	 loginToTwitter();
        //	 new AsyncFacebookTask(PROCESS_CODE_SOCIAL_SYNC, "Twitter",HDesignApp.getInstanceTwitterPreferences().getTwitterID(),"twitter_login","TwitterImage").execute();
			break;

        case R.id.google_btn:
        	syncGoogleAccount();
        //	new AsyncFacebookTask(PROCESS_CODE_SOCIAL_SYNC, "Google",HDesignApp.getInstanceGooglePreferences(),"google_login","GoogleImage");
	        break;

		default:
			break;
		}
	}
  
    /** Twitter Integration Start..... */
    private void checkTwitterSession()
    {
    	 if (!CommonUtility.isOnline(context)) {
             AlertUtility.showAlertDialog(context, "Internet Connection Error","Please connect to working Internet connection", false);
             return;
         }
          
         // Check if twitter keys are set
         if(TwitterUtils.TWITTER_CONSUMER_KEY.trim().length() == 0 || TwitterUtils.TWITTER_CONSUMER_SECRET.trim().length() == 0){
             AlertUtility.showAlertDialog(context, "Twitter oAuth tokens", "Please set your twitter oauth tokens first!", false);
             return;
         }
    }
    
    private void processTwitterToken()
    {
    	if (!isTwitterLoggedInAlready()) {
            Uri uri = getIntent().getData();
            if (uri != null && uri.toString().startsWith(TwitterUtils.TWITTER_CALLBACK_URL)) {
                // oAuth verifier
                String verifier = uri.getQueryParameter(TwitterUtils.URL_TWITTER_OAUTH_VERIFIER);
 
                try {
                    // Get the access token
                    AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
 
                    Log.e("Twitter OAuth Token", "> " + accessToken.getToken());
                                          
                    HDesignApp.getInstanceTwitterPreferences().setTwitterOAuthToken(accessToken.getToken());
                    HDesignApp.getInstanceTwitterPreferences().setTwitterOAuthSecretToken(accessToken.getTokenSecret());
                    HDesignApp.getInstanceTwitterPreferences().setTwitterLogin(true);
 
                    Log.e("Twitter OAuth Token", "> " + accessToken.getToken());
 
                    long userID = accessToken.getUserId();
                    HDesignApp.getInstanceTwitterPreferences().setTwitterID(accessToken.getUserId());
                    User user = twitter.showUser(userID);
                    HDesignApp.getInstanceTwitterPreferences().setTwitterName(user.getName());
                    HDesignApp.getInstanceTwitterPreferences().setTwitterImageUrl(user.getProfileImageURL());
                    HDesignApp.getInstanceAppPreferences().setImageUrl(user.getProfileImageURL()); 
                    
                     Intent intent = new Intent(context,EditProfileActivity.class);
                     intent.putExtra("twitter_login", "TwitterImage");
                     startActivity(intent);
                    
                } catch (Exception e) {
                    // Check log for login errors
                    Log.e("Twitter Login Error", "> " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Function to login twitter
     * */
    private void loginToTwitter() {
        // Check if already logged in
        if (!isTwitterLoggedInAlready()) {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(TwitterUtils.TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(TwitterUtils.TWITTER_CONSUMER_SECRET);
            Configuration configuration = builder.build();
             
            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();
 
            try {
                requestToken = twitter.getOAuthRequestToken(TwitterUtils.TWITTER_CALLBACK_URL);
                this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL())));
                
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        } else {
        	AlertUtility.showToast(context, getString(R.string.already_twitter_login));
        }
    }
 
    /**
     * Check user already logged in your application using twitter Login flag is
     * fetched from Shared Preferences
     * */
    private boolean isTwitterLoggedInAlready() {
        return HDesignApp.getInstanceTwitterPreferences().getTwitterLogin();
    }	
    
    
    /** Google Plus Implementation....**/
    
    private String[] getAccountNames() {
		mAccountManager = AccountManager.get(this);
		Account[] accounts = mAccountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
		String[] names = new String[accounts.length];
		for (int i = 0; i < names.length; i++) {
			names[i] = accounts[i].name;
		}
		return names;
	}

	private AbstractGetNameTask getTask(SocialSyncActivity activity, String email,String scope) {
		return new GetNameInForeground(activity, email, scope);

	}

	public void syncGoogleAccount() {
		if (CommonUtility.isOnline(context)) {
			String[] accountarrs = getAccountNames();
			if (accountarrs.length > 0) {
				//you can set here account for login
				getTask(SocialSyncActivity.this, accountarrs[0], AppConstants.GOOGLE_ME).execute();
				handleGoogleResult();
			} else {
				AlertUtility.showToast(context, "No Google Account Sync!");
			}
		} else {
			AlertUtility.showToast(context, "No Network Service!");
		}
	}
	
 
    private void handleGoogleResult()
    {

		/**
		 * get user data from google account
		 */

		try {
			System.out.println("On Home Page***"+ AbstractGetNameTask.GOOGLE_USER_DATA);
			JSONObject profileData = new JSONObject(AbstractGetNameTask.GOOGLE_USER_DATA);

			if (profileData.has("picture")) {
				HDesignApp.getInstanceGooglePreferences().setGoogleProfileImage(profileData.getString("picture"));
				HDesignApp.getInstanceAppPreferences().setImageUrl(profileData.getString("picture"));
			}
			if (profileData.has("name")) {				
				HDesignApp.getInstanceGooglePreferences().setGoogleName(profileData.getString("name"));				
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
  
    /** Facebook integration Start....**/

    private void checkFbSession()
    {
    	Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

        getFbHashKey();
		Session session = Session.getActiveSession();
		if (session == null) {
			if (savedInstance != null) {
				session = Session.restoreSession(this, null, statusCallback,savedInstance);
			}
			if (session == null) {
				session = new Session(this);
			}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
			}
		}
    }
    
    
    @Override
	public void onResume() {
	    super.onResume();    
	    AppEventsLogger.activateApp(SocialSyncActivity.this);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Session.getActiveSession().addCallback(statusCallback);
	}

	@Override
	public void onStop() {
		super.onStop();
		Session.getActiveSession().removeCallback(statusCallback);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,resultCode, data);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}

	private void loginToFacebook() {
		Session session = Session.getActiveSession();
		if (session.isOpened()) {
			HDesignApp.getInstanceFbPreferences().setFacebookAccessToken(session.getAccessToken());
			AlertUtility.printStatement("Facebook LoginActivity","Access token"+session.getAccessToken());
			if ((session != null) && (session.isOpened())) {
				new AsyncFacebookTask(PROCESS_CODE_SYNC_FACEBOOK,AppConstants.URL_PREFIX_ME + session.getAccessToken()).execute();
				isFacebookLogin = false;
			}
			

		} else {
			openFbLoginDialog();
		}
	}

	private void openFbLoginDialog() {
		Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
		} else {
			Session.openActiveSession(this, true, statusCallback);
		}
	}

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,Exception exception) {
			if(session != null && session.isOpened() && isFacebookLogin)
			{
				loginToFacebook();
			}
			
		}
	}

	private class AsyncFacebookTask extends AsyncTask<Void, Void, String> {
		ProgressDialog progressDialog;
		String functionUrl,socialType;
		int ProcessCode;
		long socialId;
		String key,value;
		
		
		public AsyncFacebookTask(int ProcessCode, String functionUrl) {
			super();
			this.ProcessCode = ProcessCode;
			this.functionUrl = functionUrl;
		}
		
		public AsyncFacebookTask(int ProcessCode, String socialType,long socialId,String key,String value) {
			super();
			this.ProcessCode = ProcessCode;
			this.socialType = socialType;
			this.socialId = socialId;
			this.key = key;
			this.value = value;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(context);
			
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					cancel(true);
				}
			});
			
			if(ProcessCode == PROCESS_CODE_SYNC_FACEBOOK){
				progressDialog.setMessage("Sync Facebook...");
				progressDialog.show();
			}
			else if(ProcessCode == PROCESS_CODE_SOCIAL_SYNC){
				progressDialog.setMessage("Social Sync...");
				progressDialog.show();
			}
		}

		@Override
		protected String doInBackground(Void... params) {
			String response = null;	
			
			if(ProcessCode == PROCESS_CODE_SYNC_FACEBOOK){
				response = HDesignApp.getInstanceWebService().postData(functionUrl);
			}
			if(ProcessCode == PROCESS_CODE_SOCIAL_SYNC){
				response = HDesignApp.getInstanceWebService().postData(URLNameValuePairBuilder.functionSocialSyncParam,
						URLNameValuePairBuilder.setSocialSyncParam(HDesignApp.getInstanceAppPreferences().getVinID(),HDesignApp.getInstanceAppPreferences().getVinNumber(), HDesignApp.getInstanceAppPreferences().getName(),
								HDesignApp.getInstanceAppPreferences().getModelName(), socialType, HDesignApp.getInstanceAppPreferences().getAvatarName(), socialId));
			}
			return response;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			finish();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressDialog.dismiss();

			if (result.equals(HDesignApp.getInstanceWebService().SERVER_BUSY)) {
				AlertUtility.showToast(context, getString(R.string.server_busy));
				finish();
			} else if (result.equals(HDesignApp.getInstanceWebService().NO_INTERNET)) {
				AlertUtility.showToast(context, getString(R.string.no_internet));
				finish();
			}
			
			if(ProcessCode == PROCESS_CODE_SYNC_FACEBOOK)
			{
				AlertUtility.printStatement(TAG,"Facebook String result - " + result);
				handleFacebookData(result);
				//downloadFbAvatar();
			}
			
			if(ProcessCode == PROCESS_CODE_SOCIAL_SYNC)
			{
				AlertUtility.printStatement(TAG,"Social Sync String result - " + result);
				startActivityResult(key, value);
			}
				
		}

	}

	private void handleFacebookData(String result) {
		JSONObject mainJsonObject = null;
		try {
			mainJsonObject = new JSONObject(result);
			UserDataBean userBean = UserDataParser.getParsedFbProfileResult(mainJsonObject);
			HDesignApp.getInstanceFbPreferences().setFacebookID(userBean.getFbId());
			HDesignApp.getInstanceFbPreferences().setFacebookName(userBean.getFbName());
			HDesignApp.getInstanceFbPreferences().setFacebookGender(userBean.getFbGender());

			downloadFbAvatar();

		} catch (JSONException e) {
			e.printStackTrace();

		}
	}

	private synchronized void downloadFbAvatar() {
		AlertUtility.printStatement("+++++++++ Facebook Login Activity +++++++", "Start Downloading Image");
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
					fbAvatarUrl = new URL("http://graph.facebook.com/"+ HDesignApp.getInstanceFbPreferences().getFacebookID() + "/picture?type=large");
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
	    		AppConstants.fbAvatarBitmap = result;
				HDesignApp.getInstanceAppPreferences().setImageUrl(CommonUtility.BitMapToString(result));
				HDesignApp.getInstanceFbPreferences().setFacebookLogin(true);
				Intent intent = new Intent(context, EditProfileActivity.class);
				startActivity(intent);
			}
		};
		task.execute();
	}
	
	private void getFbHashKey()
	{
		try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.hdesignapp", 
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
        } catch (NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        
	}
	
	private void startActivityResult(String key, String value)
	{
		Intent intent = new Intent(context, EditProfileActivity.class);
		intent.putExtra(key, value);
		startActivity(intent);
	}
	
	@Override
	public void onBackPressed() {	    
	    return;
	}
 
}