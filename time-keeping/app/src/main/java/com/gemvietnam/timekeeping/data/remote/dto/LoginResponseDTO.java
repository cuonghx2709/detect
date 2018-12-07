package com.gemvietnam.timekeeping.data.remote.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class LoginResponseDTO {
    @SerializedName("userDTO")
    private User userDTO;
    @SerializedName("token")
    private String token;

    public User getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(User userDTO) {
        this.userDTO = userDTO;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
