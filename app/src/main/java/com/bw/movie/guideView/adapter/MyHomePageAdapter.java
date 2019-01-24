package com.bw.movie.guideView.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/24.
 * email : fangshikang@outlook.com
 * desc :       引导页  适配器
 */
public class MyHomePageAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private List<Fragment> mFragments;

    public MyHomePageAdapter(FragmentManager fm, Context context, List<Fragment> fragments) {
        super(fm);
        mContext = context;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
