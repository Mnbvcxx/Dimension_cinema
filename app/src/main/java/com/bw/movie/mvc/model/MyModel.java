package com.bw.movie.mvc.model;

import com.bw.movie.mvc.MyCallback;
import com.bw.movie.netWork.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.RequestBody;

/**
 * @author : FangShiKang
 * @date : 2019/01/23.
 * email : fangshikang@outlook.com
 * desc :   Model 层
 */
public class MyModel {

    /**
     * get 请求方式
     *
     * @param url
     * @param clazz
     * @param callback
     */
    public void onGetData(String url, final Class clazz, final MyCallback callback) {
        RetrofitManager.getInstance().get(url, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    Object o = new Gson().fromJson(data, clazz);
                    if (callback != null) {
                        callback.onSuccess(o);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onFailed(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailed(String error) {
                if (callback!=null){
                    callback.onFailed(error);
                }
            }
        });
    }

    /**
     * postFormBody  表单请求
     * @param url
     * @param clazz
     * @param map
     * @param callback
     */
    public void onPostFormData(String url,Map<String,String> map, final Class clazz,  final MyCallback callback){
        Map<String, RequestBody> bodyMap = RetrofitManager.getInstance().generateRequestBody(map);
        RetrofitManager.getInstance().postFormBody(url, bodyMap, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    Object o = new Gson().fromJson(data, clazz);
                    if (callback != null) {
                        callback.onSuccess(o);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onFailed(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailed(String error) {
                if (callback!=null){
                    callback.onFailed(error);
                }
            }
        });
    }

    /**
     * 普通post 请求
     * @param url
     * @param clazz
     * @param map
     * @param callback
     */
    public void onPost(String url, final Class clazz, Map<String,String> map, final MyCallback callback){
        RetrofitManager.getInstance().post(url, map, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    Object o = new Gson().fromJson(data, clazz);
                    if (callback != null) {
                        callback.onSuccess(o);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onFailed(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailed(String error) {
                if (callback!=null){
                    callback.onFailed(error);
                }
            }
        });
    }

}
