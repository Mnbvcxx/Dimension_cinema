package com.bw.movie.activity.activity.fragment.adapter.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.activity.activity.fragment.adapter.bean.DetailsMovieBean;
import com.bw.movie.activity.activity.fragment.adapter.bean.StillsBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/28.
 * email : fangshikang@outlook.com
 * desc :   剧照  适配器
 */
public class StillsAdapter extends RecyclerView.Adapter<StillsAdapter.MyStillsViewHolder> {

    private Context mContext;
    private List<String> mList = new ArrayList<>();

    public StillsAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public MyStillsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.stills_item_view, viewGroup, false);
        return new MyStillsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyStillsViewHolder myStillsViewHolder, int i) {
        Uri parse = Uri.parse(mList.get(i));
        myStillsViewHolder.mSimpleDraweeView.setImageURI(parse);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class MyStillsViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView mSimpleDraweeView;
        public MyStillsViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.stils_img);
        }
    }
}
