package com.bw.movie.activity.activity.fragment.adapter.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.activity.fragment.adapter.adapter.CommentRecyclerAdapter;
import com.bw.movie.activity.activity.fragment.adapter.adapter.MyDetaillsReviewAdapter;
import com.bw.movie.activity.activity.fragment.adapter.adapter.StillsAdapter;
import com.bw.movie.activity.activity.fragment.adapter.adapter.VideoAdapter;
import com.bw.movie.activity.activity.fragment.adapter.bean.CommentLikeBean;
import com.bw.movie.activity.activity.fragment.adapter.bean.DetailsMovieBean;
import com.bw.movie.activity.activity.fragment.adapter.bean.ReviewsBean;
import com.bw.movie.apis.Apis;
import com.bw.movie.apis.UserApis;
import com.bw.movie.movie.fragment.cinemaActivity.bean.MoveSeatUserID;
import com.bw.movie.mvc.presenter.MyPresenter;
import com.bw.movie.mvc.view.MyView;
import com.bw.movie.register.bean.EvaluateCommentBean;
import com.bw.movie.register.bean.RegisterBean;
import com.bw.movie.utils.AlertDialogUntil;
import com.bw.movie.utils.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;


/**
 * 电影详情页面
 * <p>
 * 当用户点击电影列表中的条目时跳转到电影详情页面
 */
public class MovieDetailsActivity extends AppCompatActivity implements MyView {

