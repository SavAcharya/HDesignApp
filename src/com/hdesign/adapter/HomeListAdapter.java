package com.hdesign.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdesignapp.R;
import com.hdesignapp.activity.TutorialsActivity;
import com.hdesignapp.utils.AlertUtility;

public class HomeListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Activity context;
	
	private String[] moduleName = { "RIDE ON", "H.O.G", "MY HARLEY",
			"MY CHAPTER", "ROADSIDE ASSISTANCE", "EVENTS", "2014 MOTORCYCLES",
			"GALLERY", "TUTORIALS" };
	
	private int[] moduleImageBg = { R.drawable.ride_on, R.drawable.hog,
			R.drawable.my_harley, R.drawable.my_chapter,
			R.drawable.roadside_assistence, R.drawable.events,
			R.drawable.motorcycles, R.drawable.gallery, R.drawable.tutorial };
	
	 private String[] moduleDesc = { "REV UP THE ENGINE AND HIT THE ROAD.", "MEET FELLOW RIDERS", "KEEP YOUR MOTORCYCLE IN SHAPE.",
				"GET THE LATEST UPDATE FROM YOUR CHAPTER.", "WE RIDE WITH YOU.", "ROAR YOUR WAY TO THE NEXT CELEBRATION.", "CHECKOUT THE CURRENT HARLEY DAVIDSON LINE-UP.",
				"COLLECTION OF ICONIC MOTORCYCLES AND RIDERS.", "LEARN WHAT YOU CAN DO WITH THE APP." };
	
	public HomeListAdapter(Activity context) {
		super();
		this.context = context;
		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if(moduleImageBg.length > 0)
		  return moduleImageBg.length;
		else
			return 0;
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int pos, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.dashboard_item_list_view, null);
			holder = new ViewHolder();
			
			holder.moduleLayout = (LinearLayout) convertView.findViewById(R.id.item_linear_layout);
			holder.moduleName = (TextView) convertView.findViewById(R.id.module_name_Text);
			holder.moduleDesc = (TextView) convertView.findViewById(R.id.module_desc_Text);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.moduleName.setText(moduleName[pos]);
		holder.moduleDesc.setText(moduleDesc[pos]);
		holder.moduleLayout.setBackgroundResource(moduleImageBg[pos]);
		holder.moduleLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			   
				if(pos == 8)
				{					
					handleTutorialClick();
				}else
				{
					AlertUtility.showToast(context, moduleName[pos]+" IS SELECTED.");
				}
				
			}
		});
		
		return convertView;
	}

	private class ViewHolder {
		TextView moduleName, moduleDesc;
		LinearLayout moduleLayout;
	}
	
	private void handleTutorialClick()
	{
		Intent intent = new Intent(context,TutorialsActivity.class);
		context.startActivity(intent);
		context.finish();
	}
	
}
