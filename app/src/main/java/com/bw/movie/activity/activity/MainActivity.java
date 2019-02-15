package com.bw.movie.activity.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
import butterknife.Unbinder;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.home_film_first)
    RadioButton homeFilmFirst;
    @BindView(R.id.home_film_second)
    RadioButton homeFilmSecond;
    @BindView(R.id.home_film_third)
    RadioButton homeFilmThird;
    @BindView(R.id.main_group)
    RadioGroup mainGroup;

    private ArrayList<Fragment> mList;
    private FragmentManager mManager;
    private FilmFragment mFilmFragment;
    private MovieFragment mMovieFragment;
    private MyFragment mMyFragment;
    private Unbinder unbinder;

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
        unbinder = ButterKnife.bind(this);
        //获取Fragment管理权getSupportFragmentManager
        mManager = getSupportFragmentManager();
        mList = new ArrayList<>();
        mFilmFragment = new FilmFragment();
        mMovieFragment = new MovieFragment();
        mMyFragment = new MyFragment();
        //添加集合三个
        mList.add(mFilmFragment);
        mList.add(mMovieFragment);
        mList.add(mMyFragment);
        //选择器
        mainGroup.setOnCheckedChangeListener(this);
        mManager.beginTransaction()
                .add(R.id.frame_layout, mFilmFragment)
                .add(R.id.frame_layout, mMovieFragment)
                .add(R.id.frame_layout, mMyFragment)
                .hide(mMovieFragment)
                .hide(mMyFragment)
                .commit();
        firstToviewanimatorx = ObjectAnimator.ofFloat(homeFilmFirst, "scaleX", 1f, 1.12f);
        firstToviewanimatory = ObjectAnimator.ofFloat(homeFilmFirst, "scaleY", 1f, 1.12f);
        firstToviewanimatorx.start();
        firstToviewanimatory.start();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void netSuccess(Object object) {

    }

    @Override
    protected void netFailed(String s) {

    }
    /*
     * 目前完成了放大至70cm
     * 当点击其他的图片时不能回放到原来的大小
     */
    ObjectAnimator firstToviewanimatorx;
    ObjectAnimator firstToviewanimatory;
    ObjectAnimator toViewAnimator1X;
    ObjectAnimator toViewAnimator1Y;
    ObjectAnimator thirdToviewanimatorx;
    ObjectAnimator thirdToviewanimatory;
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.home_film_first:
                mManager.beginTransaction()
                        .show(mFilmFragment)
                        .hide(mMovieFragment)
                        .hide(mMyFragment)
                        .commit();
                homeFilmFirst.setBackgroundResource(R.mipmap.com_icon_film_selected);
                homeFilmSecond.setBackgroundResource(R.mipmap.com_icon_cinema_default);
                homeFilmThird.setBackgroundResource(R.mipmap.my_default);
                firstToviewanimatorx = ObjectAnimator.ofFloat(homeFilmFirst, "scaleX", 1f, 1.12f);
                firstToviewanimatory = ObjectAnimator.ofFloat(homeFilmFirst, "scaleY", 1f, 1.12f);
                firstToviewanimatorx.start();
                firstToviewanimatory.start();
                //第二个缩放到原来的大小
                toViewAnimator1X = ObjectAnimator.ofFloat(homeFilmSecond, "scaleX", 1f, 1f);
                toViewAnimator1Y = ObjectAnimator.ofFloat(homeFilmSecond, "scaleY", 1f, 1f);
                toViewAnimator1X.start();
                toViewAnimator1Y.start();
                //第三个缩放到原来的大小
                thirdToviewanimatorx = ObjectAnimator.ofFloat(homeFilmThird, "scaleX", 1f, 1f);
                thirdToviewanimatory = ObjectAnimator.ofFloat(homeFilmThird, "scaleY", 1f, 1f);
                thirdToviewanimatorx.start();
                thirdToviewanimatory.start();
                break;
            case R.id.home_film_second:
                mManager.beginTransaction()
                        .show(mMovieFragment)
                        .hide(mFilmFragment)
                        .hide(mMyFragment)
                        .commit();
                toViewAnimator1X = ObjectAnimator.ofFloat(homeFilmSecond, "scaleX", 1f, 1.12f);
                toViewAnimator1Y = ObjectAnimator.ofFloat(homeFilmSecond, "scaleY", 1f, 1.12f);
                toViewAnimator1X.start();
                toViewAnimator1Y.start();
                homeFilmFirst.setBackgroundResource(R.mipmap.com_icon_film_fault);
                homeFilmSecond.setBackgroundResource(R.mipmap.com_icon_cinema_selected);
                homeFilmThird.setBackgroundResource(R.mipmap.my_default);
                //第一个缩放到原来的大小
                firstToviewanimatorx = ObjectAnimator.ofFloat(homeFilmFirst, "scaleX", 1f, 1);
                firstToviewanimatory = ObjectAnimator.ofFloat(homeFilmFirst, "scaleY", 1f, 1);
                firstToviewanimatorx.start();
                firstToviewanimatory.start();
                //第三个缩放到原来的大小
                thirdToviewanimatorx = ObjectAnimator.ofFloat(homeFilmThird, "scaleX", 1f, 1f);
                thirdToviewanimatory = ObjectAnimator.ofFloat(homeFilmThird, "scaleY", 1f, 1f);
                thirdToviewanimatorx.start();
                thirdToviewanimatory.start();
                break;
            case R.id.home_film_third:
                mManager.beginTransaction()
                        .show(mMyFragment)
                        .hide(mFilmFragment)
                        .hide(mMovieFragment)
                        .commit();
                homeFilmFirst.setBackgroundResource(R.mipmap.com_icon_film_fault);
                homeFilmSecond.setBackgroundResource(R.mipmap.com_icon_cinema_default);
                homeFilmThird.setBackgroundResource(R.mipmap.com_icon_my_selected);
                //第一个缩放到原来的大小
                firstToviewanimatorx = ObjectAnimator.ofFloat(homeFilmFirst, "scaleX", 1f, 1);
                firstToviewanimatory = ObjectAnimator.ofFloat(homeFilmFirst, "scaleY", 1f, 1);
                firstToviewanimatorx.start();
                firstToviewanimatory.start();
                //第三个放大
                thirdToviewanimatorx = ObjectAnimator.ofFloat(homeFilmThird, "scaleX", 1f, 1.12f);
                thirdToviewanimatory = ObjectAnimator.ofFloat(homeFilmThird, "scaleY", 1f, 1.12f);
                thirdToviewanimatorx.start();
                thirdToviewanimatory.start();
                //第二个缩放到原来的大小
                toViewAnimator1X = ObjectAnimator.ofFloat(homeFilmSecond, "scaleX", 1f, 1f);
                toViewAnimator1Y = ObjectAnimator.ofFloat(homeFilmSecond, "scaleY", 1f, 1f);
                toViewAnimator1X.start();
                toViewAnimator1Y.start();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
