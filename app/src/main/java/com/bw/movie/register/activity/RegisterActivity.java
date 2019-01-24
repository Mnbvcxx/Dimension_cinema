package com.bw.movie.register.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;

import java.util.Calendar;
import java.util.HashMap;

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
    @BindView(R.id.reg_txt_sex)
    EditText mRegTxtSex;
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
    private String mInput_nick;
    private String mInput_dte;
    private String mInput_pho;
    private String mInput_eml;
    private String mInput_pwd;
    private String mInput_sex;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        //获取输入的数据
        mInput_nick = mRegTxtNick.getText().toString();
        mInput_sex = mRegTxtSex.getText().toString();
        mInput_dte = mRegTxtDte.getText().toString();
        mInput_pho = mRegTxtPho.getText().toString();
        mInput_eml = mRegTxtEml.getText().toString();
        mInput_pwd = mRegTxtPwd.getText().toString();

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

    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                RegisterActivity.this.mRegTxtDte.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }


    /**
     * 网络请求成功
     *
     * @param object
     */
    @Override
    protected void netSuccess(Object object) {

    }

    /**
     * @param s
     */
    @Override
    protected void netFailed(String s) {

    }




    @OnClick(R.id.reg_button)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.reg_button:
                initButton();
                break;
        }
    }

    private void initButton() {

        //判断昵称
        if (TextUtils.isEmpty(mInput_nick.trim())){
            Toast.makeText(this, "输入的昵称不能为空", Toast.LENGTH_SHORT).show();
        }
        //判断性别
        if (!mInput_sex.matches("男")||!mInput_sex.matches("女")){
            Toast.makeText(this, "请正确输入性别为“男”或“女”", Toast.LENGTH_SHORT).show();
        }
        //手机号
        String REGEX="[1][3458]\\d{9}";
        if (!mInput_pho.matches(REGEX)){
            Toast.makeText(this, "请正确输入手机号格式", Toast.LENGTH_SHORT).show();
        }
        //邮箱
        String EMAIL="^[A-Za-z0-9][\\w\\._]*[a-zA-Z0-9]+@[A-Za-z0-9-_]+\\.([A-Za-z]{2,4})";
        if (!mInput_eml.matches(EMAIL)){
            Toast.makeText(this, "请正确输入邮箱格式", Toast.LENGTH_SHORT).show();
        }
        //登录密码
        String PASSWORD="/^[A-Za-z]+[0-9]+[A-Za-z0-9]*|[0-9]+[A-Za-z]+[A-Za-z0-9]*$/g";
        if (!mInput_pwd.matches(PASSWORD)){
            Toast.makeText(this, "密码必须由6-16个英文字母和数字的字符串组成！", Toast.LENGTH_SHORT).show();
        }
        //网络请求
        if (!TextUtils.isEmpty(mInput_nick.trim())
                &&mInput_sex.matches("男")||mInput_sex.matches("女")
                &&mInput_pho.matches(REGEX)&&mInput_eml.matches(EMAIL)
                &&mInput_pwd.matches(PASSWORD)){
            //改变男女为1/2
            if (mInput_sex.matches("男")){

            }

            HashMap<String, String> map = new HashMap<>();
            map.put("nickName",mInput_nick);

        }

    }
}
