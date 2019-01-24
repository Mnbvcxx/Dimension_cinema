package com.bw.movie.netWork;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author : FangShiKang
 * email : fangshikang@outlook.com
 * desc :   创建被观察者
 */
public interface BaseApis {


    /**
     * 被观察者,ResponseBody消息体
     *
     * @param url
     * @return
     */
    @GET
    Observable<ResponseBody> get(@Url String url);

    @DELETE
    Observable<ResponseBody> delete(@Url String url);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> post(@Url String url, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> postFormBody(@Url String url,  @PartMap Map<String, RequestBody> map);

    @Multipart
    @PUT
    Observable<ResponseBody> put(@Url String url, @PartMap Map<String, RequestBody> map);
}
