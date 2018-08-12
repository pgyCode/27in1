package com.github.pgycode.a16.tool.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

public class PermissionAsker {

    private Activity activity;//activity
    private Fragment fragment;//fragment
    private OnAskAppearListener listener;//申请权限时要执行的事件
    private String permission;//权限
    private int permissionCode;//权限申请码，不同权限，不能冲突
    private int activityCode;//设置界面权限申请码，不同权限不能冲突
    private boolean popTip;//是否要弹出提示跳转权限设置界面
    private String askReason;//权限跳转提示语句


    /**
     * 构造一个asker
     * @param activity activity
     * @param listener 申请权限时要执行的事件
     * @param permisstion 权限
     * @param permissionCode 权限申请的requestCode
     * @param activityCode 跳转到设置界面的requestCode
     * @param askReason 是否弹出dialog跳转到权限设置
     * @param popTip 弹出dialog提示语句
     */
    public PermissionAsker(Activity activity, OnAskAppearListener listener, String permisstion, int permissionCode, int activityCode, String askReason, boolean popTip){
        this.activity = activity;
        this.listener = listener;
        this.permission = permisstion;
        this.permissionCode = permissionCode;
        this.activityCode = activityCode;
        this.popTip = popTip;
        this.askReason = askReason;
    }

    /**
     * 构造一个asker
     * @param fragment fragment
     * @param listener 申请权限时要执行的事件
     * @param permisstion 权限
     * @param permissionCode 权限申请的requestCode
     * @param activityCode 跳转到设置界面的requestCode
     * @param askReason 是否弹出dialog跳转到权限设置
     * @param popTip 弹出dialog提示语句
     */
    public PermissionAsker(Fragment fragment, OnAskAppearListener listener, String permisstion, int permissionCode, int activityCode, String askReason, boolean popTip){
        this.activity = fragment.getActivity();
        this.fragment = fragment;
        this.listener = listener;
        this.permission = permisstion;
        this.permissionCode = permissionCode;
        this.activityCode = activityCode;
        this.popTip = popTip;
        this.askReason = askReason;
    }


    /**
     * 请求时
     */
    public void onAsk(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            if (fragment != null)
                fragment.requestPermissions(new String[]{permission},permissionCode);
            else
                activity.requestPermissions(new String[]{permission},permissionCode);
        }
        else{
            listener.onAppear();
        }
    }


    /**
     * 权限申请对方选取是否拒绝后
     * @param requestCode
     * @param grantResults
     */
    public void onChoose(int requestCode, int[] grantResults){
        if (requestCode == permissionCode){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                listener.onAppear();
            }else{
                if (popTip){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(activity).setTitle("权限申请").setMessage(askReason)
                            .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    goToAppSetting();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    dialog.show();
                }
            }
        }
    }


    /**
     * 设置界面返回后
     * @param requestCode
     */
    public void onSet(int requestCode){
        if (requestCode == activityCode &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
            listener.onAppear();
        }
    }


    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, activityCode);
    }
}
