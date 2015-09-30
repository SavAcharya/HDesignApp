package com.hdesignapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.hdesign.BaseActivity;
import com.hdesign.HDesignApp;
import com.hdesign.ui.FooterBar;
import com.hdesignapp.R;
import com.hdesignapp.utils.CommonUtility;

public class TutorialsActivity extends BaseActivity implements OnClickListener{

	private ImageButton btnPrevious,btnNext;
	private Button btnExit;
	private ViewPager viewPager;
	private FooterBar footerBar;
	
	private int[] imgHowToUse = {R.drawable.tutorials7,R.drawable.tutorials6,R.drawable.tutorials5,R.drawable.tutorials4,
			R.drawable.tutorials3,R.drawable.tutorials2,R.drawable.tutorials1};
	
	private int currentPosition = 0;
	private Activity context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		context = (TutorialsActivity) this;
		CommonUtility.fullScreenActivity(context);
		
		setContentView(R.layout.activity_tutorials);
		
		btnPrevious = (ImageButton) findViewById(R.id.how_buttonPrevious);
		btnNext = (ImageButton) findViewById(R.id.how_buttonNext);
		btnExit = (Button) findViewById(R.id.how_buttonExit);
		viewPager = (ViewPager) findViewById(R.id.how_viewPager);
		viewPager.setAdapter(new HowToUseViewPagerAdapter());
		
		footerBar = (FooterBar) findViewById(R.id.layoutFooterBar);	
		footerBar.setHomeSelected();
		if(HDesignApp.getInstanceAppPreferences().isTutorialFirst())
		{
			footerBar.setVisibility(View.GONE);
		}
		else
		{
			footerBar.setVisibility(View.VISIBLE);
		}
		
		btnPrevious.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		btnExit.setOnClickListener(this);
		setPerfectView();
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int pos) {
				currentPosition = pos;
				setPerfectView();
			}
			@Override
			public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {

			}
			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		
		
	}
	
	private void setPerfectView(){
		
		if(currentPosition==0){
			btnPrevious.setVisibility(View.GONE);
			btnNext.setVisibility(View.VISIBLE);
			viewPager.setCurrentItem(currentPosition);	
		}
		else if(currentPosition == (imgHowToUse.length - 1)){
			btnPrevious.setVisibility(View.VISIBLE);
			btnNext.setVisibility(View.GONE);
			viewPager.setCurrentItem(currentPosition);
		}
		else {
			btnPrevious.setVisibility(View.VISIBLE);
			btnNext.setVisibility(View.VISIBLE);
			viewPager.setCurrentItem(currentPosition);	
		}	
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	showAppHomeScreen();
	    }
	    return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}	
	
	@Override
	public void onClick(View v) {
		
		if(v.equals(btnPrevious)){
			currentPosition--;
			setPerfectView();
		}
		if(v.equals(btnNext)){
			currentPosition++;
			setPerfectView();
		}
		if(v.equals(btnExit)){
			HDesignApp.getInstanceAppPreferences().setTutorialFirst(false);
			showAppHomeScreen();
		}
	}
	
	private void showAppHomeScreen(){
		Intent intent = new Intent(context,HomeActivity.class);
		startActivity(intent);
		finish();
	}
	
	private class HowToUseViewPagerAdapter extends PagerAdapter{

		private LayoutInflater inflater;
		
		public HowToUseViewPagerAdapter() {
			super();
			inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public Object instantiateItem(View collection, int pos) {
			
			View view;
			view = inflater.inflate(R.layout.pager_item_how_use_other, null);
			view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));  
			
			ImageView imgDemo = (ImageView) view.findViewById(R.id.ItemHowUseOtherImageView);
			imgDemo.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));     
			imgDemo.setScaleType(ScaleType.FIT_XY);
			imgDemo.setImageResource(imgHowToUse[pos]);
			
			((ViewPager) collection).addView(view,0);               	        
			return view;
		}
		
		@Override
		public int getItemPosition(Object view) {		
			return super.getItemPosition(view);
		}
		
		@Override
		public void destroyItem(View collection, int pos, Object view) {
			((ViewPager) collection).removeView((View) view);
		}

		@Override
		public void finishUpdate(View collection) {
			
		}

		@Override
		public int getCount() {
			return imgHowToUse.length;
		}

		@Override
		public boolean isViewFromObject(View collection, Object view) {
			return collection==((View)view);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View collection) {
			
		}
		
	}
	
	@Override
	public void onBackPressed() {	    
	    return;
	}
}
