package com.bw.movie.activity.fragment.myactivity.bean;

/**
 * @author: pengbo
 * @date:2019/2/14 desc:查询版本
 */
public class NewVersionBean {
    int flag;
    String downloadUrl,message,status;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
