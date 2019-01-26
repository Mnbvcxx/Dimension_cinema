package com.bw.movie.activity.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.adapter.CinemaFlowAdapter;
import com.bw.movie.activity.bean.FilmBean;
import com.bw.movie.apis.Apis;
import com.bw.movie.mvc.presenter.MyPresenter;
import com.bw.movie.mvc.view.MyView;
import com.bw.movie.utils.ToastUtil;
import com.lwj.widget.viewpagerindicator.ViewPagerIndicator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

import static android.view.View.VISIBLE;

/**
 * @author : FangShiKang
 * @date : 2019/01/25.
 * email : fangshikang@outlook.com
 * desc :   影片  页面
 */
public class FilmFragment extends Fragment implements MyView, View.OnClickListener {

    @BindView(R.id.film_ress)
    ImageView mFilmRess;
    @BindView(R.id.film_ress_name)
    TextView mFilmRessName;
    @BindView(R.id.film_rcf)
    RecyclerCoverFlow mFilmRcf;
    @BindView(R.id.film_vpi)
    ViewPagerIndicator mFilmVpi;
    private Unbinder unbinder;
    private CinemaFlowAdapter mCinemaFlowAdapter;
    private MyPresenter mMyPresenter;
    private View view;
    @BindView(R.id.film_seach_ima)
     ImageView mFilmSeachIma;
    @BindView(R.id.film_seach_edit)
     EditText mFilmSeachEdit;
   @BindView(R.id.film_seach_text)
     TextView mFilmSeachText;
   @BindView(R.id.film_seach_relative)
     RelativeLayout mFilmSeachRelative;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_film, container, false);
        unbinder = ButterKnife.bind(this, view);
        mMyPresenter = new MyPresenter(this);
        mMyPresenter.onGetDatas(Apis.MOVIE_BANNER_URL, FilmBean.class);
        return view;
    }

    @Override
    @OnClick({R.id.film_ress, R.id.film_rcf, R.id.film_vpi,R.id.film_seach_ima,R.id.film_seach_text})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.film_ress:
                break;
            case R.id.film_rcf:
                break;
            case R.id.film_vpi:
                break;
            case R.id.film_seach_ima:
                initfsi();
                break;
            case R.id.film_seach_text:
                initfst();
                break;
        }
    }



    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    //点击图标拉伸搜索框
    boolean mBoolean=true;
    private void initfsi() {
        if (mBoolean){
            ObjectAnimator translationX = ObjectAnimator.ofFloat(mFilmSeachRelative, "translationX", 0,(dp2px(getActivity(),-170)));
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
            mBoolean=!mBoolean;
        }
    }
    //收缩搜索框
    private void initfst() {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(mFilmSeachRelative, "translationX",(dp2px(getActivity(),-170)),0 );
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mFilmSeachEdit, "alpha", 1.0f,0.5f, 0.0f);
        ObjectAnimator alphaButton = ObjectAnimator.ofFloat(mFilmSeachText, "alpha", 1.0f,0.5f, 0.0f);
        alphaButton.setDuration(1000);
        alphaButton.start();
        alpha.setDuration(1000);
        alpha.start();
        translationX.setDuration(1000);
        translationX.start();
        mBoolean=!mBoolean;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onMySuccess(Object data) {
        if (data instanceof FilmBean) {
            FilmBean filmBean = (FilmBean) data;
            if (filmBean.getStatus().equals("0000")) {
                final List<FilmBean.ResultBean> result = filmBean.getResult();
                mCinemaFlowAdapter = new CinemaFlowAdapter(getActivity(), result);
                mFilmRcf.setAdapter(mCinemaFlowAdapter);
                mFilmRcf.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
                    @Override
                    public void onItemSelected(int position) {
                        ToastUtil.showToast(result.get(position).getName());
                    }
                });
            }
        }
    }

    @Override
    public void onMyFailed(String error) {

    }


}
