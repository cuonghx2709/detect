package com.gemvietnam.timekeeping.screen.leave;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;
import com.gemvietnam.timekeeping.data.remote.dto.leave.leaveRequest.LeaveRequestListDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.leaveRequest.LeaveRequestListRequestDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.leaveRequest.LeaveRequestResultDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.totalLeave.TotalLeaveResultDTO;
import com.google.gson.JsonElement;

import java.util.ArrayList;

/**
 * The leave Contract
 */
interface LeaveContract {

  interface Interactor extends IInteractor<Presenter> {

    void getListRequestLeaves(String token,
                              int year, int month,
                              CommonCallBack<LeaveRequestResultDTO> commonCallBack);

    void getTotalLeaves(String token, CommonCallBack<TotalLeaveResultDTO> commonCallBack);
  }

  interface View extends PresentView<Presenter> {

    void setListRequestLeaves(ArrayList<LeaveRequestListDTO> leaves);

    void getListRequestLeaves(String message);

    void setTotalLeaves(TotalLeaveResultDTO totalLeaves);

    void onReload();

  }

  interface Presenter extends IPresenter<View, Interactor> {

    void onClickAddLeave();

    void getListRequestLeaves(String token, LeaveRequestListRequestDTO leaveRequestListRequestDTO);

    void getTotalLeaves(String token);

    void onClickItemLeave(LeaveRequestListDTO leaveRequestListDTO);

    void onBack();
  }
}



