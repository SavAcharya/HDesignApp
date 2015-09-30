package com.hdesign.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdesign.bean.AchievementBean;
import com.hdesignapp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class AchievementListAdapter extends BaseAdapter {

	private Activity context;
	private ArrayList<AchievementBean> viewAchievementList;
	private LayoutInflater mInflater;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
		
	public AchievementListAdapter(Activity context, ArrayList<AchievementBean> viewAchievementList,ImageLoader _imageLoader) {
		super();
		this.context = context;
		this.viewAchievementList = viewAchievementList;
		this.imageLoader = _imageLoader;
		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.cacheInMemory().cacheOnDisc().build();
	}

	@Override
	public int getCount() {
		if(viewAchievementList.size() > 0)
			return viewAchievementList.size();
		else 
		    return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.achievement_list_item, null);
			holder = new ViewHolder();
			
			holder.achievementName = (TextView) convertView.findViewById(R.id.achievementText);
			holder.achievementThumbnail = (ImageView) convertView.findViewById(R.id.achievementThumbnailImage);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.achievementName.setText(viewAchievementList.get(pos).getAchievementName().toUpperCase());
		imageLoader.displayImage(viewAchievementList.get(pos).getAchievementImageUrl(),holder.achievementThumbnail,options);
		
		return convertView;
	}

	private class ViewHolder {
		TextView achievementName;
		ImageView achievementThumbnail;		
	}

}
