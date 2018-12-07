package com.gemvietnam.timekeeping.screen.login;

import com.gemvietnam.base.viper.Interactor;
import com.gemvietnam.timekeeping.data.remote.ServiceBuilder;
import com.gemvietnam.timekeeping.data.remote.callback.BaseCallback;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;
import com.gemvietnam.timekeeping.data.remote.dto.LoginRequestDTO;
import com.gemvietnam.timekeeping.data.remote.dto.LoginResponseDTO;
import com.gemvietnam.timekeeping.data.remote.dto.ResponseDTO;
import com.google.gson.JsonElement;

/**
 * The Login interactor
 */
class LoginInteractor extends Interactor<LoginContract.Presenter> 
	implements LoginContract.Interactor {

    LoginInteractor(LoginContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void login(String username, String password, BaseCallback<JsonElement> callback) {
        ServiceBuilder.getService().login(username, password).enqueue(callback);
    }
}
