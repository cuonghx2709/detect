package com.gemvietnam.timekeeping.screen.changepass;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.timekeeping.R;
import com.gemvietnam.timekeeping.data.remote.callback.BaseCallback;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;
import com.gemvietnam.timekeeping.data.remote.dto.ChangePassDTO;
import com.gemvietnam.timekeeping.screen.home.HomePresenter;

/**
 * The PassChange Presenter
 */
public class PassChangePresenter extends Presenter<PassChangeContract.View, PassChangeContract.Interactor>
        implements PassChangeContract.Presenter {
    ChangePassDTO changePassDTO;
    private static final String INCORRECT_PASSWORD = "INCORRECT_PASSWORD";
    public PassChangePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public PassChangeContract.View onCreateView() {
        return PassChangeFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public PassChangeContract.Interactor onCreateInteractor() {
        return new PassChangeInteractor(this);
    }

    @Override
    public void getDataChangePass(String token, String oldpass, String newpass) {
        mView.showProgress();
        mInteractor.changePassword(token, oldpass, newpass, new BaseCallback<Object>() {
            @Override
            public void onError(String errorCode) {
                mView.hideProgress();
                    Toast.makeText(getViewContext(),getBody().getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(Object data) {
                mView.hideProgress();
                new HomePresenter(mContainerView).pushView();
                Toast.makeText(getViewContext(), getViewContext().getString(R.string.change_pass_success) , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
       mContainerView.back();
    }
}
