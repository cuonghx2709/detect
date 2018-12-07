package com.gemvietnam.timekeeping.data.remote.dto.leave.addLeave;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;



public class AddLeaveResultDTO implements Parcelable {

    @SerializedName("uuid")
    private String uuid;

    @SerializedName("leaveType")
    private String leaveType;

    @SerializedName("requestDate")
    private Long requestDate;

    @SerializedName("reason")
    private String reason;

    @SerializedName("startDate")
    private Long startDate;

    @SerializedName("endDate")
    private Long endDate;

    @SerializedName("status")
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddLeaveResultDTO that = (AddLeaveResultDTO) o;

        if (!uuid.equals(that.uuid)) return false;
        if (!leaveType.equals(that.leaveType)) return false;
        if (!requestDate.equals(that.requestDate)) return false;
        if (!reason.equals(that.reason)) return false;
        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        if (!status.equals(that.status)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + leaveType.hashCode();
        result = 31 * result + requestDate.hashCode();
        result = 31 * result + reason.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public Long getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Long requestDate) {
        this.requestDate = requestDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.leaveType);
        dest.writeValue(this.requestDate);
        dest.writeString(this.reason);
        dest.writeValue(this.startDate);
        dest.writeValue(this.endDate);
        dest.writeString(this.status);
    }

    public AddLeaveResultDTO() {
    }

    protected AddLeaveResultDTO(Parcel in) {
        this.uuid = in.readString();
        this.leaveType = in.readString();
        this.requestDate = (Long) in.readValue(Long.class.getClassLoader());
        this.reason = in.readString();
        this.startDate = (Long) in.readValue(Long.class.getClassLoader());
        this.endDate = (Long) in.readValue(Long.class.getClassLoader());
        this.status = in.readString();
    }

    public static final Parcelable.Creator<AddLeaveResultDTO> CREATOR = new Parcelable.Creator<AddLeaveResultDTO>() {
        @Override
        public AddLeaveResultDTO createFromParcel(Parcel source) {
            return new AddLeaveResultDTO(source);
        }

        @Override
        public AddLeaveResultDTO[] newArray(int size) {
            return new AddLeaveResultDTO[size];
        }
    };
}
