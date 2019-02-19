package com.bw.movie.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.login.LoginActivity;

/**
 * @author: pengbo
 * @date:2019/2/18 desc:是否登录的弹框
 */
public class AlertDialogUntil {

    public static void AlertDialog(final Context context){
        AlertDialog alertDialog2 = new AlertDialog.Builder(context)
                .setTitle("未登录")
                .setMessage("您尚未登录，是否现在去登陆?")
                .setIcon(R.mipmap.ic_launcher)
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
                       ToastUtil.showToast("您已选则取消");
                    }
                })
                .create();
        alertDialog2.show();
    }
}
