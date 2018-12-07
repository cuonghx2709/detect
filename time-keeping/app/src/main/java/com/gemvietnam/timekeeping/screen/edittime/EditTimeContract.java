package com.gemvietnam.timekeeping.screen.edittime;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;
import com.gemvietnam.timekeeping.data.remote.dto.ResponseDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.addLeave.AddLeaveRequestDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.addLeave.AddLeaveResultDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.deleteLeave.DeleteLeaveResultDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.leaveRequest.LeaveRequestListDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.updateLeave.UpdateLeaveRequestDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.updateLeave.UpdateLeaveResultDTO;

import java.util.ArrayList;

/**
 * The EditTime Contract
 */
interface EditTimeContract {

    interface Interactor extends IInteractor<Presenter> {
        void addRequestLeave(String token, AddLeaveRequestDTO addLeaveRequestDTO,
                             CommonCallBack<ResponseDTO> callBack);

        void updateRequestLeave(String token, UpdateLeaveRequestDTO updateLeaveRequestDTO,
                                   CommonCallBack<ResponseDTO> callBack);

        void deleteRequestLeave(String token, String leaveRequestId,
                                   CommonCallBack<ResponseDTO> callBack);
    }

    interface View extends PresentView<Presenter> {

        void BindViewDataSpiner(ArrayList<String> data);

        void getRequestLeave(LeaveRequestListDTO leaveRequestListDTO);


    }

    interface Presenter extends IPresenter<View, Interactor> {
        void onClickCancel();

        void addLeave(String token, AddLeaveRequestDTO addLeaveRequestDTO);

        void updateRequestLeave(String token, UpdateLeaveRequestDTO updateLeaveRequestDTO);

        void deleteRequestLeave(String token, String leaveRequestId);
    }
}



