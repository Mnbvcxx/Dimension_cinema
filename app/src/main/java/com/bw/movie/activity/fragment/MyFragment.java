package com.bw.movie.activity.fragment;

import android.os.Bundle;
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

import com.bw.movie.R;
import com.bw.movie.utils.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

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
public class MyFragment extends Fragment {
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
    private View view;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        //绑定ButterKnife
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    //点击事件
    @OnClick({R.id.my_message, R.id.my_icon, R.id.my_name, R.id.my_sign_in, R.id.my_info, R.id.my_attentions, R.id.my_rccord, R.id.my_feedbacks, R.id.my_version, R.id.my_logout})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.my_message:
                ToastUtil.showToast("点击了");
                break;
            case R.id.my_icon:
                ToastUtil.showToast("点击了");
                break;
            case R.id.my_name:
                ToastUtil.showToast("点击了");
                break;
            case R.id.my_sign_in:
                ToastUtil.showToast("点击了");
                break;
            case R.id.my_info:
                ToastUtil.showToast("点击了");
                break;
            case R.id.my_attentions:
                ToastUtil.showToast("点击了");
                break;
            case R.id.my_rccord:
                ToastUtil.showToast("点击了");
                break;
            case R.id.my_feedbacks:
                ToastUtil.showToast("点击了");
                break;
            case R.id.my_version:
                ToastUtil.showToast("点击了");
                break;
            case R.id.my_logout:
                ToastUtil.showToast("点击了");
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
