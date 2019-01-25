package com.bw.movie.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.mvc.presenter.MyPresenter;
import com.bw.movie.mvc.view.MyView;

import java.util.Map;

/**
 * fragment基类
 */
public abstract class BaseFragment extends Fragment implements MyView {

    private MyPresenter mMyPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = View.inflate(getActivity(), getLayoutId(), null);
        initView(view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        mMyPresenter = new MyPresenter(this);
    }


    //初始化控件
    protected abstract void initView(View view);


    //子Fragment实现的获取Layout
    public abstract int getLayoutId();

    //初始化数据
    protected abstract void initData();

    //成功
    protected abstract void netSuccess(Object object);

    //失败
    protected abstract void netFailed(String s);

    //成功回调方法
    @Override
    public void onMySuccess(Object data) {
        netSuccess(data);
    }

    //失败回调方法
    @Override
    public void onMyFailed(String error) {
        netFailed(error);
    }


    /**
     * presenter层回调方法PostFormBody表单请求
     *
     * @param url
     * @param map
     * @param clazz
     */
    protected void doPostFormBodyDatas(String url, Map<String, String> map, Class clazz) {
        if (mMyPresenter != null) {
            mMyPresenter.onPostFormBodyDatas(url, map, clazz);
        }
    }

    /**
     * presenter层回调方法Post请求
     *
     * @param url
     * @param map
     * @param clazz
     */
    protected void doPost(String url, Map<String, String> map, Class clazz) {
        if (mMyPresenter != null) {
            mMyPresenter.onPostDatas(url, map, clazz);
        }
    }

    /**
     * presenter层回调方法Get请求
     *
     * @param url
     * @param clazz
     */
    protected void doGetData(String url, Class clazz) {
        if (mMyPresenter != null) {
            mMyPresenter.onGetDatas(url, clazz);
        }
    }


    //解绑
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMyPresenter.onDecathed();
    }
}
