package com.bw.movie.movie.fragment.cinemaActivity.bean;

/**
 * @author: pengbo
 * @date:2019/2/19 desc:存储昵称和头像
 */
public class EventBusName {
    String nickName,headPic;
    String toKen;

    public String getToKen() {
        return toKen;
    }

    public void setToKen(String toKen) {
        this.toKen = toKen;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
}
