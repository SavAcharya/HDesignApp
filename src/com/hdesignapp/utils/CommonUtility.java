package com.hdesignapp.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.view.Window;
import android.view.WindowManager;


public class CommonUtility {

	public static boolean isOnline(Activity context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnectedOrConnecting()) {
				return true;
			}
		}
		return false;
	}
	
	public static Bitmap getCroppedBitmap(Bitmap bitmap) {
		if(bitmap != null){
			 Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Config.ARGB_8888);
			    Canvas canvas = new Canvas(output);

			    final int color = 0xff424242;
			    final Paint paint = new Paint();
			    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

			    paint.setAntiAlias(true);
			    canvas.drawARGB(0, 0, 0, 0);
			    paint.setColor(color);
			    canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
			    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			    canvas.drawBitmap(bitmap, rect, rect, paint);
			    return output;
		}
	   return null;
	}
	
	// To get Rounded Shape Image...
		public static Bitmap getRoundedShape(Bitmap bitmap) {
			// TODO Auto-generated method stub
			if (bitmap != null) {
				Bitmap targetBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(targetBitmap);

				final int color = 0xff424242;
				final Paint paint = new Paint();
				paint.setAntiAlias(true);
				canvas.drawARGB(0, 0, 0, 0);
				paint.setColor(color);

				Path path = new Path();
				path.addCircle(((float) bitmap.getWidth() - 1) / 2,((float) bitmap.getHeight() - 1) / 2,
						(Math.min(((float) bitmap.getWidth()),((float) bitmap.getHeight())) / 2),
						 Path.Direction.CCW);
				canvas.clipPath(path);
				Bitmap sourceBitmap = bitmap;
				canvas.drawBitmap(bitmap,new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight()),
						new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight()), paint);
				return targetBitmap;
			}
			return null;

		}
		
	public static void fullScreenActivity(Activity context)
	{
		context.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	public static Boolean SaveMyProfileImageToApp (Activity cxt,Bitmap bitmap, String FileName) {
    	try
    	{
	    	FileOutputStream fos = cxt.openFileOutput(FileName, Context.MODE_PRIVATE);
	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	    	bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object   
	    	byte[] b = baos.toByteArray(); 
	    	fos.write(b);
	    	fos.flush();
	    	fos.close();
	    	System.out.println("Image Saved : "  + FileName );
	    	return true;
    	}
    	catch(Exception er)
    	{
    		System.out.println(" Errrrrrrrrrrrrrrrrrr while Saving  : (" + FileName + ") " + er.getMessage());
    		return false;
    	}
    }
	
	public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] arr=baos.toByteArray();
        String result=Base64.encodeToString(arr, Base64.DEFAULT);
        return result;
    }
	
	public static Bitmap StringToBitMap(String image){
	       try{
	           byte [] encodeByte=Base64.decode(image,Base64.DEFAULT);
	           Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
	           return bitmap;
	         }catch(Exception e){
	           e.getMessage();
	          return null;
	         }
	 }
	
	

}
