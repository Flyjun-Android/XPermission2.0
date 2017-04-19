package com.flyjun.xpermission.util;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/**
 * 弹窗提示
 * @author Flyjun
 *
 */
public class Tips {

	private Context context;
	private Builder mAlertDialog;
	
	
	public Tips(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
		init();
	}
	
	private void init(){
		mAlertDialog=new Builder(context);
		mAlertDialog.setCancelable(false);
		mAlertDialog.create();
	}
	
	public Tips setTitle(String title){
		mAlertDialog.setTitle(title);
		return this;
	}
	
	public Tips setIcon(int iconId){
		mAlertDialog.setIcon(iconId);
		return this;
	}
	
	public Tips setMessage(String message){
		mAlertDialog.setMessage(message);
		return this;
	}
	
	public void show(){
		mAlertDialog.show();
	}
	
	public Tips setOnSingleListener(final SingleListener singleListener){
		
		mAlertDialog.setNegativeButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				singleListener.onClick();
			}
		});
		
		return this;
	}
	
	public Tips setOnDoubleListener(String cancelStr, String okStr, final DoubleListener doubleListener){
       
		mAlertDialog.setNegativeButton(cancelStr, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				doubleListener.onCancel();
			}
		}).setPositiveButton(okStr, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				doubleListener.onOk();
			}
		});
		return this;
	}
	
	
	public interface SingleListener{
		public void onClick();
	}
	
	public interface DoubleListener{
		public void onCancel();
		public void onOk();
	}
	
}
