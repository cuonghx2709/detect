package com.gemvietnam.timekeeping.screen.changepass;

import com.gemvietnam.base.viper.Interactor;
import com.gemvietnam.timekeeping.data.remote.ServiceBuilder;
import com.gemvietnam.timekeeping.data.remote.callback.BaseCallback;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;
import com.gemvietnam.timekeeping.data.remote.dto.ChangePassDTO;

/**
 * The PassChange interactor
 */
class PassChangeInteractor extends Interactor<PassChangeContract.Presenter>
        implements PassChangeContract.Interactor {

    PassChangeInteractor(PassChangeContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public void changePassword(String token,  String currentPass, String newPass, BaseCallback<Object> callback) {
        ServiceBuilder.getService().changePassword(token, currentPass, newPass).enqueue(callback);
    }
}
