package com.gemvietnam.timekeeping.screen.home;

import com.gemvietnam.base.viper.Interactor;
import com.gemvietnam.timekeeping.data.remote.ServiceBuilder;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;
import com.gemvietnam.timekeeping.data.remote.dto.InOutRecordDTOList;
import com.gemvietnam.timekeeping.data.remote.dto.ResponseDTO;
import com.gemvietnam.timekeeping.data.remote.dto.UpdateCheckInOutRequest;

/**
 * The Home interactor
 */
class HomeInteractor extends Interactor<HomeContract.Presenter> implements HomeContract.Interactor {

    HomeInteractor(HomeContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getDataCheckInOut(String token, int year, int month,
                                  CommonCallBack<InOutRecordDTOList> callback) {
        ServiceBuilder.getService().getListCheckInOut(token, year, month).enqueue(callback);
    }

    @Override
    public void getUpdateCheckInOut(String token, UpdateCheckInOutRequest updateCheckInOutRequest,
                                    CommonCallBack<ResponseDTO> callBack) {
        ServiceBuilder.getService().updateCheckinOut(token,updateCheckInOutRequest.getQrCode(),
                updateCheckInOutRequest.getUuid(), updateCheckInOutRequest.getTime(),
                updateCheckInOutRequest.isTimeIn()).enqueue(callBack);
    }


}
