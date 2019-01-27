package com.bw.movie.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.activity.FilmDetailsActivity;
import com.bw.movie.activity.bean.FilmCinemaxBean;
import com.bw.movie.activity.bean.SeachBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/26.
 * email : fangshikang@outlook.com
 * desc :      搜索   适配器
 */
public class MyMovieSeachAdapter extends RecyclerView.Adapter<MyMovieSeachAdapter.MyMovieSeachViewHolder> {
    private Context mContext;
    private List<SeachBean.ResultBean> mResultBeans = new ArrayList<>();

    public MyMovieSeachAdapter(Context context, List<SeachBean.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    @NonNull
    @Override
    public MyMovieSeachViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recommended_item_view, viewGroup, false);
        return new MyMovieSeachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyMovieSeachViewHolder myMovieSeachViewHolder, int i) {
        String logo = mResultBeans.get(i).getLogo();
        Uri uri = Uri.parse(logo);
        myMovieSeachViewHolder.mSimpleDraweeView.setImageURI(uri);
        myMovieSeachViewHolder.mTextViewName.setText(mResultBeans.get(i).getName());
        myMovieSeachViewHolder.mTextViewDesc.setText(mResultBeans.get(i).getAddress());
        myMovieSeachViewHolder.mTextViewKm.setText(mResultBeans.get(i).getDistance() + "km");

    }

    @Override
    public int getItemCount() {
        return mResultBeans == null ? 0 : mResultBeans.size();
    }

    class MyMovieSeachViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewName, mTextViewDesc, mTextViewKm;
        ImageView mImageView;

        public MyMovieSeachViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.recommended_img);
            mTextViewName = itemView.findViewById(R.id.recommended_name);
            mTextViewDesc = itemView.findViewById(R.id.recommended_desc);
            mTextViewKm = itemView.findViewById(R.id.recommended_km);
            mImageView = itemView.findViewById(R.id.recommended_concern);
        }
    }
}
