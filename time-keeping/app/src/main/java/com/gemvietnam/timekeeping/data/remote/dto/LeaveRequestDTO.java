
package com.gemvietnam.timekeeping.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class LeaveRequestDTO {

    @SerializedName("endDate")
    private Long mEndDate;
    @SerializedName("leaveType")
    private String mLeaveType;
    @SerializedName("reason")
    private String mReason;
    @SerializedName("startDate")
    private Long mStartDate;
    @SerializedName("status")
    private String mStatus;

    public Long getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Long endDate) {
        mEndDate = endDate;
    }

    public String getLeaveType() {
        return mLeaveType;
    }

    public void setLeaveType(String leaveType) {
        mLeaveType = leaveType;
    }

    public String getReason() {
        return mReason;
    }

    public void setReason(String reason) {
        mReason = reason;
    }

    public Long getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Long startDate) {
        mStartDate = startDate;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
