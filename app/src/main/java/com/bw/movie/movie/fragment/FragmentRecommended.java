package com.bw.movie.movie.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;

/**
 * @author : FangShiKang
 * @date : 2019/01/25.
 * email : fangshikang@outlook.com
 * desc :       推荐影院
 */
public class FragmentRecommended extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommended, container, false);
        return view;
    }
}
