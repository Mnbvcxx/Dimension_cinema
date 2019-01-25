package com.bw.movie.login;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.activity.MainActivity;
import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.apis.UserApis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login.bean.LoginBean;
import com.bw.movie.register.activity.RegisterActivity;
import com.bw.movie.utils.EncryptUtil;
import com.bw.movie.utils.IntentUtils;
import com.bw.movie.utils.ToastUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录页面
 *
 * 1、记住密码状态
 * 2、自动登录状态
 * 3、快速注册跳转注册页面
 *
 */
public class LoginActivity extends BaseActivity {

    //初始化控件
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
    private IWXAPI mWxapi;

    //布局
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //绑定ButterKnife
        ButterKnife.bind(this);
        registToWX();
    }

    @Override
    protected void initData() {
        //创建SharedPreferences储存数据
        mSP = getSharedPreferences("config", MODE_PRIVATE);
        boolean isCheck = mSP.getBoolean("isCheck", false);//是否记住密码
        boolean isLogin = mSP.getBoolean("isLogin", false);//是否自动登录
        mEdit = mSP.edit();
        if (isCheck) {
            mLoginPhone.setText(mSP.getString("phone", mPhone));
            mLoginPwd.setText(mSP.getString("pwd", mPwd));
            mLoginCheckbox.setChecked(true);
            //判断自动登录多选状态
            if (isLogin) {
                mLoginCheckboxLogin.setChecked(true);
                //跳转
                mIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(mIntent);
                finish();
            }
        }
        //复选框内容改变方法
        initChecked();
    }
    //复选框内容改变方法
    private void initChecked() {
        mLoginCheckboxLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //判断是否自动登录
                if (mLoginCheckboxLogin.isChecked()) {
                    mLoginCheckbox.setChecked(true);
                    mEdit.putBoolean("isLogin", true).commit();
                } else {
                    mEdit.putBoolean("isLogin", false).commit();
                }
            }
        });
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
                ToastUtil.showToast(loginBean.getMessage());
                mIntent = new Intent(this, MainActivity.class);
                //intent传值,后续会用到这些参数,尤其是我们的 RequestHeader  入参
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
                ToastUtil.showToast(loginBean.getMessage());
            }
        }
    }

    @Override
    protected void netFailed(String s) {
        ToastUtil.showToast(s);
    }

    @OnClick({R.id.login_reg, R.id.login_btn_go, R.id.login_wx, R.id.login_hint, R.id.login_checkbox, R.id.login_jz_pwd, R.id.login_checkbox_login, R.id.login_zd_login})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.login_reg:
                IntentUtils.getInstence().intent(this, RegisterActivity.class);
                break;
            case R.id.login_btn_go:
                //动态权限
                //initPermission();
                mPhone = mLoginPhone.getText().toString().trim();
                mPwd = mLoginPwd.getText().toString().trim();
                //手机号  正则表达式验证
                String REGEX = "[1][3458]\\d{9}";
                if (TextUtils.isEmpty(mPhone) || !mPhone.matches(REGEX)) {
                    ToastUtil.showToast("请正确输入手机号格式");
                }
                String encrypt = EncryptUtil.encrypt(mPwd);
                Map<String, String> map = new HashMap<>();
                map.put(UserApis.LOGIN_KEY_PHONE, mPhone);
                map.put(UserApis.LOGIN_KEY_PWD, encrypt);
                doPost(Apis.LOGIN_URL, map, LoginBean.class);
                break;
            case R.id.login_wx:
                initPermission();
                initWxData();
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
        }
    }

    private void initWxData() {
        if (!mWxapi.isWXAppInstalled()) {
            Toast.makeText(this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        mWxapi.sendReq(req);
    }

    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        String APP_ID = "wxb3852e6a6b7d9516";
        mWxapi = WXAPIFactory.createWXAPI(this, APP_ID, false);
        // 将该app注册到微信
        mWxapi.registerApp(APP_ID);
    }

    //动态权限
    private void initPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    }
}
