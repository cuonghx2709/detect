package com.gemvietnam.timekeeping.data.remote.dto.leave.totalLeave;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class TotalLeaveResultDTO implements Parcelable {

    @SerializedName("numberInUsed")
    private Integer numberInUsed;

    @SerializedName("numberInTotal")
    private Integer numberInTotal;

    @Override
    public String toString() {
        return "TotalLeaveResultDTO{" +
                "numberInUsed=" + numberInUsed +
                ", numberInTotal=" + numberInTotal +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TotalLeaveResultDTO that = (TotalLeaveResultDTO) o;

        if (!numberInUsed.equals(that.numberInUsed)) return false;
        if (!numberInTotal.equals(that.numberInTotal)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = numberInUsed.hashCode();
        result = 31 * result + numberInTotal.hashCode();
        return result;
    }

    public Integer getNumberInUsed() {
        return numberInUsed;
    }

    public void setNumberInUsed(Integer numberInUsed) {
        this.numberInUsed = numberInUsed;
    }

    public Integer getNumberInTotal() {
        return numberInTotal;
    }

    public void setNumberInTotal(Integer numberInTotal) {
        this.numberInTotal = numberInTotal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.numberInUsed);
        dest.writeValue(this.numberInTotal);
    }

    public TotalLeaveResultDTO() {
    }

    protected TotalLeaveResultDTO(Parcel in) {
        this.numberInUsed = (Integer) in.readValue(Integer.class.getClassLoader());
        this.numberInTotal = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<TotalLeaveResultDTO> CREATOR = new Parcelable.Creator<TotalLeaveResultDTO>() {
        @Override
        public TotalLeaveResultDTO createFromParcel(Parcel source) {
            return new TotalLeaveResultDTO(source);
        }

        @Override
        public TotalLeaveResultDTO[] newArray(int size) {
            return new TotalLeaveResultDTO[size];
        }
    };
}
