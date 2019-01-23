package com.bw.movie.mvc.view;

/**
 * @author : FangShiKang
 * @date : 2019/01/23.
 * email : fangshikang@outlook.com
 * desc :   view层 接口
 */
public interface MyView<T> {
    void onMySuccess(T data);
    void onMyFailed(String error);
}
