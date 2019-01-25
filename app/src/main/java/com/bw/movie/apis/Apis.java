package com.bw.movie.apis;

/**
 * @author: pengbo
 * @date:2019/1/24 desc:访问URL接口
 */
public class Apis {
    //注册
    public static final String REGISTER_URL = "user/v1/registerUser";
    //登录
    public static final String LOGIN_URL = "user/v1/login";
    //微信登录
    public static final String LOGIN_WX_URL = "user/v1/weChatBindingLogin";
    //绑定微信账号
    public static final String BANGDING_WX_URL = "user/v1/verify/bindWeChat";
    //是否绑定微信账号
    public static final String BANGDING_ISCHECK_WX_URL = "user/v1/verify/whetherToBindWeChat";
    public static final String MOVIE_BANNER_URL = "movie/v1/findReleaseMovieList?page=1&count=10";
}
