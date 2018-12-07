package com.gemvietnam.timekeeping.data.remote.dto;

import com.google.gson.annotations.SerializedName;



public class MonthYearRequestDTO {
    @SerializedName("year")
    private int mYear;
    @SerializedName("month")
    private int mMonth;

    public MonthYearRequestDTO(int mMonth, int mYear) {
        this.mYear = mYear;
        this.mMonth = mMonth;
    }

    public int getmYear() {
        return mYear;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }

    public int getmMonth() {
        return mMonth;
    }

    public void setmMonth(int mMonth) {
        this.mMonth = mMonth;
    }
}
