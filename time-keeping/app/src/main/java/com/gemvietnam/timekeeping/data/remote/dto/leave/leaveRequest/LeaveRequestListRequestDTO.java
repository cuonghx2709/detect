package com.gemvietnam.timekeeping.data.remote.dto.leave.leaveRequest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;



public class LeaveRequestListRequestDTO implements Parcelable {

    @SerializedName("month")
    private Integer month;

    @SerializedName("year")
    private Integer year;

    @Override
    public String toString() {
        return "LeaveRequestListRequestDTO{" +
                "month=" + month +
                ", year=" + year +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LeaveRequestListRequestDTO that = (LeaveRequestListRequestDTO) o;

        if (month != null ? !month.equals(that.month) : that.month != null) return false;
        if (!year.equals(that.year)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = month != null ? month.hashCode() : 0;
        result = 31 * result + year.hashCode();
        return result;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.year);
        dest.writeValue(this.month);
    }

    public LeaveRequestListRequestDTO(Integer month, Integer year) {
        this.month = month;
        this.year = year;
    }

    public LeaveRequestListRequestDTO() {
    }

    protected LeaveRequestListRequestDTO(Parcel in) {
        this.year = (Integer) in.readValue(Integer.class.getClassLoader());
        this.month = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<LeaveRequestListRequestDTO> CREATOR = new Parcelable.Creator<LeaveRequestListRequestDTO>() {
        @Override
        public LeaveRequestListRequestDTO createFromParcel(Parcel source) {
            return new LeaveRequestListRequestDTO(source);
        }

        @Override
        public LeaveRequestListRequestDTO[] newArray(int size) {
            return new LeaveRequestListRequestDTO[size];
        }
    };
}
