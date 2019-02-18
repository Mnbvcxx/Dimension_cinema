package com.bw.movie.movie.fragment.cinemaActivity.bean;

/**
 * @author: pengbo
 * @date:2019/2/13 desc:UserId存储
 */
public class MoveSeatUserID {
    int userId;
    String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
