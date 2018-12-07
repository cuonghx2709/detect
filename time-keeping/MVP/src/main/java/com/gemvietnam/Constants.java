package com.gemvietnam;

/**
 * Common constants
 * Created by neo on 7/18/2016.
 */
public interface Constants {
  float BAR_CHART_SMALL_HEIGHT = 300;
  float HORIZONTAL_BAR_CHART_SMALL_HEIGHT = 180;
  String AES_KEY = "sample_GEM";
  String DATA_KEY = "DATA_KEY";
  String SEARCH_DATA = "SEARCH_DATA";

  interface TrangThaiCongViec {
    int CHUA_THUC_HIEN = 1;
    int DANG_THUC_HIEN = 2;
    int HOAN_THANH = 3;
    int TAT_CA = 4;

    String STRING_CHUA_THUC_HIEN = "NEW";
    String STRING_DANG_THUC_HIEN = "IN_PROGRESS";
    String STRING_HOAN_THANH = "CLOSED";

  }

  interface JobSortTypes {

    String THOI_GIAN_KET_THUC = "a.END_TIME";
    String THOI_GIAN_TAO = "a.START_TIME";
    String THOI_GIAN_HOAN_THANH = "a.UPDATE_TIME";
    String NGUOI_GIAO = "coor.full_name";
    String NGUOI_NHAN = "rece.full_name";
  }

  interface CanhBaoSortTypes {
    String TEN_CB = "NAME";
    String MUC_DO_CB = "LEVEL";
    String THOI_GIAN_CB = "START_TIME";
    String DEFAULT = "DEFAULT";
  }


  enum AssignType {
    OWNER, RECEIVER
  }

  enum GroupTargetType {
    PLANNING_ALARM, BUSINESS_ALARM
  }

  interface Extras {

    String FROM_TAB = "From_Tab";
    String KIEU_CONG_VIEC = "KIEU_CONG_VIEC";
    String DATA = "data";
    int PAGE_COUNT = 50;
    String LAYER = "layer";
  }

  interface KieuThoiGian {
    int NGAY_TAO_TU_NGAY = 1;
    int NGAY_TAO_DEN_NGAY = 2;
    int NGAY_HOAN_THANH_TU_NGAY = 3;
    int NGAY_HOAN_THANH_DEN_NGAY = 4;
    int NGAY_KET_THUC_TU_NGAY = 5;
    int NGAY_KET_THUC_DEN_NGAY = 6;
  }

  interface PAGING {
    int NUM_PER_PAGE = 50;
  }

  interface LEGENDDETAIL {
    int MAX_NUM_LEGENDS = 6;
    int MIN_NUM_LEGENDS = 2;
  }

  interface CHART {
    float label_angle = -80;
    float GROUP_SPACE = 0.1f;
    float BAR_SPACE = 0f;
//    int MAX_VISIBLE_ENTRY = 7;
  }

  interface AreaType {
    String PROVINCE = "PROVINCE";
    String DISTRICT = "DISTRICT";
    String STATION = "STATION";
    String VTT = "VTT";
  }

  interface EntryValue {
    String na = "N/A";
  }
}
