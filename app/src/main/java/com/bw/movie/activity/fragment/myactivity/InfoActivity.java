package com.bw.movie.activity.fragment.myactivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.bean.ToKenBean;
import com.bw.movie.activity.fragment.myactivity.bean.InfoHeadBean;
import com.bw.movie.activity.fragment.myactivity.bean.MessageInfoBean;
import com.bw.movie.apis.Apis;
import com.bw.movie.apis.UserApis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.movie.fragment.cinemaActivity.bean.EventBusName;
import com.bw.movie.register.bean.RegisterBean;
import com.bw.movie.utils.DateUtils;
import com.bw.movie.utils.EncryptUtil;
import com.bw.movie.utils.FileImageUntils;
import com.bw.movie.utils.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: pengbo
 * @date:2019/1/26 desc:我的信息页面
 */
public class InfoActivity extends BaseActivity {
    @BindView(R.id.info_sdv)
    SimpleDraweeView mInfoSdv;
    @BindView(R.id.info_nick)
    TextView mInfoNick;
    @BindView(R.id.info_sex)
    TextView mInfoSex;
    @BindView(R.id.info_date)
    TextView mInfoDate;
    @BindView(R.id.info_phone)
    TextView mInfoPhone;
    @BindView(R.id.info_mail)
    TextView mInfoMail;
    @BindView(R.id.info_reset_psw)
    SimpleDraweeView mInfoResetPsw;
    @BindView(R.id.info_request)
    SimpleDraweeView mInfoRequest;
    private MessageInfoBean.ResultBean mResult;
    private String filepath = Environment.getExternalStorageDirectory()
            + "/file.png";
    private WindowManager.LayoutParams mParams;
    private String mInfoemail;
    private String mNewEmail;
    private Object mToKen;
    private String mPath;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_info;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);

    }


    @Override
    protected void initData() {
        //根据用户ID查询用户信息
        doGetData(Apis.MESSAGE_USERINFO, MessageInfoBean.class);
        //头像popup
        initsdv();
        //从照相修改
        initPhotograph();
        //从相册修改
        initModify();
        //用户
        inituser();
        //密码
        initpsw();
        //邮箱
        mInfoMail.setText(mInfoemail);
    }


    @Override
    protected void netFailed(String s) {
        ToastUtil.showToast(s);
    }

    @OnClick({R.id.info_sdv, R.id.info_reset_psw, R.id.info_request, R.id.info_nick})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            //点击头像
            case R.id.info_sdv:
                msdvWindow.showAsDropDown(mInfoSdv);
                break;
            //修改用户信息
            case R.id.info_nick:
                userWindow.showAsDropDown(mInfoNick);
                break;
            //重置密码
            case R.id.info_reset_psw:
                pwdWindow.showAtLocation(mInfoResetPsw, Gravity.CENTER, 0, 0);
                pwdWindow.showAsDropDown(mInfoResetPsw, 0, 50);
                break;
            case R.id.info_request:
                finish();
                break;
        }
    }

    /**
     * 重置密码popupwind
     */
    private EditText mPwdOld, mPwdNew, mPwdAgin;
    private Button mPwdBut;

    private void initpsw() {
        //加载popupWindow的子布局
        View mView = View.inflate(this, R.layout.info_pwd_popupwindow, null);
        //获取popupWindow中的控件--通过子布局中的到ID
        mPwdOld = (EditText) mView.findViewById(R.id.pwd_oldpwd);
        mPwdNew = (EditText) mView.findViewById(R.id.pwd_newpwd);
        mPwdAgin = (EditText) mView.findViewById(R.id.pwd_aginpwd);
        mPwdBut = (Button) mView.findViewById(R.id.pwd_but);
        //1.创建popupwindow   contentView 子布局  width,宽   height 高
        pwdWindow = new PopupWindow(mView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置焦点
        pwdWindow.setFocusable(true);
        pwdWindow.setBackgroundDrawable(new ColorDrawable(Color.RED));
        //设置是否可以触摸
        pwdWindow.setTouchable(true);
        mPwdBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到输入的数据
                String oldpwd = mPwdOld.getText().toString();
                String newpwd = mPwdNew.getText().toString();
                String aginpwd = mPwdAgin.getText().toString();
                if (oldpwd.length() < 6 || TextUtils.isEmpty(oldpwd)) {
                    ToastUtil.showToast("密码最低由六位数字组成！");
                } else if (newpwd.length() < 6 || TextUtils.isEmpty(newpwd)) {
                    ToastUtil.showToast("新密码最低由六位数字组成！");
                } else if (aginpwd.length() < 6 || TextUtils.isEmpty(aginpwd)) {
                    ToastUtil.showToast("重输新密码最低由六位数字组成！");
                } else if (oldpwd == newpwd) {
                    ToastUtil.showToast("新旧密码不能一样");
                } else {
                    String encrypt_old = EncryptUtil.encrypt(oldpwd);
                    String encrypt_new = EncryptUtil.encrypt(newpwd);
                    String encrypt_agin = EncryptUtil.encrypt(aginpwd);
                    //请求网络
                    HashMap<String, String> map = new HashMap<>();
                    map.put("oldPwd", encrypt_old);
                    map.put("newPwd", encrypt_new);
                    map.put("newPwd2", encrypt_agin);
                    doPost(Apis.MESSAGE_INFO_PWD, map, RegisterBean.class);
                }

            }
        });
    }

    /**
     * 修改用户确认
     */
    private void inituserbut() {
        //得到新数据
        String newNick = mUserNick.getText().toString();
        String newSex = mUserSex.getText().toString();
        mNewEmail = mUserEmail.getText().toString();
        String EMAIL = "^[A-Za-z0-9][\\w\\._]*[a-zA-Z0-9]+@[A-Za-z0-9-_]+\\.([A-Za-z]{2,4})";
        if (TextUtils.isEmpty(newNick)) {
            ToastUtil.showToast("输入的昵称不能为空");
        } else if (TextUtils.isEmpty(newSex)) {
            ToastUtil.showToast("请正确输入性别为“男”或“女”");
        } else if (TextUtils.isEmpty(mNewEmail) || !mNewEmail.matches(EMAIL)) {
            ToastUtil.showToast("请正确输入邮箱格式");
        } else {
            if (newSex.equals("男")) {
                newSex = "1";
            } else if (newSex.equals("女")) {
                newSex = "2";
            }

            //进行网络请求
            HashMap<String, String> map = new HashMap<>();
            map.put("nickName", newNick);
            map.put("sex", newSex);
            map.put("email", mNewEmail);
            doPost(Apis.MESSAGE_INFO_USER, map, RegisterBean.class);
        }
    }

    /**
     * pupopwindow
     */
    private PopupWindow msdvWindow, userWindow, pwdWindow;
    private TextView mModify;
    private TextView mPhotograph;

    /**
     * 头像popupwindow
     */
    private void initsdv() {
        //加载popupWindow的子布局
        View mView = View.inflate(this, R.layout.info_head_popupwindow, null);
        //获取popupWindow中的控件--通过子布局中的到ID
        mModify = (TextView) mView.findViewById(R.id.Modify_Head);
        mPhotograph = (TextView) mView.findViewById(R.id.Photograph);
        //1.创建popupwindow   contentView 子布局  width,宽   height 高
        msdvWindow = new PopupWindow(mView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置显示位置,findViewById获取的是包含当前整个页面的view
        //设置焦点
        msdvWindow.setFocusable(true);
        msdvWindow.setBackgroundDrawable(new ColorDrawable(Color.RED));
        //设置是否可以触摸
        msdvWindow.setTouchable(true);
    }

    /**
     * 相册
     */
    private void initModify() {
        mModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpick(v);
                msdvWindow.dismiss();
                //动态权限
                initPermission();
            }
        });
    }

    /**
     * 照相
     */
    private void initPhotograph(){
        mPhotograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhoto(v);
                msdvWindow.dismiss();
                initPermission();
            }
        });

    }




    /**
     * 修改用户的弹窗
     */
    private EditText mUserNick, mUserSex, mUserEmail;
    private Button mUserBut;

    private void inituser() {
        View mView = View.inflate(this, R.layout.info_user_popupwindow, null);
        //获取popupWindow中的控件--通过子布局中的到ID
        mUserNick = (EditText) mView.findViewById(R.id.user_nick);
        mUserSex = (EditText) mView.findViewById(R.id.user_sex);
        mUserEmail = (EditText) mView.findViewById(R.id.user_email);
        mUserBut = (Button) mView.findViewById(R.id.user_but);
        //1.创建popupwindow   contentView 子布局  width,宽   height 高
        userWindow = new PopupWindow(mView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //设置焦点
        userWindow.setFocusable(true);
        userWindow.setBackgroundDrawable(new ColorDrawable(Color.RED));
        //设置是否可以触摸
        userWindow.setTouchable(true);
        mUserBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改用户确认
                inituserbut();
            }
        });
    }

    /**
     * 照片
     * @param v
     */
    private void openPhoto(View v) {
        mPath = Environment.getExternalStorageDirectory()+"/image.png";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //在Sdcard存入图片
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mPath)));
        startActivityForResult(intent, 300);
    }
    /**
     * 打开相册
     *
     * @param v
     */
    private void openpick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        //设置图片的格式
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    /**
     * 剪切
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        //调取裁剪
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //得到相册图片的路径
            Uri uri = data.getData();
            Intent intent = new Intent("com.android.camera.action.CROP");
            //将图片设置给裁剪
            intent.setDataAndType(uri, "image/*");
            //设置是否支持裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出的大小
            intent.putExtra("outputX", 250);
            intent.putExtra("outputY", 250);
            //将图片进行返回
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 200);

        }
        if(requestCode == 300 && resultCode == RESULT_OK ){
            //调取裁剪功能  com.android.camera.action.CROP调取裁剪功能的动作
            Intent intent = new Intent("com.android.camera.action.CROP");
            //将图片设置给裁剪
            intent.setDataAndType(Uri.fromFile(new File(mPath)), "image/*");
            //设置是否支持裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出的大小
            intent.putExtra("outputX", 250);
            intent.putExtra("outputY", 250);
            //将图片返回给data
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 200);

        }
        if (requestCode == 200 && resultCode == RESULT_OK) {
            Bitmap bitmap = data.getParcelableExtra("data");
            Log.i("TAG","bitmap=="+bitmap);
            FileImageUntils.setBitmap(bitmap, filepath, 50);
            //TODO:网络请求
            HashMap<String, String> map = new HashMap<>();
            map.put("image", filepath);
            doPostImageData(Apis.MESSAGE_INFO_HEAD, map, InfoHeadBean.class);
        }
    }

    /**
     * 得到数据
     *
     * @param object
     */
    @Override
    protected void netSuccess(Object object) {
        if (object instanceof MessageInfoBean) {
            MessageInfoBean infoBean = (MessageInfoBean) object;
            if (infoBean.getStatus().equals("0000")) {
                mResult = infoBean.getResult();
                //头像
                mInfoSdv.setImageURI(Uri.parse(mResult.getHeadPic()));
                //昵称
                mInfoNick.setText(mResult.getNickName());
                mUserNick.setText(mResult.getNickName());
                mUserEmail.setText(mResult.getEmail());
                mInfoMail.setText(mResult.getEmail());
                //性别
                if (mResult.getSex() == 1) {
                    mInfoSex.setText("男");
                    mUserSex.setText("男");
                } else if (mResult.getSex() == 2) {
                    mInfoSex.setText("女");
                    mUserSex.setText("女");
                }
                //出生日期
                mInfoDate.setText(DateUtils.getDateToString(mResult.getBirthday()));
                //手机号
                mInfoPhone.setText(mResult.getPhone());

            } else {
                ToastUtil.showToast(infoBean.getMessage());
            }
        }
        if (object instanceof InfoHeadBean) {
            InfoHeadBean headBean = (InfoHeadBean) object;
            if (headBean.getStatus().equals("0000")) {
                ToastUtil.showToast(headBean.getMessage());
                //重新请求数据实现即时更新头像图片
                doGetData(Apis.MESSAGE_USERINFO, MessageInfoBean.class);
            }
        }
        if (object instanceof RegisterBean) {
            RegisterBean registerBean = (RegisterBean) object;
            if (registerBean.getStatus().equals("0000")) {
                Map<String, String> map = new HashMap<>();
                map.put(UserApis.TOKEN_KEY, mToKen + "");
                map.put(UserApis.OS_KEY, 1 + "");
                doPost(Apis.USER_POST_TOKEN, map, ToKenBean.class);
                ToastUtil.showToast(registerBean.getMessage());
                userWindow.dismiss();
                pwdWindow.dismiss();
                //重新请求数据实现即时更新头像图片
                doGetData(Apis.MESSAGE_USERINFO, MessageInfoBean.class);
                //邮箱
                mInfoMail.setText(mNewEmail);
            } else {
                ToastUtil.showToast(registerBean.getMessage());
            }
        }
        if (object instanceof ToKenBean){
            ToKenBean toKenBean = (ToKenBean) object;
            if (toKenBean.getStatus().equals("0000")){
                ToastUtil.showToast(toKenBean.getMessage());
            }
        }
    }

    @Subscribe(sticky = true)
    public void onLoginName(EventBusName eventBusName) {
        mToKen = eventBusName.getToKen();
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
