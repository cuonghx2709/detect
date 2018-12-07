package com.gemvietnam.timekeeping.screen.edittime;

import android.widget.ArrayAdapter;

import com.gemvietnam.base.viper.Interactor;
import com.gemvietnam.timekeeping.data.remote.ServiceBuilder;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;
import com.gemvietnam.timekeeping.data.remote.dto.LeaveRequestDTO;
import com.gemvietnam.timekeeping.data.remote.dto.LeaveRespondDTO;
import com.gemvietnam.timekeeping.data.remote.dto.ResponseDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.addLeave.AddLeaveRequestDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.addLeave.AddLeaveResultDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.deleteLeave.DeleteLeaveResultDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.updateLeave.UpdateLeaveRequestDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.updateLeave.UpdateLeaveResultDTO;

import java.util.ArrayList;

/**
 * The EditTime interactor
 */
class EditTimeInteractor extends Interactor<EditTimeContract.Presenter>
        implements EditTimeContract.Interactor {

    EditTimeInteractor(EditTimeContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void addRequestLeave(String token, AddLeaveRequestDTO addLeaveRequestDTO,
                                CommonCallBack<ResponseDTO> callBack) {
        ServiceBuilder.getService().addLeave(token,addLeaveRequestDTO.getLeaveType(),
                addLeaveRequestDTO.getReason(), addLeaveRequestDTO.getStartDate(),
                addLeaveRequestDTO.getEndDate(), addLeaveRequestDTO.getStatus(),
                addLeaveRequestDTO.getRequestDate()).enqueue(callBack);
    }

    @Override
    public void updateRequestLeave(String token, UpdateLeaveRequestDTO updateLeaveRequestDTO,
                                   CommonCallBack<ResponseDTO> callBack) {
        ServiceBuilder.getService().updateLeave(token,updateLeaveRequestDTO.getUuid(), updateLeaveRequestDTO.getReason(),
                updateLeaveRequestDTO.getStartDate(), updateLeaveRequestDTO.getEndDate(),
                updateLeaveRequestDTO.getType()).enqueue(callBack);
    }

    @Override
    public void deleteRequestLeave(String token, String leaveRequestId,
                                   CommonCallBack<ResponseDTO> callBack) {
        ServiceBuilder.getService().deleteLeave(token,leaveRequestId).enqueue(callBack);

    }
}
