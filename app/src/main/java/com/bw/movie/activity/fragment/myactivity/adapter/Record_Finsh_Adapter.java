package com.bw.movie.activity.fragment.myactivity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.fragment.myactivity.bean.RecordBean;
import com.bw.movie.utils.DateUtils;
import java.util.ArrayList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: pengbo
 * @date:2019/2/14 desc:购票记录已完成的适配器
 */
public class Record_Finsh_Adapter extends RecyclerView.Adapter<Record_Finsh_Adapter.ViewHolder> {
    private Context mContext;
    private List<RecordBean.ResultBean> mjihe;

    public Record_Finsh_Adapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<RecordBean.ResultBean> mjihe) {
        this.mjihe = mjihe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(mContext).inflate(R.layout.record_finsh_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //标题
        viewHolder.finsh_title.setText(mjihe.get(i).getMovieName());
        //播放时间
        String beginTime = DateUtils.getDateToString(mjihe.get(i).getBeginTime());
        String endTime = DateUtils.getDateToString(mjihe.get(i).getEndTime());
        viewHolder.finsh_time.setText(beginTime+"-"+endTime);
        viewHolder.finsh_code.setText("订单号："+mjihe.get(i).getOrderId());
        viewHolder.finsh_cinema.setText("影院："+mjihe.get(i).getCinemaName());
        viewHolder.finsh_hall.setText("影厅："+mjihe.get(i).getScreeningHall());
        viewHolder.finsh_amount.setText("数量："+mjihe.get(i).getAmount());
        viewHolder.finsh_price.setText("金额："+mjihe.get(i).getPrice());
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView finsh_title,finsh_time,finsh_code,finsh_ordertime,finsh_cinema,finsh_hall,finsh_amount,finsh_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            finsh_title=itemView.findViewById(R.id.finsh_title);
            finsh_time=itemView.findViewById(R.id.finsh_time);
            finsh_code=itemView.findViewById(R.id.finsh_code);
            finsh_ordertime=itemView.findViewById(R.id.finsh_ordertime);
            finsh_cinema=itemView.findViewById(R.id.finsh_cinema);
            finsh_hall=itemView.findViewById(R.id.finsh_hall);
            finsh_amount=itemView.findViewById(R.id.finsh_amount);
            finsh_price=itemView.findViewById(R.id.finsh_price);
        }
    }
}
