package com.bw.movie.activity.fragment.myactivity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.fragment.myactivity.bean.MessageRecyclerBean;
import com.bw.movie.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: pengbo
 * @date:2019/1/26 desc:
 */
public class UnreadAdapter extends RecyclerView.Adapter<UnreadAdapter.ViewHolder> {
        private Context mContext;
        private List<MessageRecyclerBean.ResultBean>mjihe;

    public UnreadAdapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<MessageRecyclerBean.ResultBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.fragment_myactivity_unreadadapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            //标题、内容、时间、未读、
        viewHolder.adapter_title.setText(mjihe.get(i).getTitle());
        viewHolder.adapter_content.setText(mjihe.get(i).getContent());
            String dateToString = DateUtils.getDateToString(mjihe.get(i).getPushTime());
        viewHolder.adapter_pushtime.setText(dateToString);
        viewHolder.adapter_status.setText(mjihe.get(i).getStatus()+"");
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            TextView adapter_title,adapter_content,adapter_pushtime,adapter_status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            adapter_title=itemView.findViewById(R.id.adapter_title);
            adapter_content=itemView.findViewById(R.id.adapter_content);
            adapter_pushtime=itemView.findViewById(R.id.adapter_pushtime);
            adapter_status=itemView.findViewById(R.id.adapter_status);
        }
    }
}
