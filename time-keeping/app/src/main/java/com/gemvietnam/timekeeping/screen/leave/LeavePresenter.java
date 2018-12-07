package com.gemvietnam.timekeeping.screen.leave;

import android.util.Log;
import android.widget.Toast;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.timekeeping.Constant;
import com.gemvietnam.timekeeping.R;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;
import com.gemvietnam.timekeeping.data.remote.dto.ResponseDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.leaveRequest.LeaveRequestListDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.leaveRequest.LeaveRequestListRequestDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.leaveRequest.LeaveRequestResultDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.totalLeave.TotalLeaveResultDTO;
import com.gemvietnam.timekeeping.screen.edittime.EditTimePresenter;

import retrofit2.Call;

/**
 * The leave Presenter
 */
public class LeavePresenter extends Presenter<LeaveContract.View, LeaveContract.Interactor>
        implements LeaveContract.Presenter, EditTimePresenter.OnEditSuccessListener {

    private static String TAG = "leavePresenter";

    public LeavePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public LeaveContract.View onCreateView() {
        return LeaveFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here

    }

    @Override
    public LeaveContract.Interactor onCreateInteractor() {
        return new LeaveInteractor(this);
    }

    @Override
    public void onClickAddLeave() {
        new EditTimePresenter(mContainerView).setOnEditSuccessListener(this).pushView();
    }

    @Override
    public void getListRequestLeaves(String token,
                                     LeaveRequestListRequestDTO leaveRequestListRequestDTO) {

        mInteractor.getListRequestLeaves(token, leaveRequestListRequestDTO.getYear(), leaveRequestListRequestDTO.getMonth(),
                new CommonCallBack<LeaveRequestResultDTO>(getViewContext()) {
                    @Override
                    public void onError(String errorCode) {
                        super.onError(errorCode);
                        mView.getListRequestLeaves(errorCode);
                        Log.e(TAG, errorCode);
                    }

                    @Override
                    public void onFailure(Call<ResponseDTO<LeaveRequestResultDTO>> call, Throwable t) {
                        super.onFailure(call, t);
                        mView.getListRequestLeaves("error");
                    }

                    @Override
                    public void onSuccess(LeaveRequestResultDTO data) {
                        super.onSuccess(data);
                        mView.setListRequestLeaves(data.getLeaveRequestDTOList());
                    }
                });

    }

    @Override
    public void getTotalLeaves(String token) {

        mInteractor.getTotalLeaves(token,
                new CommonCallBack<TotalLeaveResultDTO>(getViewContext()) {
                    @Override
                    public void onError(String errorCode) {
                        super.onError(errorCode);
                        Log.e(TAG, errorCode);
                    }

                    @Override
                    public void onSuccess(TotalLeaveResultDTO data) {
                        super.onSuccess(data);
                        mView.setTotalLeaves(data);
                    }
                });
    }

    @Override
    public void onClickItemLeave(LeaveRequestListDTO leaveRequestListDTO) {
        if (leaveRequestListDTO.getStatus().equals(Constant.STATUS_PENDING)) {
            new EditTimePresenter(mContainerView, leaveRequestListDTO)
                    .setOnEditSuccessListener(this).addView();
        } else if (leaveRequestListDTO.getStatus().equals(Constant.STATUS_APPROVED)) {
            Toast.makeText(getViewContext(), R.string.request_approved, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getViewContext(), R.string.request_ejected, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBack() {
        mContainerView.back();
    }

    @Override
    public void onReloadSuccess() {
        mView.onReload();
    }
}
