package com.bw.movie.guideView.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.bw.movie.R;
import com.bw.movie.activity.activity.MainActivity;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.guideView.adapter.MyHomePageAdapter;
import com.bw.movie.guideView.fragment.FragmentFirst;
import com.bw.movie.guideView.fragment.FragmentFourth;
import com.bw.movie.guideView.fragment.FragmentThe;
import com.bw.movie.guideView.fragment.FragmentThird;
import com.bw.movie.login.LoginActivity;
import com.bw.movie.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 引导页页面
 */
public class GuideViewActivity extends BaseActivity {

    @BindView(R.id.guide_vp)
    ViewPager mViewPager;
    private List<Fragment> mFragments;
    private SharedPreferences ps;
    private boolean isFirst;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        //设置SharedPreferences 判断 是否第一次登陆
        ps = getSharedPreferences("config", MODE_PRIVATE);
        isFirst = ps.getBoolean("isFirst", false);
        mFragments = new ArrayList<>();
        mFragments.add(new FragmentFirst());
        mFragments.add(new FragmentThe());
        mFragments.add(new FragmentThird());
        mFragments.add(new FragmentFourth());
        mViewPager.setAdapter(new MyHomePageAdapter(getSupportFragmentManager(), this, mFragments));
        /**
         * 如果不是第一次登陆的话 跳过启动页到登录页面
         */
        if (isFirst) {
            IntentUtils.getInstence().intent(this, MainActivity.class);
            finish();
        }

    }

    @Override
    protected void netSuccess(Object object) {

    }

    @Override
    protected void netFailed(String s) {

    }
}
