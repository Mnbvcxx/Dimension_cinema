package com.bw.movie.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bw.movie.activity.activity.MainActivity;
import com.bw.movie.activity.fragment.myactivity.RecordActivity;

import java.util.Map;

/**
 * @author: pengbo
 * @date:2019/2/20 desc:支付宝
 */
public class AlipayUntil {
    private static final int SDK_PAY_FLAG = 1001;
    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // Result result = new Result((String) msg.obj);
//            Toast.makeText(this, "asdfg",
//                    Toast.LENGTH_LONG).show();
            ToastUtil.showToast((String) msg.obj);
        };
    };

    public static void Alipay(final Activity activity, final String orderInfo) {

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);

                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
