package com.bw.movie.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.MainActivity;
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
    @BindView(R.id.login_hint)
    ImageView mLoginHint;
    @BindView(R.id.login_checkbox)
    CheckBox mLoginCheckbox;
    @BindView(R.id.login_jz_pwd)
    RelativeLayout mLoginJzPwd;
    @BindView(R.id.login_checkbox_login)
    CheckBox mLoginCheckboxLogin;
    @BindView(R.id.login_zd_login)
    RelativeLayout mLoginZdLogin;
    private boolean showPassword;
    private SharedPreferences mSP;
    private SharedPreferences.Editor mEdit;
    private String mPhone;
    private String mPwd;
    private Intent mIntent;

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
        //创建SharedPreferences储存数据
        mSP = getSharedPreferences("config", MODE_PRIVATE);
        boolean isCheck = mSP.getBoolean("isCheck", false);//是否记住密码
        mEdit = mSP.edit();
        if (isCheck) {
            mLoginPhone.setText(mSP.getString("phone", mPhone));
            mLoginPwd.setText(mSP.getString("pwd", mPwd));
            mLoginCheckbox.setChecked(true);
        }
    }

    @Override
    protected void netSuccess(Object object) {
        if (object instanceof LoginBean) {
            LoginBean loginBean = (LoginBean) object;
            if (loginBean.getStatus().equals("0000")) {
                //记住密码
                if (mLoginCheckbox.isChecked()) {
                    mEdit.putBoolean("isCheck", true);
                    mEdit.putString("phone", mPhone);
                    mEdit.putString("pwd", mPwd);
                    mEdit.commit();
                } else {//清除记住密码
                    mEdit.clear();
                    mEdit.commit();
                }
                Toast.makeText(this, loginBean.getMessage(), Toast.LENGTH_SHORT).show();
                mIntent = new Intent(this, MainActivity.class);
                mIntent.putExtra("userId", loginBean.getResult().getUserId());
                mIntent.putExtra("sessionId", loginBean.getResult().getSessionId());
                mIntent.putExtra("nickName", loginBean.getResult().getUserInfo().getNickName());
                mIntent.putExtra("headPic", loginBean.getResult().getUserInfo().getHeadPic());
                mIntent.putExtra("phone", loginBean.getResult().getUserInfo().getPhone());
                startActivity(mIntent);
                mSP.edit()
                        .putString("userId", loginBean.getResult().getUserId() + "")
                        .putString("sessionId", loginBean.getResult().getSessionId())
                        .commit();
                finish();
            } else {
                Toast.makeText(this, loginBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void netFailed(String s) {

    }

    @OnClick({R.id.login_phone, R.id.login_pwd, R.id.login_reg, R.id.login_btn_go, R.id.login_wx, R.id.login_hint, R.id.login_checkbox, R.id.login_jz_pwd, R.id.login_checkbox_login, R.id.login_zd_login})
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
                mPhone = mLoginPhone.getText().toString().trim();
                mPwd = mLoginPwd.getText().toString().trim();
                String encrypt = EncryptUtil.encrypt(mPwd);
                Map<String, String> map = new HashMap<>();
                map.put(UserApis.LOGIN_KEY_PHONE, mPhone);
                map.put(UserApis.LOGIN_KEY_PWD, encrypt);
                doPost(Apis.LOGIN_URL, map, LoginBean.class);
                break;
            case R.id.login_wx:
                break;
            case R.id.login_hint:
                if (showPassword) {// 显示密码
                    mLoginHint.setImageDrawable(getResources().getDrawable(R.mipmap.log_icon_eye_default));
                    mLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mLoginPwd.setSelection(mLoginPwd.getText().toString().length());
                    showPassword = !showPassword;
                } else {// 隐藏密码
                    mLoginHint.setImageDrawable(getResources().getDrawable(R.mipmap.login_icon_eye_y));
                    mLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mLoginPwd.setSelection(mLoginPwd.getText().toString().length());
                    showPassword = !showPassword;
                }
                break;
            case R.id.login_checkbox:
                break;
            case R.id.login_jz_pwd:
                break;
            case R.id.login_checkbox_login:
                break;
            case R.id.login_zd_login:
                break;
        }
    }

}
