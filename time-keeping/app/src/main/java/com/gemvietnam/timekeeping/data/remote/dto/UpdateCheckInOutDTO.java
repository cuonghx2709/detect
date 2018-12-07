
package com.gemvietnam.timekeeping.data.remote.dto;


import com.google.gson.annotations.SerializedName;

public class UpdateCheckInOutDTO {

    @SerializedName("checkInTime")
    private Long mCheckInTime;
    @SerializedName("checkOutTime")
    private Long mCheckOutTime;
    @SerializedName("dateCheck")
    private Long mDateCheck;
    @SerializedName("dayOfWeek")
    private String mDayOfWeek;
    @SerializedName("total")
    private Double mTotal;
    @SerializedName("uuid")
    private String mUuid;

    public Long getCheckInTime() {
        return mCheckInTime;
    }

    public void setCheckInTime(Long checkInTime) {
        mCheckInTime = checkInTime;
    }

    public Long getCheckOutTime() {
        return mCheckOutTime;
    }

    public void setCheckOutTime(Long checkOutTime) {
        mCheckOutTime = checkOutTime;
    }

    public Long getDateCheck() {
        return mDateCheck;
    }

    public void setDateCheck(Long dateCheck) {
        mDateCheck = dateCheck;
    }

    public String getDayOfWeek() {
        return mDayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        mDayOfWeek = dayOfWeek;
    }

    public Double getTotal() {
        return mTotal;
    }

    public void setTotal(Double total) {
        mTotal = total;
    }

    public String getUuid() {
        return mUuid;
    }

    public void setUuid(String uuid) {
        mUuid = uuid;
    }

}
