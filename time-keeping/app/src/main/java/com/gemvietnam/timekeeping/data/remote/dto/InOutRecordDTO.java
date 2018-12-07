package com.gemvietnam.timekeeping.data.remote.dto;

import com.google.gson.annotations.SerializedName;


public class InOutRecordDTO {
    @SerializedName("id")
    private String id;
    @SerializedName("checkInTime")
    private Long checkInTime;
    @SerializedName("checkOutTime")
    private Long checkOutTime;
    @SerializedName("total")
    private Double total;
    @SerializedName("dateCheck")
    private Long dateCheck;
    @SerializedName("dayOfWeek")
    private String dayOfWeek;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Long checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Long getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Long checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getDateCheck() {
        return dateCheck;
    }

    public void setDateCheck(Long dateCheck) {
        this.dateCheck = dateCheck;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public String toString() {
        return "InOutRecordDTO{" +
                "id='" + id + '\'' +
                ", checkInTime=" + checkInTime +
                ", checkOutTime=" + checkOutTime +
                ", total=" + total +
                ", dateCheck=" + dateCheck +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                '}';
    }
}
