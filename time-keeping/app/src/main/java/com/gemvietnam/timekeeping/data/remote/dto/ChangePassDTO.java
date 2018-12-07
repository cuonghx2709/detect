package com.gemvietnam.timekeeping.data.remote.dto;

import com.google.gson.annotations.SerializedName;


public class ChangePassDTO {
    @SerializedName("oldPassword")
    private String oldPassword;
    @SerializedName("newPassword")
    private String newPassword;

    public ChangePassDTO(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
