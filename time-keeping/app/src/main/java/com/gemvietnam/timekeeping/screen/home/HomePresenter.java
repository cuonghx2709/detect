package com.gemvietnam.timekeeping.screen.home;

import android.util.Log;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.timekeeping.Constant;
import com.gemvietnam.timekeeping.R;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;
import com.gemvietnam.timekeeping.data.remote.dto.InOutRecordDTO;
import com.gemvietnam.timekeeping.data.remote.dto.InOutRecordDTOList;
import com.gemvietnam.timekeeping.data.remote.dto.MonthYearRequestDTO;
import com.gemvietnam.timekeeping.data.remote.dto.ResponseDTO;
import com.gemvietnam.timekeeping.data.remote.dto.UpdateCheckInOutDTO;
import com.gemvietnam.timekeeping.data.remote.dto.UpdateCheckInOutRequest;
import com.gemvietnam.timekeeping.pref.PrefWrapper;
import com.gemvietnam.timekeeping.screen.home.adapter.CheckInOutAdapter;
import com.gemvietnam.timekeeping.utils.DateUtils;

import java.util.Date;

import retrofit2.Call;

/**
 * The Home Presenter
 */
public class HomePresenter extends Presenter<HomeContract.View, HomeContract.Interactor>
        implements HomeContract.Presenter {
    CheckInOutAdapter adapter;
    MonthYearRequestDTO monthYearRequestDTO;
    UpdateCheckInOutDTO updateCheckInOutDTO;
    InOutRecordDTOList inOutRecordDTO;
    private static final String INCORRECT_QR_CODE = "INCORRECT_QR_CODE";
    private static final String IN_OUT_RECORD_NOT_FOUND = "IN_OUT_RECORD_NOT_FOUND";
    private static final String EXCEPTION = "EXCEPTION";

    private int isTimeIn = 1;

    public HomePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public HomeContract.View onCreateView() {
        return HomeFragment.getInstance();
    }

    @Override
    public void start() {
    }

    @Override
    public HomeContract.Interactor onCreateInteractor() {
        return new HomeInteractor(this);
    }

    @Override
    public CheckInOutAdapter getCheckInOutAdapter() {
        return adapter;
    }

    @Override
    public void getDataCheckInOut(String token, MonthYearRequestDTO monthYearRequestDTO) {
        mInteractor.getDataCheckInOut(PrefWrapper.getxAuthToken(getViewContext()),
                monthYearRequestDTO.getmYear(), monthYearRequestDTO.getmMonth(),
                new CommonCallBack<InOutRecordDTOList>(getViewContext()) {
                    @Override
                    public void onError(String errorCode) {
                        super.onError(errorCode);
                        Log.e("onError: ", errorCode);
                        mView.BindViewListCheckInOutError(errorCode);
                        if (null != inOutRecordDTO && null != inOutRecordDTO.getInOutRecordDTOList()) {
                            inOutRecordDTO.getInOutRecordDTOList().clear();
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseDTO<InOutRecordDTOList>> call, Throwable t) {
                        super.onFailure(call, t);
                        mView.BindViewListCheckInOutError("error");
                        if (null != inOutRecordDTO && null != inOutRecordDTO.getInOutRecordDTOList()) {
                            inOutRecordDTO.getInOutRecordDTOList().clear();
                            adapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onSuccess(InOutRecordDTOList data) {
                        super.onSuccess(data);
                        inOutRecordDTO = data;
                        getTextCount(inOutRecordDTO);
                        adapter = new CheckInOutAdapter(mView.getViewContext(),
                                inOutRecordDTO.getInOutRecordDTOList());
                        mView.BindViewListCheckInOut(inOutRecordDTO);
                        adapter.notifyDataSetChanged();

                        PrefWrapper.setDayUpdate(getViewContext(),
                                inOutRecordDTO.getInOutRecordDTOList()
                                        .get(inOutRecordDTO.getInOutRecordDTOList().size() - 1).getId());

                        if (inOutRecordDTO.getInOutRecordDTOList()
                                .get(inOutRecordDTO.getInOutRecordDTOList().size() - 1).getCheckInTime() != null &&
                                !DateUtils.getTimeString(inOutRecordDTO.getInOutRecordDTOList()
                                        .get(inOutRecordDTO.getInOutRecordDTOList().size() - 1).getCheckInTime()).equals("00:00")) {
                            isTimeIn = 0;
                        } else {
                            isTimeIn = 1;
                        }

//                        if (0 != inOutRecordDTO.getInOutRecordDTOList().size()) {
//                            if (DateUtils.getMonth(inOutRecordDTO.getInOutRecordDTOList().get(0).getDateCheck())
//                                    == new Date().getMonth()) {
//                                for (int i = 0; i < inOutRecordDTO.getInOutRecordDTOList().size(); i++) {
//                                    if (DateUtils.getEqualDate(inOutRecordDTO.getInOutRecordDTOList().get(i).getDateCheck())
//                                            == DateUtils.getCurrentDay()) {
//                                        PrefWrapper.setDayUpdate(getViewContext(),
//                                                inOutRecordDTO.getInOutRecordDTOList().get(i).getId());
//                                        break;
//                                    }
//                                }
//                            }
//                        }


                    }
                });
    }

    private void getTextCount(InOutRecordDTOList data) {
        try {
            double total = 0;
            for (int i = 0; i < data.getInOutRecordDTOList().size(); i++) {
                double time = ((data.getInOutRecordDTOList().get(i).getCheckOutTime()
                        - data.getInOutRecordDTOList().get(i).getCheckInTime())
                        / (60 * 60)) / 1000 - 1.5;
                double count;
                if (time <= 0)
                    count = 0;
                else count = (time >= 8) ? 1 : time / 8;
                inOutRecordDTO.getInOutRecordDTOList().get(i).setTotal(count);
                total += count;
            }
            inOutRecordDTO.setCount_total(total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendQRCode(final String contents) {

        mInteractor.getUpdateCheckInOut(PrefWrapper.getxAuthToken(getViewContext()),
                new UpdateCheckInOutRequest(contents, PrefWrapper.getDayUpdate(getViewContext()),
                        DateUtils.getStringDateAddLeave(new Date()), isTimeIn),
                new CommonCallBack<ResponseDTO>(getViewContext()) {

                    @Override
                    public void onError(String errorCode) {
                        super.onError(errorCode);
                        Log.e("errorHome", errorCode);
                        switch (errorCode) {
                            case INCORRECT_QR_CODE: {
                                mView.showAlertDialog(getViewContext().getString(R.string.incorrect_qr_code));
                            }
                            case IN_OUT_RECORD_NOT_FOUND: {
                                mView.showAlertDialog(getViewContext().getString(R.string.in_out_record_not_found));
                            }
                            case EXCEPTION: {
                                mView.showAlertDialog(getViewContext().getString(R.string.check_in_out_fail));
                            }
                        }
                        mView.BindViewListCheckInOutError(errorCode);
                    }

                    @Override
                    public void onSuccess(ResponseDTO data) {
                        super.onSuccess(data);
                        mView.showAlertDialog(getViewContext().getString(R.string.check_in_out_success));
//                        if (DateUtils.getMonth(data.getDateCheck()) == new Date().getMonth() &&
//                                (!data.getDayOfWeek().equals(Constant.SATURDAY) &&
//                                        !data.getDayOfWeek().equals(Constant.SUNDAY))) {
//                            for (int i = 0; i < inOutRecordDTO.getInOutRecordDTOList().size(); i++) {
//                                if (DateUtils.getCurrentDay() == DateUtils.getEqualDate(inOutRecordDTO.
//                                        getInOutRecordDTOList().get(i).getDateCheck())) {
//
//                                    inOutRecordDTO.getInOutRecordDTOList().get(i).
//                                            setCheckInTime(data.getCheckInTime());
//                                    inOutRecordDTO.getInOutRecordDTOList().get(i).
//                                            setCheckOutTime(data.getCheckOutTime());
//                                    inOutRecordDTO.getInOutRecordDTOList().get(i).
//                                            setTotal(data.getTotal());
//                                    mView.showAlertDialog(getViewContext().getString(R.string.check_in_out_success));
//                                    break;
//                                }
//                            }
                        mView.loadBackCheckInOut();
//                        }
                    }
                });


    }

    @Override
    public void onBack() {
        mContainerView.back();

    }

}
