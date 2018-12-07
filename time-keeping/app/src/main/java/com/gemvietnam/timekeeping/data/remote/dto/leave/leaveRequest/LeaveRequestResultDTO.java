package com.gemvietnam.timekeeping.data.remote.dto.leave.leaveRequest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;



public class LeaveRequestResultDTO implements Parcelable {

    @SerializedName("leaveRequestDTOList")
    private ArrayList<LeaveRequestListDTO> leaveRequestDTOList;

    public ArrayList<LeaveRequestListDTO> getLeaveRequestDTOList() {
        return leaveRequestDTOList;
    }

    public void setLeaveRequestDTOList(ArrayList<LeaveRequestListDTO> leaveRequestDTOList) {
        this.leaveRequestDTOList = leaveRequestDTOList;
    }

    @Override
    public String toString() {
        return "LeaveRequestResultDTO{" +
                "leaveRequestDTOList=" + leaveRequestDTOList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LeaveRequestResultDTO that = (LeaveRequestResultDTO) o;

        if (!leaveRequestDTOList.equals(that.leaveRequestDTOList)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return leaveRequestDTOList.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.leaveRequestDTOList);
    }

    public LeaveRequestResultDTO() {
    }

    protected LeaveRequestResultDTO(Parcel in) {
        this.leaveRequestDTOList = in.createTypedArrayList(LeaveRequestListDTO.CREATOR);
    }

    public static final Parcelable.Creator<LeaveRequestResultDTO> CREATOR = new Parcelable.Creator<LeaveRequestResultDTO>() {
        @Override
        public LeaveRequestResultDTO createFromParcel(Parcel source) {
            return new LeaveRequestResultDTO(source);
        }

        @Override
        public LeaveRequestResultDTO[] newArray(int size) {
            return new LeaveRequestResultDTO[size];
        }
    };
}
