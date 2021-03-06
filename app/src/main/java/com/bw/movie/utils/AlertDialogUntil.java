package com.bw.movie.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.activity.MainActivity;
import com.bw.movie.login.LoginActivity;

/**
 * @author: pengbo
 * @date:2019/2/18 desc:是否登录的弹框
 */
public class AlertDialogUntil {

    private static AlertDialog alertDialogLogin;
    private static AlertDialog alertDialogMy;

    public static void AlertDialogLogin(final Context context){
        //添加"Yes"按钮
//添加取消
        alertDialogLogin = new AlertDialog.Builder(context)
                .setTitle("请选择")
                .setMessage("您尚未登录，是否现在去登陆?")
                .setIcon(R.mipmap.icon)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);

                    }
                })

                .setNegativeButton("游客", new DialogInterface.OnClickListener() {//添加取消
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       ToastUtil.showToast("您已以游客方式进入");
                        Intent intent = new Intent(context, MainActivity.class );
                        context.startActivity(intent);
                    }
                })
                .create();
        alertDialogLogin.show();
    }

    public static void DiaLoginDimess(){
        alertDialogLogin.dismiss();
    }

    public static void AlertDialogMy(final Context context){
        //添加"Yes"按钮
//添加取消
        alertDialogMy = new AlertDialog.Builder(context)
                .setTitle("请选择")
                .setMessage("您尚未登录，是否现在去登陆?")
                .setIcon(R.mipmap.icon)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);

                    }
                })

                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtil.showToast("您已选择取消");
                        alertDialogMy.dismiss();
                    }
                })
                .create();
        alertDialogMy.show();
    }
    public static void DiaLoginMy(){

    }
}
