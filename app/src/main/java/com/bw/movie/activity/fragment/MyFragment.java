package com.bw.movie.activity.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.fragment.myactivity.AttentionActivity;
import com.bw.movie.activity.fragment.myactivity.FeedBacksActivity;
import com.bw.movie.activity.fragment.myactivity.InfoActivity;
import com.bw.movie.activity.fragment.myactivity.MessageActivity;
import com.bw.movie.activity.fragment.myactivity.RecordActivity;
import com.bw.movie.activity.fragment.myactivity.bean.MessageInfoBean;
import com.bw.movie.activity.fragment.myactivity.bean.NewVersionBean;
import com.bw.movie.apis.Apis;
import com.bw.movie.login.LoginActivity;
import com.bw.movie.mvc.presenter.MyPresenter;
import com.bw.movie.mvc.view.MyView;
import com.bw.movie.register.bean.RegisterBean;
import com.bw.movie.utils.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author : FangShiKang
 * @date : 2019/01/25.
 * email : fangshikang@outlook.com
 * desc :       我的  页面
 */
public class MyFragment extends Fragment implements MyView {
    @BindView(R.id.my_message)
    ImageView mMyMessage;
    @BindView(R.id.my_icon)
    SimpleDraweeView mMyIcon;
    @BindView(R.id.my_name)
    TextView mMyName;
    @BindView(R.id.my_sign_in)
    Button mMySignIn;
    @BindView(R.id.my_info)
    RelativeLayout mMyInfo;
    @BindView(R.id.my_attentions)
    RelativeLayout mMyAttentions;
    @BindView(R.id.my_rccord)
    RelativeLayout mMyRccord;
    @BindView(R.id.my_feedbacks)
    RelativeLayout mMyFeedbacks;
    @BindView(R.id.my_version)
    RelativeLayout mMyVersion;
    @BindView(R.id.my_logout)
    RelativeLayout mMyLogout;
    private Unbinder unbinder;
    private MyPresenter mMyPresenter;
    private NewVersionBean mVersionBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        //绑定ButterKnife
        unbinder = ButterKnife.bind(this, view);
        mMyPresenter = new MyPresenter(this);
        //根据用户ID查询用户信息
        mMyPresenter.onGetDatas(Apis.MESSAGE_USERINFO, MessageInfoBean.class);
        return view;
    }

    //点击事件
    @OnClick({R.id.my_message, R.id.my_icon, R.id.my_name, R.id.my_sign_in, R.id.my_info, R.id.my_attentions, R.id.my_rccord, R.id.my_feedbacks, R.id.my_version, R.id.my_logout})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.my_message:
                Intent message = new Intent(getActivity(), MessageActivity.class);
                startActivity(message);
                break;
            case R.id.my_icon:
                break;
            case R.id.my_name:
                break;
            case R.id.my_sign_in:
                mMyPresenter.onGetDatas(Apis.USER_SIGNIN_URL, RegisterBean.class);
                break;
            case R.id.my_info:
                ToastUtil.showToast("点击了我的信息");
                Intent info = new Intent(getActivity(), InfoActivity.class);
                startActivity(info);
                break;
            case R.id.my_attentions:
                Intent attent = new Intent(getActivity(), AttentionActivity.class);
                startActivity(attent);
                break;
            case R.id.my_rccord:
                Intent record = new Intent(getActivity(), RecordActivity.class);
                startActivity(record);
                break;
            case R.id.my_feedbacks:
                Intent feedbacks = new Intent(getActivity(), FeedBacksActivity.class);
                startActivity(feedbacks);
                break;
            case R.id.my_version:
                //请求网络
                //mMyPresenter.onGetDatas(Apis.USER_NEW_VERSION, NewVersionBean.class);
                break;
            case R.id.my_logout:
                //跳转到登录页
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onMySuccess(Object data) {
        if (data instanceof MessageInfoBean) {
            MessageInfoBean infoBean = (MessageInfoBean) data;
            if (infoBean.getStatus().equals("0000")) {
                MessageInfoBean.ResultBean result = infoBean.getResult();
                String headPic = result.getHeadPic();
                Uri parse = Uri.parse(headPic);
                String nickName = result.getNickName();
                mMyIcon.setImageURI(parse);
                mMyName.setText(nickName);
                //根据用户ID查询用户信息
                mMyPresenter.onGetDatas(Apis.MESSAGE_USERINFO, MessageInfoBean.class);
            }
        } else if (data instanceof RegisterBean) {
            RegisterBean registerBean = (RegisterBean) data;
            if (registerBean.getStatus().equals("0000")) {
                ToastUtil.showToast(registerBean.getMessage());
            } else {
                ToastUtil.showToast(registerBean.getMessage());
            }
        } else if (data instanceof NewVersionBean) {
            mVersionBean = (NewVersionBean) data;
            if (mVersionBean.getStatus().equals("0000")) {
                if (mVersionBean.getFlag() == 1) {
                    //弹框
                    initversion();
                } else if (mVersionBean.getFlag() == 2) {
                    ToastUtil.showToast("当期版本已是最新版本，无需更新");
                }
            }
        }
    }

    /**
     * 版本更新的弹框
     */
    private void initversion() {
        final Dialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.version_user_dialog, null);
        dialog.setContentView(view);
        //立即更新
        final Button btnOk = (Button) view.findViewById(R.id.btn_update_id_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String url = mVersionBean.getDownloadUrl();
                loadNewVersionProgress(url);
            }
        });
        //以后再说
        Button btnCancel = (Button) view.findViewById(R.id.btn_update_id_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    @Override
    public void onMyFailed(String error) {
        ToastUtil.showToast(error);
    }

    /**
     * 下载新版本程序，需要子线程
     */
    private void loadNewVersionProgress(final String uri) {
        //final   String uri="http://www.apk.anzhi.com/data3/apk/201703/14/4636d7fce23c9460587d602b9dc20714_88002100.apk";
        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        //启动子线程下载任务
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(uri, pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    //下载apk失败
                    ToastUtil.showToast("下载新版本失败");
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 从服务器获取apk文件的代码
     * 传入网址uri，进度条对象即可获得一个File文件
     * （要在子线程中执行哦）
     */
    public static File getFileFromServer(String uri, ProgressDialog pd) throws Exception {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            long time = System.currentTimeMillis();//当前时间的毫秒数
            File file = new File(Environment.getExternalStorageDirectory(), time + "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    /**
     * 安装apk
     */
    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

}
