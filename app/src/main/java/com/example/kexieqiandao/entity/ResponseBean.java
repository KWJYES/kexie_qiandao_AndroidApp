package com.example.kexieqiandao.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBean {

    @SerializedName("data")
    private List<DataDTO> data;
    @SerializedName("code")
    private Integer code;
    @SerializedName("msg")
    private String msg;

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataDTO {
        @SerializedName("userId")
        private String userId;
        @SerializedName("userName")
        private String userName;
        @SerializedName("userDept")
        private String userDept;
        @SerializedName("userLocation")
        private String userLocation;
        @SerializedName("status")
        private Integer status;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserDept() {
            return userDept;
        }

        public void setUserDept(String userDept) {
            this.userDept = userDept;
        }

        public String getUserLocation() {
            return userLocation;
        }

        public void setUserLocation(String userLocation) {
            this.userLocation = userLocation;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }
}
