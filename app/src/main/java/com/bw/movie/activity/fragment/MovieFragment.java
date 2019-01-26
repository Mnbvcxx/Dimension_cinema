package com.bw.movie.activity.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.movie.adapter.MyMovieFragmentAdapter;
import com.bw.movie.movie.fragment.FragmentNearby;
import com.bw.movie.movie.fragment.FragmentRecommended;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author : FangShiKang
 * @date : 2019/01/25.
 * email : fangshikang@outlook.com
 * desc :   影院  页面
 */
public class MovieFragment extends Fragment {
    @BindView(R.id.movie_ress)
    ImageView mMovieRess;
    @BindView(R.id.layout_movie_ress)
    RelativeLayout mLayoutMovieRess;
    @BindView(R.id.movie_ress_name)
    TextView mMovieRessName;
    @BindView(R.id.movie_recommended)
    RadioButton mMovieRecommended;
    @BindView(R.id.movie_nearby)
    RadioButton mMovieNearby;
    @BindView(R.id.movie_rg)
    RadioGroup mMovieRg;
    @BindView(R.id.movie_vp)
    ViewPager mMovieVp;
    private View view;
    private Unbinder unbinder;
    private List<Fragment> mFragments;
    private MyMovieFragmentAdapter mMyMovieFragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        //绑定ButterKnife
        unbinder = ButterKnife.bind(this, view);
        //初始化fragment
        initFragmentDatas();
        return view;
    }

    //初始化fragment
    private void initFragmentDatas() {
        mFragments = new ArrayList<>();
        mFragments.add(new FragmentRecommended());
        mFragments.add(new FragmentNearby());
        mMovieVp.addOnPageChangeListener(new MyPagerChangeListener());
        mMyMovieFragmentAdapter = new MyMovieFragmentAdapter(getChildFragmentManager(), getActivity(), mFragments);
        mMovieVp.setAdapter(mMyMovieFragmentAdapter);
        //第一次点击进入,默认选择推荐影院
        mMovieVp.setCurrentItem(0);
    }


    //点击事件
    @OnClick({R.id.movie_ress, R.id.layout_movie_ress, R.id.movie_ress_name, R.id.movie_recommended, R.id.movie_nearby, R.id.movie_rg})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.movie_ress://地址图标
                break;
            case R.id.layout_movie_ress://地址图标点击事件
                break;
            case R.id.movie_ress_name://地址名称
                break;
            case R.id.movie_recommended:
                mMovieVp.setCurrentItem(0);
                break;
            case R.id.movie_nearby:
                mMovieVp.setCurrentItem(1);
                break;
            case R.id.movie_rg:
                break;
        }
    }


    //解绑
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //ButterKnife解绑
        unbinder.unbind();
    }

    /**
     * 设置一个ViewPager的侦听事件，当左右滑动ViewPager时菜单栏被选中状态跟着改变
     */
    private class MyPagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            switch (i) {
                case 0:
                    mMovieRecommended.setChecked(true);
                    mMovieNearby.setChecked(false);
                    mMovieRecommended.setTextColor(getActivity().getResources().getColor(R.color.colorfff));
                    mMovieNearby.setTextColor(getActivity().getResources().getColor(R.color.color333));
                    break;
                case 1:
                    mMovieRecommended.setChecked(false);
                    mMovieNearby.setChecked(true);
                    mMovieRecommended.setTextColor(getActivity().getResources().getColor(R.color.color333));
                    mMovieNearby.setTextColor(getActivity().getResources().getColor(R.color.colorfff));
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
