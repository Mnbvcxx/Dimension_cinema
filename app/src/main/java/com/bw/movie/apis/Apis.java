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
    //正在热映和轮播图
    public static final String MOVIE_BANNER_URL = "movie/v1/findReleaseMovieList?page=1&count=10";
    //热门电影
    public static final String MOVIE_RM_URL = "movie/v1/findHotMovieList?page=1&count=10";
    //即将上映
    public static final String MOVIE_COMINGSOON_URL = "movie/v1/findComingSoonMovieList?page=1&count=10";
    //查询用户当前未读消息数量
    public static final String MESSAGE_UNREAND_COUNT="tool/v1/verify/findUnreadMessageCount";
    //查询系统消息列表
    public static final String MESSAGE_ALLSYS_LIST="tool/v1/verify/findAllSysMsgList";
    //用户关注电影
    public static final String USER_MOVIE_ATTENTION_URL = "movie/v1/verify/followMovie?movieId=";
    //根据电影id查看电影详情
    public static final String MOVIE_DETAILS_URL = "movie/v1/findMoviesDetail?movieId=";
    //意见反馈
    public static final String FEED_BACKS="tool/v1/verify/recordFeedBack";
}
