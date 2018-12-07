package com.gemvietnam.timekeeping.data.remote.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;


public class InOutRecordDTOList {
    @SerializedName("inOutRecordDTOList")
    private List<InOutRecordDTO> inOutRecordDTOList;
    @SerializedName("total")
    private Double count_total;

    public List<InOutRecordDTO> getInOutRecordDTOList() {
        return inOutRecordDTOList;
    }

    public void setInOutRecordDTOList(List<InOutRecordDTO> inOutRecordDTOList) {
        this.inOutRecordDTOList = inOutRecordDTOList;
    }

    public Double getCount_total() {
        return count_total;
    }

    public void setCount_total(Double count_total) {
        this.count_total = count_total;
    }

    @Override
    public String toString() {
        return "InOutRecordDTOList{" +
                "inOutRecordDTOList=" + inOutRecordDTOList +
                ", count_total=" + count_total +
                '}';
    }
}
