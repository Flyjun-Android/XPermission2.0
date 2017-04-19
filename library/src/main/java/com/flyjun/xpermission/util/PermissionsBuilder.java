package com.flyjun.xpermission.util;

import android.app.Activity;

import com.flyjun.xpermission.listener.XPermissionsListener;

/**
 * Created by Flyjun on 2017/2/20.
 */

public class PermissionsBuilder {
    //上下文
    private Activity activity;
    //申请权限的code
    private int requestCode;
    //需要申请的权限数组
    private String[] permissions;
    //是否需要显示说明申请权限的弹窗提示？
    private boolean shouldShow;
    //申请权限回调
    private XPermissionsListener xPermissionsListener;

    public PermissionsBuilder(Activity activity) {
        this.activity=activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public PermissionsBuilder setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public PermissionsBuilder setRequestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public PermissionsBuilder setPermissions(String[] permissions) {
        this.permissions = permissions;
        return this;
    }

    public boolean isShouldShow() {
        return shouldShow;
    }

    public PermissionsBuilder setShouldShow(boolean shouldShow) {
        this.shouldShow = shouldShow;
        return this;
    }

    public XPermissionsListener getXPermissionsListener() {
        return xPermissionsListener;
    }

    public PermissionsBuilder setOnXPermissionsListener(XPermissionsListener xPermissionsListener) {
        this.xPermissionsListener = xPermissionsListener;
        return this;
    }

    public void builder(){
       PermissionsHelper.requestPermissions(activity,this);
    }
}
