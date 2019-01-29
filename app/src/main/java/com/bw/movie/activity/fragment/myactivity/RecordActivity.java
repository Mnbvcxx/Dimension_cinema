package com.bw.movie.activity.fragment.myactivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.activity.fragment.myactivity.adapter.RecordAdapter;
import com.bw.movie.activity.fragment.myactivity.bean.RecordBean;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: pengbo
 * @date:2019/1/28 desc:购票记录
 */
public class RecordActivity extends BaseActivity {
    @BindView(R.id.record_wait)
    Button mRecordWait;
    @BindView(R.id.record_ok)
    Button mRecordOk;
    @BindView(R.id.record_recycler)
    RecyclerView mRecordRecycler;
    @BindView(R.id.record_request)
    ImageView mRecordRequest;
    private RecordAdapter mRecordAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initwait();
    }

    @OnClick({R.id.record_wait, R.id.record_ok, R.id.record_request})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
                //待付款
            case R.id.record_wait:
                initwait();
                break;
                //已完成
            case R.id.record_ok:
                break;
            case R.id.record_request:
                finish();
                break;
        }
    }

    /**
     * 待付款
     */
    private void initwait() {
        //布局
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecordRecycler.setLayoutManager(manager);
        //适配器
        mRecordAdapter = new RecordAdapter(this);
        mRecordRecycler.setAdapter(mRecordAdapter);
        //网络请求
        doGetData(Apis.RECORD_ACTIVITY+"?page="+1+"&count="+5+"&status="+1,RecordBean.class);

    }

    @Override
    protected void netSuccess(Object object) {
            if (object instanceof RecordBean) {
                RecordBean recordBean = (RecordBean) object;
                if (recordBean.getResult().size() > 0) {
                    mRecordAdapter.setMjihe(recordBean.getResult());
                }else {
                    ToastUtil.showToast("您还没有待付款项");
                }
            }
    }

    @Override
    protected void netFailed(String s) {
        ToastUtil.showToast(s);
    }
}
