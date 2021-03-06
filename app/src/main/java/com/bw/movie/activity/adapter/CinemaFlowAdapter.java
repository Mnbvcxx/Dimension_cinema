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
import com.bw.movie.utils.DateUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/25.
 * email : fangshikang@outlook.com
 * desc :轮播图适配器
 */
public class CinemaFlowAdapter extends RecyclerView.Adapter<CinemaFlowAdapter.MyCinemaFlowViewHolder> {
    private Context mContext;
    private List<FilmBean.ResultBean> mResultBeans = new ArrayList<>();
    private List<String> mListImg = new ArrayList<>();

    public CinemaFlowAdapter(Context context, List<String> listImg, List<FilmBean.ResultBean> resultBeans) {
        mContext = context;
        mListImg = listImg;
        mResultBeans = resultBeans;
    }

    @NonNull
    @Override
    public MyCinemaFlowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.film_viewpager_item_view, viewGroup, false);
        return new MyCinemaFlowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCinemaFlowViewHolder myCinemaFlowViewHolder, final int i) {
        String imageUrl = mResultBeans.get(i % mListImg.size()).getImageUrl();
        Uri uri = Uri.parse(imageUrl);
        myCinemaFlowViewHolder.mSimpleDraweeView.setImageURI(uri);
        myCinemaFlowViewHolder.mTextViewName.setText(mResultBeans.get(i).getName());
        String date = DateUtils.getDateToString(mResultBeans.get(i).getReleaseTime());
        myCinemaFlowViewHolder.mTextViewTiem.setText(date);
        myCinemaFlowViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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

    class MyCinemaFlowViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewName, mTextViewTiem;

        public MyCinemaFlowViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.flow_sdv);
            mTextViewName = itemView.findViewById(R.id.flow_name);
            mTextViewTiem = itemView.findViewById(R.id.flow_tiem);
        }
    }
}
