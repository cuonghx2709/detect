package com.gemvietnam.timekeeping.screen.home;

import android.widget.TextView;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.gemvietnam.timekeeping.data.remote.callback.BaseCallback;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;
import com.gemvietnam.timekeeping.data.remote.dto.InOutRecordDTO;
import com.gemvietnam.timekeeping.data.remote.dto.InOutRecordDTOList;
import com.gemvietnam.timekeeping.data.remote.dto.MonthYearRequestDTO;
import com.gemvietnam.timekeeping.data.remote.dto.ResponseDTO;
import com.gemvietnam.timekeeping.data.remote.dto.UpdateCheckInOutDTO;
import com.gemvietnam.timekeeping.data.remote.dto.UpdateCheckInOutRequest;
import com.gemvietnam.timekeeping.screen.home.adapter.CheckInOutAdapter;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Home Contract
 */
interface HomeContract {

    interface Interactor extends IInteractor<Presenter> {
//        void prepareListData(List<String> listDataHeader,
//                             HashMap<String, List<String>> listDataChild
//        );
        void getDataCheckInOut(String token, int year, int month,
                               CommonCallBack<InOutRecordDTOList> callback);
        void getUpdateCheckInOut(String token, UpdateCheckInOutRequest updateCheckInOutRequest,
                                 CommonCallBack<ResponseDTO> callBack);
    }

    interface View extends PresentView<Presenter> {
        void initList();
        void BindViewListCheckInOut(InOutRecordDTOList inOutRecordDTO);
        void BindViewListCheckInOutError(String message);
        void BindViewUpdateCheckInOut(UpdateCheckInOutDTO updateCheckInOutDTO);
        void BindViewUpdateCheckInOutError(String message);
        void loadBackCheckInOut();

    }

    interface Presenter extends IPresenter<View, Interactor> {
        CheckInOutAdapter getCheckInOutAdapter();

        void getDataCheckInOut(String token , MonthYearRequestDTO monthYearRequestDTO);

        void sendQRCode(String value);

        void onBack();
//        void countDay(Long checkInTime,Long checkOutTime);
    }
}



