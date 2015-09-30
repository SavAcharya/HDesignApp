package com.hdesignapp.activity;

import android.os.Bundle;

import com.hdesign.BaseActivity;
import com.hdesign.ui.FooterBar;
import com.hdesignapp.R;

public class FavoriteActivity extends BaseActivity{

	private FooterBar footerBar;
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.activity_favorite);
		footerBar = (FooterBar) findViewById(R.id.layoutFooterBar);	
		footerBar.setFavoriteSelected();
	}
	
	@Override
	public void onBackPressed() {	    
	    return;
	}
}
