package com.bw.movie.login.bean;

/**
 * @author: pengbo
 * @date:2019/1/27 desc:通过注册得到邮箱、密码的数据并存储
 */
public class EventBusInfoBean {
    String infoemail;
    String infopwd;

    public String getInfopwd() {
        return infopwd;
    }

    public void setInfopwd(String infopwd) {
        this.infopwd = infopwd;
    }

    public String getInfoemail() {
        return infoemail;
    }

    public void setInfoemail(String infoemail) {
        this.infoemail = infoemail;
    }
}
