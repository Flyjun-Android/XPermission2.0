package com.flyjun.xpermission.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.flyjun.xpermission.R;
import com.flyjun.xpermission.db.PermissionsDB;
import com.flyjun.xpermissions.XPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Flyjun on 2017/2/20.
 */

public class PermissionsHelper {

    private static PermissionsDB permissionsDB;

    // 权限参数
    private static final String PACKAGE_URL_SCHEME = "package:";

    /**
     * 请求权限
     * @param permissionsBuilder
     */
    public static void requestPermissions(Activity activity,final PermissionsBuilder permissionsBuilder){

        if(null == permissionsBuilder.getPermissions()){
            return;
        }


        /**
         * 低版本不检测
         */
        if(activity.getApplicationInfo().targetSdkVersion < 23){
            onHandlerCallback(permissionsBuilder,permissionsBuilder.getRequestCode(), XPermissions.PERMISSION_SUCCESS);
            return;
        }

        //需要请求的权限列表
        final List<String> neededPerList=new ArrayList<String>();

        //需要提示给用户的权限列表
        List<String> shouldShowList=new ArrayList<String>();


        for(int i=0;i<permissionsBuilder.getPermissions().length;i++){

            String permission=permissionsBuilder.getPermissions()[i];


            boolean isNeed=addPermission(permissionsBuilder.getActivity(), neededPerList, permission);

            if(isNeed){
                shouldShowList.add(permission);
            }

        }

        //这里可以按需求自己自定义弹窗显示
        if(shouldShowList.size() > 0 && permissionsBuilder.isShouldShow() && !getPermissionDB(permissionsBuilder.getActivity()).hasRequestCode(permissionsBuilder.getRequestCode())){


            new Tips(permissionsBuilder.getActivity()).setTitle("权限申请提示").setMessage(getPermissionMsg(permissionsBuilder.getActivity(),shouldShowList))
                    .setOnSingleListener(new Tips.SingleListener() {

                        @Override
                        public void onClick() {
                            // TODO Auto-generated method stub
                            if(neededPerList.size() >0 ){
                                ActivityCompat.requestPermissions(permissionsBuilder.getActivity(), neededPerList.toArray(new String[neededPerList.size()]), permissionsBuilder.getRequestCode());
                            }
                        }
                    }).show();
            return ;
        }


        if(neededPerList.size() >0 ){
            ActivityCompat.requestPermissions(permissionsBuilder.getActivity(), neededPerList.toArray(new String[neededPerList.size()]), permissionsBuilder.getRequestCode());
        }else{
            //处理权限预先被允许了的情况
            onHandlerCallback(permissionsBuilder, permissionsBuilder.getRequestCode(), XPermissions.PERMISSION_SUCCESS);
        }

    }

    /**
     * 添加一个需要的权限
     * @param neededPerList
     * @param permission
     * @return
     */
    public static boolean addPermission(Activity activity, List<String> neededPerList, String permission) {

//		System.out.println("checkSelfPermission:"+ContextCompat.checkSelfPermission(activity,permission));

        if (ContextCompat.checkSelfPermission(activity,permission) != PackageManager.PERMISSION_GRANTED) {

            neededPerList.add(permission);

            /**
             * 检测是否需要请求权限,第一次检测返回false,弹窗申请点击拒绝请求的时候返回true(勾选了永久拒绝返回false，我们需要权限需要提示用户手动去开启)
             *
             * 第一次检测返回false，勾选了永久不提示返回false，只是点击了拒绝返回true
             */
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)){
                return true;
            }
        }
        return false;
    }

    public static void onHandlerCallback(PermissionsBuilder builder,int requestCode,int result){

        if(null != builder.getXPermissionsListener()){
            builder.getXPermissionsListener().onXPermissions(requestCode,result);
        }
    }


    /**
     * 获取需要提示用户sdk需要的权限有哪些
     * @param list
     * @return
     */
    public static String getPermissionMsg(Context context,List<String> list){
        Map<Integer, Integer> map=new HashMap<Integer, Integer>();
        for(String permission:list){
            int type= PermissionsGroup.getCheckType(permission);
            if( type != -1){
                map.put(type, type);
            }
        }


        StringBuffer sb=new StringBuffer();

        if(map.size() > 0){

            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {

                int result=entry.getValue();

                //根据result来判断需要的是什么权限

                switch (result){
                    case 1:

                        sb.append(context.getString(R.string.calendar)+" ");

                        break;

                    case 2:

                        sb.append(context.getString(R.string.camera)+" ");

                        break;

                    case 3:

                        sb.append(context.getString(R.string.contacts)+" ");

                        break;

                    case 4:

                        sb.append(context.getString(R.string.location)+" ");

                        break;

                    case 5:

                        sb.append(context.getString(R.string.microphone)+" ");

                        break;

                    case 6:

                        sb.append(context.getString(R.string.phone)+" ");

                        break;

                    case 7:

                        sb.append(context.getString(R.string.sensors)+" ");

                        break;

                    case 8:

                        sb.append(context.getString(R.string.sms)+" ");

                        break;

                    case 9:

                        sb.append(context.getString(R.string.storage)+" ");

                        break;

                }

            }
        }

        return sb.toString();
    }


    private static PermissionsDB getPermissionDB(Context context){

        if(null == permissionsDB){
            permissionsDB=new PermissionsDB(context);
        }
        return permissionsDB;
    }


    // 启动应用的设置
    public static void startAppSettings(Activity activity)
    {
//				Settings.
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + activity.getPackageName()));
        activity.startActivity(intent);

    }

}
