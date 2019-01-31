package com.bw.movie.activity.activity.fragment.adapter.adapter;

import android.content.Context;
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
import com.bw.movie.activity.activity.fragment.adapter.bean.MovieIdBean;
import com.bw.movie.movie.fragment.adapter.NearbyAdapter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/30.
 * email : fangshikang@outlook.com
 * desc :   根据电影ID查询当前排片该电影的影院列表
 */
public class MovieTicketAdapter extends RecyclerView.Adapter<MovieTicketAdapter.MovieTicketViewHolde> {
    private Context mContext;
    private List<MovieIdBean.ResultBean> mResultBeans = new ArrayList<>();

    public MovieTicketAdapter(Context context, List<MovieIdBean.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    @NonNull
    @Override
    public MovieTicketViewHolde onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recommended_item_view, viewGroup, false);
        return new MovieTicketViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTicketViewHolde movieTicketViewHolde, final int i) {
        String logo = mResultBeans.get(i).getLogo();
        Uri uri = Uri.parse(logo);
        movieTicketViewHolde.mSimpleDraweeView.setImageURI(uri);
        movieTicketViewHolde.mTextViewName.setText(mResultBeans.get(i).getName());
        movieTicketViewHolde.mTextViewDesc.setText(mResultBeans.get(i).getAddress());
        movieTicketViewHolde.mTextViewKm.setText(mResultBeans.get(i).getDistance() + "km");
        movieTicketViewHolde.mImageView.setVisibility(View.GONE);
        movieTicketViewHolde.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickedListener!=null){
                    mOnClickedListener.onClicked(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResultBeans == null ? 0 : mResultBeans.size();
    }

    class MovieTicketViewHolde extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewName, mTextViewDesc, mTextViewKm;
        ImageView mImageView;
        RelativeLayout mRelativeLayout;
        public MovieTicketViewHolde(@NonNull View itemView) {
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
        void onClicked(int position);
    }
}
