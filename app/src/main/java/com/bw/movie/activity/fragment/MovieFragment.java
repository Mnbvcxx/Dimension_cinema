package com.bw.movie.activity.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.adapter.MyMovieSeachAdapter;
import com.bw.movie.activity.bean.SeachBean;
import com.bw.movie.apis.Apis;
import com.bw.movie.movie.adapter.MyMovieFragmentAdapter;
import com.bw.movie.movie.fragment.FragmentNearby;
import com.bw.movie.movie.fragment.FragmentRecommended;
import com.bw.movie.mvc.presenter.MyPresenter;
import com.bw.movie.mvc.view.MyView;
import com.bw.movie.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.view.View.VISIBLE;

/**
 * @author : FangShiKang
 * @date : 2019/01/25.
 * email : fangshikang@outlook.com
 * desc :   影院  页面
 */
public class MovieFragment extends Fragment implements MyView {
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
    @BindView(R.id.movie_seach_ima)
    ImageView mFilmmovieIma;
    @BindView(R.id.movie_seach_edit)
    EditText mFilmSeachEdit;
    @BindView(R.id.movie_seach_text)
    TextView mFilmSeachText;
    @BindView(R.id.movie_seach_relative)
    RelativeLayout mFilmSeachRelative;
    @BindView(R.id.movie_seach_rv)
    RecyclerView mFilmSeachRv;
    private Unbinder unbinder;
    private List<Fragment> mFragments;
    private MyMovieFragmentAdapter mMyMovieFragmentAdapter;
    private MyPresenter mMyPresenter;
    private MyMovieSeachAdapter mMyMovieSeachAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        //绑定ButterKnife
        unbinder = ButterKnife.bind(this, view);
        mMyPresenter = new MyPresenter(this);
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
    @OnClick({R.id.movie_ress, R.id.layout_movie_ress, R.id.movie_ress_name,
            R.id.movie_recommended, R.id.movie_nearby, R.id.movie_rg
            , R.id.movie_seach_ima, R.id.movie_seach_text})
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
                mFilmSeachRv.setVisibility(View.GONE);
                mMovieVp.setVisibility(View.VISIBLE);
                break;
            case R.id.movie_nearby:
                mMovieVp.setCurrentItem(1);
                mFilmSeachRv.setVisibility(View.GONE);
                mMovieVp.setVisibility(View.VISIBLE);
                break;
            case R.id.movie_rg:
                break;
            case R.id.movie_seach_ima:
                initfsi();
                break;
            case R.id.movie_seach_text://根据关键字查询电影院
                initfst();
                String movieName = mFilmSeachEdit.getText().toString().trim();
                if (TextUtils.isEmpty(movieName)){
                    ToastUtil.showToast("不能为空");
                }else {
                    mMyPresenter.onGetDatas(Apis.SEACH_NAME_URL,SeachBean.class);
                }
                break;

        }
    }


    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    //点击图标拉伸搜索框
    boolean mBoolean = true;
    private void initfsi() {
        if (mBoolean) {
            ObjectAnimator translationX = ObjectAnimator.ofFloat(mFilmSeachRelative, "translationX", 0, (dp2px(getActivity(), -170)));
            ObjectAnimator alpha = ObjectAnimator.ofFloat(mFilmSeachEdit, "alpha", 0.0f, 1.0f);
            ObjectAnimator alphaButton = ObjectAnimator.ofFloat(mFilmSeachText, "alpha", 0.0f, 1.0f);
            alphaButton.setDuration(1000);
            mFilmSeachText.setVisibility(VISIBLE);
            alphaButton.start();
            alpha.setDuration(1000);
            mFilmSeachEdit.setVisibility(VISIBLE);
            alpha.start();
            //动画时间
            translationX.setDuration(1000);
            translationX.start();
            mBoolean = !mBoolean;
        }
    }

    //收缩搜索框
    private void initfst() {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(mFilmSeachRelative, "translationX", (dp2px(getActivity(), -170)), 0);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mFilmSeachEdit, "alpha", 1.0f, 0.5f, 0.0f);
        ObjectAnimator alphaButton = ObjectAnimator.ofFloat(mFilmSeachText, "alpha", 1.0f, 0.5f, 0.0f);
        alphaButton.setDuration(1000);
        alphaButton.start();
        alpha.setDuration(1000);
        alpha.start();
        translationX.setDuration(1000);
        translationX.start();
        mBoolean = !mBoolean;
    }


    //解绑
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //ButterKnife解绑
        unbinder.unbind();
    }

    @Override
    public void onMySuccess(Object data) {
        if (data instanceof SeachBean){
            SeachBean seachBean = (SeachBean) data;
            if (seachBean.getStatus().equals("0000")){
                List<SeachBean.ResultBean> result = seachBean.getResult();
                ToastUtil.showToast(seachBean.getMessage());
                mMovieVp.setVisibility(View.GONE);
                mFilmSeachRv.setVisibility(VISIBLE);
                mMyMovieSeachAdapter = new MyMovieSeachAdapter(getActivity(), result);
                mFilmSeachRv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
                mFilmSeachRv.setAdapter(mMyMovieSeachAdapter);
            }
        }
    }

    @Override
    public void onMyFailed(String error) {

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
