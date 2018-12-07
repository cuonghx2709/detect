package com.gemvietnam.timekeeping.screen.leave;

import com.gemvietnam.base.viper.Interactor;
import com.gemvietnam.timekeeping.data.remote.ServiceBuilder;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;
import com.gemvietnam.timekeeping.data.remote.dto.leave.leaveRequest.LeaveRequestListRequestDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.leaveRequest.LeaveRequestResultDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.totalLeave.TotalLeaveResultDTO;

/**
 * The leave interactor
 */
class LeaveInteractor extends Interactor<LeaveContract.Presenter>
    implements LeaveContract.Interactor {

  LeaveInteractor(LeaveContract.Presenter presenter) {
    super(presenter);
  }

  @Override
  public void getListRequestLeaves(String token, int year, int month,
                                   CommonCallBack<LeaveRequestResultDTO> commonCallBack) {

    ServiceBuilder.getService().listLeave(token,month, year).enqueue(commonCallBack);

  }

  @Override
  public void getTotalLeaves(String token, CommonCallBack<TotalLeaveResultDTO> commonCallBack) {

    ServiceBuilder.getService().totalLeaves(token).enqueue(commonCallBack);
  }
}
