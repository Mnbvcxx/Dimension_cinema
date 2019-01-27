package com.bw.movie.activity.fragment.myactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.mvc.presenter.MyPresenter;
import com.bw.movie.mvc.view.MyView;
import com.bw.movie.register.bean.RegisterBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedBacksActivity extends AppCompatActivity implements MyView {
    @BindView(R.id.feebak_edit)
    EditText mFeebakEdit;
    @BindView(R.id.feebak_scro)
    ScrollView mFeebakScro;
    @BindView(R.id.feebak_success)
    LinearLayout mFeebakSuccess;
    @BindView(R.id.feedback_butn)
    Button mFeedbackButn;
    @BindView(R.id.feebak_requst)
    SimpleDraweeView mFeebakRequst;
    MyPresenter mMyPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_backs);
        ButterKnife.bind(this);
        mMyPresenter=new MyPresenter(this);


    }

    @OnClick({R.id.feedback_butn, R.id.feebak_requst})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.feedback_butn:
                //请求网络
                initbutn();
                break;
            case R.id.feebak_requst:
                finish();
                break;
        }
    }

    /**
     * 网络请求
      */
    private void initbutn() {
        //得到输入的数据
        String feeedit = mFeebakEdit.getText().toString();
        HashMap<String, String> map = new HashMap<>();
        map.put("content",feeedit);
        //网络请求
        mMyPresenter.onPostDatas(Apis.FEED_BACKS,map,RegisterBean.class);
    }

    /**
     * 得到数据
     * @param data
     */
    @Override
    public void onMySuccess(Object data) {
        if (data instanceof RegisterBean){
            RegisterBean registerBean=(RegisterBean)data;
            if (registerBean.getStatus().equals("0000")){
                mFeebakSuccess.setVisibility(View.VISIBLE);
                mFeebakRequst.setVisibility(View.VISIBLE);
                mFeebakScro.setVisibility(View.INVISIBLE);
                mFeedbackButn.setVisibility(View.INVISIBLE);
            }else {
                Toast.makeText(this, registerBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onMyFailed(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
