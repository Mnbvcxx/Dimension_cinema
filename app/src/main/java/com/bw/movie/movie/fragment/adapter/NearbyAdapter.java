package com.bw.movie.movie.fragment.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.movie.fragment.bean.NearbyBean;
import com.bw.movie.movie.fragment.bean.RecommendedBean;
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
    public void onBindViewHolder(@NonNull NearbyViewHolde nearbyViewHolde, int i) {
        String logo = mResultBeans.get(i).getLogo();
        Uri uri = Uri.parse(logo);
        nearbyViewHolde.mSimpleDraweeView.setImageURI(uri);
        nearbyViewHolde.mTextViewName.setText(mResultBeans.get(i).getName());
        nearbyViewHolde.mTextViewDesc.setText(mResultBeans.get(i).getAddress());
        nearbyViewHolde.mTextViewKm.setText(mResultBeans.get(i).getDistance() + "km");
    }

    @Override
    public int getItemCount() {
        return mResultBeans == null ? 0 : mResultBeans.size();
    }

    class NearbyViewHolde extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewName, mTextViewDesc, mTextViewKm;
        ImageView mImageView;

        public NearbyViewHolde(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.recommended_img);
            mTextViewName = itemView.findViewById(R.id.recommended_name);
            mTextViewDesc = itemView.findViewById(R.id.recommended_desc);
            mTextViewKm = itemView.findViewById(R.id.recommended_km);
            mImageView = itemView.findViewById(R.id.recommended_concern);
        }
    }
}
