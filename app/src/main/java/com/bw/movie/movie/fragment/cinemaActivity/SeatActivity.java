package com.bw.movie.movie.fragment.cinemaActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.fragment.myactivity.RecordActivity;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.movie.fragment.cinemaActivity.bean.MoveSeatAmount;
import com.bw.movie.movie.fragment.cinemaActivity.bean.MoveSeatUserID;
import com.bw.movie.movie.fragment.cinemaActivity.bean.MoveTicketBean;
import com.bw.movie.utils.AlertDialogUntil;
import com.bw.movie.utils.MD5Utils;
import com.bw.movie.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: pengbo
 * @date:2019/1/29 desc:下单
 */
public class SeatActivity extends BaseActivity {
    private static final String TAG = "SeatActivity";

    @BindView(R.id.seat_begingtime)
    TextView mSeatBegingtime;
    @BindView(R.id.seat_endtime)
    TextView mSeatEndtime;
    @BindView(R.id.seat_hall)
    TextView mSeatHall;
    @BindView(R.id.seat_price)
    TextView mSeatPrice;
    @BindView(R.id.seat_ok)
    ImageView mSeatOk;
    @BindView(R.id.seat_no)
    ImageView mSeatNo;
    @BindView(R.id.seat_moveseat_view)
    MoveSeatView mMoveSeatView;
    private SpannableString mSpannableString;
    private int mScheduleId;
    private int mNum;
    private int mUserId;

    private int mIntPrice;
    private double mMPrice;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_seat;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void initData() {
        //得到厅号、时间、价格
        Intent intent = getIntent();
        //排期表id
        mScheduleId = intent.getIntExtra("scheduleId", 0);
        Log.i("TAG", "排期表id:" + mScheduleId);
        String hall = intent.getStringExtra("hall");
        String begintime = intent.getStringExtra("begintime");
        String endtime = intent.getStringExtra("endtime");
        mMPrice = intent.getDoubleExtra("price", 0);
        mSeatBegingtime.setText(begintime + "-");
        mSeatEndtime.setText("-" + endtime);
        mSeatHall.setText(hall);
        //TODO：初始价格-->未选座
        double v = mMPrice * 0;
        mSpannableString = changTVsize(v + "");
        mSeatPrice.setText(mSpannableString);
        mMoveSeatView.setData(10, 15);

    }

    @OnClick({R.id.seat_ok, R.id.seat_no})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.seat_ok:
                initpopup();
                break;
            case R.id.seat_no:
                finish();
                break;

        }
    }

    /**
     * 选中支付金额
     */
    private PopupWindow mPopupWindow;
    private ImageView popup_request;
    private CheckBox popup_wei, popup_zhi;
    private Button popup_button;

    private void initpopup() {
        View view = View.inflate(this, R.layout.seat_ok_popup, null);
        popup_request = (ImageView) view.findViewById(R.id.popup_request);
        popup_wei = (CheckBox) view.findViewById(R.id.popup_wei);
        popup_zhi = (CheckBox) view.findViewById(R.id.popup_zhi);
        popup_button = (Button) view.findViewById(R.id.popup_button);
        popup_button.setText("微信支付" + mSpannableString + "元");
        mPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        //点击事件
        popup_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

        popup_wei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_button.setText("微信支付" + mSpannableString + "元");
                popup_zhi.setChecked(false);
            }
        });
        popup_zhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_button.setText("支付宝支付" + mSpannableString + "元");
                popup_wei.setChecked(false);
            }
        });
        //下单的点击事件
        popup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //下单成功跳转到购票记录
                //得到排期表，数量，sign

                HashMap<String, String> map = new HashMap<>();
                map.put("scheduleId", mScheduleId + "");
                map.put("amount", mNum + "");
                String sign = "" + UserId + mScheduleId + mNum + "movie";
                Log.i("TAG", "sign===" + sign);
                map.put("scheduleId", mScheduleId + "");
                map.put("amount", mNum + "");
                //String sign = "" + UserId + mScheduleId + mNum + "movie";
                String convertMD5 = MD5Utils.string2MD5(sign);
                map.put("sign", convertMD5);
                Log.i("TAG", "接口入参：" + map);
                doPost(Apis.MOVE_TICKET, map, MoveTicketBean.class);
            }
        });
    }

    /**
     * 获取选座的数量
     *
     * @param moveSeatBean
     */

    @Subscribe(sticky = true)
    public void onMoveSeatAmount(MoveSeatAmount moveSeatBean) {
        mNum = moveSeatBean.getNum();

        if (moveSeatBean.getNum() == 0) {
            double v = mMPrice * 0;
            mSpannableString = changTVsize(v + "");
            mSeatPrice.setText(mSpannableString);
        } else {
            if (mNum>3){
                mNum=3;
            }
            double v = mMPrice * mNum;
            //保证总价为double
            BigDecimal bg = new BigDecimal(v);
            double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            mSpannableString = changTVsize(f1 + "");
            //TODO：初始价格-->未选座
            mSeatPrice.setText(mSpannableString);
        }
    }

    /**
     * 获取UserId
     */
    int UserId;

    @Subscribe(sticky = true)
    public void onMoveSeatUserId(MoveSeatUserID moveSeatUserID) {
        mUserId = moveSeatUserID.getUserId();
        UserId = mUserId;
        Log.i("TAG", "UserId=" + UserId);
        Log.i("TAG", "mUserId=" + mUserId);
    }


    @Override
    protected void netSuccess(Object object) {
        if (object instanceof MoveTicketBean) {
            MoveTicketBean moveTicketBean = (MoveTicketBean) object;
            if (moveTicketBean.getStatus().equals("0000")) {
                Intent intent = new Intent(this, RecordActivity.class);
                //获取订单号
                intent.putExtra("orderId", moveTicketBean.getOrderId() + "");
                Log.i(TAG,"订单号---"+moveTicketBean.getOrderId() );
                startActivity(intent);
                finish();
            } else {
                AlertDialogUntil.AlertDialogMy(this);
            }
        }
    }

    @Override
    protected void netFailed(String s) {
        ToastUtil.showToast(s);
    }

    /**
     * 小数点前后大小不一致
     *
     * @param value
     * @return
     */
    public static SpannableString changTVsize(String value) {
        SpannableString spannableString = new SpannableString(value);
        if (value.contains(".")) {
            spannableString.setSpan(new RelativeSizeSpan(0.6f), value.indexOf("."), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }



}
