package com.gemvietnam.timekeeping.data.remote.dto.leave.updateLeave;

import com.google.gson.annotations.SerializedName;


public class UpdateLeaveRequestDTO {

    @SerializedName("id")
    private String uuid;

    @SerializedName("reason")
    private String reason;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("endDate")
    private String endDate;

    @SerializedName("type")
    private int type;

    @Override
    public String toString() {
        return "UpdateLeaveRequestDTO{" +
                "uuid='" + uuid + '\'' +
                ", reason='" + reason + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public UpdateLeaveRequestDTO(String uuid, String reason, String startDate, String endDate, int type) {
        this.uuid = uuid;
        this.reason = reason;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }
}
