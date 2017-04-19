package com.flyjun.xpermission.util;

import android.Manifest;

/**
 * 目前谷歌的9种权限组
 * @author Flyjun
 *
 */
public class PermissionsGroup {
	
	//http://developer.android.com/guide/topics/security/permissions.html
	
	public static int CALENDAR=1;
	public static int CAMERA=2;
	public static int CONTACTS=3;
	public static int LOCATION=4;
	public static int MICROPHONE=5;
	public static int PHONE=6;
	public static int SENSORS=7;
	public static int SMS=8;
	public static int STORAGE=9;

	public static int getCheckType(String permission){
		
		if(permission.equals(Manifest.permission.READ_CALENDAR)
				|| permission.equals(Manifest.permission.WRITE_CALENDAR)){
			
			return CALENDAR;
		
		}else if(permission.equals(Manifest.permission.CAMERA)){
			
			return CAMERA;
			
		}else if(permission.equals(Manifest.permission.READ_CONTACTS)
				|| permission.equals(Manifest.permission.WRITE_CONTACTS)
				|| permission.equals(Manifest.permission.GET_ACCOUNTS)){
			
			return CONTACTS;
			
		}else if(permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)
				|| permission.equals(Manifest.permission.ACCESS_COARSE_LOCATION)){
			
			return LOCATION;
			
		}else if(permission.equals(Manifest.permission.RECORD_AUDIO)){
			
			return MICROPHONE;
			
		}else if(permission.equals(Manifest.permission.READ_PHONE_STATE)
				|| permission.equals(Manifest.permission.CALL_PHONE)
				|| permission.equals(Manifest.permission.READ_CALL_LOG)
				|| permission.equals(Manifest.permission.WRITE_CALL_LOG)
				|| permission.equals(Manifest.permission.ADD_VOICEMAIL)
				|| permission.equals(Manifest.permission.USE_SIP)
				|| permission.equals(Manifest.permission.PROCESS_OUTGOING_CALLS)){
			
			return PHONE;
			
		}else if(permission.equals(Manifest.permission.BODY_SENSORS)){
			
			return SENSORS;
			
		}else if(permission.equals(Manifest.permission.SEND_SMS)
				|| permission.equals(Manifest.permission.RECEIVE_SMS)
				|| permission.equals(Manifest.permission.READ_SMS)
				|| permission.equals(Manifest.permission.RECEIVE_WAP_PUSH)
				|| permission.equals(Manifest.permission.RECEIVE_MMS)
				){
			
			return SMS;
			
		}else if(permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)
				|| permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
			
			return STORAGE;
			
		}
		
		return -1;
	}
}
