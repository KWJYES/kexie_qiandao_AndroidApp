package com.example.kexieqiandao.entity;

import com.google.gson.annotations.SerializedName;

public class ComplaintRequestBody {

    @SerializedName("targetUserId")
    private String targetUserId;
    @SerializedName("operatorUserId")
    private String operatorUserId;

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(String operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    @Override
    public String toString() {
        return "ComplaintRequestBody{" +
                "targetUserId='" + targetUserId + '\'' +
                ", operatorUserId='" + operatorUserId + '\'' +
                '}';
    }
}
