package com.bw.movie.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.bw.movie.MyApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 自定义拦截器
 */
public class CustomIntercept implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        SharedPreferences sharedPreferences = MyApplication.getmContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");
        String sessionId = sharedPreferences.getString("sessionId", "");

        Request.Builder newBuilder = original.newBuilder();
        newBuilder.method(original.method(), original.body());
        if (!TextUtils.isEmpty(userId) && !TextUtils.isEmpty(sessionId)) {
            newBuilder.addHeader("userId", userId);
            newBuilder.addHeader("sessionId", sessionId);
        }
        Request request = newBuilder.build();
        return chain.proceed(request);
    }
}
