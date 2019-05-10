package com.base.baselib.bean;

/**
 * Created by dhy
 * Date: 2019/5/9
 * Time: 11:19
 * describe:
 */
public class LoginRequestBean {

    /**
     * data : {"type":"User","attributes":{"phoneNum":"18101771890","password":"Password1","deviceNo":"123456123456","deviceType":"IOS"}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * type : User
         * attributes : {"phoneNum":"18101771890","password":"Password1","deviceNo":"123456123456","deviceType":"IOS"}
         */

        private String type = "User";
        private AttributesBean attributes;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public AttributesBean getAttributes() {
            return attributes;
        }

        public void setAttributes(AttributesBean attributes) {
            this.attributes = attributes;
        }

        public static class AttributesBean {
            /**
             * phoneNum : 18101771890
             * password : Password1
             * deviceNo : 123456123456
             * deviceType : IOS
             */

            private String phoneNum;
            private String password;
            private String deviceNo;
            private String deviceType = "Android";

            public String getPhoneNum() {
                return phoneNum;
            }

            public void setPhoneNum(String phoneNum) {
                this.phoneNum = phoneNum;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getDeviceNo() {
                return deviceNo;
            }

            public void setDeviceNo(String deviceNo) {
                this.deviceNo = deviceNo;
            }

            public String getDeviceType() {
                return deviceType;
            }

            public void setDeviceType(String deviceType) {
                this.deviceType = deviceType;
            }
        }
    }
}
