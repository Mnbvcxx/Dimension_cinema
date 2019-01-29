package com.bw.movie.movie.fragment.cinemaActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: pengbo
 * @date:2019/1/29 desc:选座
 */
public class SeatActivity extends BaseActivity {
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_seat;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        //得到厅号、时间、价格
        Intent intent = getIntent();
        String hall = intent.getStringExtra("hall");
        String begintime = intent.getStringExtra("begintime");
        String endtime = intent.getStringExtra("endtime");
        String price = intent.getStringExtra("price");
        mSeatBegingtime.setText(begintime+"-");
        mSeatEndtime.setText("-"+endtime);
        mSeatHall.setText(hall);
        mSpannableString = changTVsize(price);
        mSeatPrice.setText(mSpannableString);
        mMoveSeatView.setData(10,15);

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
                break;

        }
    }

    /**
     * 选中支付金额
     */
    private PopupWindow mPopupWindow;
    private ImageView popup_request;
    private CheckBox popup_wei,popup_zhi;
    private Button popup_button;
    private void initpopup() {
        View view = View.inflate(this, R.layout.seat_ok_popup, null);
         popup_request =(ImageView) view.findViewById(R.id.popup_request);
         popup_wei =(CheckBox) view.findViewById(R.id.popup_wei);
         popup_zhi =(CheckBox) view.findViewById(R.id.popup_zhi);
         popup_button =(Button) view.findViewById(R.id.popup_button);
        popup_button.setText("微信支付"+mSpannableString+"元");
        mPopupWindow=new PopupWindow(view,WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,true);
        mPopupWindow.showAtLocation(view,Gravity.BOTTOM,0,0);
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
                popup_button.setText("微信支付"+mSpannableString+"元");
                popup_zhi.setChecked(false);
            }
        });
        popup_zhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_button.setText("支付宝支付"+mSpannableString+"元");
                popup_wei.setChecked(false);
            }
        });
        popup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("点击事件");
            }
        });
    }


    @Override
    protected void netSuccess(Object object) {

    }

    @Override
    protected void netFailed(String s) {

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
