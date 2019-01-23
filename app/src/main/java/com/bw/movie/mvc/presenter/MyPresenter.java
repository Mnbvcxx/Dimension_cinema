package com.bw.movie.mvc.presenter;

import com.bw.movie.mvc.MyCallback;
import com.bw.movie.mvc.model.MyModel;
import com.bw.movie.mvc.view.MyView;

import java.util.Map;

/**
 * @author : FangShiKang
 * @date : 2019/01/23.
 * email : fangshikang@outlook.com
 * desc :   Presenter层,  view层和model层之间的桥梁
 */
public class MyPresenter {
    private MyView mMyView;
    private MyModel mMyModel;

    public MyPresenter(MyView myView) {
        mMyView = myView;
        mMyModel = new MyModel();
    }

    /**
     * get数据请求
     *
     * @param url
     * @param clazz
     */
    public void onGetDatas(String url, Class clazz) {
        mMyModel.onGetData(url, clazz, new MyCallback() {
            @Override
            public void onSuccess(Object data) {
                mMyView.onMySuccess(data);
            }

            @Override
            public void onFailed(String error) {
                mMyView.onMyFailed(error);
            }
        });
    }

    /**
     * PostFormBody表单请求数据
     *
     * @param url
     * @param clazz
     * @param map
     */
    public void onPostFormBodyDatas(String url, Class clazz, Map<String, String> map) {
        mMyModel.onPostFormData(url, clazz, map, new MyCallback() {
            @Override
            public void onSuccess(Object data) {
                mMyView.onMySuccess(data);
            }

            @Override
            public void onFailed(String error) {
                mMyView.onMyFailed(error);
            }
        });
    }

    public void onPostDatas(String url, Class clazz, Map<String, String> map) {
        mMyModel.onPost(url, clazz, map, new MyCallback() {
            @Override
            public void onSuccess(Object data) {
                mMyView.onMySuccess(data);
            }

            @Override
            public void onFailed(String error) {
                mMyView.onMyFailed(error);
            }
        });
    }
}
