package com.flyjun.test;

import android.Manifest;
import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.flyjun.xpermission.listener.XPermissionsListener;
import com.flyjun.xpermission.util.Logger;
import com.flyjun.xpermissions.XPermissions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XPermissions.init(this);


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                XPermissions.requestPermissions().setRequestCode(203).setShouldShow(true).setPermissions(new String[]{Manifest.permission.READ_PHONE_STATE
                        ,Manifest.permission.CAMERA}).setOnXPermissionsListener(new XPermissionsListener() {
                    @Override
                    public void onXPermissions(int requestCode, int resultCode) {
                        Logger.log("resultCode:"+resultCode);
                        if (resultCode == XPermissions.PERMISSION_SUCCESS){
                            //权限申请成功，可以继续往下走
                        }else{
                            //权限申请失败，此时应该关闭界面或者退出程序
                        }
                    }
                }).builder();

            }
        });
    }

    public void test(){}


    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        XPermissions.handlerPermissionResult(requestCode,permissions,grantResults);
    }
}
