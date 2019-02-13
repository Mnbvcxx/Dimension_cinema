package com.bw.movie.activity.fragment.myactivity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.fragment.myactivity.bean.RecordBean;
import com.bw.movie.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: pengbo
 * @date:2019/1/28 desc:购票记录的适配器
 */
public class Record_Wait_Adapter extends RecyclerView.Adapter<Record_Wait_Adapter.ViewHolder> {
    private Context mContext;
    private List<RecordBean.ResultBean>mjihe;
    public Record_Wait_Adapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<RecordBean.ResultBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
           View  view=LayoutInflater.from(mContext).inflate(R.layout.record_adapter_wait,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            //电影名
            viewHolder.record_title.setText(mjihe.get(i).getMovieName());
        viewHolder.record_code.setText("订单号："+mjihe.get(i).getOrderId());
        viewHolder.record_cinema.setText("影院："+mjihe.get(i).getCinemaName());
        viewHolder.record_hall.setText("影厅："+mjihe.get(i).getScreeningHall());
        String createTime = DateUtils.getDateToString(mjihe.get(i).getCreateTime());
        String endTime = DateUtils.getDateToString(mjihe.get(i).getEndTime());
        viewHolder.wait_time.setText("时间："+createTime+"-"+endTime);
        viewHolder.record_amount.setText("数量："+mjihe.get(i).getAmount());
        viewHolder.record_price.setText("金额："+mjihe.get(i).getPrice());
        //付款的点击事件
        viewHolder.wait_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWaitCallBack!=null){
                    mWaitCallBack.waitcallback();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView record_title,record_code,record_cinema,record_hall,wait_time,record_amount,record_price;
        Button wait_butt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            record_title=itemView.findViewById(R.id.wait_title);
            record_code=itemView.findViewById(R.id.wait_code);
            record_cinema=itemView.findViewById(R.id.wait_cinema);
            record_hall=itemView.findViewById(R.id.wait_hall);
            wait_time=itemView.findViewById(R.id.wait_time);
            record_amount=itemView.findViewById(R.id.wait_amount);
            record_price=itemView.findViewById(R.id.wait_price);
            wait_butt=itemView.findViewById(R.id.wait_butt);

        }
    }

    public interface WaitCallBack{
        void waitcallback();
    }
    public WaitCallBack mWaitCallBack;

    public void setWaitCallBack(WaitCallBack waitCallBack) {
        mWaitCallBack = waitCallBack;
    }
}

