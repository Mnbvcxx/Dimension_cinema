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
                return;
                //mMyView.onMyFailed("阿欧，出错啦"+error);
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
    public void onPostFormBodyDatas(String url, Map<String, String> map,Class clazz) {
        mMyModel.onPostFormData(url,map, clazz, new MyCallback() {
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
     * 普通post请求
     *
     * @param url
     * @param clazz
     * @param map
     */
    public void onPostDatas(String url, Map<String, String> map, Class clazz) {
        mMyModel.onPost(url, map,clazz,  new MyCallback() {
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


    public void onPostImage(String path, Map<String, String> map, Class clazz) {
        mMyModel.requestImage(path, map, clazz, new MyCallback() {
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





    //解绑
    public void onDecathed() {
        if (mMyModel != null) {
            mMyModel = null;
        }
        if (mMyView != null) {
            mMyView = null;
        }
    }
}
