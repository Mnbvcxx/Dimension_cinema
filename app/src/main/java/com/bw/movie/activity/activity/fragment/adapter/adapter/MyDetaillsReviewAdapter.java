package com.bw.movie.activity.activity.fragment.adapter.adapter;

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
import com.bw.movie.activity.activity.fragment.adapter.bean.ReviewsBean;
import com.bw.movie.utils.DateUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/29.
 * email : fangshikang@outlook.com
 * desc :       影评适配器
 */
public class MyDetaillsReviewAdapter extends RecyclerView.Adapter<MyDetaillsReviewAdapter.MyReviewViewHolder> {

    private Context mContext;
    private List<ReviewsBean.ResultBean> mResultBeans = new ArrayList<>();

    public MyDetaillsReviewAdapter(Context context, List<ReviewsBean.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    @NonNull
    @Override
    public MyReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.review_item_view, viewGroup, false);
        return new MyReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyReviewViewHolder myReviewViewHolder, final int i) {
        String headPic = mResultBeans.get(i).getCommentHeadPic();
        Uri parse = Uri.parse(headPic);
        myReviewViewHolder.mSimpleDraweeView.setImageURI(parse);
        myReviewViewHolder.mTextViewName.setText(mResultBeans.get(i).getCommentUserName());
        myReviewViewHolder.mTextViewDesc.setText(mResultBeans.get(i).getCommentContent());
        myReviewViewHolder.mTextViewDzCount.setText(mResultBeans.get(i).getGreatNum() + "");
        myReviewViewHolder.mTextViewCommentCount.setText(mResultBeans.get(i).getReplyNum() + "");
        String date = DateUtils.getDateToStrings(mResultBeans.get(i).getCommentTime());
        myReviewViewHolder.mTextViewTiem.setText(date);
        if (mResultBeans.get(i).getIsGreat() == 1) {
            myReviewViewHolder.mImageViewDz.setImageResource(R.mipmap.com_icon_praise_selected);
        } else if (mResultBeans.get(i).getIsGreat() == 2) {
            myReviewViewHolder.mImageViewDz.setImageResource(R.mipmap.com_icon_praise_default);
        }
        //点赞
        myReviewViewHolder.mImageViewDz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickedListener != null) {
                    mOnClickedListener.onChecled(mResultBeans.get(i).getCommentId(), myReviewViewHolder.mImageViewDz);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResultBeans == null ? 0 : mResultBeans.size();
    }


    class MyReviewViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewName, mTextViewDesc, mTextViewTiem, mTextViewCommentCount, mTextViewDzCount;
        ImageView mImageViewComment, mImageViewDz;

        public MyReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.review_img);
            mTextViewName = itemView.findViewById(R.id.review_name);
            mTextViewDesc = itemView.findViewById(R.id.review_desc);
            mTextViewTiem = itemView.findViewById(R.id.review_tiem);
            mTextViewCommentCount = itemView.findViewById(R.id.review_comment_count);
            mTextViewDzCount = itemView.findViewById(R.id.review_dz_count);
            mImageViewComment = itemView.findViewById(R.id.review_comment);
            mImageViewDz = itemView.findViewById(R.id.review_dz);
        }
    }


    private onClickedListener mOnClickedListener;

    public void setOnClickedListener(onClickedListener onClickedListener) {
        mOnClickedListener = onClickedListener;
    }

    public interface onClickedListener {
        void onChecled(int position, ImageView imageView);
    }
}
