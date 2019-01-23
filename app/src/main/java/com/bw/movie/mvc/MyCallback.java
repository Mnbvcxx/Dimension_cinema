package com.bw.movie.mvc;

/**
 * @author : FangShiKang
 * @date : 2019/01/23.
 * email : fangshikang@outlook.com
 * desc :   Callback  接口层
 */
public interface MyCallback {
    void onSuccess(Object data);

    void onFailed(String error);
}
