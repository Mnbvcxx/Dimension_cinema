package com.bw.movie.activity.activity.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.activity.activity.fragment.adapter.DetailsRmAdapter;
import com.bw.movie.activity.bean.FilmCinemaxBean;
import com.bw.movie.apis.Apis;
import com.bw.movie.movie.fragment.cinemaActivity.bean.MoveSeatUserID;
import com.bw.movie.mvc.presenter.MyPresenter;
import com.bw.movie.mvc.view.MyView;
import com.bw.movie.register.bean.RegisterBean;
import com.bw.movie.utils.AlertDialogUntil;
import com.bw.movie.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author : FangShiKang
 * @date : 2019/01/26.
 * email : fangshikang@outlook.com
 * desc :   热门电影
 */
public class DetailsRmFragment extends Fragment implements MyView {
    @BindView(R.id.rm_details_rv)
    RecyclerView mRmDetailsRv;
    @BindView(R.id.rm_back)
    ImageView mRmBack;
    private View view;
    private Unbinder unbinder;
    private MyPresenter mMyPresenter;
    private DetailsRmAdapter mDetailsRmAdapter;
    private boolean isCheck = false;
    private int num;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_rm, container, false);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        unbinder = ButterKnife.bind(this, view);
        mMyPresenter = new MyPresenter(this);
        mMyPresenter.onGetDatas(Apis.MOVIE_RM_URL, FilmCinemaxBean.class);
        //布局管理器
        mRmDetailsRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //分割线
        return view;
    }

    //成功
    @Override
    public void onMySuccess(Object data) {
        if (data instanceof FilmCinemaxBean) {
            FilmCinemaxBean filmCinemaxBean = (FilmCinemaxBean) data;
            if (filmCinemaxBean.getStatus().equals("0000")) {
                final List<FilmCinemaxBean.ResultBean> result = filmCinemaxBean.getResult();
                mDetailsRmAdapter = new DetailsRmAdapter(getActivity(), result);
                mRmDetailsRv.setAdapter(mDetailsRmAdapter);
                //关注电影
                mDetailsRmAdapter.setOnCheckedListener(new DetailsRmAdapter.onCheckedListener() {
                    @Override
                    public void onClicked(int position, ImageView imageView) {
                        if (mUserId == null && mSessionId == null) {
                            AlertDialogUntil.AlertDialogMy(getActivity());
                        } else {
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
                    }
                });
            }

        } else if (data instanceof RegisterBean) {
            RegisterBean registerBean = (RegisterBean) data;
            if (registerBean.getStatus().equals("0000")) {
                mDetailsRmAdapter.notifyDataSetChanged();
                mMyPresenter.onGetDatas(Apis.MOVIE_RM_URL, FilmCinemaxBean.class);
                ToastUtil.showToast(registerBean.getMessage());
            }
        }
    }

    //失败
    @Override
    public void onMyFailed(String error) {
        ToastUtil.showToast(error);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //返回键   点击监听事件
    @OnClick(R.id.rm_back)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.rm_back:
                getActivity().finish();
                break;
        }
    }

    /**
     * 得到userid/sessionid
     * @param moveSeatUserID
     */
    private String mUserId;
    private String mSessionId;
    @Subscribe(sticky = true)
    public void onLoginIntent(MoveSeatUserID moveSeatUserID){
        mUserId = moveSeatUserID.getUserId()+"";
        mSessionId = moveSeatUserID.getSessionId();
    }
}
