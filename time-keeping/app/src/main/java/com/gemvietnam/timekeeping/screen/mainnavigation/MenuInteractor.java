package com.gemvietnam.timekeeping.screen.mainnavigation;

import com.gemvietnam.base.viper.Interactor;
import com.gemvietnam.timekeeping.data.remote.ServiceBuilder;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;

/**
 * The Menu interactor
 */
class MenuInteractor extends Interactor<MenuContract.Presenter>
        implements MenuContract.Interactor {

    MenuInteractor(MenuContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void logout(String token, CommonCallBack<Object> commonCallBack) {

        ServiceBuilder.getService().logout(token).enqueue(commonCallBack);
    }
}
