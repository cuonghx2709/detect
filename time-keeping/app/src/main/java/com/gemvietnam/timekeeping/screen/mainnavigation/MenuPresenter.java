package com.gemvietnam.timekeeping.screen.mainnavigation;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.timekeeping.Main2Activity;
import com.gemvietnam.timekeeping.MainActivity;
import com.gemvietnam.timekeeping.R;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;
import com.gemvietnam.timekeeping.pref.PrefWrapper;
import com.gemvietnam.timekeeping.screen.changepass.PassChangePresenter;
import com.gemvietnam.timekeeping.screen.edittime.EditTimePresenter;
import com.gemvietnam.timekeeping.screen.home.HomePresenter;
import com.gemvietnam.timekeeping.screen.leave.LeaveFragment;
import com.gemvietnam.timekeeping.screen.leave.LeavePresenter;
import com.gemvietnam.timekeeping.screen.login.LoginPresenter;

/**
 * The Menu Presenter
 */
public class MenuPresenter extends Presenter<MenuContract.View, MenuContract.Interactor>
        implements MenuContract.Presenter {

    private static final String TAG = "MenuPresenter";
    private Main2Activity mainActivity;
    private OnItemClicked onItemClicked;

    public MenuPresenter(ContainerView containerView) {
        super(containerView);
    }

    public MenuPresenter setMainActivity(Main2Activity activity) {
        this.mainActivity = activity;
        return this;
    }

    public MenuPresenter setOnItemClick(OnItemClicked onItemClick) {
        this.onItemClicked = onItemClick;
        return this;
    }

    @Override
    public MenuContract.View onCreateView() {
        return MenuFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        clickText(1);
    }

    public void clickText(int pos) {
        Fragment f = null;
        if (pos == 0) {
//            f = new LeavePresenter(mainActivity).getFragment();
        } else if (pos == 1) {
            f = new HomePresenter(mainActivity).getFragment();
        }else if (pos == 2) {
            f = new LeavePresenter(mainActivity).getFragment();
        } else if (pos == 3) {
            f = new PassChangePresenter(mainActivity).getFragment();
        }

        if (f == null) return;

        FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_frame, f);
        transaction.addToBackStack(MenuFragment.class.getSimpleName());
        transaction.commit();

        onItemClicked.onItemClick();

    }

    @Override
    public void logout(String token) {
        mInteractor.logout(token, new CommonCallBack<Object>(getViewContext()) {
            @Override
            public void onError(String errorCode) {
                super.onError(errorCode);
                Log.e(TAG,errorCode);
            }

            @Override
            public void onSuccess(Object data) {
                super.onSuccess(data);
                PrefWrapper.saveToken(getViewContext(),null);
                Intent intent = new Intent(getViewContext(), MainActivity.class);
                getViewContext().startActivity(intent);
            }
        });

//        getViewContext().finish();
    }

    @Override
    public MenuContract.Interactor onCreateInteractor() {
        return new MenuInteractor(this);
    }

    public interface OnItemClicked{
        void onItemClick();
    }
}
