package com.bw.movie.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.apis.UserApis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login.bean.LoginBean;
import com.bw.movie.register.activity.RegisterActivity;
import com.bw.movie.utils.EncryptUtil;
import com.bw.movie.utils.IntentUtils;
import com.bw.movie.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_phone)
    EditText mLoginPhone;
    @BindView(R.id.login_pwd)
    EditText mLoginPwd;
    @BindView(R.id.login_reg)
    TextView mLoginReg;
    @BindView(R.id.login_btn_go)
    Button mLoginBtnGo;
    @BindView(R.id.login_wx)
    ImageView mLoginWx;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void netSuccess(Object object) {
        if (object instanceof LoginBean) {
            LoginBean loginBean = (LoginBean) object;
            if (loginBean.getStatus().equals("0000")) {
                ToastUtil.showToast(loginBean.getMessage());
            }
        }
    }

    @Override
    protected void netFailed(String s) {

    }

    @OnClick({R.id.login_phone, R.id.login_pwd, R.id.login_reg, R.id.login_btn_go, R.id.login_wx})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.login_phone:
                break;
            case R.id.login_pwd:
                break;
            case R.id.login_reg:
                IntentUtils.getInstence().intent(this, RegisterActivity.class);
                break;
            case R.id.login_btn_go:
                String phone = mLoginPhone.getText().toString().trim();
                String pwd = mLoginPwd.getText().toString().trim();
                String encrypt = EncryptUtil.encrypt(pwd);
                Map<String, String> map = new HashMap<>();
                map.put(UserApis.LOGIN_KEY_PHONE, phone);
                map.put(UserApis.LOGIN_KEY_PWD, encrypt);
                doPost(Apis.LOGIN_URL, map, LoginBean.class);
                break;
            case R.id.login_wx:
                break;
        }
    }
}
