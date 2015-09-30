package com.hdesignapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.hdesign.BaseActivity;
import com.hdesign.HDesignApp;
import com.hdesignapp.R;
import com.hdesignapp.utils.CommonUtility;

public class GetStartedActivity extends BaseActivity implements OnClickListener {

	private Button getAppTourBtn, getStartedBtn;
	private Activity context;
	
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		context = (GetStartedActivity) this;
		CommonUtility.fullScreenActivity(context);
		setContentView(R.layout.activity_get_started);
		
		setupViews();
	}

	private void setupViews()
	{
		getAppTourBtn = (Button) findViewById(R.id.get_app_tour_btn);
		getStartedBtn = (Button) findViewById(R.id.get_started_btn);
		
		getAppTourBtn.setOnClickListener(this);
		getStartedBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.get_app_tour_btn:
			
			Intent demoIntent = new Intent(context,TutorialsActivity.class);
			startActivity(demoIntent);
			break;

        case R.id.get_started_btn:
        	HDesignApp.getInstanceAppPreferences().setLogin(true);
        	Intent intent = new Intent(context,HomeActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {	    
	    return;
	}
}
