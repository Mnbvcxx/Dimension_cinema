package com.bw.movie.activity.fragment;

import android.content.Intent;
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
import com.bw.movie.activity.fragment.myactivity.AttentionActivity;
import com.bw.movie.activity.fragment.myactivity.FeedBacksActivity;
import com.bw.movie.activity.fragment.myactivity.InfoActivity;
import com.bw.movie.activity.fragment.myactivity.MessageActivity;
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
                ToastUtil.showToast("点击了系统消息页");
                Intent message = new Intent(getActivity(), MessageActivity.class);
                startActivity(message);
                break;
            case R.id.my_icon:
                ToastUtil.showToast("点击了我的头像");
                break;
            case R.id.my_name:
                ToastUtil.showToast("点击了我的名字");
                break;
            case R.id.my_sign_in:
                ToastUtil.showToast("点击了签到");
                break;
            case R.id.my_info:
                ToastUtil.showToast("点击了我的信息");
                Intent info = new Intent(getActivity(), InfoActivity.class);
                startActivity(info);
                break;
            case R.id.my_attentions:
                ToastUtil.showToast("点击了我的关注");
                Intent attent = new Intent(getActivity(), AttentionActivity.class);
                startActivity(attent);
                break;
            case R.id.my_rccord:
                ToastUtil.showToast("点击了购票记录");
                break;
            case R.id.my_feedbacks:
                ToastUtil.showToast("点击了意见反馈");
                Intent feedbacks = new Intent(getActivity(), FeedBacksActivity.class);
                startActivity(feedbacks);
                break;
            case R.id.my_version:
                ToastUtil.showToast("点击了最新版本");
                break;
            case R.id.my_logout:
                ToastUtil.showToast("点击了退出登录");
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
