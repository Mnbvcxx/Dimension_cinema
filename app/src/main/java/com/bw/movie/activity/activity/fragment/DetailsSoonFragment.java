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
import com.bw.movie.activity.bean.FilmComingSoonBean;
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
 * desc :   即将上映
 */
public class DetailsSoonFragment extends Fragment implements MyView {
    @BindView(R.id.soon_details_rv)
    RecyclerView mSoonDetailsRv;
    @BindView(R.id.soon_back)
    ImageView mSoonBack;
    private View view;
    private Unbinder unbinder;
    private MyPresenter mMyPresenter;
    private DetailsSoonAdapter mDetailsSoonAdapter;
    private int num;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_soon, container, false);
        unbinder = ButterKnife.bind(this, view);
        mMyPresenter = new MyPresenter(this);
        mMyPresenter.onGetDatas(Apis.MOVIE_COMINGSOON_URL, FilmComingSoonBean.class);
        //布局管理器
        mSoonDetailsRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    @OnClick(R.id.soon_back)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.soon_back:
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
        if (data instanceof FilmComingSoonBean) {
            FilmComingSoonBean filmComingSoonBean = (FilmComingSoonBean) data;
            if (filmComingSoonBean.getStatus().equals("0000")) {
                final List<FilmComingSoonBean.ResultBean> result = filmComingSoonBean.getResult();

                mDetailsSoonAdapter = new DetailsSoonAdapter(getActivity(), result);
                mSoonDetailsRv.setAdapter(mDetailsSoonAdapter);
                //关注电影
                mDetailsSoonAdapter.setOnCheckedListener(new DetailsSoonAdapter.onCheckedListener() {
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
                mDetailsSoonAdapter.notifyDataSetChanged();
                mMyPresenter.onGetDatas(Apis.MOVIE_COMINGSOON_URL, FilmComingSoonBean.class);
                ToastUtil.showToast(registerBean.getMessage());
            }
        }
    }

    @Override
    public void onMyFailed(String error) {
        ToastUtil.showToast(error);
    }
}
