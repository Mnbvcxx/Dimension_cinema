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

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.movie.fragment.adapter.NearbyAdapter;
import com.bw.movie.movie.fragment.adapter.RecommendedAdapter;
import com.bw.movie.movie.fragment.bean.NearbyBean;
import com.bw.movie.movie.fragment.bean.RecommendedBean;
import com.bw.movie.mvc.presenter.MyPresenter;
import com.bw.movie.mvc.view.MyView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author : FangShiKang
 * @date : 2019/01/25.
 * email : fangshikang@outlook.com
 * desc :       附近影院
 */
public class FragmentNearby extends Fragment implements MyView {

    @BindView(R.id.nearby_rv)
    RecyclerView mNearbyRv;
    private Unbinder unbinder;
    private NearbyAdapter mNearbyAdapter;
    private MyPresenter mMyPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby, container, false);
        unbinder = ButterKnife.bind(this, view);
        mMyPresenter = new MyPresenter(this);
        mMyPresenter.onGetDatas(Apis.NEARBY_URL, NearbyBean.class);
        mNearbyRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onMySuccess(Object data) {
        if (data instanceof NearbyBean) {
            NearbyBean nearbyBean = (NearbyBean) data;
            if (nearbyBean.getStatus().equals("0000")) {
                List<NearbyBean.ResultBean> result = nearbyBean.getResult();
                mNearbyAdapter = new NearbyAdapter(getActivity(), result);
                mNearbyRv.setAdapter(mNearbyAdapter);
            }
        }
    }

    @Override
    public void onMyFailed(String error) {

    }
}
