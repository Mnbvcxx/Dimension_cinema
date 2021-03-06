package com.bw.movie.activity.fragment;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bw.movie.R;
import com.bw.movie.activity.activity.FilmDetailsActivity;
import com.bw.movie.activity.activity.MainActivity;
import com.bw.movie.activity.adapter.CinemaFlowAdapter;
import com.bw.movie.activity.adapter.MyFilmCinemaxAdapter;
import com.bw.movie.activity.adapter.MyFilmComingSoonAdapter;
import com.bw.movie.activity.adapter.MyFilmHosMoviesAdapter;
import com.bw.movie.activity.adapter.MyMovieSeachAdapter;
import com.bw.movie.activity.addressselector.CityPickerActivity;
import com.bw.movie.activity.addressselector.addressview.RequestCodeInfo;
import com.bw.movie.activity.bean.FilmBean;
import com.bw.movie.activity.bean.FilmCinemaxBean;
import com.bw.movie.activity.bean.FilmComingSoonBean;
import com.bw.movie.activity.bean.SeachBean;
import com.bw.movie.apis.Apis;
import com.bw.movie.mvc.presenter.MyPresenter;
import com.bw.movie.mvc.view.MyView;
import com.bw.movie.register.bean.RegisterBean;
import com.bw.movie.utils.CustomDialog;
import com.bw.movie.utils.IntentUtils;
import com.bw.movie.utils.NetworkUtils;
import com.bw.movie.utils.ToastUtil;
import com.lwj.widget.viewpagerindicator.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import recycler.coverflow.RecyclerCoverFlow;

import static android.view.View.VISIBLE;

/**
 * @author : FangShiKang
 * @date : 2019/01/25.
 * email : fangshikang@outlook.com
 * desc :   影片  页面
 */
public class FilmFragment extends Fragment implements MyView, View.OnClickListener {
    private static final String TAG = "FilmFragment";
    @BindView(R.id.film_ress)
    ImageView mFilmRess;
    @BindView(R.id.film_ress_name)
    TextView mFilmRessName;
    @BindView(R.id.film_rcf)
    RecyclerCoverFlow mFilmRcf;
    @BindView(R.id.film_vpi)
    ViewPagerIndicator mFilmVpi;
    @BindView(R.id.layout_ress)
    RelativeLayout mLayoutRess;
    @BindView(R.id.rm)
    TextView mRm;
    @BindView(R.id.film_rmdy)
    ImageView mFilmRmdy;
    @BindView(R.id.layout_rm)
    RelativeLayout mLayoutRm;
    @BindView(R.id.film_rm_rv)
    RecyclerView mFilmRmRv;
    @BindView(R.id.layout_remen)
    RelativeLayout mLayoutRemen;
    @BindView(R.id.zz)
    TextView mZz;
    @BindView(R.id.film_zzry)
    ImageView mFilmZzry;
    @BindView(R.id.layout_zz)
    RelativeLayout mLayoutZz;
    @BindView(R.id.film_zz_rv)
    RecyclerView mFilmZzRv;
    @BindView(R.id.layout_zhenzai)
    RelativeLayout mLayoutZhenzai;
    @BindView(R.id.jij)
    TextView mJij;
    @BindView(R.id.film_jjsy)
    ImageView mFilmJjsy;
    @BindView(R.id.layout_jij)
    RelativeLayout mLayoutJij;
    @BindView(R.id.film_jij_rv)
    RecyclerView mFilmJijRv;
    @BindView(R.id.layout_jijiang)
    RelativeLayout mLayoutJijiang;
    private Unbinder unbinder;
    private CinemaFlowAdapter mCinemaFlowAdapter;
    private MyPresenter mMyPresenter;
    private View view;
    @BindView(R.id.film_seach_ima)
    ImageView mFilmSeachIma;
    @BindView(R.id.film_seach_edit)
    EditText mFilmSeachEdit;
    @BindView(R.id.film_seach_text)
    TextView mFilmSeachText;
    @BindView(R.id.film_seach_relative)
    RelativeLayout mFilmSeachRelative;
    @BindView(R.id.homepager_layout)
    RelativeLayout mHomePagerLayout;
    @BindView(R.id.homepager_rv)
    RecyclerView mHomePagerRv;
    private MyFilmCinemaxAdapter mMyFilmCinemaxAdapter;
    private MyFilmComingSoonAdapter mMyFilmComingSoonAdapter;
    private MyFilmHosMoviesAdapter mMyFilmHosMoviesAdapter;

