package com.bw.movie.base;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.bw.movie.mvc.presenter.MyPresenter;
import com.bw.movie.mvc.view.MyView;

import java.util.Map;

/**
 * activity  基类抽取
 */
public abstract class BaseActivity extends AppCompatActivity implements MyView {
    private MyPresenter mMyPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView(savedInstanceState);
        initData();
        mMyPresenter = new MyPresenter(this);
    }

    //子类向父传递的layout
    protected abstract int getLayoutId();

    protected abstract void initView(Bundle savedInstanceState);

    //初始化
    protected abstract void initData();

    protected abstract void netSuccess(Object object);

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

    //presenter层回调方法
    protected void doNetWorkRequest(String url, Map<String, String> map, Class clazz) {
        if (mMyPresenter != null) {
            mMyPresenter.onPostFormBodyDatas(url, map, clazz);
        }
    }

    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMyPresenter != null) {
            mMyPresenter.onDecathed();
        }
    }
}