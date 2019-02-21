package com.bw.movie.activity.fragment.myactivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.activity.fragment.myactivity.adapter.AttenCinemaAdapter;
import com.bw.movie.activity.fragment.myactivity.adapter.AttenMoveAdapter;
import com.bw.movie.activity.fragment.myactivity.bean.AttenCinemaBean;
import com.bw.movie.activity.fragment.myactivity.bean.AttenMoveBean;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: pengbo
 * @date:2019/1/27 desc:我的关注
 */
public class AttentionActivity extends BaseActivity {

    @BindView(R.id.attention_move)
    Button mAttentionMove;
    @BindView(R.id.attention_cinema)
    Button mAttentionCinema;
    @BindView(R.id.move_recycler)
    RecyclerView mMoveRecycler;
    @BindView(R.id.cinema_recycler)
    RecyclerView mCinemaRecycler;
    @BindView(R.id.attention_request)
    ImageView mAttentionRequest;
    private AttenMoveAdapter mMoveAdapter;
    private AttenCinemaAdapter mCinemaAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_attention;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);


    }

    @Override
    protected void initData() {
        initmove();
    }


    @OnClick({R.id.attention_move, R.id.attention_cinema, R.id.attention_request})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            //电影
            case R.id.attention_move:
                initmove();
                break;
            //影院
            case R.id.attention_cinema:
                initcinema();
                break;
            case R.id.attention_request:
                finish();
                break;
        }
    }

    /**
     * 影院的点击事件
     */
    private void initcinema() {
        //显示隐藏
        mCinemaRecycler.setVisibility(View.VISIBLE);
        mMoveRecycler.setVisibility(View.GONE);
        //电影beijing
        mAttentionMove.setBackgroundResource(R.drawable.movie_selecter);
        //电影文字
        mAttentionMove.setTextColor(Color.parseColor("#333333"));
        //影院文字
        mAttentionCinema.setTextColor(Color.parseColor("#ffffff"));
        //影院背景
        mAttentionCinema.setBackgroundResource(R.drawable.movie_shape_bg_failed);
        //布局
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mCinemaRecycler.setLayoutManager(manager);
        //适配器
        mCinemaAdapter = new AttenCinemaAdapter(this);
        mCinemaRecycler.setAdapter(mCinemaAdapter);
        //网络请求
        doGetData(Apis.ATTEN_CINEMA + "?page=" + 1 + "&count=" + 10, AttenCinemaBean.class);
    }

    /**
     * 电影的点击事件
     */
    private void initmove() {
        mAttentionMove.setTextColor(AttentionActivity.this.getResources().getColor(R.color.colorfff));
        mAttentionMove.setBackgroundResource(R.drawable.movie_shape_bg_failed);
        //影院背景
        mAttentionCinema.setBackgroundResource(R.drawable.movie_selecter);
        mAttentionCinema.setTextColor(AttentionActivity.this.getResources().getColor(R.color.color333));
        mMoveRecycler.setVisibility(View.VISIBLE);
        mCinemaRecycler.setVisibility(View.INVISIBLE);
        //recycler布局
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mMoveRecycler.setLayoutManager(manager);
        //适配器
        mMoveAdapter = new AttenMoveAdapter(this);
        mMoveRecycler.setAdapter(mMoveAdapter);
        //网络请求
        doGetData(Apis.ATTEN_MOVE + "?page=" + 1 + "&count=" + 10, AttenMoveBean.class);
    }


    @Override
    protected void netSuccess(Object object) {
        if (object instanceof AttenMoveBean) {
            AttenMoveBean moveBean = (AttenMoveBean) object;
            if (moveBean.getResult()==null){
                ToastUtil.showToast(moveBean.getMessage());
            }else {
                //适配器
                mMoveAdapter = new AttenMoveAdapter(this);
                mMoveAdapter.setMjihe(moveBean.getResult());
                mMoveRecycler.setAdapter(mMoveAdapter);
            }
        }
        if (object instanceof AttenCinemaBean) {
            AttenCinemaBean cinemaBean = (AttenCinemaBean) object;
            mCinemaAdapter.setMjihe(cinemaBean.getResult());
        }
    }

    @Override
    protected void netFailed(String s) {
        ToastUtil.showToast(s);
    }

}
