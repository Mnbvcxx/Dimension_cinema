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
import com.bw.movie.movie.fragment.bean.NearbyBean;
import com.bw.movie.movie.fragment.bean.RecommendedBean;
import com.bw.movie.mvc.presenter.MyPresenter;
import com.bw.movie.mvc.view.MyView;
import com.bw.movie.register.bean.RegisterBean;

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
    private int num;

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
                mNearbyAdapter.setOnClickedListener(new NearbyAdapter.onClickedListener() {
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
