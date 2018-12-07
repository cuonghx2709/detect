package com.gemvietnam.timekeeping.data.remote.dto.leave.addLeave;


import com.google.gson.annotations.SerializedName;


public class AddLeaveRequestDTO {

    @SerializedName("leaveType")
    private int leaveType;

    @SerializedName("reason")
    private String reason;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("endDate")
    private String endDate;

    @SerializedName("status")
    private int status;

    @SerializedName("requestDate")
    private String requestDate;

    public AddLeaveRequestDTO(int leaveType, String reason, String startDate, String endDate,
                              int status, String requestDate) {
        this.leaveType = leaveType;
        this.reason = reason;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.requestDate = requestDate;
    }

    @Override
    public String toString() {
        return "AddLeaveRequestDTO{" +
                "leaveType='" + leaveType + '\'' +
                ", reason='" + reason + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                '}';
    }





    public int getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(int leaveType) {
        this.leaveType = leaveType;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }
}
