package com.bw.movie.activity.activity.fragment.adapter.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.activity.fragment.adapter.bean.DetailsMovieBean;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.mvc.presenter.MyPresenter;
import com.bw.movie.mvc.view.MyView;
import com.bw.movie.utils.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 电影详情页面
 * <p>
 * 当用户点击电影列表中的条目时跳转到电影详情页面
 */
public class MovieDetailsActivity extends AppCompatActivity implements MyView {

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
    @BindView(R.id.dy_details_review)
    TextView mDyDetailsReview;
    @BindView(R.id.dy_details_back)
    ImageView mDyDetailsBack;
    @BindView(R.id.dy_details_ticket)
    TextView mDyDetailsTicket;
    private MyPresenter mMyPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(MovieDetailsActivity.this);
        mMyPresenter = new MyPresenter(this);
        Intent intent = getIntent();
        int movieId = intent.getIntExtra("movieId", 0);
        mMyPresenter.onGetDatas(Apis.MOVIE_DETAILS_URL + movieId, DetailsMovieBean.class);
    }

    /*@Override
    protected int getLayoutId() {
        return R.layout.activity_movie_details;
    }*/

    /*@Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(MovieDetailsActivity.this);
        Intent intent = getIntent();
        int movieId = intent.getIntExtra("movieId", 0);
        doGetData(Apis.MOVIE_DETAILS_URL + movieId, DetailsMovieBean.class);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void netSuccess(Object object) {
        if (object instanceof DetailsMovieBean) {
            DetailsMovieBean detailsMovieBean = (DetailsMovieBean) object;
            if (detailsMovieBean.getStatus().equals("0000")) {
                DetailsMovieBean.ResultBean result = detailsMovieBean.getResult();
                mDyDetailsName.setText(result.getName());
                String imageUrl = result.getImageUrl();
                Uri uri = Uri.parse(imageUrl);
                mDyDetailsImg.setImageURI(uri);
            }
        }
    }

    @Override
    protected void netFailed(String s) {

    }*/

    @OnClick({R.id.dy_details_name, R.id.dy_details_img, R.id.dy_details_details,
            R.id.dy_details_prediction, R.id.dy_details_stills, R.id.dy_details_review,
            R.id.dy_details_back, R.id.dy_details_ticket})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.dy_details_name:
                break;
            case R.id.dy_details_img:
                break;
            case R.id.dy_details_details:
                setPopupWindow();
                break;
            case R.id.dy_details_prediction:
                break;
            case R.id.dy_details_stills:
                break;
            case R.id.dy_details_review:
                break;
            case R.id.dy_details_back:
                finish();
                break;
            case R.id.dy_details_ticket:
                ToastUtil.showToast("敬请期待");
                break;
        }
    }

    //设置popupwindow
    private void setPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.details_popup_view, null, false);
        PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        //设置显示位置,findViewById获取的是包含当前整个页面的view
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAsDropDown(mDyDetailsDetails, 0, 0);
    }

    @Override
    public void onMySuccess(Object data) {
        if (data instanceof DetailsMovieBean) {
            DetailsMovieBean detailsMovieBean = (DetailsMovieBean) data;
            if (detailsMovieBean.getStatus().equals("0000")) {
                DetailsMovieBean.ResultBean result = detailsMovieBean.getResult();
                mDyDetailsName.setText(result.getName());
                String imageUrl = result.getImageUrl();
                Uri uri = Uri.parse(imageUrl);
                mDyDetailsImg.setImageURI(uri);
            }
        }
    }

    @Override
    public void onMyFailed(String error) {
        ToastUtil.showToast(error);
    }
}
