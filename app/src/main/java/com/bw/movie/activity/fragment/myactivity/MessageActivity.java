package com.bw.movie.activity.fragment.myactivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.fragment.myactivity.adapter.UnreadAdapter;
import com.bw.movie.activity.fragment.myactivity.bean.MessageRecyclerBean;
import com.bw.movie.activity.fragment.myactivity.bean.MessageUnreadBean;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: pengbo
 * @date:2019/1/26 desc:系统消页面
 */
public class MessageActivity extends BaseActivity {
    @BindView(R.id.message_xt)
    TextView mMessageXt;
    @BindView(R.id.message_unread)
    TextView mMessageUnread;
    @BindView(R.id.message_recycler)
    RecyclerView mMessageRecycler;
    @BindView(R.id.message_request)
    ImageView mMessageRequest;
    private UnreadAdapter mUnreadAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutId();
        initView(savedInstanceState);
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        //TODO:请求网络出错，提示登录？？？？？
        //请求网络查询用户当前未读消息数量
      //  doGetData(Apis.MESSAGE_UNREAND_COUNT,MessageUnreadBean.class);
        //recycler
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mMessageRecycler.setLayoutManager(manager);
        mUnreadAdapter = new UnreadAdapter(this);
        mMessageRecycler.setAdapter(mUnreadAdapter);
        //查询系统消息列表
      //  doGetData(Apis.MESSAGE_ALLSYS_LIST+"?page="+1+"&count="+5,MessageRecyclerBean.class);
    }

    /**
     * TODO：得到数据========出错！！！！！！！！！
     * @param object
     */
    @Override
    protected void netSuccess(Object object) {
        Log.i("TAG","爱上对方过后");
        //得到未读信息数量
        if (object instanceof MessageUnreadBean){
            MessageUnreadBean unreadBean=(MessageUnreadBean)object;
            //将count给文本
            mMessageUnread.setText(unreadBean.getCount()+"条未读");

        }
        //查询系统消息列表
        if (object instanceof MessageRecyclerBean){
            MessageRecyclerBean recyclerBean=(MessageRecyclerBean)object;
            mUnreadAdapter.setMjihe(recyclerBean.getResult());
        }
    }

    @Override
    protected void netFailed(String s) {

    }


    @OnClick(R.id.message_request)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.message_request:
                finish();
                break;
        }
    }
}
