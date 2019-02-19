package com.bw.movie.activity.fragment.myactivity.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.activity.fragment.adapter.activity.MovieDetailsActivity;
import com.bw.movie.activity.fragment.myactivity.bean.AttenMoveBean;
import com.bw.movie.utils.DateUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: pengbo
 * @date:2019/1/27 desc:我关注的电影的适配器
 */
public class AttenMoveAdapter extends RecyclerView.Adapter<AttenMoveAdapter.ViewHolder> {
        private Context mContext;
        private List<AttenMoveBean.ResultBean> mjihe;

    public List<AttenMoveBean.ResultBean> getMjihe() {
        return mjihe;
    }

    public void setMjihe(List<AttenMoveBean.ResultBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    public AttenMoveAdapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.atten_move_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Uri uri = Uri.parse(mjihe.get(i).getImageUrl());
        viewHolder.move_sdv.setImageURI(uri);
        viewHolder.move_title.setText(mjihe.get(i).getName());
        viewHolder.move_count.setText(mjihe.get(i).getSummary());
        String date = DateUtils.getDateToString(mjihe.get(i).getReleaseTime());
        viewHolder.move_time.setText(date);
        //跳转到电影详情页面
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MovieDetailsActivity.class);
                int id = mjihe.get(i).getId();
                intent.putExtra("movieId", id);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView move_sdv;
        TextView move_title,move_count,move_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            move_sdv=itemView.findViewById(R.id.move_sdv);
            move_title=itemView.findViewById(R.id.move_title);
            move_count=itemView.findViewById(R.id.move_count);
            move_time=itemView.findViewById(R.id.move_time);
        }
    }
}
