package com.gemvietnam.timekeeping.data.remote.dto;

import com.google.gson.annotations.SerializedName;


public class LoginRequestDTO {
    @SerializedName("username")
    private String email;
    @SerializedName("password")
    private String pass;
//    @SerializedName("deviceToken")
//    private String deviceToken;

    public LoginRequestDTO(String email, String pass) {
        this.email = email;
        this.pass = pass;
//        this.deviceToken = deviceToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

//    public String getDeviceToken() {
//        return deviceToken;
//    }
//
//    public void setDeviceToken(String deviceToken) {
//        this.deviceToken = deviceToken;
//    }
}
