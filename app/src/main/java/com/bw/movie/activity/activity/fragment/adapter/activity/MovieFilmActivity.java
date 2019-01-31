package com.bw.movie.activity.activity.fragment.adapter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.activity.fragment.adapter.adapter.MovieAndFilmAdapter;
import com.bw.movie.activity.activity.fragment.adapter.bean.MovieAndFilmBean;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.utils.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 根据电影ID和影院ID查询电影排期列表
 */
public class MovieFilmActivity extends BaseActivity {

    @BindView(R.id.m_f_ressname)
    TextView mMFRessname;
    @BindView(R.id.m_f_ress)
    TextView mMFRess;
    @BindView(R.id.m_f_img)
    SimpleDraweeView mMFImg;
    @BindView(R.id.m_f_name)
    TextView mMFName;
    @BindView(R.id.m_f_lx)
    TextView mMFLx;
    @BindView(R.id.m_f_dy)
    TextView mMFDy;
    @BindView(R.id.m_f_sc)
    TextView mMFSc;
    @BindView(R.id.m_f_cd)
    TextView mMFCd;
    @BindView(R.id.layout)
    RelativeLayout mLayout;
    @BindView(R.id.m_f_rv)
    RecyclerView mMFRv;
    @BindView(R.id.m_f_back)
    ImageView mMFBack;
    private int mMovieId;
    private String mName;
    private String mImageUrl;
    private String mMovieTypes;
    private String mDirector;
    private String mDuration;
    private String mPlaceOrigin;
    private String mRessName;
    private int mId;
    private String mAddRess;
    private MovieAndFilmAdapter mMovieAndFilmAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_film;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mMovieId = intent.getIntExtra("movieId", 0);
        mId = intent.getIntExtra("Id", 0);
        mRessName = intent.getStringExtra("name");
        mAddRess = intent.getStringExtra("addRess");
        mName = intent.getStringExtra("ressName");
        mImageUrl = intent.getStringExtra("imageUrl");
        mMovieTypes = intent.getStringExtra("movieTypes");
        mDirector = intent.getStringExtra("director");
        mDuration = intent.getStringExtra("duration");
        mPlaceOrigin = intent.getStringExtra("placeOrigin");
        mMFImg.setImageURI(mImageUrl);
        mMFLx.setText("类型：" + mMovieTypes);
        mMFCd.setText("产地：" + mPlaceOrigin);
        mMFDy.setText("导演：" + mDirector);
        mMFSc.setText("时长：" + mDuration);
        mMFName.setText(mRessName);
        mMFRessname.setText(mName);
        mMFRess.setText(mAddRess);
        mMFRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //根据电影ID和影院ID查询电影排期列表
        doGetData(Apis.MOVIESCHEDULELIST_ID_URL + mId + "&movieId=" + mMovieId,MovieAndFilmBean.class);
    }

    @Override
    protected void netSuccess(Object object) {
        if (object instanceof MovieAndFilmBean) {//根据电影ID和影院ID查询电影排期列表
            MovieAndFilmBean movieAndFilmBean = (MovieAndFilmBean) object;
            if (movieAndFilmBean.getStatus().equals("0000")) {
                List<MovieAndFilmBean.ResultBean> result = movieAndFilmBean.getResult();
                //判断电影所在集合长度,为 0 时 ,说明没有数据,进行视图的显示与隐藏
                if (result.size() == 0) {
                    mMFRv.setVisibility(View.GONE);
                    ToastUtil.showToast("该电影暂未排期");
                } else {
                    mMFRv.setVisibility(View.VISIBLE);
                    mMovieAndFilmAdapter = new MovieAndFilmAdapter(this, result);
                    mMFRv.setAdapter(mMovieAndFilmAdapter);
                }
            }
        }
    }

    @Override
    protected void netFailed(String s) {

    }

    @OnClick(R.id.m_f_back)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.m_f_back:
                this.finish();
                break;
        }
    }
}
