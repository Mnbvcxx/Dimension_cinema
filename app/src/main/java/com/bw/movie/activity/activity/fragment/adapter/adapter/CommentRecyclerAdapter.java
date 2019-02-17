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
import com.bw.movie.register.bean.EvaluateCommentBean;
import com.bw.movie.utils.DateUtils;
import com.facebook.drawee.view.GenericDraweeView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @author: pengbo
 * @date:2019/2/16 desc:回复的列表
 */
public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private List<EvaluateCommentBean.ResultBean> mjihe;

    public CommentRecyclerAdapter(Context context, List<EvaluateCommentBean.ResultBean> mjihe) {
        mContext = context;
        this.mjihe = mjihe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(mContext).inflate(R.layout.comment_recycler_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final EvaluateCommentBean.ResultBean resultBean = mjihe.get(i);
        //头像
        viewHolder.comment_adapter_sdv.setImageURI(Uri.parse(resultBean.getReplyHeadPic()));
        //名字
        viewHolder.comment_adapter_name.setText(resultBean.getReplyUserName());
        //内容
        viewHolder.comment_adapter_count.setText(resultBean.getReplyContent());
        //时间
        viewHolder.comment_adapter_time.setText(DateUtils.getDateToString(resultBean.getCommentTime()));
        //点赞数
        //viewHolder.comment_adapter_likenum.setText(resultBean.get);

        //TODO:点赞
        viewHolder.comment_adapter_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickedListener != null) {
                    mOnClickedListener.onChecled(resultBean.getReplyUserId(), viewHolder.comment_adapter_like);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView comment_adapter_sdv;
        TextView comment_adapter_name,comment_adapter_count,comment_adapter_time,comment_adapter_likenum;
        ImageView comment_adapter_like;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comment_adapter_sdv=itemView.findViewById(R.id.comment_adapter_sdv);
            comment_adapter_name=itemView.findViewById(R.id.comment_adapter_name);
            comment_adapter_count=itemView.findViewById(R.id.comment_adapter_count);
            comment_adapter_time=itemView.findViewById(R.id.comment_adapter_time);
            comment_adapter_likenum=itemView.findViewById(R.id.comment_adapter_likenum);
            comment_adapter_like=itemView.findViewById(R.id.comment_adapter_like);
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
