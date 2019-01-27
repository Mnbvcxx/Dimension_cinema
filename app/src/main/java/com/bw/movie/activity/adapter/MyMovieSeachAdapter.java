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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.activity.FilmDetailsActivity;
import com.bw.movie.activity.bean.FilmCinemaxBean;
import com.bw.movie.activity.bean.SeachBean;
import com.bw.movie.movie.fragment.adapter.RecommendedAdapter;
import com.bw.movie.movie.fragment.cinemaActivity.CinemaDetailsActivity;
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
    public void onBindViewHolder(@NonNull final MyMovieSeachViewHolder myMovieSeachViewHolder, final int i) {
        String logo = mResultBeans.get(i).getLogo();
        Uri uri = Uri.parse(logo);
        myMovieSeachViewHolder.mSimpleDraweeView.setImageURI(uri);
        myMovieSeachViewHolder.mTextViewName.setText(mResultBeans.get(i).getName());
        myMovieSeachViewHolder.mTextViewDesc.setText(mResultBeans.get(i).getAddress());
        myMovieSeachViewHolder.mTextViewKm.setText(mResultBeans.get(i).getDistance() + "km");
        if (mResultBeans.get(i).getFollowCinema() == 1) {
            myMovieSeachViewHolder.mImageView.setImageResource(R.mipmap.com_icon_collection_selected);
        } else if (mResultBeans.get(i).getFollowCinema() == 2) {
            myMovieSeachViewHolder.mImageView.setImageResource(R.mipmap.com_icon_collection_default);
        }
        //关注
        myMovieSeachViewHolder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickedListenrt != null) {
                    mOnClickedListenrt.onClicked(mResultBeans.get(i).getId(), myMovieSeachViewHolder.mImageView);
                }
            }
        });

        myMovieSeachViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CinemaDetailsActivity.class);
                intent.putExtra("cinemaId", mResultBeans.get(i).getId());
                intent.putExtra("logo",mResultBeans.get(i).getLogo());
                intent.putExtra("name",mResultBeans.get(i).getName());
                intent.putExtra("address",mResultBeans.get(i).getAddress());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mResultBeans == null ? 0 : mResultBeans.size();
    }

    class MyMovieSeachViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewName, mTextViewDesc, mTextViewKm;
        ImageView mImageView;
        RelativeLayout mRelativeLayout;
        public MyMovieSeachViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.recommended_img);
            mTextViewName = itemView.findViewById(R.id.recommended_name);
            mTextViewDesc = itemView.findViewById(R.id.recommended_desc);
            mTextViewKm = itemView.findViewById(R.id.recommended_km);
            mImageView = itemView.findViewById(R.id.recommended_concern);
            mRelativeLayout = itemView.findViewById(R.id.layout_recommended_concern);
        }
    }

    private onClickedListenrt mOnClickedListenrt;

    public void setOnClickedListenrt(onClickedListenrt onClickedListenrt) {
        mOnClickedListenrt = onClickedListenrt;
    }

    public interface onClickedListenrt {
        void onClicked(int position, ImageView imageView);
    }
}
