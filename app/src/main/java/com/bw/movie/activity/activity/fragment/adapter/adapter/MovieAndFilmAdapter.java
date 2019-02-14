package com.bw.movie.activity.activity.fragment.adapter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.activity.fragment.adapter.bean.MovieAndFilmBean;
import com.bw.movie.movie.fragment.cinemaActivity.SeatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/30.
 * email : fangshikang@outlook.com
 * desc :电影排期
 */
public class MovieAndFilmAdapter extends RecyclerView.Adapter<MovieAndFilmAdapter.MovieAndFilmViewHolder> {
    private Context mContext;
    private List<MovieAndFilmBean.ResultBean> mResultBeans = new ArrayList<>();

    public MovieAndFilmAdapter(Context context, List<MovieAndFilmBean.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    @NonNull
    @Override
    public MovieAndFilmViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_and_cinema_item_view, viewGroup, false);
        return new MovieAndFilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAndFilmViewHolder movieAndFilmViewHolder, final int i) {
        movieAndFilmViewHolder.mTextViewT.setText(mResultBeans.get(i).getScreeningHall());
        movieAndFilmViewHolder.mTextViewBegin.setText(mResultBeans.get(i).getBeginTime());
        movieAndFilmViewHolder.mTextViewEnd.setText(mResultBeans.get(i).getEndTime());
        SpannableString spannableString = SeatActivity.changTVsize(mResultBeans.get(i).getPrice() + "");
        movieAndFilmViewHolder.mTextViewPrice.setText(spannableString);
        //点击箭头---跳转到选座页
        movieAndFilmViewHolder.mTextViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SeatActivity.class);
                //传值：厅号，时间，价格
                intent.putExtra("hall",mResultBeans.get(i).getScreeningHall());
                intent.putExtra("begintime",mResultBeans.get(i).getBeginTime());
                intent.putExtra("endtime",mResultBeans.get(i).getEndTime());
                intent.putExtra("price",mResultBeans.get(i).getPrice());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResultBeans == null ? 0 : mResultBeans.size();
    }

    class MovieAndFilmViewHolder extends RecyclerView.ViewHolder{
        TextView mTextViewT,mTextViewBegin,mTextViewEnd,mTextViewPrice;
        ImageView mTextViewBack;
        public MovieAndFilmViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewT = itemView.findViewById(R.id.salon_t);
            mTextViewBegin = itemView.findViewById(R.id.salon_begin);
            mTextViewEnd = itemView.findViewById(R.id.salon_end);
            mTextViewPrice = itemView.findViewById(R.id.salon_price);
            mTextViewBack = itemView.findViewById(R.id.salon_back);
        }
    }
}
