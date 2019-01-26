package com.bw.movie.activity.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.activity.fragment.DetailsRmFragment;
import com.bw.movie.activity.activity.fragment.DetailsRyFragment;
import com.bw.movie.activity.activity.fragment.DetailsSoonFragment;
import com.bw.movie.activity.adapter.MyDetailsAdapter;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 影片详情  页面
 */
public class FilmDetailsActivity extends BaseActivity {

    @BindView(R.id.detail_ress)
    ImageView mDetailRess;
    @BindView(R.id.layout_detail_ress)
    RelativeLayout mLayoutDetailRess;
    @BindView(R.id.detail_ress_name)
    TextView mDetailRessName;
    @BindView(R.id.detail_rm_movie)
    RadioButton mDetailRmMovie;
    @BindView(R.id.detail_zz_ry)
    RadioButton mDetailZzRy;
    @BindView(R.id.detail_soon)
    RadioButton mDetailSoon;
    @BindView(R.id.detail_rg)
    RadioGroup mDetailRg;
    @BindView(R.id.detail_vp)
    ViewPager mDetailVp;
    private List<Fragment> mFragments;
    private MyDetailsAdapter mMyDetailsAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_film_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        //初始化fragment
        initFragmentDatas();
    }

    private void initFragmentDatas() {
        mFragments = new ArrayList<>();
        mFragments.add(new DetailsRmFragment());
        mFragments.add(new DetailsRyFragment());
        mFragments.add(new DetailsSoonFragment());
        mDetailVp.addOnPageChangeListener(new MyPagersChangeListener());
        mMyDetailsAdapter = new MyDetailsAdapter(getSupportFragmentManager(), FilmDetailsActivity.this, mFragments);
        mDetailVp.setAdapter(mMyDetailsAdapter);
        //第一次点击进入,默认选择推荐影院
        mDetailVp.setCurrentItem(0);

    }

    @Override
    protected void netSuccess(Object object) {

    }

    @Override
    protected void netFailed(String s) {

    }

    //点击监听事件
    @OnClick({R.id.layout_detail_ress, R.id.detail_ress_name, R.id.detail_rm_movie, R.id.detail_zz_ry, R.id.detail_soon, R.id.detail_rg, R.id.detail_vp})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_detail_ress:
                break;
            case R.id.detail_ress_name:
                break;
            case R.id.detail_rm_movie:
                mDetailVp.setCurrentItem(0);
                break;
            case R.id.detail_zz_ry:
                mDetailVp.setCurrentItem(1);
                break;
            case R.id.detail_soon:
                mDetailVp.setCurrentItem(2);
                break;
            case R.id.detail_rg:
                break;
            case R.id.detail_vp:
                break;
            default:
                break;
        }
    }

    /**
     * 设置一个ViewPager的侦听事件，当左右滑动ViewPager时菜单栏被选中状态跟着改变
     */
    private class MyPagersChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            switch (i) {
                case 0:
                    mDetailRmMovie.setChecked(true);
                    mDetailZzRy.setChecked(false);
                    mDetailSoon.setChecked(false);
                    mDetailRmMovie.setTextColor(FilmDetailsActivity.this.getResources().getColor(R.color.colorfff));
                    mDetailZzRy.setTextColor(FilmDetailsActivity.this.getResources().getColor(R.color.color333));
                    mDetailSoon.setTextColor(FilmDetailsActivity.this.getResources().getColor(R.color.color333));
                    break;
                case 1:
                    mDetailRmMovie.setChecked(false);
                    mDetailZzRy.setChecked(true);
                    mDetailSoon.setChecked(false);
                    mDetailRmMovie.setTextColor(FilmDetailsActivity.this.getResources().getColor(R.color.color333));
                    mDetailZzRy.setTextColor(FilmDetailsActivity.this.getResources().getColor(R.color.colorfff));
                    mDetailSoon.setTextColor(FilmDetailsActivity.this.getResources().getColor(R.color.color333));
                    break;
                case 2:
                    mDetailRmMovie.setChecked(false);
                    mDetailZzRy.setChecked(false);
                    mDetailSoon.setChecked(true);
                    mDetailRmMovie.setTextColor(FilmDetailsActivity.this.getResources().getColor(R.color.color333));
                    mDetailZzRy.setTextColor(FilmDetailsActivity.this.getResources().getColor(R.color.color333));
                    mDetailSoon.setTextColor(FilmDetailsActivity.this.getResources().getColor(R.color.colorfff));
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

}