    private static final String TAG = "MovieDetailsActivity";
    @BindView(R.id.dy_details_name)
    TextView mDyDetailsName;
    @BindView(R.id.dy_details_img)
    SimpleDraweeView mDyDetailsImg;
    @BindView(R.id.dy_details_details)
    TextView mDyDetailsDetails;
    @BindView(R.id.dy_details_prediction)
    TextView mDyDetailsPrediction;
    @BindView(R.id.dy_details_stills)
    TextView mDyDetailsStills;
    @BindView(R.id.dy_reviews)//影评
            TextView mDyReviews;
    @BindView(R.id.dy_details_layout_review)
    RelativeLayout mDyDetailsLayoutReview;
    @BindView(R.id.dy_details_back)
    ImageView mDyDetailsBack;
    @BindView(R.id.dy_details_ticket)
    TextView mDyDetailsTicket;
    @BindView(R.id.review_pp_xl)
    ImageView mReviewPpXl;
    @BindView(R.id.review_pp_rv)
    RecyclerView mReviewPpRv;
    @BindView(R.id.review_back)
    ImageView mReviewBack;
    @BindView(R.id.review_publish)
    ImageView mReviewPublish;
    private MyPresenter mMyPresenter;
    private ImageView mDetailsPpXl;
    private TextView mDetailsPpLx;
    private TextView mDetailsPpDy;
    private TextView mDetailsPpSc;
    private TextView mDetailsPpCd;
    private TextView mDetailsPpJJie;
    private SimpleDraweeView mDetailsPpImg;
    private Uri mUri;
    private String mDirector;
    private String mDuration;
    private String mPlaceOrigin;
    private String mSummary;
    private String mMovieTypes;
    private String mName;
    private TextView mDetailsPpActorName;
    private String mStarring;
    private ImageView mNoticePpXl;
    private ImageView mStillsPpXl;
    private RecyclerView mNoticeRv;
    private RecyclerView mStillsRv;
    private List<DetailsMovieBean.ResultBean.ShortFilmListBean> mShortFilmList;
    private List<String> mPosterList;
    private StillsAdapter mStillsAdapter;
    private List<String> mArrayList;
    private MyDetaillsReviewAdapter mReviewAdapter;
    private int mMovieId;
    private String mImageUrl;
    private EditText mPubEdTxt;
    private ImageView mCommentImageView1;
    private int mPosition1;
    private int mCommentId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        mMyPresenter = new MyPresenter(this);
        mReviewPpRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Intent intent = getIntent();
        mMovieId = intent.getIntExtra("movieId", 0);
        mMyPresenter.onGetDatas(Apis.MOVIE_DETAILS_URL + mMovieId, DetailsMovieBean.class);
        mDyReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDyDetailsLayoutReview.setVisibility(View.VISIBLE);
                mMyPresenter.onGetDatas(Apis.REVIEW_CINEMA + mMovieId + "&page=1&count=10", ReviewsBean.class);
            }
        });
    }

    @OnClick({R.id.dy_details_name, R.id.dy_details_img, R.id.dy_details_details, R.id.review_pp_xl,
            R.id.dy_details_prediction, R.id.dy_details_stills, R.id.dy_reviews,
            R.id.dy_details_back, R.id.dy_details_ticket, R.id.review_back, R.id.review_publish})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.dy_details_name:
                break;
            case R.id.dy_details_img:
                break;
            case R.id.dy_details_details:
                mDyDetailsLayoutReview.setVisibility(View.GONE);
                setPopupWindow();
                break;
            case R.id.dy_details_prediction://预告
                mDyDetailsLayoutReview.setVisibility(View.GONE);
                initNotice();
                break;
            case R.id.dy_details_stills://剧照
                mDyDetailsLayoutReview.setVisibility(View.GONE);
                initStills();
                break;
            case R.id.dy_reviews://影评
                break;
            case R.id.dy_details_back:
                finish();
                break;
            case R.id.dy_details_ticket://购票跳转
                Intent intent = new Intent(MovieDetailsActivity.this, MovieTicketActivity.class);
                intent.putExtra("movieId", mMovieId);
                intent.putExtra("name", mName);
                intent.putExtra("imageUrl", mImageUrl);
                intent.putExtra("movieTypes", mMovieTypes);
                intent.putExtra("director", mDirector);
                intent.putExtra("duration", mDuration);
                intent.putExtra("placeOrigin", mPlaceOrigin);
                startActivity(intent);
                break;
            case R.id.review_pp_xl:
                mDyDetailsLayoutReview.setVisibility(View.GONE);
                break;
            case R.id.review_back:
                mDyDetailsLayoutReview.setVisibility(View.GONE);
                break;
            case R.id.review_publish://发表评论
                initPublish();
                break;
        }
    }

    //发表评论
    private void initPublish() {
        View view = LayoutInflater.from(this).inflate(R.layout.publish_popup_view, null, false);
        final PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        //设置显示位置,findViewById获取的是包含当前整个页面的view
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        mPubEdTxt = (EditText) view.findViewById(R.id.pub_ed_txt);
        TextView PubSend = view.findViewById(R.id.pub_send);
        PubSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> map = new HashMap<>();
                map.put(UserApis.COMMENT_KEY, mMovieId + "");
                map.put(UserApis.COMMENT_COMMENTCONTENT_KEY, mPubEdTxt.getText().toString().trim());
                mMyPresenter.onPostDatas(Apis.USER_COMMENT_URL, map, RegisterBean.class);
                popupWindow.dismiss();
            }
        });

    }


    //点击剧照弹出popupwindow
    private void initStills() {
        View view = LayoutInflater.from(this).inflate(R.layout.stills_popup_view, null, false);
        PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        //设置显示位置,findViewById获取的是包含当前整个页面的view
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAsDropDown(mDyDetailsPrediction, 0, 0);
        initStillsPopup(view, popupWindow);
    }

    private void initStillsPopup(View view, final PopupWindow popupWindow) {
        mStillsPpXl = view.findViewById(R.id.stills_pp_xl);
        mStillsRv = view.findViewById(R.id.stills_pp_rv);
        mStillsPpXl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        initStillsData();
    }

    private void initStillsData() {
        mStillsRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mStillsAdapter = new StillsAdapter(this, mArrayList);
        mStillsRv.setAdapter(mStillsAdapter);
    }

    //点击预告弹出popupwindow
    private void initNotice() {
        View mView = LayoutInflater.from(this).inflate(R.layout.notice_popup_view, null, false);
        PopupWindow mPopupWindow = new PopupWindow(mView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(true);
        //设置显示位置,findViewById获取的是包含当前整个页面的view
        mPopupWindow.showAtLocation(mView, Gravity.BOTTOM, 0, 0);
        //点击空白处时，隐藏掉pop窗口
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.showAsDropDown(mDyDetailsPrediction, 0, 0);
        initNoticePopup(mView, mPopupWindow);
    }

    private void initNoticePopup(View view, final PopupWindow popupWindow) {
        mNoticePpXl = view.findViewById(R.id.notice_pp_xl);
        mNoticeRv = view.findViewById(R.id.notice_rv);
        mNoticePpXl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                JZVideoPlayer.releaseAllVideos();
            }
        });
        initNoticeData();
    }

    private void initNoticeData() {
        mNoticeRv.setLayoutManager(new LinearLayoutManager(this));
        mNoticeRv.setHasFixedSize(true);
        VideoAdapter adapter = new VideoAdapter(this, mShortFilmList);
        mNoticeRv.setAdapter(adapter);
    }

    //点击详情弹出popupwindow
    private void setPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.details_popup_view, null, false);
        PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        //设置显示位置,findViewById获取的是包含当前整个页面的view
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAsDropDown(mDyDetailsDetails, 0, 0);
        initPopup(view, popupWindow);
    }

    private void initPopup(View view, final PopupWindow popupWindow) {
        mDetailsPpXl = view.findViewById(R.id.details_pp_xl);
        mDetailsPpActorName = view.findViewById(R.id.details_pp_actor_name);
        mDetailsPpImg = view.findViewById(R.id.details_pp_img);
        mDetailsPpLx = view.findViewById(R.id.details_pp_lx);
        mDetailsPpDy = view.findViewById(R.id.details_pp_dy);
        mDetailsPpSc = view.findViewById(R.id.details_pp_sc);
        mDetailsPpCd = view.findViewById(R.id.details_pp_cd);
        mDetailsPpJJie = view.findViewById(R.id.details_pp_jjie);
        mDetailsPpXl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        mDetailsPpImg.setImageURI(mUri);
        mDetailsPpLx.setText(mMovieTypes);
        mDetailsPpDy.setText(mDirector);
        mDetailsPpSc.setText(mDuration);
        mDetailsPpCd.setText(mPlaceOrigin);
        mDetailsPpJJie.setText(mSummary);
        mDetailsPpActorName.setText(mStarring);
    }

    /**
     * 查看评论回复的消息
     * @param commentImageView
     * @param result
     */
    private void initCommentPopup(ImageView commentImageView, List<EvaluateCommentBean.ResultBean> result) {
        View view = LayoutInflater.from(this).inflate(R.layout.details_popup_comment, null, false);
        final PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAsDropDown(commentImageView,0,20);
        //获取资源ID
        TextView commentText = view.findViewById(R.id.comment_text);
        ImageView commentPublish = view.findViewById(R.id.comment_publish);
        ImageView commentBack = view.findViewById(R.id.comment_back);
        RecyclerView commentRecycler = view.findViewById(R.id.comment_recycler);
        //返回
        commentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //对回复的评论
        commentPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCommentPublish();
            }
        });
        //recycler列表
        //布局
        commentRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if (result!=null) {
            //TODO:适配器
            CommentRecyclerAdapter commentRecyclerAdapter = new CommentRecyclerAdapter(this, result);
            //评论回复的列表点赞
            commentRecyclerAdapter.setOnClickedListener(new CommentRecyclerAdapter.onClickedListener() {
                @Override
                public void onChecled(int position, ImageView imageView) {
                    Map<String, String> map = new HashMap<>();
                    map.put(UserApis.FILM_COMMENT_DZ, position + "");
                    mMyPresenter.onPostDatas(Apis.COMMENT_DZ_URL, map, CommentLikeBean.class);
                    imageView.setImageResource(R.mipmap.com_icon_praise_selected);
                }
            });
            commentRecycler.setAdapter(commentRecyclerAdapter);
        }
        else {
            ToastUtil.showToast("还未有回复哦");
        }
    }

    /**
     * 用户对评论的回复进行评论
     */
    private void initCommentPublish() {
            View view = LayoutInflater.from(this).inflate(R.layout.publish_popup_view, null, false);
            final PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupWindow.setOutsideTouchable(true);
            //设置显示位置,findViewById获取的是包含当前整个页面的view
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            //点击空白处时，隐藏掉pop窗口
            popupWindow.setFocusable(true);
            popupWindow.setTouchable(true);
            mPubEdTxt = (EditText) view.findViewById(R.id.pub_ed_txt);
            TextView PubSend = view.findViewById(R.id.pub_send);
            PubSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String, String> map = new HashMap<>();
                    map.put(UserApis.COMMENT_COMMENTID_KEY, mCommentId + "");
                    map.put(UserApis.COMMENT_REPLYCONTENT_COMMENTCONTENT_KEY, mPubEdTxt.getText().toString().trim());
                   //添加用户对评论的回复
                    mMyPresenter.onPostDatas(Apis.USER_COMMENT_REPLY_URL, map, CommentLikeBean.class);
                    popupWindow.dismiss();
                }
            });


    }

    @Override
    public void onMySuccess(Object data) {
        if (data instanceof DetailsMovieBean) {
            mArrayList = new ArrayList<>();
            DetailsMovieBean detailsMovieBean = (DetailsMovieBean) data;
            if (detailsMovieBean.getStatus().equals("0000")) {
                DetailsMovieBean.ResultBean result = detailsMovieBean.getResult();
                mDyDetailsName.setText(result.getName());
                mImageUrl = result.getImageUrl();
                mUri = Uri.parse(mImageUrl);
                mDyDetailsImg.setImageURI(mUri);
                mName = result.getName();
                mDirector = result.getDirector();//导演
                mDuration = result.getDuration();//时长
                mPlaceOrigin = result.getPlaceOrigin();//产地
                mSummary = result.getSummary();//简介
                mMovieTypes = result.getMovieTypes();//类型
                mStarring = result.getStarring();//演员名字
                mShortFilmList = result.getShortFilmList();//视频
                mPosterList = result.getPosterList();//剧照
                for (int i = 0; i < mPosterList.size(); i++) {
                    String[] split = mPosterList.get(i).split(",");
                    for (int j = 0; j < split.length; j++) {
                        mArrayList.add(split[j]);
                    }
                }
            }
        } else if (data instanceof ReviewsBean) {//影评
            ReviewsBean reviewBean = (ReviewsBean) data;
            if (reviewBean.getStatus().equals("0000")) {
                final List<ReviewsBean.ResultBean> result = reviewBean.getResult();
                for (int i = 0; i < result.size(); i++) {
                    mCommentId = result.get(i).getCommentId();
                }
                mReviewAdapter = new MyDetaillsReviewAdapter(this, result);
                mReviewPpRv.setAdapter(mReviewAdapter);
                mReviewAdapter.setOnClickedListener(new MyDetaillsReviewAdapter.onClickedListener() {
                    @Override
                    public void onChecled(int position, ImageView imageView) {
                        //判断是否登录
                        if (mUserId==null&&mSessionId==null){
                            AlertDialogUntil.AlertDialogMy(MovieDetailsActivity.this);
                        }else {
                            //评论点赞
                            Map<String, String> map = new HashMap<>();
                            map.put(UserApis.FILM_COMMENT_DZ, position + "");
                            mMyPresenter.onPostDatas(Apis.COMMENT_DZ_URL, map, RegisterBean.class);
                            imageView.setImageResource(R.mipmap.com_icon_praise_selected);
                        }
                    }
                    //查看评论回复的信息
                    @Override
                    public void onComment(int position,ImageView commentImageView) {
                        mPosition1 = position;
                        //查看影片评论回复
                        mMyPresenter.onGetDatas(Apis.CINEMA_EVALUATE_COMMENT+"?commentId="+ mPosition1 +"&page="+1+"&count="+10,EvaluateCommentBean.class);
                        mCommentImageView1 = commentImageView;
                    }

                });
            }
        } else if (data instanceof RegisterBean) {
            RegisterBean registerBean = (RegisterBean) data;
            if (registerBean.getStatus().equals("0000")) {
               mMyPresenter.onGetDatas(Apis.REVIEW_CINEMA + mMovieId + "&page=1&count=10", ReviewsBean.class);
                ToastUtil.showToast(registerBean.getMessage());
            } else {
                ToastUtil.showToast("不能重复点赞");
            }
        }else if (data instanceof EvaluateCommentBean){
            //查询评论回复
            EvaluateCommentBean commentBean=(EvaluateCommentBean)data;
            if (commentBean.getStatus().equals("0000")) {
                if (commentBean.getResult() == null) {
                    ToastUtil.showToast("暂时还未有回复");
                    initCommentPopup(mCommentImageView1, null);
                } else {
                    List<EvaluateCommentBean.ResultBean> result = commentBean.getResult();
                    //查看评论回复的popupwindow
                    initCommentPopup(mCommentImageView1, result);
                }
            }
        }else if (data instanceof CommentLikeBean){
            //回复点赞、对评论的回复的结果
            CommentLikeBean commentLikeBean=(CommentLikeBean)data;
            //如果回复成功，
            if (commentLikeBean.getStatus().equals("0000")){
                mMyPresenter.onGetDatas(Apis.CINEMA_EVALUATE_COMMENT+"?commentId="+ mPosition1 +"&page="+1+"&count="+10,EvaluateCommentBean.class);
        } else {
            ToastUtil.showToast(commentLikeBean.getMessage());
        }
        }
    }



    @Override
    public void onMyFailed(String error) {
        ToastUtil.showToast(error);
    }

    @Override
    protected void onPause() {
        JZVideoPlayer.releaseAllVideos();
        super.onPause();
        // 视频回去的时候要暂停
        ((AudioManager) getSystemService(
                Context.AUDIO_SERVICE)).requestAudioFocus(
                new AudioManager.OnAudioFocusChangeListener() {
                    @Override
                    public void onAudioFocusChange(int focusChange) {
                    }
                }, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
    }

    private String mUserId;
    private String mSessionId;
    //得到userId、sessionId
    @Subscribe(sticky = true)
    public void onLoginIntent(MoveSeatUserID moveSeatUserID){
        mUserId = moveSeatUserID.getUserId()+"";
        mSessionId = moveSeatUserID.getSessionId();
    }
}
