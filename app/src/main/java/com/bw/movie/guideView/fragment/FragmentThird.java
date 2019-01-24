package com.bw.movie.guideView.fragment;

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
 * @date : 2019/01/24.
 * email : fangshikang@outlook.com
 * desc :   引导页第三张
 */
public class FragmentThird extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_view_the_third, container, false);
        return view;
    }
}
