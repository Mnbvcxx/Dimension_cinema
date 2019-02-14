package com.bw.movie.movie.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.movie.fragment.adapter.NearbyAdapter;
import com.bw.movie.movie.fragment.adapter.RecommendedAdapter;
import com.bw.movie.movie.fragment.bean.RecommendedBean;
import com.bw.movie.mvc.presenter.MyPresenter;
import com.bw.movie.mvc.view.MyView;
import com.bw.movie.register.bean.RegisterBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author : FangShiKang
 * @date : 2019/01/25.
 * email : fangshikang@outlook.com
 * desc :       推荐影院
 */
public class FragmentRecommended extends Fragment implements MyView {

    @BindView(R.id.reconm_rv)
    RecyclerView mReconmRv;
    private Unbinder unbinder;
    private MyPresenter mMyPresenter;
    private RecommendedAdapter mRecommendedAdapter;
    private int num;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommended, container, false);
        unbinder = ButterKnife.bind(this, view);
        mMyPresenter = new MyPresenter(this);
        mMyPresenter.onGetDatas(Apis.RECOMMEND_URL, RecommendedBean.class);
        mReconmRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onMySuccess(Object data) {
        if (data instanceof RecommendedBean) {
            RecommendedBean recommendedBean = (RecommendedBean) data;
            if (recommendedBean.getStatus().equals("0000")) {
                List<RecommendedBean.ResultBean> result = recommendedBean.getResult();
                mRecommendedAdapter = new RecommendedAdapter(getActivity(), result);
                mReconmRv.setAdapter(mRecommendedAdapter);
                mRecommendedAdapter.setOnClickedListenrt(new RecommendedAdapter.onClickedListenrt() {
                    @Override
                    public void onClicked(int position, ImageView imageView) {
                        num++;
                        if (num % 2 == 0) {
                            //为偶数时取消关注
                            mMyPresenter.onGetDatas(Apis.CANCELFOLLOW_CINEMA_ID_URL + position, RegisterBean.class);
                            imageView.setImageResource(R.mipmap.com_icon_collection_default);
                        } else {
                            //为奇数时关注成功
                            mMyPresenter.onGetDatas(Apis.FOLLOW_CINEMA_ID_URL + position, RegisterBean.class);
                            imageView.setImageResource(R.mipmap.com_icon_collection_selected);
                        }
                    }
                });
            }
        } else if (data instanceof RegisterBean) {
            RegisterBean registerBean = (RegisterBean) data;
            if (registerBean.getStatus().equals("0000")) {
                Toast.makeText(getActivity(), registerBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMyFailed(String error) {

    }
}
