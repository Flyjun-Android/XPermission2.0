package com.flyjun.xpermissions;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.flyjun.xpermission.util.PermissionsBuilder;
import com.flyjun.xpermission.util.PermissionsHelper;
import com.flyjun.xpermission.util.Tips;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 权限管理
 * tragetSdkVersion和compileSdkVersion都是23时，权限管理才会管理
 * @author Flyjun
 *
 */
public class XPermissions {

	//请求权限获取成功
	public static final int PERMISSION_SUCCESS=0;
	//请求权限获取失败
	public static final int PERMISSION_FAIL=-1;
	//跳转到包名设置权限
	public static final int PERMISSION_SETTING=-2;



	private Activity activity;

	private PermissionsBuilder permissionsBuilder;

	private static XPermissions xPermissions;

	public static void init(Activity activity){
		xPermissions=new XPermissions(activity);
	}

	public XPermissions(Activity activity) {
		this.activity = activity;
	}

	public static PermissionsBuilder requestPermissions(){

		xPermissions.permissionsBuilder=new PermissionsBuilder(xPermissions.activity);

		return xPermissions.permissionsBuilder;

	}

	/**
	 * 处理onRequestPermissionsResult的回调
	 * @param requestCode
	 * @param permissions
	 * @param grantResults
	 *
	 */
	public static void handlerPermissionResult(final int requestCode,
											   String[] permissions, int[] grantResults){
		xPermissions.permissionResult(requestCode,permissions,grantResults);

	}


	/**
	 * 处理onRequestPermissionsResult的回调
	 * @param requestCode
	 * @param permissions
	 * @param grantResults
	 *
	 */
	private void permissionResult(final int requestCode,
			String[] permissions, int[] grantResults){
		
		/**
		 * 保存可以重试的权限
		 */
		final List<String> shouldShowList=new ArrayList<String>();
		
		/**
		 * 保存所有被拒绝的权限
		 */
		List<String> deniedList=new ArrayList<String>();
	
		
		/**
		 * 记录权限是否是有点击 不再提示的权限
		 */
		Map<String, Boolean> shouldMap=new HashMap<String, Boolean>();
		
	    for(int i=0;i<permissions.length;i++){
	    	

	    	if(grantResults[i] == PackageManager.PERMISSION_DENIED){
	    		
	    		String permission=permissions[i];
	    		
	    		boolean isShould=ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);

	    		/*
	    		 * 这时候的检测，如果返回false，则是勾选了不再提示（永久拒绝了，这时候需要提示用户手动在在应用设置中打开），只有有个权限是返回false就成立
	    		 * 如果返回true，我们可以执行
	    		 */
	    		shouldMap.put(permission, isShould);
	    		
	    		deniedList.add(permission);
	    		
	    		if(isShould){
	    			//保存可以重试获取的权限
	    			shouldShowList.add(permission);
	    		}
	    		
	    	}
	    }
	    
	    //权限获取成功
	    if(shouldMap.size() == 0){
	    	PermissionsHelper.onHandlerCallback(permissionsBuilder,requestCode, PERMISSION_SUCCESS);
	    }else{
	    	
	    	/**
	    	 * 检测是否有被勾选不再提示的
	    	 */
	    	for(Entry<String, Boolean> entry : shouldMap.entrySet()){
	    		
	    		if(false == entry.getValue()){
	    			
	    			//打开应用的设置界面去打开权限
	    			
	    			new Tips(activity).setTitle("去设置权限").setMessage(PermissionsHelper.getPermissionMsg(activity,deniedList))
							.setOnDoubleListener("取消", "去设置", new Tips.DoubleListener() {
						
						@Override
						public void onOk() {
							// TODO Auto-generated method stub
							
							PermissionsHelper.startAppSettings(activity);
							
							PermissionsHelper.onHandlerCallback(permissionsBuilder,requestCode, PERMISSION_SETTING);
						}
						
						@Override
						public void onCancel() {
							// TODO Auto-generated method stub
							PermissionsHelper.onHandlerCallback(permissionsBuilder,requestCode, PERMISSION_FAIL);
						}
					}).show();
	    			
	    			return;
	    		}
	    	}
	    	
	    	//权限不够，可以选择去重试
	    	new Tips(activity).setTitle("权限").setMessage(PermissionsHelper.getPermissionMsg(activity,shouldShowList))
	    	.setOnDoubleListener("取消", "重试", new Tips.DoubleListener() {
				
				@Override
				public void onOk() {
					// TODO Auto-generated method stub

					    permissionsBuilder.setRequestCode(requestCode);
					    permissionsBuilder.setPermissions(shouldShowList.toArray(new String[shouldShowList.size()]));
					    permissionsBuilder.setShouldShow(false);
						PermissionsHelper.requestPermissions(permissionsBuilder );
				}
				
				@Override
				public void onCancel() {
					// TODO Auto-generated method stub
					PermissionsHelper.onHandlerCallback(permissionsBuilder, requestCode, PERMISSION_FAIL);
				}
			}).show();
	    	
	    }
 
	}
	
}
