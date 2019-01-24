package com.bw.movie.homepage;

import android.os.Bundle;
import android.os.Handler;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.guideView.activity.GuideViewActivity;
import com.bw.movie.utils.IntentUtils;


import butterknife.ButterKnife;

public class HomePageActivity extends BaseActivity {

    //布局
    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_page;
    }

    //初始化控件
    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected void initData() {
        /**
         * 通过handler进行延时跳转
         */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //进行跳转操作
                IntentUtils.getInstence().intent(HomePageActivity.this, GuideViewActivity.class);
                finish();
            }
        }, 1000); //在欢迎界面停留1秒钟
    }

    @Override
    protected void netSuccess(Object object) {

    }

    @Override
    protected void netFailed(String s) {

    }
}
