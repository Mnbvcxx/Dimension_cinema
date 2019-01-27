package com.bw.movie.netWork;

import com.bw.movie.utils.CustomIntercept;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author : FangShiKang
 * email : fangshikang@outlook.com
 * desc :   网络请求工具类
 */
public class RetrofitManager {



    /**
     * 内网环境：172.17.8.100
     *
     * 外网环境：mobile.bwstudent.com
     */
    private final String BASE_URL = "http://mobile.bwstudent.com/movieApi/";

    private static RetrofitManager manager;

    //声明BaseApis
    private BaseApis mBaseApis;
    //单例模式
    public static synchronized RetrofitManager getInstance() {
        if (manager == null) {
            manager = new RetrofitManager();
        }
        return manager;
    }

    public RetrofitManager() {
        //获取 builder对象
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.writeTimeout(15, TimeUnit.SECONDS);//写入超时
        builder.readTimeout(15, TimeUnit.SECONDS);//读取超时
        builder.connectTimeout(15, TimeUnit.SECONDS);//连接超时
        builder.addNetworkInterceptor(new CustomIntercept());//添加自定义拦截器
        builder.retryOnConnectionFailure(true);
        //获取 client 对象
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();
        mBaseApis = retrofit.create(BaseApis.class);
    }

    public Map<String, RequestBody> generateRequestBody(Map<String, String> requestDataMap) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String kay : requestDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(kay) == null ? "" : requestDataMap.get(kay));
            requestBodyMap.put(kay, requestBody);
        }
        return requestBodyMap;
    }

    /**
     * put
     * @param url
     * @param map
     * @param mHttpListener
     * @return
     */
    public RetrofitManager put(String url, Map<String, RequestBody> map, HttpListener mHttpListener) {
        //判断,当map为空的时候,重新创建一个   HashMap
        if (map == null) {
            map = new HashMap<>();
        }
        mBaseApis.put(url,map)
                .subscribeOn(Schedulers.io())//后台执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())//最终完成后执行在哪个线程
                .subscribe(getObserver(mHttpListener));//设置我们的    RxJava
        return manager;
    }


    /**
     * get请求
     *
     * @param url
     * @return
     */
    public RetrofitManager get(String url, HttpListener mHttpListener) {
        mBaseApis.get(url)
                .subscribeOn(Schedulers.io())//后台执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())//最终完成后执行在哪个线程
                .subscribe(getObserver(mHttpListener));//设置我们的    RxJava
        return manager;
    }


    /**
     * delete 请求
     *
     * @param url
     * @return
     */
    public RetrofitManager delete(String url, HttpListener mHttpListener) {
        mBaseApis.delete(url)
                .subscribeOn(Schedulers.io())//后台执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())//最终完成后执行在哪个线程
                .subscribe(getObserver(mHttpListener));//设置我们的    RxJava
        return manager;
    }

    /**
     * 表单post请求
     *
     * @param url
     * @param map
     * @return
     */
    public RetrofitManager postFormBody(String url, Map<String, RequestBody> map, HttpListener mHttpListener) {
        //判断,当map为空的时候,重新创建一个   HashMap
        if (map == null) {
            map = new HashMap<>();
        }
        mBaseApis.postFormBody(url, map)//postFormBody请求
                .subscribeOn(Schedulers.io())//后台执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())//最终完成后执行在哪个线程
                .subscribe(getObserver(mHttpListener));//设置我们的    RxJava
        return manager;
    }

    /**
     * 普通post请求
     *
     * @param url
     * @param map
     * @return
     */
    public RetrofitManager post(String url, Map<String, String> map, HttpListener mHttpListener) {
        //判断,当map为空的时候,重新创建一个   HashMap
        if (map == null) {
            map = new HashMap<>();
        }
        mBaseApis.post(url, map)//post请求
                .subscribeOn(Schedulers.io())//后台执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())//最终完成后执行在哪个线程
                .subscribe(getObserver(mHttpListener));//设置我们的    RxJava
        return manager;
    }


    /**
     * 创建观察者模式
     */

    private Observer getObserver(final HttpListener mHttpListener) {
        Observer mObserver = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (mHttpListener != null) {
                    mHttpListener.onFailed(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String data = responseBody.string();// TODO:注意： 这里是string(),不是toString()!!!
                    if (mHttpListener != null) {
                        mHttpListener.onSuccess(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (mHttpListener != null) {
                        mHttpListener.onFailed(e.getMessage());
                    }
                }

            }

        };
        return mObserver;
    }

    //声明接口
    private HttpListener mHttpListener;

    //  set方法
    public void setHttpListener(HttpListener httpListener) {
        mHttpListener = httpListener;
    }

    //自定义接口
    public interface HttpListener {
        void onSuccess(String data);//成功

        void onFailed(String error);//失败
    }

    /**
     * 10.图片传入
     *  创建MultipartBody.Builder，类型为Form
     */
    public static MultipartBody filesMultipar(Map<String,String> map) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            File file = new File(entry.getValue());
            builder.addFormDataPart(entry.getKey(), "tp.png",
                    RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }

    public RetrofitManager upImage(String path,Map<String,String>map,HttpListener httpListener){
        if (map==null){
            map=new HashMap<>();
        }
        MultipartBody multipartBody = filesMultipar(map);
        mBaseApis.upImage(path,multipartBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(httpListener));
        return manager;
    }


}
