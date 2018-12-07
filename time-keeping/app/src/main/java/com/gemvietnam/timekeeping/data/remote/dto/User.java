
package com.gemvietnam.timekeeping.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("active")
    private Boolean mActive= true;
    @SerializedName("email")
    private String mEmail = "";
    @SerializedName("fullName")
    private String mFullName = "";
    @SerializedName("uuid")
    private String mId = "";
    @SerializedName("role")
    private String mRole = "";
    @SerializedName("team")
    private String mTeam = "";
    @SerializedName("type")
    private String mType = "";
    @SerializedName("code")
    private String code = "";
    @SerializedName("embeddings")
    private String embeddings="";


    public Boolean getmActive() {
        return mActive;
    }

    public void setmActive(Boolean mActive) {
        this.mActive = mActive;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmFullName() {
        return mFullName;
    }

    public void setmFullName(String mFullName) {
        this.mFullName = mFullName;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmRole() {
        return mRole;
    }

    public void setmRole(String mRole) {
        this.mRole = mRole;
    }

    public String getmTeam() {
        return mTeam;
    }

    public void setmTeam(String mTeam) {
        this.mTeam = mTeam;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String[] getEmbeddings() {
        return embeddings.split(",");
    }

    public void setEmbeddings(String embeddings) {
        this.embeddings = embeddings;
    }

    @Override
    public String toString() {
        return "User{" +
                "mActive=" + mActive +
                ", mEmail='" + mEmail + '\'' +
                ", mFullName='" + mFullName + '\'' +
                ", mId='" + mId + '\'' +
                ", mRole='" + mRole + '\'' +
                ", mTeam='" + mTeam + '\'' +
                ", mType='" + mType + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
