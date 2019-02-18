package com.bw.movie.utils;

/**
 * date：2019/1/27
 * desc：
 */
public class SexUtils {
    public static int onSex(String sex) {
        if (sex.equals("男")) {
            return 1;
        }else if (sex.equals("女")){
            return 2;
        }else {
            ToastUtil.showToast("请输入正确的性别！！！");
            return 0;
        }
    }
}
