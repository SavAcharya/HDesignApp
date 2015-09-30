package com.hdesignapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.hdesign.BaseActivity;
import com.hdesign.adapter.HomeListAdapter;
import com.hdesign.ui.FooterBar;
import com.hdesignapp.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeActivity extends BaseActivity {
	
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private Activity context;
	private FooterBar footerBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (HomeActivity) this;
        setContentView(R.layout.activity_home);
        
        footerBar = (FooterBar) findViewById(R.id.layoutFooterBar);	
		footerBar.setHomeSelected();
		
        ListView moduleListView = (ListView) findViewById(R.id.module_list_view);
		moduleListView.setAdapter(new HomeListAdapter(context));
        
    }
    
    @Override
	public void onBackPressed() {	    
	    return;
	}
}

