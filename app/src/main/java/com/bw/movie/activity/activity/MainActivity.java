package com.bw.movie.activity.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.activity.adapter.TabFragmentPagerAdapter;
import com.bw.movie.activity.fragment.FilmFragment;
import com.bw.movie.activity.fragment.MovieFragment;
import com.bw.movie.activity.fragment.MyFragment;
import com.bw.movie.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.main_vp)
    ViewPager mMainVp;
    @BindView(R.id.main_film)
    ImageView mMainFilm;
    @BindView(R.id.main_cinema)
    ImageView mMainCinema;
    @BindView(R.id.main_my)
    ImageView mMainMy;
    private List<Fragment> mFragments;
    private TabFragmentPagerAdapter mTabFragmentPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //全屏沉浸式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ButterKnife.bind(this);
        //默认选中第一个,按钮变大
        mMainVp.setCurrentItem(0);
        ViewGroup.LayoutParams mMainFilmparams = mMainFilm.getLayoutParams();
        mMainFilmparams.height=200;
        mMainFilmparams.width =200;
        mMainFilm.setLayoutParams(mMainFilmparams);
    }

    //初始化数据
    @Override
    protected void initData() {
        //创建一个集合,用来添加我们所需要的fragment
        mFragments = new ArrayList<>();
        mFragments.add(new FilmFragment());
        mFragments.add(new MovieFragment());
        mFragments.add(new MyFragment());
        //创建适配器实例
        mTabFragmentPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mMainVp.setAdapter(mTabFragmentPagerAdapter);
    }

    @Override
    protected void netSuccess(Object object) {

    }

    @Override
    protected void netFailed(String s) {

    }

    //点击监听切换事件
    @OnClick({R.id.main_film, R.id.main_cinema, R.id.main_my})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.main_film:
                mMainVp.setCurrentItem(0);
                mMainFilm.setImageResource(R.mipmap.com_icon_film_selected);
                ViewGroup.LayoutParams mMainFilmparams = mMainFilm.getLayoutParams();
                mMainFilmparams.height=200;
                mMainFilmparams.width =200;
                mMainFilm.setLayoutParams(mMainFilmparams);
                mMainCinema.setImageResource(R.mipmap.com_icon_cinema_default);
                ViewGroup.LayoutParams mMainCinemapara = mMainCinema.getLayoutParams();
                mMainCinemapara.height=180;
                mMainCinemapara.width =180;
                mMainCinema.setLayoutParams(mMainCinemapara);
                mMainMy.setImageResource(R.mipmap.my_default);
                ViewGroup.LayoutParams mMainMypara = mMainMy.getLayoutParams();
                mMainMypara.height=180;
                mMainMypara.width =180;
                mMainMy.setLayoutParams(mMainMypara);
                break;
            case R.id.main_cinema:
                mMainVp.setCurrentItem(1);
                mMainFilm.setImageResource(R.mipmap.com_icon_film_fault);
                ViewGroup.LayoutParams param = mMainFilm.getLayoutParams();
                param.height=180;
                param.width =180;
                mMainFilm.setLayoutParams(param);
                mMainCinema.setImageResource(R.mipmap.com_icon_cinema_selected);
                ViewGroup.LayoutParams mMainCinemaparams = mMainCinema.getLayoutParams();
                mMainCinemaparams.height=200;
                mMainCinemaparams.width =200;
                mMainCinema.setLayoutParams(mMainCinemaparams);
                mMainMy.setImageResource(R.mipmap.my_default);
                ViewGroup.LayoutParams mMainMyparam = mMainMy.getLayoutParams();
                mMainMyparam.height=180;
                mMainMyparam.width =180;
                mMainMy.setLayoutParams(mMainMyparam);
                break;
            case R.id.main_my:
                mMainVp.setCurrentItem(2);
                mMainFilm.setImageResource(R.mipmap.com_icon_film_fault);
                ViewGroup.LayoutParams mMainFilmparam = mMainFilm.getLayoutParams();
                mMainFilmparam.height=180;
                mMainFilmparam.width =180;
                mMainFilm.setLayoutParams(mMainFilmparam);
                mMainCinema.setImageResource(R.mipmap.com_icon_cinema_default);
                ViewGroup.LayoutParams mMainCinemaparam = mMainCinema.getLayoutParams();
                mMainCinemaparam.height=180;
                mMainCinemaparam.width =180;
                mMainCinema.setLayoutParams(mMainCinemaparam);
                mMainMy.setImageResource(R.mipmap.com_icon_my_selected);
                ViewGroup.LayoutParams mMainMyparams = mMainMy.getLayoutParams();
                mMainMyparams.height=200;
                mMainMyparams.width =200;
                mMainMy.setLayoutParams(mMainMyparams);
                break;
        }
    }
}
