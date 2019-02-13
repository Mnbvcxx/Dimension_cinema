package com.bw.movie.activity.activity.fragment.adapter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.activity.fragment.adapter.adapter.MovieTicketAdapter;
import com.bw.movie.activity.activity.fragment.adapter.bean.MovieIdBean;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieTicketActivity extends BaseActivity {

    @BindView(R.id.tick_name)
    TextView mTickName;
    @BindView(R.id.xz)
    TextView mXz;
    @BindView(R.id.tick_rv)
    RecyclerView mTickRv;
    @BindView(R.id.tick_back)
    ImageView mTickBack;
    private MovieTicketAdapter mMovieTicketAdapter;
    private String mName;
    private int mMovieId;
    private String mImageUrl;
    private String mMovieTypes;
    private String mDirector;
    private String mDuration;
    private String mPlaceOrigin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_ticket;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mMovieId = intent.getIntExtra("movieId", 0);
        mName = intent.getStringExtra("name");
        mImageUrl = intent.getStringExtra("imageUrl");
        mMovieTypes = intent.getStringExtra("movieTypes");
        mDirector = intent.getStringExtra("director");
        mDuration = intent.getStringExtra("duration");
        mPlaceOrigin = intent.getStringExtra("placeOrigin");
        mTickName.setText(mName);
        doGetData(Apis.MOVIE_ID_URL + mMovieId, MovieIdBean.class);
    }

    @Override
    protected void netSuccess(Object object) {
        if (object instanceof MovieIdBean) {
            MovieIdBean movieIdBean = (MovieIdBean) object;
            if (movieIdBean.getStatus().equals("0000")) {
                final List<MovieIdBean.ResultBean> result = movieIdBean.getResult();
                mTickRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                mMovieTicketAdapter = new MovieTicketAdapter(this, result);
                mTickRv.setAdapter(mMovieTicketAdapter);
                mMovieTicketAdapter.setOnClickedListener(new MovieTicketAdapter.onClickedListener() {
                    @Override
                    public void onClicked(int position) {
                        String name = result.get(position).getName();
                        String address = result.get(position).getAddress();
                        int id = result.get(position).getId();
                        Intent intent = new Intent(MovieTicketActivity.this, MovieFilmActivity.class);
                        intent.putExtra("ressName",name);
                        intent.putExtra("addRess",address);
                        intent.putExtra("Id",id);
                        intent.putExtra("movieId",mMovieId);
                        intent.putExtra("name",mName);
                        intent.putExtra("imageUrl",mImageUrl);
                        intent.putExtra("movieTypes",mMovieTypes);
                        intent.putExtra("director",mDirector);
                        intent.putExtra("duration",mDuration);
                        intent.putExtra("placeOrigin",mPlaceOrigin);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    protected void netFailed(String s) {

    }

    @OnClick(R.id.tick_back)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tick_back:
                this.finish();
                break;
        }
    }
}
