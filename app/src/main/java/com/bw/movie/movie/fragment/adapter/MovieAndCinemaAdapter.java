package com.bw.movie.movie.fragment.adapter;

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
import com.bw.movie.movie.bean.MovieAndCinemaBean;
import com.bw.movie.movie.fragment.cinemaActivity.SeatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/27.
 * email : fangshikang@outlook.com
 * desc :影院排期
 */
public class MovieAndCinemaAdapter extends RecyclerView.Adapter<MovieAndCinemaAdapter.MovieAndCinemaViewHolder> {
    private Context mContext;
    private List<MovieAndCinemaBean.ResultBean> mResultBeans = new ArrayList<>();

    public MovieAndCinemaAdapter(Context context, List<MovieAndCinemaBean.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    @NonNull
    @Override
    public MovieAndCinemaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_and_cinema_item_view, viewGroup, false);
        return new MovieAndCinemaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAndCinemaViewHolder movieAndCinemaViewHolder, final int i) {
        movieAndCinemaViewHolder.mTextViewT.setText(mResultBeans.get(i).getScreeningHall());
        movieAndCinemaViewHolder.mTextViewBegin.setText(mResultBeans.get(i).getBeginTime());
        movieAndCinemaViewHolder.mTextViewEnd.setText(mResultBeans.get(i).getEndTime());
        SpannableString spannableString = SeatActivity.changTVsize(mResultBeans.get(i).getPrice() + "");
        movieAndCinemaViewHolder.mTextViewPrice.setText(spannableString);
        //点击箭头---跳转到选座页
        movieAndCinemaViewHolder.mTextViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnChecked != null){
                    mOnChecked.onClicked(i,mResultBeans);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mResultBeans == null ? 0 : mResultBeans.size();
    }

    class MovieAndCinemaViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewT, mTextViewBegin, mTextViewEnd, mTextViewPrice;
        ImageView mTextViewBack;

        public MovieAndCinemaViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewT = itemView.findViewById(R.id.salon_t);
            mTextViewBegin = itemView.findViewById(R.id.salon_begin);
            mTextViewEnd = itemView.findViewById(R.id.salon_end);
            mTextViewPrice = itemView.findViewById(R.id.salon_price);
            mTextViewBack = itemView.findViewById(R.id.salon_back);
        }
    }

    private onChecked mOnChecked;

    public void setOnChecked(onChecked onChecked) {
        mOnChecked = onChecked;
    }

    public interface onChecked{
        void onClicked(int position,List<MovieAndCinemaBean.ResultBean> resultBeans);
    }

}
