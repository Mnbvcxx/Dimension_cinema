package com.bw.movie.register.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.activity.activity.MainActivity;
import com.bw.movie.apis.Apis;
import com.bw.movie.apis.UserApis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login.LoginActivity;
import com.bw.movie.login.bean.LoginBean;
import com.bw.movie.movie.fragment.cinemaActivity.bean.MoveSeatUserID;
import com.bw.movie.register.bean.RegisterBean;
import com.bw.movie.utils.CustomDialog;
import com.bw.movie.utils.EncryptUtil;
import com.bw.movie.utils.IntentUtils;
import com.bw.movie.utils.JudgeNetWorkUtils;
import com.bw.movie.utils.SexUtils;
import com.bw.movie.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author lenovo
 * desc:注册页面
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.reg_txt_nick)
    EditText mRegTxtNick;
    @BindView(R.id.reg_radio_group)
    RadioGroup mRegGroup;
    @BindView(R.id.reg_radio_man)
    RadioButton mRegRadioMan;
    @BindView(R.id.reg_radio_woman)
    RadioButton mRegRadioWoman;
    @BindView(R.id.reg_txt_dte)
    EditText mRegTxtDte;
    @BindView(R.id.reg_txt_pho)
    EditText mRegTxtPho;
    @BindView(R.id.reg_txt_eml)
    EditText mRegTxtEml;
    @BindView(R.id.reg_txt_pwd)
    EditText mRegTxtPwd;
    @BindView(R.id.reg_button)
    Button mRegButton;
    private String mPhone;
    private String mPwd;
    private String mEncrypt_pwd;
    private SharedPreferences mSP;
    private CustomDialog mCustomDialog;
    private String mText;
    private int mOnSex;
    private Intent mIntent;


    //布局
    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //绑定ButterKnife
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

        //创建SharedPreferences储存数据
        mSP = getSharedPreferences("config", MODE_PRIVATE);
        //日期第三方
        mRegTxtDte.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });
        mRegTxtDte.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg();
                }
            }
        });

    }

    //第三方控件  日期格式
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                RegisterActivity.this.mRegTxtDte.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        //设置起始日期和结束日期
        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();

    }

    //点击事件
    @OnClick(R.id.reg_button)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.reg_button://注册按钮
                if (!JudgeNetWorkUtils.hasNetwork(this)) {
                    ToastUtil.showToast("无可用网络，请检查网络是否连接");
                } else {
                    mCustomDialog = new CustomDialog(this);
                    mCustomDialog.show();//显示,显示时页面不可点击,只能点击返回

                    for (int i = 0; i < mRegGroup.getChildCount(); i++) {
                        RadioButton childAt = (RadioButton) mRegGroup.getChildAt(i);
                        if (childAt.isChecked()) {
                            mText = childAt.getText().toString();
                            mOnSex = SexUtils.onSex(mText);
                        }
                    }

                    initButton();


                }
                break;
        }
    }

    private void initButton() {
         Map<String, String> map = new HashMap<>();
        //获取输入的数据
        String name = mRegTxtNick.getText().toString().trim();
        String date = mRegTxtDte.getText().toString().trim();
        mPhone = mRegTxtPho.getText().toString().trim();
        final String email = mRegTxtEml.getText().toString().trim();
        mPwd = mRegTxtPwd.getText().toString().trim();
        //判断昵称
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showToast("输入的昵称不能为空");
        }

        //手机号
        String REGEX = "[1][3458]\\d{9}";
        if (TextUtils.isEmpty(mPhone) || !mPhone.matches(REGEX)) {
            ToastUtil.showToast("请正确输入手机号格式");
        }
        //邮箱
        final String EMAIL = "^[A-Za-z0-9][\\w._]*[a-zA-Z0-9]+@[A-Za-z0-9-_]+\\.([A-Za-z]{2,4})";
        if (TextUtils.isEmpty(email) || !TextUtils.isEmpty(EMAIL)){
            ToastUtil.showToast("请输入正确的邮箱格式！");
        }

        if (TextUtils.isEmpty(mPwd) || mPwd.length() < 6) {
            ToastUtil.showToast("密码最低由六位数字组成！");
        }

        //密码加密
        mEncrypt_pwd = EncryptUtil.encrypt(mPwd);
        map.put(UserApis.REG_KEY_NICKNAME, name);
        map.put(UserApis.REG_KEY_SEX, mOnSex + "");
        map.put(UserApis.REG_KEY_PHONE, mPhone);
        map.put(UserApis.REG_KEY_PWD, mEncrypt_pwd);
        map.put(UserApis.REG_KEY_BIRTHDAY, date);
        map.put(UserApis.REG_KEY_EMAIL, email);
        map.put(UserApis.REG_KEY_PWD2, mEncrypt_pwd);
        //访问数据接口
        doPost(Apis.REGISTER_URL, map, RegisterBean.class);
    }

    /**
     * 网络请求成功
     *
     * @param object
     */
    @Override
    protected void netSuccess(Object object) {
        if (object instanceof RegisterBean) {
            //注册
            RegisterBean registerBean = (RegisterBean) object;
            if (registerBean.getStatus().equals("0000")) {
                        mCustomDialog.dismiss();
                ToastUtil.showToast(registerBean.getMessage());
                //当用户注册成功时不要跳转到登录页面,应该直接访问登录接口进入首页
                Map<String, String> map = new HashMap<>();
                map.put(UserApis.LOGIN_KEY_PHONE, mPhone);
                map.put(UserApis.LOGIN_KEY_PWD, mEncrypt_pwd);
                doPost(Apis.LOGIN_URL, map, LoginBean.class);
            } else {
                ToastUtil.showToast(registerBean.getMessage());
                mCustomDialog.dismiss();
            }
        } else if (object instanceof LoginBean) {
            LoginBean loginBean = (LoginBean) object;
            if (loginBean.getStatus().equals("0000")) {
                ToastUtil.showToast(loginBean.getMessage());
                mIntent = new Intent(RegisterActivity.this, MainActivity.class);
                //intent传值,后续会用到这些参数,尤其是我们的 RequestHeader  入参
                mIntent.putExtra("userId", loginBean.getResult().getUserId()+"");
                MoveSeatUserID moveSeatUserID = new MoveSeatUserID();
                moveSeatUserID.setUserId(loginBean.getResult().getUserId());
                EventBus.getDefault().postSticky(moveSeatUserID);
                Log.i("TAG","moveSeatBean集合中的UserID="+moveSeatUserID);
                mIntent.putExtra("sessionId", loginBean.getResult().getSessionId());
                mIntent.putExtra("nickName", loginBean.getResult().getUserInfo().getNickName());
                mIntent.putExtra("headPic", loginBean.getResult().getUserInfo().getHeadPic());
                mIntent.putExtra("phone", loginBean.getResult().getUserInfo().getPhone());
                startActivity(mIntent);
                mSP.edit()
                        .putString("userId", loginBean.getResult().getUserId() + "")
                        .putString("sessionId", loginBean.getResult().getSessionId())
                        .commit();
                IntentUtils.getInstence().intent(this, MainActivity.class);
                finish();
            }
        }
    }

    /**
     * @param s
     */
    @Override
    protected void netFailed(String s) {
        mCustomDialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCustomDialog.dismiss();
            }
        }, 1000);
        ToastUtil.showToast("请输入完整信息");
    }

}
