package com.bw.movie.activity.activity.fragment.adapter.adapter;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.activity.activity.fragment.adapter.bean.DetailsMovieBean;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * @author : FangShiKang
 * @date : 2019/01/28.
 * email : fangshikang@outlook.com
 * desc :       预告片视频播放器
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyVideoViewHolder> {

    private Context mContext;
    private List<DetailsMovieBean.ResultBean.ShortFilmListBean> mShortFilmListBeans = new ArrayList<>();

    public VideoAdapter(Context context, List<DetailsMovieBean.ResultBean.ShortFilmListBean> shortFilmListBeans) {
        mContext = context;
        mShortFilmListBeans = shortFilmListBeans;
    }

    @NonNull
    @Override
    public MyVideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.video_item_view, viewGroup, false);
        return new MyVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVideoViewHolder myVideoViewHolder, int i) {
        String imageUrl = mShortFilmListBeans.get(i).getImageUrl();
        String videoUrl = mShortFilmListBeans.get(i).getVideoUrl();
        Glide.with(mContext).load(imageUrl).into(myVideoViewHolder.mJZVideoPlayerStandard.thumbImageView);
        //视频里的预告片
        myVideoViewHolder.mJZVideoPlayerStandard.setUp(videoUrl, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "预告片");

        myVideoViewHolder.mJZVideoPlayerStandard.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //全屏
        myVideoViewHolder.mJZVideoPlayerStandard.fullscreenButton.setVisibility(View.GONE);
        myVideoViewHolder.mJZVideoPlayerStandard.progressBar.setVisibility(View.VISIBLE);
        myVideoViewHolder.mJZVideoPlayerStandard.currentTimeTextView.setVisibility(View.GONE);
        myVideoViewHolder.mJZVideoPlayerStandard.totalTimeTextView.setVisibility(View.GONE);
        myVideoViewHolder.mJZVideoPlayerStandard.tinyBackImageView.setVisibility(View.GONE);
        myVideoViewHolder.mJZVideoPlayerStandard.batteryLevel.setVisibility(View.GONE);
        myVideoViewHolder.mJZVideoPlayerStandard.startButton.setVisibility(View.VISIBLE);
        //设置容器内播放器高,解决黑边（视频全屏）
        JZVideoPlayer.setVideoImageDisplayType(JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT);
        JZVideoPlayer.TOOL_BAR_EXIST = false;
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public int getItemCount() {
        return mShortFilmListBeans == null ? 0 : mShortFilmListBeans.size();
    }

    class MyVideoViewHolder extends RecyclerView.ViewHolder {
        JZVideoPlayerStandard mJZVideoPlayerStandard;

        public MyVideoViewHolder(@NonNull View itemView) {
            super(itemView);
            mJZVideoPlayerStandard = itemView.findViewById(R.id.jcvideoplayer);
        }
    }
}
