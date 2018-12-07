package com.gemvietnam.timekeeping.screen.edittime;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.timekeeping.R;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;
import com.gemvietnam.timekeeping.data.remote.dto.ResponseDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.addLeave.AddLeaveRequestDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.addLeave.AddLeaveResultDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.deleteLeave.DeleteLeaveResultDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.leaveRequest.LeaveRequestListDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.updateLeave.UpdateLeaveRequestDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.updateLeave.UpdateLeaveResultDTO;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * The EditTime Presenter
 */
public class EditTimePresenter extends Presenter<EditTimeContract.View, EditTimeContract.Interactor>
        implements EditTimeContract.Presenter {

    private OnEditSuccessListener onEditSuccessListener;

    private static String TAG = "EditTimePrsenter";

    private LeaveRequestListDTO leaveRequestListDTO;

    public static final int REQUEST_CODE_LOAD_LEAVES = 123;

    public EditTimePresenter(ContainerView containerView) {
        super(containerView);
    }

    public EditTimePresenter(ContainerView containerView, LeaveRequestListDTO leaveRequestListDTO) {
        super(containerView);
        this.leaveRequestListDTO = leaveRequestListDTO;
    }

    public EditTimePresenter setOnEditSuccessListener(OnEditSuccessListener onEditSuccessListener) {
        this.onEditSuccessListener = onEditSuccessListener;
        return this;
    }

    @Override
    public EditTimeContract.View onCreateView() {
        return EditTimeFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        dataSpinner();
        mView.getRequestLeave(leaveRequestListDTO);
    }

    private void onClickLoad() {
        if (onEditSuccessListener != null) {
            onEditSuccessListener.onReloadSuccess();
        }
    }
    private void dataSpinner() {
        ArrayList<String> data = new ArrayList<>();
        data.add(getViewContext().getString(R.string.spin_leave));
        data.add(getViewContext().getString(R.string.spin_compensatory_leave));
        mView.BindViewDataSpiner(data);
    }

    @Override
    public EditTimeContract.Interactor onCreateInteractor() {
        return new EditTimeInteractor(this);
    }

    @Override
    public void onClickCancel() {
        mContainerView.back();
    }

    @Override
    public void addLeave(String token, AddLeaveRequestDTO addLeaveRequestDTO) {


        mView.showProgress();
        mInteractor.addRequestLeave(token, addLeaveRequestDTO,
                new CommonCallBack<ResponseDTO>(getViewContext()) {
                    @Override
                    public void onError(String errorCode) {
                        super.onError(errorCode);
                        mView.hideProgress();
                        mView.showAlertDialog(errorCode);
                        Log.e(TAG, errorCode);
                    }

                    @Override
                    public void onSuccess(ResponseDTO data) {
                        super.onSuccess(data);
                        onClickLoad();
                        mContainerView.back();
                        mView.showAlertDialog(getViewContext().
                                getString(R.string.message_success_add_leave));
                    }
                });

    }

    @Override
    public void updateRequestLeave(String token, UpdateLeaveRequestDTO updateLeaveRequestDTO) {
        mView.showProgress();
        mInteractor.updateRequestLeave(token, updateLeaveRequestDTO,
                new CommonCallBack<ResponseDTO>(getViewContext()) {
                    @Override
                    public void onError(String errorCode) {
                        super.onError(errorCode);
                        mView.hideProgress();
                        mView.showAlertDialog(errorCode);
                    }

                    @Override
                    public void onSuccess(ResponseDTO data) {
                        super.onSuccess(data);
                        mView.hideProgress();
                        onClickLoad();
                        mContainerView.back();
                        mView.showAlertDialog(getViewContext().getString(R.string.update_successfully));


                    }
                });
    }

    @Override
    public void deleteRequestLeave(final String token, final String leaveRequestId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getViewContext());
        builder.setMessage(R.string.delete_request).setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mView.showProgress();
                        mInteractor.deleteRequestLeave(token, leaveRequestId,
                                new CommonCallBack<ResponseDTO>(getViewContext()) {
                                    @Override
                                    public void onError(String errorCode) {
                                        super.onError(errorCode);
                                        mView.hideProgress();
                                        mView.showAlertDialog(errorCode);
                                    }

                                    @Override
                                    public void onSuccess(ResponseDTO data) {
                                        super.onSuccess(data);
                                        mView.hideProgress();
                                        onClickLoad();
                                        mContainerView.back();
                                        Toast.makeText(getViewContext(),getViewContext().
                                                getString(R.string.delete_successfully),Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
        builder.create();
        builder.show();

    }

    public interface OnEditSuccessListener{
        void onReloadSuccess();
    }
}
