package com.bw.movie.activity.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.activity.FilmDetailsActivity;
import com.bw.movie.activity.adapter.CinemaFlowAdapter;
import com.bw.movie.activity.adapter.MyFilmCinemaxAdapter;
import com.bw.movie.activity.adapter.MyFilmComingSoonAdapter;
import com.bw.movie.activity.adapter.MyFilmHosMoviesAdapter;
import com.bw.movie.activity.bean.FilmBean;
import com.bw.movie.activity.bean.FilmCinemaxBean;
import com.bw.movie.activity.bean.FilmComingSoonBean;
import com.bw.movie.apis.Apis;
import com.bw.movie.mvc.presenter.MyPresenter;
import com.bw.movie.mvc.view.MyView;
import com.bw.movie.utils.IntentUtils;
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
    @BindView(R.id.layout_ress)
    RelativeLayout mLayoutRess;
    @BindView(R.id.rm)
    TextView mRm;
    @BindView(R.id.film_rmdy)
    ImageView mFilmRmdy;
    @BindView(R.id.layout_rm)
    RelativeLayout mLayoutRm;
    @BindView(R.id.film_rm_rv)
    RecyclerView mFilmRmRv;
    @BindView(R.id.layout_remen)
    RelativeLayout mLayoutRemen;
    @BindView(R.id.zz)
    TextView mZz;
    @BindView(R.id.film_zzry)
    ImageView mFilmZzry;
    @BindView(R.id.layout_zz)
    RelativeLayout mLayoutZz;
    @BindView(R.id.film_zz_rv)
    RecyclerView mFilmZzRv;
    @BindView(R.id.layout_zhenzai)
    RelativeLayout mLayoutZhenzai;
    @BindView(R.id.jij)
    TextView mJij;
    @BindView(R.id.film_jjsy)
    ImageView mFilmJjsy;
    @BindView(R.id.layout_jij)
    RelativeLayout mLayoutJij;
    @BindView(R.id.film_jij_rv)
    RecyclerView mFilmJijRv;
    @BindView(R.id.layout_jijiang)
    RelativeLayout mLayoutJijiang;
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
    private MyFilmCinemaxAdapter mMyFilmCinemaxAdapter;
    private MyFilmComingSoonAdapter mMyFilmComingSoonAdapter;
    private MyFilmHosMoviesAdapter mMyFilmHosMoviesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_film, container, false);
        unbinder = ButterKnife.bind(this, view);
        mMyPresenter = new MyPresenter(this);
        mMyPresenter.onGetDatas(Apis.MOVIE_BANNER_URL, FilmBean.class);
        mMyPresenter.onGetDatas(Apis.MOVIE_RM_URL, FilmCinemaxBean.class);
        mMyPresenter.onGetDatas(Apis.MOVIE_COMINGSOON_URL, FilmComingSoonBean.class);
        mFilmRmRv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        mFilmZzRv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        mFilmJijRv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        return view;
    }

    @Override
    @OnClick({R.id.film_ress, R.id.film_rcf, R.id.film_vpi, R.id.film_seach_ima,
            R.id.film_seach_text,R.id.film_rmdy,R.id.film_zzry,R.id.film_jjsy})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.film_ress:
                break;
            case R.id.film_rmdy:
                IntentUtils.getInstence().intent(getContext(),FilmDetailsActivity.class);
                break;
            case R.id.film_zzry:
                IntentUtils.getInstence().intent(getContext(),FilmDetailsActivity.class);
                break;
            case R.id.film_jjsy:
                IntentUtils.getInstence().intent(getContext(),FilmDetailsActivity.class);
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
                mMyFilmHosMoviesAdapter = new MyFilmHosMoviesAdapter(getActivity(), result);
                mFilmZzRv.setAdapter(mMyFilmHosMoviesAdapter);
                mFilmRcf.setAdapter(mCinemaFlowAdapter);
                mFilmRcf.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
                    @Override
                    public void onItemSelected(int position) {
                        ToastUtil.showToast(result.get(position).getName());
                    }
                });
            }
        } else if (data instanceof FilmCinemaxBean) {
            FilmCinemaxBean filmCinemaxBean = (FilmCinemaxBean) data;
            if (filmCinemaxBean.getStatus().equals("0000")) {
                List<FilmCinemaxBean.ResultBean> result = filmCinemaxBean.getResult();
                mMyFilmCinemaxAdapter = new MyFilmCinemaxAdapter(getActivity(), result);
                mFilmRmRv.setAdapter(mMyFilmCinemaxAdapter);
            }
        }else if (data instanceof FilmComingSoonBean) {
            FilmComingSoonBean filmComingSoonBean = (FilmComingSoonBean) data;
            if (filmComingSoonBean.getStatus().equals("0000")) {
                List<FilmComingSoonBean.ResultBean> result = filmComingSoonBean.getResult();
                mMyFilmComingSoonAdapter = new MyFilmComingSoonAdapter(getActivity(), result);
                mFilmJijRv.setAdapter(mMyFilmComingSoonAdapter);
            }
        }
    }

    @Override
    public void onMyFailed(String error) {

    }
}
