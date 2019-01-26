package com.bw.movie.activity.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.bean.FilmBean;
import com.bw.movie.activity.bean.FilmComingSoonBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/26.
 * email : fangshikang@outlook.com
 * desc :       正在热映   适配器
 */
public class MyFilmComingSoonAdapter extends RecyclerView.Adapter<MyFilmComingSoonAdapter.MyFilmComingSoonViewHolder> {
    private Context mContext;
    private List<FilmComingSoonBean.ResultBean> mResultBeans = new ArrayList<>();

    public MyFilmComingSoonAdapter(Context context, List<FilmComingSoonBean.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    @NonNull
    @Override
    public MyFilmComingSoonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.coming_soon_item_view, viewGroup, false);
        return new MyFilmComingSoonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyFilmComingSoonViewHolder myFilmCinemaxViewHolder, int i) {
        String imageUrl = mResultBeans.get(i).getImageUrl();
        Uri uri = Uri.parse(imageUrl);
        myFilmCinemaxViewHolder.mSimpleDraweeView.setImageURI(uri);
        myFilmCinemaxViewHolder.mTextViewName.setText(mResultBeans.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mResultBeans == null ? 0 : mResultBeans.size();
    }

    class MyFilmComingSoonViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewName;

        public MyFilmComingSoonViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.soon_img);
            mTextViewName = itemView.findViewById(R.id.soon_name);
        }
    }
}
