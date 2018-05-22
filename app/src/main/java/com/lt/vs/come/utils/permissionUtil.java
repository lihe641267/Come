package com.lt.vs.come.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Created by 金色VS时空 on 2017/10/17.
 */

public class permissionUtil {
    public static boolean isGrantExternalRW(Activity activity){
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M
                && activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            activity.requestPermissions(new String[]{
                    //读写权限
                    Manifest.permission.WRITE_EXTERNAL_STORAGE ,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },1);
            return false;
        }
        return true;
    }

}
