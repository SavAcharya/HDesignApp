package com.hdesignapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.hdesign.BaseActivity;
import com.hdesign.HDesignApp;
import com.hdesignapp.R;
import com.hdesignapp.utils.CommonUtility;

public class SplashActivity extends BaseActivity {

	private static final int SPLASH_DISPLAY_TIME = 2000; 
	private final Handler handler = new Handler();	
	private Activity context;
		
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (SplashActivity) this;
        CommonUtility.fullScreenActivity(context);
        setContentView(R.layout.activity_splash); 
        handler.postDelayed(runnableSplash, SPLASH_DISPLAY_TIME);
    }
	
	private Runnable runnableSplash = new Runnable() {
    	public void run() {   		
    		handler.removeCallbacks(runnableSplash); 
    		System.out.println("Isfirst Value - "+HDesignApp.getInstanceAppPreferences().isFirst());
    		if(HDesignApp.getInstanceAppPreferences().isFirst()){
    		  // Show User's Home Screen.
     			Intent intent = new Intent(SplashActivity.this,InitialActivity.class);
        		startActivity(intent);    	
        		finish();	
    		}
    		else
    		{
    			Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
        		startActivity(intent);    	
        		finish();	

    		}
    		
    	}
    };
}
