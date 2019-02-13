package com.bw.movie.activity.fragment.myactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
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

import com.bw.movie.R;
import com.bw.movie.activity.fragment.myactivity.adapter.Record_Wait_Adapter;
import com.bw.movie.activity.fragment.myactivity.bean.RecordBean;
import com.bw.movie.activity.fragment.myactivity.bean.RecordPayBean;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.movie.fragment.cinemaActivity.bean.MoveTicketBean;
import com.bw.movie.utils.EncryptUtil;
import com.bw.movie.utils.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: pengbo
 * @date:2019/1/28 desc:购票记录：待付款、已完成
 */
public class RecordActivity extends BaseActivity {
    @BindView(R.id.record_wait)
    Button mRecordWait;
    @BindView(R.id.record_ok)
    Button mRecordOk;
    @BindView(R.id.record_recycler)
    RecyclerView mRecordRecycler;
    @BindView(R.id.record_request)
    ImageView mRecordRequest;
    private Record_Wait_Adapter mRecordWaitAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initwait();
    }

    @OnClick({R.id.record_wait, R.id.record_ok, R.id.record_request})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            //待付款
            case R.id.record_wait:
                initwait();
                break;
            //已完成
            case R.id.record_ok:
                break;
            case R.id.record_request:
                finish();
                break;
        }
    }

    /**
     * 待付款
     */
    private void initwait() {
        //布局
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecordRecycler.setLayoutManager(manager);
        //适配器
        mRecordWaitAdapter = new Record_Wait_Adapter(this);
        mRecordRecycler.setAdapter(mRecordWaitAdapter);
        //网络请求
        doGetData(Apis.RECORD_ACTIVITY + "?page=" + 1 + "&count=" + 10 + "&status=" + 1, RecordBean.class);

    }

    @Override
    protected void netSuccess(Object object) {
        //待付款
        if (object instanceof RecordBean) {
            RecordBean recordBean = (RecordBean) object;
            if (recordBean.getResult().size() > 0) {
                mRecordWaitAdapter.setMjihe(recordBean.getResult());
                //弹出popupwindow
                mRecordWaitAdapter.setWaitCallBack(new Record_Wait_Adapter.WaitCallBack() {
                    @Override
                    public void waitcallback() {
                        initpopup();
                    }
                });
            } else {
                ToastUtil.showToast("您还没有待付款项");
            }
        }
        //支付
        if (object instanceof RecordPayBean){
            RecordPayBean recordPayBean=(RecordPayBean)object;
            ToastUtil.showToast("支付情况"+recordPayBean.getMessage());
        }
    }

    /**
     * 选中支付金额
     */
    Intent intent = getIntent();
    private SpannableString mSpannableString;
    private PopupWindow mPopupWindow;
    private ImageView popup_request;
    private CheckBox popup_wei,popup_zhi;
    private Button popup_button;
    int payType;
    private void initpopup() {
        View view = View.inflate(this, R.layout.seat_ok_popup, null);
        popup_request =(ImageView) view.findViewById(R.id.popup_request);
        popup_wei =(CheckBox) view.findViewById(R.id.popup_wei);
        popup_zhi =(CheckBox) view.findViewById(R.id.popup_zhi);
        popup_button =(Button) view.findViewById(R.id.popup_button);
        //得到价钱
        String mPrice = intent.getCharSequenceExtra("price").toString();
        mSpannableString = changTVsize(mPrice +"");
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
        //下单的点击事件
        popup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到订单号
                String orderId = intent.getStringExtra("orderId");
                HashMap<String, String> map = new HashMap<>();
                //如果微信选中，payType==1；
                if (popup_wei.isChecked()){
                    payType=1;
                    //支付宝选中，payType=2;
                }else if (popup_zhi.isChecked()){
                    payType=2;
                }
                //网络请求
                map.put("payType",payType+"");
                map.put("orderId",orderId);
                doPost(Apis.MOVE_RECORD_PAY,map,RecordPayBean.class);
            }
        });
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
    @Override
    protected void netFailed(String s) {
        ToastUtil.showToast(s);
    }
}
