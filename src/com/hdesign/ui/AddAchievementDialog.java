package com.hdesign.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hdesign.adapter.AchievementListAdapter;
import com.hdesign.bean.AchievementBean;
import com.hdesignapp.R;
import com.hdesignapp.activity.AddAchievementActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

public class AddAchievementDialog extends Dialog
{
	private Activity context;
	private ListView listView;
	private TextView achievementText;
	private Button closeBtn;
	private AchievementListAdapter adapter;
	private ImageLoader imageLoader;
	ArrayList<AchievementBean> viewAchieveList;
	LayoutInflater inflater;


    public AddAchievementDialog(Activity context,ImageLoader imageLoader,ArrayList<AchievementBean> viewAchieveList) 
    {
		super(context);
		this.context = context;
		this.imageLoader = imageLoader;
		this.viewAchieveList = viewAchieveList;
	}
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.achievement_dialog);
		
		achievementText = (TextView) findViewById(R.id.countAchievementText);
	    closeBtn = (Button) findViewById(R.id.dialogCloseBtn);
	    listView = (ListView) findViewById(R.id.achievementListView);
		  
			addButtonInListView();
			adapter = new AchievementListAdapter(context,viewAchieveList,imageLoader);
			listView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			achievementText.setText(""+viewAchieveList.size()+" Achievements");
		    
			closeBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					 dismiss();
					
				}
			});
			
    }
    
    private void handleAddAchieveAction()
   	{
    	Intent intent = new Intent(context,AddAchievementActivity.class);
    	context.startActivity(intent);
   	}
    
    private void addButtonInListView()
    {   
	    View view = inflater.inflate(R.layout.list_footer_view, null);
	    Button addMoreAchieveBtn = (Button) view.findViewById(R.id.add_more_achieve_btn);
	    addMoreAchieveBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				handleAddAchieveAction();
				dismiss();				}
		});
	    		
		listView.addFooterView(addMoreAchieveBtn);
    }
       
}