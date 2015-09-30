package com.hdesign;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.nostra13.universalimageloader.core.ImageLoader;

public class BaseActivity extends FragmentActivity {
	
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private boolean instanceStateSaved;
	protected SQLiteDatabase db;
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		instanceStateSaved = true;
	}
	
	@Override
	protected void onDestroy() {
		if (!instanceStateSaved) {
			imageLoader.stop();
		}
		super.onDestroy();
	}
	
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		Window window = getWindow();
		window.setFormat(PixelFormat.RGBA_8888);
	}
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	}
	
	@Override
	protected void onPause() {
		super.onPause();

	}
	
}
