package com.bw.movie.apis;

/**
 * @author : FangShiKang
 * @date : 2019/01/24.
 * email : fangshikang@outlook.com
 * desc :       所有 post请求 入参参数
 */
public class UserApis {

    /**
     * 注册所需参数
     */
    public static final String REG_KEY_PHONE = "phone";
    public static final String REG_KEY_NICKNAME = "nickName";
    public static final String REG_KEY_PWD = "pwd";
    public static final String REG_KEY_SEX = "sex";
    public static final String REG_KEY_BIRTHDAY = "birthday";
    public static final String REG_KEY_EMAIL = "email";
    public static final String REG_KEY_PWD2 = "pwd2";

    /**
     * 登录所需参数
     */
    public static final String LOGIN_KEY_PHONE = "phone";
    public static final String LOGIN_KEY_PWD = "pwd";
    /**
     * 微信登录所需参数
     */
    public static final String WX_LOGIN_KEY_PWD = "code";
    /**
     * 影片评论点赞
     */
    public static final String FILM_COMMENT_DZ = "commentId";
    /**
     *根据电影ID和影院ID查询电影排期列表
     */
    public static final String CINEMASID_KEY = "cinemasId";
    public static final String MOVIEID_KEY = "movieId";

}
