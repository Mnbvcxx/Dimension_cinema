package com.bw.movie.activity.fragment.myactivity.adapter;

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
import com.bw.movie.activity.fragment.myactivity.bean.AttenCinemaBean;
import com.bw.movie.movie.fragment.cinemaActivity.CinemaDetailsActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: pengbo
 * @date:2019/1/28
 * desc:关注的影院适配器
 */
public class AttenCinemaAdapter extends RecyclerView.Adapter<AttenCinemaAdapter.ViewHolder> {
            private Context mContext;
            private List<AttenCinemaBean.ResultBean> mjihe;

    public AttenCinemaAdapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<AttenCinemaBean.ResultBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.atten_cinema_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Uri parse = Uri.parse(mjihe.get(i).getLogo());
        viewHolder.cinemaSDV.setImageURI(parse);
        viewHolder.cinemaTitle.setText(mjihe.get(i).getName());
        viewHolder.cinemaCount.setText(mjihe.get(i).getAddress());
        //影院旗下所排期电影
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CinemaDetailsActivity.class);
                intent.putExtra("cinemaId", mjihe.get(i).getId());
                intent.putExtra("logo", mjihe.get(i).getLogo());
                intent.putExtra("name", mjihe.get(i).getName());
                intent.putExtra("address", mjihe.get(i).getAddress());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView cinemaSDV;
        TextView cinemaTitle,cinemaCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cinemaSDV=itemView.findViewById(R.id.cinema_adapter_sdv);
            cinemaTitle=itemView.findViewById(R.id.cinema_adapter_title);
            cinemaCount=itemView.findViewById(R.id.cinema_adapter_count);
        }
    }
}
