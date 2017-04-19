# XPermission
XPermissions权限管理框架，用于快速友好的给用户提示、开发接入管理权限

Android6.0上需要运行时的权限，如果我们不处理那么在6.0上权限申请不到app就会闪退，被摧残了这么久，是时候来管理你的权限了！
那么我们需要怎么管理呢？开发者方便的接入使用，XPermissions告诉你需要这么做。。。

申请一个危险的权限我们先要告诉用户，我们需要这个权限来干嘛？其次再去申请，如果是被拒绝，需要提示用户要不要重试，如果是被永久拒绝了，神仙也没办法了，
但是我们需要做一步去引导用户把我们需要申请的权限开启

XPermissions接入只需3步

1、请求我们需要申请的权限
     
     XPermissions.requestPermissions(MainActivity.this,203,new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.CAMERA});

2、需要写个方法来接收回调
    
     @XPermissionInject(requestCode=203)
     public void test(int result){
        Logger.log("result:"+result);

        switch (result){

            //权限申请成功
            case XPermissions.PERMISSION_SUCCESS:
            break;

            //权限申请失败
            case XPermissions.PERMISSION_FAIL:
                break;

            //跳去设置权限
            case XPermissions.PERMISSION_SETTING:
                break;
        }
    }
  
3、重写activity的onRequestPermissionsResult方法
  
     @TargetApi(23)
     @Override
     public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        XPermissions.handlerPermissionResult(this,requestCode,permissions,grantResults);

    }
  
  注：对于弹框的提示，可以提示更友好的，demo只是一个测试
    
    <string name="calendar">查看日历</string>
    <string name="camera">使用拍照和查看相册</string>
    <string name="contacts">查看联系人</string>
    <string name="location">定位</string>
    <string name="microphone">使用麦克风</string>
    <string name="phone">读取联系人</string>
    <string name="sensors">使用传感器</string>
    <string name="sms">使用短信功能</string>
    <string name="storage">存储</string>
