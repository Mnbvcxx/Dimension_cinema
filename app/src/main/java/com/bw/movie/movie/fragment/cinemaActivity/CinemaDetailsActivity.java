package com.bw.movie.movie.fragment.cinemaActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.movie.bean.CinemaDetailsBean;
import com.bw.movie.movie.bean.MovieAndCinemaBean;
import com.bw.movie.movie.fragment.adapter.CinemaDetailsAdapter;
import com.bw.movie.movie.fragment.adapter.MovieAndCinemaAdapter;
import com.lwj.widget.viewpagerindicator.ViewPagerIndicator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * 影院旗下所排期电影
 */
public class CinemaDetailsActivity extends BaseActivity {

    @BindView(R.id.cin_rcf)
    RecyclerCoverFlow mCinRcf;
    @BindView(R.id.cin_vpi)
    ViewPagerIndicator mCinVpi;
    @BindView(R.id.cin_rv)
    RecyclerView mCinRv;
    @BindView(R.id.cin_back)
    ImageView mCinBack;
    private CinemaDetailsAdapter mCinemaDetailsAdapter;
    private MovieAndCinemaAdapter mMovieAndCinemaAdapter;
    private int mCinemaId;

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_details);
        ButterKnife.bind(this);

    }
*/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_cinema_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mCinemaId = intent.getIntExtra("cinemaId", 0);
        doGetData(Apis.CINEMA_ID_URL + mCinemaId, CinemaDetailsBean.class);
    }

    @Override
    protected void netSuccess(Object object) {
        if (object instanceof CinemaDetailsBean) {
            CinemaDetailsBean cinemaDetailsBean = (CinemaDetailsBean) object;
            if (cinemaDetailsBean.getStatus().equals("0000")) {
                List<CinemaDetailsBean.ResultBean> result = cinemaDetailsBean.getResult();
                mCinemaDetailsAdapter = new CinemaDetailsAdapter(this, result);
                mCinRcf.setAdapter(mCinemaDetailsAdapter);
                mCinRcf.scrollToPosition(2);
                //轮播图滑动监听事件
                mCinRcf.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
                    @Override
                    public void onItemSelected(int position) {
                        doGetData(Apis.MOVIEANDCINEMA_ID_URL + mCinemaId + "&movieId=" + position, MovieAndCinemaBean.class);
                    }
                });
            }
        } else if (object instanceof MovieAndCinemaBean) {
            MovieAndCinemaBean movieAndCinemaBean = (MovieAndCinemaBean) object;
            if (movieAndCinemaBean.getStatus().equals("0000")) {
                List<MovieAndCinemaBean.ResultBean> result = movieAndCinemaBean.getResult();
                mMovieAndCinemaAdapter = new MovieAndCinemaAdapter(this, result);
                mCinRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                mCinRv.setAdapter(mMovieAndCinemaAdapter);
            }
        }
    }

    @Override
    protected void netFailed(String s) {

    }

    @OnClick(R.id.cin_back)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.cin_back:
                this.finish();
                break;
        }
    }
}
