package com.gemvietnam.timekeeping.screen.changepass;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.gemvietnam.timekeeping.data.remote.callback.BaseCallback;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;
import com.gemvietnam.timekeeping.data.remote.dto.ChangePassDTO;
import com.gemvietnam.timekeeping.data.remote.dto.InOutRecordDTOList;
import com.gemvietnam.timekeeping.data.remote.dto.MonthYearRequestDTO;

/**
 * The PassChange Contract
 */
interface PassChangeContract {

    interface Interactor extends IInteractor<Presenter> {
        void changePassword(String token, String currentPass, String newPass,
                               BaseCallback<Object> callback);
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getDataChangePass(String token, String oldpass, String newpass);

        void onBackPressed();
    }
}



