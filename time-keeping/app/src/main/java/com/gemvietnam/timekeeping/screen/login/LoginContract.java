package com.gemvietnam.timekeeping.screen.login;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.gemvietnam.timekeeping.data.remote.callback.BaseCallback;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;
import com.gemvietnam.timekeeping.data.remote.dto.LoginRequestDTO;
import com.gemvietnam.timekeeping.data.remote.dto.LoginResponseDTO;
import com.gemvietnam.timekeeping.data.remote.dto.ResponseDTO;
import com.google.gson.JsonElement;

/**
 * The Login Contract
 */
interface LoginContract {

    interface Interactor extends IInteractor<Presenter> {
        void login(String username, String password,BaseCallback<JsonElement> callback);
    }

    interface View extends PresentView<Presenter> {
//        void LoginSuccess();
//        void saveUserPass();
//        void LoginFailed();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void login(String user, String password);
    }
}



