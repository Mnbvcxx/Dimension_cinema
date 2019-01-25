package com.bw.movie.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.activity.adapter.TabFragmentPagerAdapter;
import com.bw.movie.activity.fragment.FilmFragment;
import com.bw.movie.activity.fragment.MovieFragment;
import com.bw.movie.activity.fragment.MyFragment;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.register.activity.RegisterActivity;
import com.bw.movie.utils.IntentUtils;

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
        //初始化显示第一个页面
        mMainVp.setCurrentItem(0);
    }

    //初始化数据
    @Override
    protected void initData() {
        //创建一个集合,用来添加我们所需要的fragment
        mFragments = new ArrayList<>();
        mFragments.add(new FilmFragment());
        mFragments.add(new MovieFragment());
        mFragments.add(new MyFragment());
        //mMainVp.addOnPageChangeListener(new MyPagerChangeListener());//滑动监听事件
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
                mMainCinema.setImageResource(R.mipmap.com_icon_cinema_default);
                mMainMy.setImageResource(R.mipmap.my_default);
                break;
            case R.id.main_cinema:
                mMainVp.setCurrentItem(1);
                mMainFilm.setImageResource(R.mipmap.com_icon_film_fault);
                mMainCinema.setImageResource(R.mipmap.com_icon_cinema_selected);
                mMainMy.setImageResource(R.mipmap.my_default);
                break;
            case R.id.main_my:
                mMainVp.setCurrentItem(2);
                mMainFilm.setImageResource(R.mipmap.com_icon_film_fault);
                mMainCinema.setImageResource(R.mipmap.com_icon_cinema_default);
                mMainMy.setImageResource(R.mipmap.com_icon_my_selected);
                break;
        }
    }

    //滑动监听事件
    /*private class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        //滑动切换,改变按钮状态
        @Override
        public void onPageSelected(int i) {
            switch (i){
                default:
                    break;
                case 0:
                    mMainFilm.setImageResource(R.mipmap.com_icon_film_selected);
                    mMainFilm.setMaxWidth(70);
                    mMainFilm.setMaxHeight(70);
                    mMainCinema.setImageResource(R.mipmap.com_icon_cinema_default);
                    mMainMy.setImageResource(R.mipmap.my_default);
                    break;
                case 1:
                    mMainFilm.setImageResource(R.mipmap.com_icon_film_fault);
                    mMainCinema.setImageResource(R.mipmap.com_icon_cinema_selected);
                    mMainCinema.setMaxWidth(70);
                    mMainCinema.setMaxHeight(70);
                    mMainMy.setImageResource(R.mipmap.my_default);
                    break;
                case 2:
                    mMainFilm.setImageResource(R.mipmap.com_icon_film_fault);
                    mMainCinema.setImageResource(R.mipmap.com_icon_cinema_default);
                    mMainMy.setImageResource(R.mipmap.com_icon_my_selected);
                    mMainMy.setMaxWidth(70);
                    mMainMy.setMaxHeight(70);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }*/
}
