package com.bw.movie.movie.fragment.cinemaActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.movie.bean.CinemaDetailsBean;
import com.bw.movie.movie.bean.MovieAndCinemaBean;
import com.bw.movie.movie.fragment.adapter.CinemaDetailsAdapter;
import com.bw.movie.movie.fragment.adapter.EvaluateAdapter;
import com.bw.movie.movie.fragment.adapter.MovieAndCinemaAdapter;
import com.bw.movie.movie.fragment.bean.DetailsPopupBean;
import com.bw.movie.movie.fragment.bean.EvaluateBean;
import com.bw.movie.register.bean.RegisterBean;
import com.bw.movie.utils.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lwj.widget.viewpagerindicator.ViewPagerIndicator;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * 影院旗下所排期电影
 */
public class CinemaDetailsActivity extends BaseActivity {

    @BindView(R.id.cin_rcf)
    RecyclerCoverFlow mCinRcf;
    @BindView(R.id.cin_vpi)
    ViewPagerIndicator mCinVpi;
    @BindView(R.id.cin_rv)
    RecyclerView mCinRv;
    @BindView(R.id.cin_back)
    ImageView mCinBack;
    @BindView(R.id.cin_img)
    SimpleDraweeView mCinImg;
    @BindView(R.id.cin_name)
    TextView mCinName;
    @BindView(R.id.cin_desc)
    TextView mCinDesc;
    @BindView(R.id.cin_navigation)
    ImageView mCinNavigation;
    private CinemaDetailsAdapter mCinemaDetailsAdapter;
    private MovieAndCinemaAdapter mMovieAndCinemaAdapter;
    private int mCinemaId;
    private EvaluateAdapter mEvaluateAdapter;
    private ImageView thumb;
    private RegisterBean mRegisterBean;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_cinema_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initpopup();
        Intent intent = getIntent();
        mCinemaId = intent.getIntExtra("cinemaId", 0);
        String logo = intent.getStringExtra("logo");
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        Uri uri = Uri.parse(logo);
        mCinImg.setImageURI(uri);
        mCinName.setText(name);
        mCinDesc.setText(address);
        doGetData(Apis.CINEMA_ID_URL + mCinemaId, CinemaDetailsBean.class);
    }

    /**
     * popupwindow
     */
    private PopupWindow mPopupWindow;
    //详情      评价        地区          电话           地铁
    private TextView details, evaluate, details_name, details_phone, details_metro;
    private ImageView request;
    private View details_view, evaluate_view;
    //详情的数据内容
    private RelativeLayout details_details;
    private RecyclerView evaluate_recycler;

    private void initpopup() {
        //加载popupWindow的子布局
        View view = View.inflate(this, R.layout.cinema_details_popupwindow, null);
        //获取popupWindow中的控件
        //通过子布局中的到ID
        details = (TextView) view.findViewById(R.id.details);
        evaluate = (TextView) view.findViewById(R.id.evaluate);
        request = (ImageView) view.findViewById(R.id.request);
        details_name = (TextView) view.findViewById(R.id.details_name);
        details_phone = (TextView) view.findViewById(R.id.details_phone);
        details_metro = (TextView) view.findViewById(R.id.details_metro);
        details_view = (View) view.findViewById(R.id.details_view);
        evaluate_view = (View) view.findViewById(R.id.evaluate_view);
        details_details = (RelativeLayout) view.findViewById(R.id.details_details);
        evaluate_recycler = (RecyclerView) view.findViewById(R.id.evaluate_recycler);
        //1.创建popupwindow   contentView 子布局  width,宽   height 高
        mPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //设置焦点
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //设置是否可以触摸
        mPopupWindow.setTouchable(true);
        //评价的点击事件
        evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluate_view.setVisibility(View.VISIBLE);
                details_view.setVisibility(View.GONE);
                details_details.setVisibility(View.GONE);
                evaluate_recycler.setVisibility(View.VISIBLE);
                initrecy();
            }
        });
        //详情的点击事件
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluate_view.setVisibility(View.GONE);
                details_view.setVisibility(View.VISIBLE);
                details_details.setVisibility(View.VISIBLE);
                evaluate_recycler.setVisibility(View.GONE);
                doGetData(Apis.CINEMA_INFO + "?cinemaId=" + mCinemaId, DetailsPopupBean.class);
            }
        });
        //返回的点击事件
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
    }

    /**
     * 评价的recycler
     */
    private void initrecy() {
        //布局
        LinearLayoutManager manager = new LinearLayoutManager(this);
        evaluate_recycler.setLayoutManager(manager);
        //适配器
        mEvaluateAdapter = new EvaluateAdapter(this);
        evaluate_recycler.setAdapter(mEvaluateAdapter);
        //网络请求
        doGetData(Apis.CIINEMA_EVALUATE + "?cinemaId=" + mCinemaId + "&page=" + 1 + "&count=" + 10, EvaluateBean.class);
    }

    @OnClick({R.id.cin_back, R.id.cin_navigation})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.cin_back:
                this.finish();
                break;
            case R.id.cin_navigation:
                //网络请求
                doGetData(Apis.CINEMA_INFO + "?cinemaId=" + mCinemaId, DetailsPopupBean.class);
                //点击弹框
                mPopupWindow.showAsDropDown(v, 0, 250);
                break;
        }
    }


    @Override
    protected void netSuccess(Object object) {
        if (object instanceof CinemaDetailsBean) {
            CinemaDetailsBean cinemaDetailsBean = (CinemaDetailsBean) object;
            if (cinemaDetailsBean.getStatus().equals("0000")) {
                final List<CinemaDetailsBean.ResultBean> result = cinemaDetailsBean.getResult();
                if (result.size()==0){
                    ToastUtil.showToast("无数据呢");
                }else {
                mCinemaDetailsAdapter = new CinemaDetailsAdapter(this, result);
                mCinRcf.setAdapter(mCinemaDetailsAdapter);
                mCinRcf.scrollToPosition(2);
                //轮播图滑动监听事件
                mCinRcf.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
                    @Override
                    public void onItemSelected(int position) {
                        doGetData(Apis.MOVIEANDCINEMA_ID_URL + mCinemaId + "&movieId=" + position, MovieAndCinemaBean.class);
                    }
                });
              }
            }else {
                    ToastUtil.showToast(cinemaDetailsBean.getMessage());
                    //设置触摸轮播图
                mCinRcf.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
            }
        } else if (object instanceof MovieAndCinemaBean) {
            MovieAndCinemaBean movieAndCinemaBean = (MovieAndCinemaBean) object;
            if (movieAndCinemaBean.getStatus().equals("0000")) {
                List<MovieAndCinemaBean.ResultBean> result = movieAndCinemaBean.getResult();
                //判断电影所在集合长度,为 0 时 ,说明没有数据,进行视图的显示与隐藏
                if (result.size() == 0) {
                    mCinRv.setVisibility(View.GONE);
                    ToastUtil.showToast("该电影暂未排期");
                } else {
                    mCinRv.setVisibility(View.VISIBLE);
                    mMovieAndCinemaAdapter = new MovieAndCinemaAdapter(this, result);
                    mCinRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                    mCinRv.setAdapter(mMovieAndCinemaAdapter);
                    mMovieAndCinemaAdapter.setOnChecked(new MovieAndCinemaAdapter.onChecked() {
                        @Override
                        public void onClicked(int position, List<MovieAndCinemaBean.ResultBean> resultBeans) {
                            Intent intent = new Intent(CinemaDetailsActivity.this, SeatActivity.class);
                            intent.putExtra("scheduleId", resultBeans.get(position).getId());
                            intent.putExtra("hall", resultBeans.get(position).getScreeningHall());
                            intent.putExtra("begintime", resultBeans.get(position).getBeginTime());
                            intent.putExtra("endtime", resultBeans.get(position).getEndTime());
                            intent.putExtra("price", resultBeans.get(position).getPrice());
                            startActivity(intent);
                            finish();
                        }
                    });

                }
            }
        } else if (object instanceof DetailsPopupBean) {
            DetailsPopupBean popupBean = (DetailsPopupBean) object;
            if (popupBean.getStatus().equals("0000")) {
                //影院名称
                details_name.setText(popupBean.getResult().getAddress());
                //影院电话
                details_phone.setText(popupBean.getResult().getPhone());
                //地铁
                String split = popupBean.getResult().getVehicleRoute();
                details_metro.setText(split);

            }
        } else if (object instanceof EvaluateBean) {
            EvaluateBean evaluateBean = (EvaluateBean) object;
            if (evaluateBean.getResult().size() > 0) {
                mEvaluateAdapter.setMjihe(evaluateBean.getResult());
                mEvaluateAdapter.setCallBack(new EvaluateAdapter.CallBack() {
                    @Override
                    public void callback(final ImageView thumbs, final int commentId) {
                        thumb = thumbs;
                        //点赞
                        thumb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                thumbs.setImageResource(R.mipmap.com_icon_praise_selected);
                                //网络请求
                                HashMap<String, String> map = new HashMap<>();
                                map.put("commentId", commentId + "");
                                doPost(Apis.CINEMA_EVALUATE_GREAT, map, RegisterBean.class);
                            }
                        });
                    }
                });

            } else {
                ToastUtil.showToast("暂无评价");
            }
        } else if (object instanceof RegisterBean) {
            mRegisterBean = (RegisterBean) object;
            if (!mRegisterBean.getMessage().isEmpty()) {
                thumb.setImageResource(R.mipmap.com_icon_praise_selected);
                doGetData(Apis.CIINEMA_EVALUATE + "?cinemaId=" + mCinemaId + "&page=" + 1 + "&count=" + 10, EvaluateBean.class);
                ToastUtil.showToast(mRegisterBean.getMessage());
            } else {
                ToastUtil.showToast(mRegisterBean.getMessage());
            }

        }


    }

    @Override
    protected void netFailed(String s) {
        ToastUtil.showToast(s);
    }


}
