package com.bw.movie.activity.activity.fragment.adapter;

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
import com.bw.movie.activity.activity.fragment.adapter.activity.MovieDetailsActivity;
import com.bw.movie.activity.bean.FilmCinemaxBean;
import com.bw.movie.utils.IntentUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/26.
 * email : fangshikang@outlook.com
 * desc :   热门  全部列表  适配器
 */
public class DetailsRmAdapter extends RecyclerView.Adapter<DetailsRmAdapter.DetailsRmViewHolder> {
    private Context mContext;
    private List<FilmCinemaxBean.ResultBean> mResultBeans = new ArrayList<>();

    public DetailsRmAdapter(Context context, List<FilmCinemaxBean.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    @NonNull
    @Override
    public DetailsRmViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.hot_item_view, viewGroup, false);
        return new DetailsRmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailsRmViewHolder detailsRmViewHolder, final int i) {
        String imageUrl = mResultBeans.get(i).getImageUrl();
        Uri uri = Uri.parse(imageUrl);
        detailsRmViewHolder.mSimpleDraweeView.setImageURI(uri);
        detailsRmViewHolder.mTextViewNam.setText(mResultBeans.get(i).getName());
        detailsRmViewHolder.mTextViewDesc.setText(mResultBeans.get(i).getSummary());

        if (mResultBeans.get(i).getFollowMovie() == 1) {
            detailsRmViewHolder.mImageView.setImageResource(R.mipmap.com_icon_collection_selected);
        } else if (mResultBeans.get(i).getFollowMovie() == 2) {
            detailsRmViewHolder.mImageView.setImageResource(R.mipmap.com_icon_collection_default);
        }
        //点赞
        detailsRmViewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnCheckedListener != null) {
                    mOnCheckedListener.onClicked(mResultBeans.get(i).getId(), detailsRmViewHolder.mImageView);
                    notifyDataSetChanged();
                }
            }
        });

        //点击获取详情
        detailsRmViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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

    class DetailsRmViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewNam, mTextViewDesc;
        ImageView mImageView;

        public DetailsRmViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.rm_img);
            mTextViewNam = itemView.findViewById(R.id.rm_name);
            mTextViewDesc = itemView.findViewById(R.id.rm_desc);
            mImageView = itemView.findViewById(R.id.rm_like);
        }
    }

    private onCheckedListener mOnCheckedListener;

    public void setOnCheckedListener(onCheckedListener onCheckedListener) {
        mOnCheckedListener = onCheckedListener;
    }

    //自定义接口回调
    public interface onCheckedListener {
        void onClicked(int positon, ImageView imageView);
    }
}
