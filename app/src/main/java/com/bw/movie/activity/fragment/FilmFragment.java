package com.bw.movie.activity.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

/**
 * @author : FangShiKang
 * @date : 2019/01/25.
 * email : fangshikang@outlook.com
 * desc :   影片  页面
 */
public class FilmFragment extends Fragment implements MyView {

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_film, container, false);
        unbinder = ButterKnife.bind(this, view);
        mMyPresenter = new MyPresenter(this);
        mMyPresenter.onGetDatas(Apis.MOVIE_BANNER_URL, FilmBean.class);
        return view;
    }

    @OnClick({R.id.film_ress, R.id.film_rcf, R.id.film_vpi})
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
        }
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
