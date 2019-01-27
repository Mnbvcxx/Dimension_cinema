package com.bw.movie.movie.fragment.adapter;

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
import com.bw.movie.activity.bean.FilmBean;
import com.bw.movie.movie.bean.CinemaDetailsBean;
import com.bw.movie.utils.DateUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/25.
 * email : fangshikang@outlook.com
 * desc :
 */
public class CinemaDetailsAdapter extends RecyclerView.Adapter<CinemaDetailsAdapter.MyCinemaDetailsViewHolder> {
    private Context mContext;
    private List<CinemaDetailsBean.ResultBean> mResultBeans = new ArrayList<>();

    public CinemaDetailsAdapter(Context context, List<CinemaDetailsBean.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    @NonNull
    @Override
    public MyCinemaDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.film_viewpager_item_view, viewGroup, false);
        return new MyCinemaDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCinemaDetailsViewHolder myCinemaDetailsViewHolder, int i) {
        String imageUrl = mResultBeans.get(i).getImageUrl();
        Uri uri = Uri.parse(imageUrl);
        myCinemaDetailsViewHolder.mSimpleDraweeView.setImageURI(uri);
        myCinemaDetailsViewHolder.mTextViewName.setText(mResultBeans.get(i).getName());
        myCinemaDetailsViewHolder.mTextViewTiem.setText(mResultBeans.get(i).getDuration());
    }

    @Override
    public int getItemCount() {
        return mResultBeans == null ? 0 : mResultBeans.size();
    }

    class MyCinemaDetailsViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewName, mTextViewTiem;

        public MyCinemaDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.flow_sdv);
            mTextViewName = itemView.findViewById(R.id.flow_name);
            mTextViewTiem = itemView.findViewById(R.id.flow_tiem);
        }
    }
}
