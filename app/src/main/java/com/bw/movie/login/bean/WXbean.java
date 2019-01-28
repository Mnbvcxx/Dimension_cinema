package com.bw.movie.login.bean;

/**
 * @author : FangShiKang
 * @date : 2019/01/28.
 * email : fangshikang@outlook.com
 * desc :       微信登录  实体类
 */
public class WXbean {
    /**
     * result : {"sessionId":"15486402541981758","userId":1758,"userInfo":{"headPic":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLriasd3ahl6DsNZibuSwVUpHcG086jlWGw3jMFjVarDoHYCVWjnl3qXakNygB7BBa8tHKx1cvCIXEQ/132","id":1758,"lastLoginTime":1548118809000,"nickName":"稚恃苑年灬_cqm","sex":1}}
     * message : 登陆成功
     * status : 0000
     */

    private ResultBean result;
    private String message;
    private String status;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
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

    public static class ResultBean {
        /**
         * sessionId : 15486402541981758
         * userId : 1758
         * userInfo : {"headPic":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLriasd3ahl6DsNZibuSwVUpHcG086jlWGw3jMFjVarDoHYCVWjnl3qXakNygB7BBa8tHKx1cvCIXEQ/132","id":1758,"lastLoginTime":1548118809000,"nickName":"稚恃苑年灬_cqm","sex":1}
         */

        private String sessionId;
        private int userId;
        private UserInfoBean userInfo;

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

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public static class UserInfoBean {
            /**
             * headPic : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLriasd3ahl6DsNZibuSwVUpHcG086jlWGw3jMFjVarDoHYCVWjnl3qXakNygB7BBa8tHKx1cvCIXEQ/132
             * id : 1758
             * lastLoginTime : 1548118809000
             * nickName : 稚恃苑年灬_cqm
             * sex : 1
             */

            private String headPic;
            private int id;
            private long lastLoginTime;
            private String nickName;
            private int sex;

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public long getLastLoginTime() {
                return lastLoginTime;
            }

            public void setLastLoginTime(long lastLoginTime) {
                this.lastLoginTime = lastLoginTime;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }
        }
    }
}
