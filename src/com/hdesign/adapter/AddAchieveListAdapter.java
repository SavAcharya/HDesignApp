package com.hdesign.adapter;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdesign.HDesignApp;
import com.hdesign.bean.AchievementBean;
import com.hdesign.net.URLNameValuePairBuilder;
import com.hdesignapp.R;
import com.hdesignapp.utils.AlertUtility;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class AddAchieveListAdapter extends BaseAdapter{

	private Activity context;
	private ArrayList<AchievementBean> achievementList;
	private LayoutInflater mInflater;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	
	public AddAchieveListAdapter(Activity context, ArrayList<AchievementBean> achievementList,ImageLoader _imageLoader) {
		super();
		this.context = context;
		this.achievementList = achievementList;
		this.imageLoader = _imageLoader;
		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.cacheInMemory().cacheOnDisc().build();
	}

	@Override
	public int getCount() {
		if(achievementList.size() > 0)
			return achievementList.size();
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
	public View getView(final int pos, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.achievement_list_item, null);
			holder = new ViewHolder();
			
			holder.achieveName = (TextView) convertView.findViewById(R.id.achievementText);
			holder.achieveThumbnail = (ImageView) convertView.findViewById(R.id.achievementThumbnailImage);
			holder.addAchieveBtn = (TextView) convertView.findViewById(R.id.addAchieveTextView);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.addAchieveBtn.setVisibility(View.VISIBLE);
		holder.achieveName.setTextColor(context.getResources().getColor(R.color.white));
		holder.achieveName.setText(achievementList.get(pos).getAchievementName().toUpperCase());

		imageLoader.displayImage(achievementList.get(pos).getAchievementImageUrl(),holder.achieveThumbnail,options);
		
		holder.addAchieveBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 
                 new ManageAchieveAsyncTask(pos).execute();
			}
		});
		
		return convertView;
	}

	private class ViewHolder {
		TextView achieveName,addAchieveBtn;
		ImageView achieveThumbnail;		
	}
	
	private class ManageAchieveAsyncTask extends AsyncTask<Void, Void, String>
	{

		ProgressDialog progressDialog;
		int pos;
		
		public ManageAchieveAsyncTask(int pos) {
			super();
			this.pos = pos;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("Add Achievement...");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setCancelable(false);
			progressDialog.setCanceledOnTouchOutside(false);
			
			progressDialog.setOnCancelListener(new OnCancelListener() {				
				@Override
				public void onCancel(DialogInterface dialog) {
					cancel(true);
				}
			});	
			progressDialog.show();	
		}

		@Override
		protected String doInBackground(Void... arg0) {
			String response = "";
			response = HDesignApp.getInstanceWebService().postData(URLNameValuePairBuilder.functionManageAchievementParam, 
					URLNameValuePairBuilder.setManageAchievementParam("11_AAAA",achievementList.get(pos).getAchievementMaserId()));
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
				AlertUtility.showToast(context,context.getResources().getString(R.string.server_busy));				
				return;
			}
			else if(result.equals(HDesignApp.getInstanceWebService().NO_INTERNET)){
				progressDialog.dismiss();
				AlertUtility.showToast(context,context.getResources().getString(R.string.no_internet));		
				return;
			}
			
			manageAchievementResult(result);
			AlertUtility.printStatement("AddAchieveListAdapter", "API Result - "+result);
			progressDialog.dismiss();
		}
		
		private void manageAchievementResult(String result)
		{
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
				JSONObject mainJsonObject = jsonObject.getJSONObject("status");
				String success = mainJsonObject.getString("type");
				if(success.equalsIgnoreCase("Success"))
				{
					String message = mainJsonObject.getString("msg");
					AlertUtility.printStatement("AddAchieveListAdapte",message);
					AlertUtility.showToast(context, message);
					achievementList.remove(pos);
					notifyDataSetChanged();
					AlertUtility.printStatement("AddAchieveListAdapter","AchieveList Size - "+achievementList.size());
				}
				else
				{
					AlertUtility.printStatement("AddAchieveListAdapter","Achievement not added properly.");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
}
