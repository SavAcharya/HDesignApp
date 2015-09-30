package com.hdesign.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.hdesignapp.R;
import com.hdesignapp.activity.FavoriteActivity;
import com.hdesignapp.activity.HomeActivity;
import com.hdesignapp.activity.MyProfileActivity;
import com.hdesignapp.activity.UpdateActivity;

public class FooterBar extends RadioGroup {

	private View view;
	private LayoutInflater inflater;
	private Context context;
	private ViewHolderFooter holder;
	
	public FooterBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.layout_footer, null);
		view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.footer_height)));
		init();
	}

	public FooterBar(Context context) {
		super(context);
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.layout_footer, null);
		view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.footer_height)));
		init();
	}

	public void init() {
		removeAllViews();
		holder = new ViewHolderFooter();
		
		holder.btnHome = (Button) view.findViewById(R.id.footer_buttonHome);		
		holder.btnProfile = (Button) view.findViewById(R.id.footer_buttonProfile);
		holder.btnFavorite = (Button) view.findViewById(R.id.footer_buttonFavorite);		
		holder.btnUpdate = (Button) view.findViewById(R.id.footer_buttonUpdate);
		
		holder.btnHome.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				System.out.println("User clicked on Home Button on FooterBar");
				Intent intentHome = new Intent(context, HomeActivity.class);
				intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(intentHome);
				((Activity) context).finish();
			}
		});
		
		holder.btnProfile.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(! holder.btnProfile.isSelected()){
					System.out.println("User clicked on My Profile Button on FooterBar");
					Intent intentMap = new Intent(context, MyProfileActivity.class);
					intentMap.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
					context.startActivity(intentMap);
				}				
			}
		});
		
		holder.btnFavorite.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(! holder.btnFavorite.isSelected()){
					System.out.println("User clicked on Favorite Button on FooterBar");
					Intent intentMap = new Intent(context, FavoriteActivity.class);
					intentMap.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
					context.startActivity(intentMap);
				}				
			}
		});
		
		holder.btnUpdate.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(! holder.btnUpdate.isSelected()){
					System.out.println("User clicked on Update Button on FooterBar");
					Intent intentMap = new Intent(context, UpdateActivity.class);
					intentMap.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
					context.startActivity(intentMap);
				}	
			}
		});		
		
		view.setTag(holder);
		addView(view);
	}
	
	public void setHomeSelected(){
		holder.btnHome.setSelected(true);
	}
	
	public void setProfileSelected(){
		holder.btnProfile.setSelected(true);
	}
	
	public void setFavoriteSelected(){
		holder.btnFavorite.setSelected(true);
	}
	
	public void setUpdateSelected(){
		holder.btnUpdate.setSelected(true);
	}
	
	
	class ViewHolderFooter {	
		Button btnHome,btnProfile,btnFavorite,btnUpdate;
	}
}
