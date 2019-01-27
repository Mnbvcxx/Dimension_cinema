package com.bw.movie.movie.fragment.adapter;

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
import com.bw.movie.movie.fragment.bean.NearbyBean;
import com.bw.movie.movie.fragment.bean.RecommendedBean;
import com.bw.movie.movie.fragment.cinemaActivity.CinemaDetailsActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/27.
 * email : fangshikang@outlook.com
 * desc :       推荐影院  适配器
 */
public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.NearbyViewHolde> {

    private Context mContext;
    private List<NearbyBean.ResultBean> mResultBeans = new ArrayList<>();

    public NearbyAdapter(Context context, List<NearbyBean.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    @NonNull
    @Override
    public NearbyViewHolde onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recommended_item_view, viewGroup, false);
        return new NearbyViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NearbyViewHolde nearbyViewHolde, final int i) {
        String logo = mResultBeans.get(i).getLogo();
        Uri uri = Uri.parse(logo);
        nearbyViewHolde.mSimpleDraweeView.setImageURI(uri);
        nearbyViewHolde.mTextViewName.setText(mResultBeans.get(i).getName());
        nearbyViewHolde.mTextViewDesc.setText(mResultBeans.get(i).getAddress());
        nearbyViewHolde.mTextViewKm.setText(mResultBeans.get(i).getDistance() + "km");
        if (mResultBeans.get(i).getFollowCinema() == 1) {
            nearbyViewHolde.mImageView.setImageResource(R.mipmap.com_icon_collection_selected);
        } else if (mResultBeans.get(i).getFollowCinema() == 2) {
            nearbyViewHolde.mImageView.setImageResource(R.mipmap.com_icon_collection_default);
        }
        //点击跳转到影院旗下的所有电影列表信息页面
        nearbyViewHolde.itemView.setOnClickListener(new View.OnClickListener() {
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
        //关注
        nearbyViewHolde.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickedListener != null) {
                    mOnClickedListener.onClicked(mResultBeans.get(i).getId(), nearbyViewHolde.mImageView);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResultBeans == null ? 0 : mResultBeans.size();
    }

    class NearbyViewHolde extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewName, mTextViewDesc, mTextViewKm;
        ImageView mImageView;
        RelativeLayout mRelativeLayout;
        public NearbyViewHolde(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.recommended_img);
            mTextViewName = itemView.findViewById(R.id.recommended_name);
            mTextViewDesc = itemView.findViewById(R.id.recommended_desc);
            mTextViewKm = itemView.findViewById(R.id.recommended_km);
            mImageView = itemView.findViewById(R.id.recommended_concern);
            mRelativeLayout = itemView.findViewById(R.id.layout_recommended_concern);
        }
    }

    private onClickedListener mOnClickedListener;

    public void setOnClickedListener(onClickedListener onClickedListener) {
        mOnClickedListener = onClickedListener;
    }

    public interface onClickedListener {
        void onClicked(int position, ImageView imageView);
    }
}
