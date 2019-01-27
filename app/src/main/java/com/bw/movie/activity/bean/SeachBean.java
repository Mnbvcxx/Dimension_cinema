package com.bw.movie.activity.bean;

import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/27.
 * email : fangshikang@outlook.com
 * desc :       根据电影院名称,模糊查询电影院   实体类
 */
public class SeachBean {

    /**
     * result : [{"address":"东城区滨河路乙1号雍和航星园74-76号楼","commentTotal":0,"distance":0,"followCinema":0,"id":1,"logo":"http://mobile.bwstudent.com/images/movie/logo/qcgx.jpg","name":"青春光线电影院"}]
     * message : 查询成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * address : 东城区滨河路乙1号雍和航星园74-76号楼
         * commentTotal : 0
         * distance : 0
         * followCinema : 0
         * id : 1
         * logo : http://mobile.bwstudent.com/images/movie/logo/qcgx.jpg
         * name : 青春光线电影院
         */

        private String address;
        private int commentTotal;
        private int distance;
        private int followCinema;
        private int id;
        private String logo;
        private String name;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getCommentTotal() {
            return commentTotal;
        }

        public void setCommentTotal(int commentTotal) {
            this.commentTotal = commentTotal;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getFollowCinema() {
            return followCinema;
        }

        public void setFollowCinema(int followCinema) {
            this.followCinema = followCinema;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
