package com.bw.movie.wxapi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.bw.movie.MyApplication;
import com.bw.movie.R;
import com.bw.movie.activity.activity.MainActivity;
import com.bw.movie.apis.Apis;
import com.bw.movie.apis.UserApis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login.bean.WXbean;
import com.bw.movie.mvc.view.MyView;
import com.bw.movie.utils.IntentUtils;
import com.bw.movie.utils.ToastUtil;
import com.bw.movie.utils.WeiXinUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : FangShiKang
 * @date : 2019/01/25.
 * email : fangshikang@outlook.com
 * desc :   微信第三方登录
 */
public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler, MyView {

    private static final String TAG = "WXEntryActivity";
    private String mCode;
    private SharedPreferences mSP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        WeiXinUtil.reg(WXEntryActivity.this).handleIntent(getIntent(), this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wxentry;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        //创建SharedPreferences储存数据
        mSP = getSharedPreferences("config", MODE_PRIVATE);
    }

    @Override
    protected void netSuccess(Object object) {

    }

    @Override
    protected void netFailed(String s) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        MyApplication.api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(final BaseResp baseResp) {

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                //主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //得到code
                        mCode = ((SendAuth.Resp) baseResp).code;
                        Map<String, String> map = new HashMap<>();
                        map.put(UserApis.WX_LOGIN_KEY_PWD, mCode);
                        doPost(Apis.LOGIN_WX_URL, map, WXbean.class);
                        Log.e(TAG, mCode);
                    }
                });
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:

                break;
        }
    }

    @Override
    public void onMySuccess(Object data) {
        if (data instanceof WXbean) {
            WXbean wXbean = (WXbean) data;
            if (wXbean.getStatus().equals("0000")) {
                ToastUtil.showToast(wXbean.getMessage());
                mSP.edit()
                        .putString("userId", wXbean.getResult().getUserId() + "")
                        .putString("sessionId", wXbean.getResult().getSessionId())
                        .commit();
                IntentUtils.getInstence().intent(WXEntryActivity.this, MainActivity.class);
                finish();
            }
        }
    }

    @Override
    public void onMyFailed(String error) {

    }
}
