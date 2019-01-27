package com.bw.movie.activity.activity.fragment;

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

import com.bw.movie.R;
import com.bw.movie.activity.activity.fragment.adapter.DetailsRmAdapter;
import com.bw.movie.activity.activity.fragment.adapter.DetailsRyAdapter;
import com.bw.movie.activity.activity.fragment.adapter.DetailsSoonAdapter;
import com.bw.movie.activity.bean.FilmBean;
import com.bw.movie.activity.bean.FilmCinemaxBean;
import com.bw.movie.apis.Apis;
import com.bw.movie.mvc.presenter.MyPresenter;
import com.bw.movie.mvc.view.MyView;
import com.bw.movie.register.bean.RegisterBean;
import com.bw.movie.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author : FangShiKang
 * @date : 2019/01/26.
 * email : fangshikang@outlook.com
 * desc :   正在热映
 */
public class DetailsRyFragment extends Fragment implements MyView {
    @BindView(R.id.ry_details_rv)
    RecyclerView mRyDetailsRv;
    @BindView(R.id.ry_back)
    ImageView mRyBack;
    private View view;
    private Unbinder unbinder;
    private MyPresenter mMyPresenter;
    private DetailsRyAdapter mDetailsRyAdapter;
    private int num;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_ry, container, false);
        unbinder = ButterKnife.bind(this, view);
        mMyPresenter = new MyPresenter(this);
        mMyPresenter.onGetDatas(Apis.MOVIE_BANNER_URL, FilmBean.class);
        //布局管理器
        mRyDetailsRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    @OnClick(R.id.ry_back)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ry_back:
                getActivity().finish();
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
        if (data instanceof FilmBean) {
            FilmBean filmCinemaxBean = (FilmBean) data;
            if (filmCinemaxBean.getStatus().equals("0000")) {
                final List<FilmBean.ResultBean> result = filmCinemaxBean.getResult();
                mDetailsRyAdapter = new DetailsRyAdapter(getActivity(), result);
                mRyDetailsRv.setAdapter(mDetailsRyAdapter);
                //关注电影
                mDetailsRyAdapter.setOnCheckedListener(new DetailsRyAdapter.onCheckedListener() {
                    @Override
                    public void onClicked(int position, ImageView imageView) {
                        num++;
                        if (num % 2 == 0) {
                            //为偶数时取消关注
                            mMyPresenter.onGetDatas(Apis.USER_MOVIE_CANCEL_URL + position, RegisterBean.class);
                            imageView.setImageResource(R.mipmap.com_icon_collection_default);
                        } else {
                            //为奇数时关注成功
                            mMyPresenter.onGetDatas(Apis.USER_MOVIE_ATTENTION_URL + position, RegisterBean.class);
                            imageView.setImageResource(R.mipmap.com_icon_collection_selected);
                        }
                    }
                });
            }

        } else if (data instanceof RegisterBean) {
            RegisterBean registerBean = (RegisterBean) data;
            if (registerBean.getStatus().equals("0000")) {
                ToastUtil.showToast(registerBean.getMessage());
            }
        }
    }

    @Override
    public void onMyFailed(String error) {

    }
}
