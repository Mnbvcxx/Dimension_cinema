package com.bw.movie.activity.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/26.
 * email : fangshikang@outlook.com
 * desc :       影片详情页  滑动   适配器
 */
public class MyDetailsAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private List<Fragment> mFragments;

    public MyDetailsAdapter(FragmentManager fm, Context context, List<Fragment> fragments) {
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
