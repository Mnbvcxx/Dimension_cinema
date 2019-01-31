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
import com.bw.movie.movie.fragment.bean.EvaluateBean;
import com.bw.movie.utils.DateUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: pengbo
 * @date:2019/1/28 desc:影院评价的适配器
 */
public class EvaluateAdapter extends RecyclerView.Adapter<EvaluateAdapter.ViewHolder> {
    private Context mContext;
    private List<EvaluateBean.ResultBean> mjihe;

    public EvaluateAdapter(Context context) {
        mContext = context;
        mjihe = new ArrayList<>();
    }

    public void setMjihe(List<EvaluateBean.ResultBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cinema_evaluate_adapter, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        //头像
        viewHolder.evaluate_sdv.setImageURI(Uri.parse(mjihe.get(i).getCommentHeadPic()));
        //昵称
        viewHolder.evaluate_nick.setText(mjihe.get(i).getCommentUserName());
        //内容
        viewHolder.evaluate_count.setText(mjihe.get(i).getCommentContent());
        //时间
        String date = DateUtils.getDateToString(mjihe.get(i).getCommentTime());
        viewHolder.evaluate_time.setText(date);
        //点赞数
        viewHolder.evaluate_num.setText(mjihe.get(i).getGreatNum() + "");
        int commentId = mjihe.get(i).getCommentId();

        if (mjihe.get(i).getIsGreat() == 1) {
            viewHolder.evaluate_thumbs.setImageResource(R.mipmap.com_icon_praise_selected);
        } else if (mjihe.get(i).getIsGreat() == 2) {
            viewHolder.evaluate_thumbs.setImageResource(R.mipmap.com_icon_praise_default);
        }
        //点击事件
        if (mCallBack != null) {
            mCallBack.callback(viewHolder.evaluate_thumbs, commentId);
        }

    }


    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView evaluate_sdv;
        TextView evaluate_nick, evaluate_count, evaluate_time, evaluate_num;
        ImageView evaluate_thumbs;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            evaluate_sdv = itemView.findViewById(R.id.evaluate_sdv);
            evaluate_nick = itemView.findViewById(R.id.evaluate_nick);
            evaluate_count = itemView.findViewById(R.id.evaluate_count);
            evaluate_time = itemView.findViewById(R.id.evaluate_time);
            evaluate_thumbs = itemView.findViewById(R.id.evaluate_thumbs);
            evaluate_num = itemView.findViewById(R.id.evaluate_num);
        }
    }

    public interface CallBack {
        void callback(ImageView thumbs, int id);
    }

    CallBack mCallBack;

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }
}
