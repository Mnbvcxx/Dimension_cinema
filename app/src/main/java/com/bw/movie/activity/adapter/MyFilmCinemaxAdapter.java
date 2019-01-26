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
import com.bw.movie.activity.bean.FilmCinemaxBean;
import com.bw.movie.utils.IntentUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/26.
 * email : fangshikang@outlook.com
 * desc :       热门电影   适配器
 */
public class MyFilmCinemaxAdapter extends RecyclerView.Adapter<MyFilmCinemaxAdapter.MyFilmCinemaxViewHolder> {
    private Context mContext;
    private List<FilmCinemaxBean.ResultBean> mResultBeans = new ArrayList<>();

    public MyFilmCinemaxAdapter(Context context, List<FilmCinemaxBean.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    @NonNull
    @Override
    public MyFilmCinemaxViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cinemax_item_view, viewGroup, false);
        return new MyFilmCinemaxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyFilmCinemaxViewHolder myFilmCinemaxViewHolder, int i) {
        String imageUrl = mResultBeans.get(i).getImageUrl();
        Uri uri = Uri.parse(imageUrl);
        myFilmCinemaxViewHolder.mSimpleDraweeView.setImageURI(uri);
        myFilmCinemaxViewHolder.mTextViewName.setText(mResultBeans.get(i).getName());
        myFilmCinemaxViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext,FilmDetailsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResultBeans == null ? 0 : mResultBeans.size();
    }

    class MyFilmCinemaxViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewName;

        public MyFilmCinemaxViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.cinemax_img);
            mTextViewName = itemView.findViewById(R.id.cinemax_name);
        }
    }
}
