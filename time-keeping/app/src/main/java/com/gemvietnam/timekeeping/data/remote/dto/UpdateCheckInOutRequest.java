
package com.gemvietnam.timekeeping.data.remote.dto;

import com.google.gson.annotations.SerializedName;


public class UpdateCheckInOutRequest {

    @SerializedName("qrCode")
    private String mQrCode;
    @SerializedName("uuid")
    private String mId;
    private String time;
    private int isTimeIn;

    public UpdateCheckInOutRequest(String mQrCode, String mId, String time, int isTimeIn) {
        this.mQrCode = mQrCode;
        this.mId = mId;
        this.time = time;
        this.isTimeIn = isTimeIn;
    }

    public String getQrCode() {
        return mQrCode;
    }

    public void setQrCode(String qrCode) {
        mQrCode = qrCode;
    }

    public String getUuid() {
        return mId;
    }

    public void setUuid(String uuid) {
        mId = uuid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int isTimeIn() {
        return isTimeIn;
    }

    public void setTimeIn(int timeIn) {
        isTimeIn = timeIn;
    }
}
