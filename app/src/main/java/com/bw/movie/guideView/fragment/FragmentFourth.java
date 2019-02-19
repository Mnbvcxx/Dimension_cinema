package com.bw.movie.guideView.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bw.movie.R;
import com.bw.movie.activity.activity.MainActivity;
import com.bw.movie.utils.AlertDialogUntil;

/**
 * @author : FangShiKang
 * @date : 2019/01/24.
 * email : fangshikang@outlook.com
 * desc :   引导页第四张
 */
public class FragmentFourth extends Fragment implements View.OnClickListener {
    private View view;
    /**
     * 立即体验
     */
    private Button mGuideButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_view_the_fourth, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mGuideButton = (Button) view.findViewById(R.id.guide_button);
        mGuideButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.guide_button:
                AlertDialogUntil.AlertDialog(getActivity());

                break;
        }
    }
}
