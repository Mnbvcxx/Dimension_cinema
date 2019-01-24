package com.bw.movie.guideView.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.guideView.adapter.MyHomePageAdapter;
import com.bw.movie.guideView.fragment.FragmentFirst;
import com.bw.movie.guideView.fragment.FragmentFourth;
import com.bw.movie.guideView.fragment.FragmentThe;
import com.bw.movie.guideView.fragment.FragmentThird;
import com.bw.movie.homepage.HomePageActivity;
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
        mFragments = new ArrayList<>();
        mFragments.add(new FragmentFirst());
        mFragments.add(new FragmentThe());
        mFragments.add(new FragmentThird());
        mFragments.add(new FragmentFourth());
        mViewPager.setAdapter(new MyHomePageAdapter(getSupportFragmentManager(), this, mFragments));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (mViewPager.getCurrentItem() == mFragments.size() - 1) {

                    /**
                     * 通过handler进行延时跳转
                     */
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //进行跳转操作
                            IntentUtils.getInstence().intent(GuideViewActivity.this, LoginActivity.class);
                            finish();
                        }
                    }, 1000); //在欢迎界面停留1秒钟

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void netSuccess(Object object) {

    }

    @Override
    protected void netFailed(String s) {

    }
}
