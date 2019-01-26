package com.bw.movie.wxapi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.mvc.presenter.MyPresenter;
import com.bw.movie.register.bean.RegisterBean;
import com.bw.movie.utils.ToastUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * @author : FangShiKang
 * @date : 2019/01/25.
 * email : fangshikang@outlook.com
 * desc :   微信第三方登录
 */
public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void netSuccess(Object object) {
        if (object instanceof RegisterBean) {
            RegisterBean registerBean = (RegisterBean) object;
            if (registerBean.getStatus().equals("0000")) {
                ToastUtil.showToast(registerBean.getMessage());
            }else {
                ToastUtil.showToast(registerBean.getMessage());
            }
        }
    }

    @Override
    protected void netFailed(String s) {

    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
        //LogUtils.sf(resp.errStr);
        //LogUtils.sf("错误码 : " + resp.errCode + "");
        switch (resp.errCode) {

            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (RETURN_MSG_TYPE_SHARE == resp.getType()) {
                    // UIUtils.showToast("分享失败");
                    Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
                } else {
                    // UIUtils.showToast("登录失败");
                    Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
                }

                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) resp).code;
                        // LogUtils.sf("code = " + code);
                        Toast.makeText(this, "code=" + code, Toast.LENGTH_SHORT).show();
                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求
                        doGetData(Apis.LOGIN_WX_URL + code, RegisterBean.class);
                        break;

                    case RETURN_MSG_TYPE_SHARE:
                        // UIUtils.showToast("微信分享成功");
                        Toast.makeText(this, "微信分享成功", Toast.LENGTH_SHORT).show();
                        finish();
                        break;

                }
                break;

        }
    }
}