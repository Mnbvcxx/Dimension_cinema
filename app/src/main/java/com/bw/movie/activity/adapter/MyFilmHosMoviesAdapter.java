package com.bw.movie.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.activity.FilmDetailsActivity;
import com.bw.movie.activity.activity.fragment.adapter.activity.MovieDetailsActivity;
import com.bw.movie.activity.bean.FilmBean;
import com.bw.movie.activity.bean.FilmCinemaxBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/26.
 * email : fangshikang@outlook.com
 * desc :       正在热映   适配器
 */
public class MyFilmHosMoviesAdapter extends RecyclerView.Adapter<MyFilmHosMoviesAdapter.MyFilmHosMoviesViewHolder> {
    private Context mContext;
    private List<FilmBean.ResultBean> mResultBeans = new ArrayList<>();

    public MyFilmHosMoviesAdapter(Context context, List<FilmBean.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    @NonNull
    @Override
    public MyFilmHosMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.hos_movies_item_view, viewGroup, false);
        return new MyFilmHosMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyFilmHosMoviesViewHolder myFilmHosMoviesViewHolder, final int i) {
        String imageUrl = mResultBeans.get(i).getImageUrl();
        Uri uri = Uri.parse(imageUrl);
        myFilmHosMoviesViewHolder.mSimpleDraweeView.setImageURI(uri);
        myFilmHosMoviesViewHolder.mTextViewName.setText(mResultBeans.get(i).getName());
        myFilmHosMoviesViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MovieDetailsActivity.class);
                int id = mResultBeans.get(i).getId();
                intent.putExtra("movieId", id);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResultBeans == null ? 0 : mResultBeans.size();
    }

    class MyFilmHosMoviesViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewName;

        public MyFilmHosMoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.hos_img);
            mTextViewName = itemView.findViewById(R.id.hos_name);
        }
    }
}
