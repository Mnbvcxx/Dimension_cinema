package com.bw.movie.wxapi;

import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.MyApplication;
import com.bw.movie.R;
import com.bw.movie.activity.fragment.myactivity.RecordActivity;
import com.bw.movie.utils.ToastUtil;
import com.bw.movie.utils.WeiXinUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private static PayReq req;
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);
        if (api==null){
            api=WXAPIFactory.createWXAPI(MyApplication.getmContext(),null);
            req=new PayReq();
            api.registerApp(WeiXinUtil.APP_ID);
        }

        WeiXinUtil.reg(WXPayEntryActivity.this).handleIntent(getIntent(), this);
        Intent intent = getIntent();
        req.appId = intent.getStringExtra("appId");
        req.nonceStr = intent.getStringExtra("nonceStr");
        req.partnerId = intent.getStringExtra("partnerId");
        req.prepayId = intent.getStringExtra("prepayId");
        req.sign = intent.getStringExtra("sign");
        req.timeStamp = intent.getStringExtra("timeStamp");
        req.packageValue = intent.getStringExtra("packageValue");
        api.sendReq(req);

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode==0){
                ToastUtil.showToast("付款成功");
                Intent intent = new Intent(this, RecordActivity.class);
                startActivity(intent);
            }else if (resp.errCode==-2){
                ToastUtil.showToast("您已取消付款");
            }else {
                ToastUtil.showToast("参数错误");
            }
        }
        finish();
    }
}
