package com.gemvietnam.timekeeping.screen.mainnavigation;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;

/**
 * The Menu Contract
 */
interface MenuContract {

    interface Interactor extends IInteractor<Presenter> {

        void logout(String token, CommonCallBack<Object> commonCallBack);
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void clickText(int pos);
        void logout(String token);
    }
}



