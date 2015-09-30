package com.hdesign;

import android.app.Application;

import com.hdesign.net.WebService;
import com.hdesignapp.extras.LazyImageDownloader;
import com.hdesignapp.prefs.AppPreferences;
import com.hdesignapp.prefs.FacebookPrefs;
import com.hdesignapp.prefs.GooglePrefs;
import com.hdesignapp.prefs.TwitterPrefs;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class HDesignApp extends Application{

	private static AppPreferences appPreferences;
	private static FacebookPrefs fbPreferences;
	private static TwitterPrefs twitPreferences;
	private static GooglePrefs googlePreferences;
	private static WebService webService;

	@Override
	public void onCreate() {
		super.onCreate();
		
		appPreferences = new AppPreferences(getApplicationContext());
		fbPreferences = new FacebookPrefs(getApplicationContext());
		twitPreferences = new TwitterPrefs(getApplicationContext());
		googlePreferences = new GooglePrefs(getApplicationContext());
		webService = new WebService(getApplicationContext());
		
		// This configuration tuning is custom. You can tune every option, you may tune some of them, 
				// or you can create default configuration by ImageLoaderConfiguration.createDefault(this); method.
				ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
					.threadPriority(Thread.NORM_PRIORITY - 2)
					.memoryCacheSize(2 * 1024 * 1024) // 2 Mb
					.denyCacheImageMultipleSizesInMemory()
					.discCacheFileNameGenerator(new Md5FileNameGenerator())
					.imageDownloader(new LazyImageDownloader(getApplicationContext()))
					.tasksProcessingOrder(QueueProcessingType.LIFO)
					.enableLogging() // Not necessary in common
					.build();
				// Initialize ImageLoader with configuration.
				ImageLoader.getInstance().init(config);
	}
	
	public static AppPreferences getInstanceAppPreferences() {
        return appPreferences;
    }
	
	public static FacebookPrefs getInstanceFbPreferences() {
        return fbPreferences;
    }
	
	public static TwitterPrefs getInstanceTwitterPreferences() {
        return twitPreferences;
    }
	
	public static GooglePrefs getInstanceGooglePreferences() {
        return googlePreferences;
    }
	
	public static WebService getInstanceWebService() {
        return webService;
    }
		
}