    private List<String> mListImg;
    private MyMovieSeachAdapter mMyMovieSeachAdapter;
    private int num;
    private CustomDialog mCustomDialog;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_film, container, false);
        mCustomDialog = new CustomDialog(getActivity());
        mCustomDialog.show();//显示,显示时页面不可点击,只能点击返回
        /**
         * 通过handler进行延时
         */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCustomDialog.dismiss();
            }
        }, 2000); //停留3秒钟
        unbinder = ButterKnife.bind(this, view);
        if (!NetworkUtils.isConnected(getActivity())) {
            ToastUtil.showToast("网络未连接");
        } else {
            mMyPresenter = new MyPresenter(this);
            mMyPresenter.onGetDatas(Apis.MOVIE_BANNER_URL, FilmBean.class);
            mMyPresenter.onGetDatas(Apis.MOVIE_RM_URL, FilmCinemaxBean.class);
            mMyPresenter.onGetDatas(Apis.MOVIE_COMINGSOON_URL, FilmComingSoonBean.class);
            mFilmRmRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            mFilmZzRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            mFilmJijRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        } else {
            startLocaion();//进入页面开始定位
            Toast.makeText(getActivity(), "已开启定位权限", Toast.LENGTH_LONG).show();
        }
        return view;
    }

    private void startLocaion() {
        mLocationClient = new AMapLocationClient(getContext());
        mLocationClient.setLocationListener(mLocationListener);

        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }


    private String mCity;
    private SharedPreferences mSP;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    Log.i(TAG, "当前定位结果来源-----" + amapLocation.getLocationType());//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    Log.i(TAG, "纬度 ----------------" + amapLocation.getLatitude());//获取纬度
                    Log.i(TAG, "经度-----------------" + amapLocation.getLongitude());//获取经度
                    Log.i(TAG, "精度信息-------------" + amapLocation.getAccuracy());//获取精度信息
                    Log.i(TAG, "地址-----------------" + amapLocation.getAddress());//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    Log.i(TAG, "国家信息-------------" + amapLocation.getCountry());//国家信息
                    Log.i(TAG, "省信息---------------" + amapLocation.getProvince());//省信息
                    Log.i(TAG, "城市信息-------------" + amapLocation.getCity());//城市信息
                    Log.i(TAG, "城区信息-------------" + amapLocation.getDistrict());//城区信息
                    Log.i(TAG, "街道信息-------------" + amapLocation.getStreet());//街道信息
                    Log.i(TAG, "街道门牌号信息-------" + amapLocation.getStreetNum());//街道门牌号信息
                    Log.i(TAG, "城市编码-------------" + amapLocation.getCityCode());//城市编码
                    Log.i(TAG, "地区编码-------------" + amapLocation.getAdCode());//地区编码
                    Log.i(TAG, "当前定位点的信息-----" + amapLocation.getAoiName());//获取当前定位点的AOI信息
                    ToastUtil.showToast("当前定位城市：" + amapLocation.getCity());
                    //创建SharedPreferences储存数据
                    mSP = getActivity().getSharedPreferences("configs", Context.MODE_PRIVATE);
                    mSP.edit().putString("city",amapLocation.getCity()).commit();
                    mCity = amapLocation.getCity();
                    mFilmRessName.setText(amapLocation.getCity());
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    @Override
    @OnClick({R.id.film_ress, R.id.film_rcf, R.id.film_vpi, R.id.film_seach_ima,
            R.id.film_seach_text, R.id.film_rmdy, R.id.film_zzry, R.id.film_jjsy})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.film_ress://点击定位图标跳到地址选择器
                startLocaion();
                Intent intent = new Intent(getActivity(), CityPickerActivity.class);
                intent.putExtra("citys", mCity);
                startActivityForResult(intent, RequestCodeInfo.GETCITY);

                break;
            case R.id.film_rmdy:
                IntentUtils.getInstence().intent(getContext(), FilmDetailsActivity.class);
                break;
            case R.id.film_zzry:
                IntentUtils.getInstence().intent(getContext(), FilmDetailsActivity.class);
                break;
            case R.id.film_jjsy:
                IntentUtils.getInstence().intent(getContext(), FilmDetailsActivity.class);
                break;
            case R.id.film_rcf:
                break;
            case R.id.film_vpi:
                break;
            case R.id.film_seach_ima:
                initfsi();
                break;
            case R.id.film_seach_text://根据关键字查询电影所在电影院
                initfst();
                String movieName = mFilmSeachEdit.getText().toString().trim();
                if (TextUtils.isEmpty(movieName)) {
                    ToastUtil.showToast("不能为空");
                } else {
                    mMyPresenter.onGetDatas(Apis.SEACH_NAME_URL, SeachBean.class);
                }
                break;
        }
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    //点击图标拉伸搜索框
    boolean mBoolean = true;

    private void initfsi() {
        if (mBoolean) {
            ObjectAnimator translationX = ObjectAnimator.ofFloat(mFilmSeachRelative, "translationX", 0, (dp2px(getActivity(), -170)));
            ObjectAnimator alpha = ObjectAnimator.ofFloat(mFilmSeachEdit, "alpha", 0.0f, 1.0f);
            ObjectAnimator alphaButton = ObjectAnimator.ofFloat(mFilmSeachText, "alpha", 0.0f, 1.0f);
            alphaButton.setDuration(1000);
            mFilmSeachText.setVisibility(VISIBLE);
            alphaButton.start();
            alpha.setDuration(1000);
            mFilmSeachEdit.setVisibility(VISIBLE);
            alpha.start();
            //动画时间
            translationX.setDuration(1000);
            translationX.start();
            mBoolean = !mBoolean;
        }
    }

    //收缩搜索框
    private void initfst() {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(mFilmSeachRelative, "translationX", (dp2px(getActivity(), -170)), 0);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mFilmSeachEdit, "alpha", 1.0f, 0.5f, 0.0f);
        ObjectAnimator alphaButton = ObjectAnimator.ofFloat(mFilmSeachText, "alpha", 1.0f, 0.5f, 0.0f);
        alphaButton.setDuration(1000);
        alphaButton.start();
        alpha.setDuration(1000);
        alpha.start();
        translationX.setDuration(1000);
        translationX.start();
        mBoolean = !mBoolean;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onMySuccess(Object data) {
        if (data instanceof FilmBean) {//正在热播和轮播图
            FilmBean filmBean = (FilmBean) data;
            if (filmBean.getStatus().equals("0000")) {
                mListImg = new ArrayList<>();
                final List<FilmBean.ResultBean> result = filmBean.getResult();
                if (result.size() == 0) {
                    ToastUtil.showToast("无数据");
                }
                for (int i = 0; i < result.size(); i++) {
                    mListImg.add(result.get(i).getImageUrl());
                }
                //创建轮播图适配器
                mCinemaFlowAdapter = new CinemaFlowAdapter(getActivity(), mListImg, result);
                //创建正在热播适配器
                mMyFilmHosMoviesAdapter = new MyFilmHosMoviesAdapter(getActivity(), result);
                mFilmZzRv.setAdapter(mMyFilmHosMoviesAdapter);
                mFilmRcf.setIntervalRatio(0.3f);
                mFilmRcf.setGreyItem(true);
                mFilmRcf.setFlatFlow(false);
                mFilmRcf.setAdapter(mCinemaFlowAdapter);
                mFilmRcf.scrollToPosition(2);
            }
        } else if (data instanceof FilmCinemaxBean) {
            //热门电影
            FilmCinemaxBean filmCinemaxBean = (FilmCinemaxBean) data;
            if (filmCinemaxBean.getStatus().equals("0000")) {
                List<FilmCinemaxBean.ResultBean> result = filmCinemaxBean.getResult();
                if (result.size() == 0) {
                    ToastUtil.showToast("无数据");
                }
                //创建热门电影适配器
                mMyFilmCinemaxAdapter = new MyFilmCinemaxAdapter(getActivity(), result);
                mFilmRmRv.setAdapter(mMyFilmCinemaxAdapter);
            }
        } else if (data instanceof FilmComingSoonBean) {
            //即将上映
            FilmComingSoonBean filmComingSoonBean = (FilmComingSoonBean) data;
            if (filmComingSoonBean.getStatus().equals("0000")) {
                List<FilmComingSoonBean.ResultBean> result = filmComingSoonBean.getResult();
                if (result.size() == 0) {
                    ToastUtil.showToast("无数据");
                }
                //创建即将上映适配器
                mMyFilmComingSoonAdapter = new MyFilmComingSoonAdapter(getActivity(), result);
                mFilmJijRv.setAdapter(mMyFilmComingSoonAdapter);
            }
        } else if (data instanceof SeachBean) {
            SeachBean seachBean = (SeachBean) data;
            if (seachBean.getStatus().equals("0000")) {
                List<SeachBean.ResultBean> result = seachBean.getResult();
                if (result.size() == 0) {
                    ToastUtil.showToast("无数据");
                }
                ToastUtil.showToast(seachBean.getMessage());
                mHomePagerLayout.setVisibility(View.GONE);
                mHomePagerRv.setVisibility(VISIBLE);
                mMyMovieSeachAdapter = new MyMovieSeachAdapter(getActivity(), result);
                mHomePagerRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                mHomePagerRv.setAdapter(mMyMovieSeachAdapter);
                mMyMovieSeachAdapter.setOnClickedListenrt(new MyMovieSeachAdapter.onClickedListenrt() {
                    @Override
                    public void onClicked(int position, ImageView imageView) {
                        num++;
                        if (num % 2 == 0) {
                            //为偶数时取消关注
                            mMyPresenter.onGetDatas(Apis.CANCELFOLLOW_CINEMA_ID_URL + position, RegisterBean.class);
                            imageView.setImageResource(R.mipmap.com_icon_collection_default);
                        } else {
                            //为奇数时关注成功
                            mMyPresenter.onGetDatas(Apis.FOLLOW_CINEMA_ID_URL + position, RegisterBean.class);
                            imageView.setImageResource(R.mipmap.com_icon_collection_selected);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onMyFailed(String error) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200://刚才的识别码
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//用户同意权限,执行我们的操作
                    startLocaion();//开始定位
                } else {//用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                    Toast.makeText(getActivity(), "未开启定位权限,请手动到设置去开启权限", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }


    //地址选择器
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeInfo.GETCITY:
                    String city = data.getExtras().getString("city");
                    if (city != null) {
                        System.out.println("ccccccctttttt" + city);
                        mFilmRessName.setText(city);
                    }
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getFourse();
    }

    long exitTime = 0;

    //  点击返回键回退到首页的fragment
    private void getFourse() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

                    if ((System.currentTimeMillis() - exitTime) > 1000) {
                        mHomePagerLayout.setVisibility(View.VISIBLE);
                        mHomePagerRv.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "再按一次退出", Toast.LENGTH_SHORT).show();
                        exitTime = System.currentTimeMillis();
                    } else {
                        getActivity().finish();
                        System.exit(0);
                    }
                    return true;
                }
                return false;
            }
        });
    }
}
