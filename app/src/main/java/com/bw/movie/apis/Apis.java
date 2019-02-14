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
    public static final String MESSAGE_UNREAND_COUNT = "tool/v1/verify/findUnreadMessageCount";
    //查询系统消息列表
    public static final String MESSAGE_ALLSYS_LIST = "tool/v1/verify/findAllSysMsgList";
    //用户关注电影
    public static final String USER_MOVIE_ATTENTION_URL = "movie/v1/verify/followMovie?movieId=";
    //用户取消关注电影
    public static final String USER_MOVIE_CANCEL_URL = "movie/v1/verify/cancelFollowMovie?movieId=";
    //根据电影id查看电影详情
    public static final String MOVIE_DETAILS_URL = "movie/v1/findMoviesDetail?movieId=";
    //意见反馈
    public static final String FEED_BACKS = "tool/v1/verify/recordFeedBack";
    //推荐影院
    public static final String RECOMMEND_URL = "cinema/v1/findRecommendCinemas?page=1&count=10";
    //附近影院
    public static final String NEARBY_URL = "cinema/v1/findNearbyCinemas?page=1&count=10";
    //根据用户ID查询用户信息
    public static final String MESSAGE_USERINFO="user/v1/verify/getUserInfoByUserId";
    //上传头像
    public static final String MESSAGE_INFO_HEAD="user/v1/verify/uploadHeadPic";
    //修改用户信息
    public static final String MESSAGE_INFO_USER="user/v1/verify/modifyUserInfo";
    //修改密码
    public static final String MESSAGE_INFO_PWD="user/v1/verify/modifyUserPwd";
    //根据电影院名称,模糊查询电影院
    public static final String SEACH_NAME_URL = "cinema/v1/findAllCinemas?page=1&count=10&cinemaName=";
    //根据影院id查询该影院当前排期的电影列表
    public static final String CINEMA_ID_URL = "movie/v1/findMovieListByCinemaId?cinemaId=";
    //根据电影ID和影院ID查询电影排期列表   2&movieId=3
    public static final String MOVIEANDCINEMA_ID_URL = "movie/v1/findMovieScheduleList?cinemasId=";
    //关注影院
    public static final String FOLLOW_CINEMA_ID_URL = "cinema/v1/verify/followCinema?cinemaId=";
    //取消关注影院
    public static final String CANCELFOLLOW_CINEMA_ID_URL = "cinema/v1/verify/cancelFollowCinema?cinemaId=";
    //我关注的电影列表
    public static final String ATTEN_MOVE="movie/v1/verify/findMoviePageList";
    //我关注的影院列表
    public static final String ATTEN_CINEMA="cinema/v1/verify/findCinemaPageList";
    //用户购票记录查询列表
    public static final String RECORD_ACTIVITY="user/v1/verify/findUserBuyTicketRecordList";
    //影院详情
    public static final String CINEMA_INFO="cinema/v1/findCinemaInfo";
    //影院评价
    public static final String CIINEMA_EVALUATE="cinema/v1/findAllCinemaComment";
    //影院评论点赞
    public static final String CINEMA_EVALUATE_GREAT="cinema/v1/verify/cinemaCommentGreat";
    //查询影片评论
    public static final String REVIEW_CINEMA="movie/v1/findAllMovieComment?movieId=";
    //评论点赞
    public static final String COMMENT_DZ_URL="movie/v1/verify/movieCommentGreat";
    //添加用户对影片的评论
    public static final String USER_COMMENT_URL="movie/v1/verify/movieComment";
    //添加用户对评论的回复
    public static final String USER_COMMENT_REPLY_URL="movie/v1/verify/commentReply";
    //购票下单
    public static final String MOVE_TICKET="movie/v1/verify/buyMovieTicket";
    //支付
    public static final String MOVE_RECORD_PAY="movieApi/movie/v1/verify/pay";
    //根据电影ID查询当前排片该电影的影院列表
    public static final String MOVIE_ID_URL="movie/v1/findCinemasListByMovieId?movieId=";
    //根据电影ID和影院ID查询电影排期列表
    public static final String MOVIESCHEDULELIST_ID_URL="movie/v1/findMovieScheduleList?cinemasId=";
    //用户签到
    public static final String USER_SIGNIN_URL="user/v1/verify/userSignIn";
    //查询版本
    public static final String USER_NEW_VERSION="tool/v1/findNewVersion";

}
