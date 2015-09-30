package com.hdesign.ui;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.hdesignapp.R;
import com.hdesignapp.utils.AppConstants;

public class CustomDialog extends Dialog implements android.view.View.OnClickListener {

	private Activity context;
	private Button closeBtn;
	private TextView dialogText;
	private int FLAG;

	public CustomDialog(Activity context,int FLAG) {
		super(context);
		this.context = context;
		this.FLAG = FLAG;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.forget_dialog);
       
		dialogText = (TextView) findViewById(R.id.forgot_vin_text_view);
		closeBtn = (Button) findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(this);
        
        if(FLAG == AppConstants.OPEN_FORGET_DIALOG)
        {
        	dialogText.setText(context.getResources().getString(R.string.forget_vin_text));
        }
        
        if(FLAG == AppConstants.WRONG_VIN_DIALOG)
        {
        	dialogText.setText(context.getResources().getString(R.string.wrong_vin_number));
        }
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId() == R.id.closeBtn)
		    dismiss();
	}
}