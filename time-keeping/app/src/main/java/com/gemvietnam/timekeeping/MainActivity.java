package com.gemvietnam.timekeeping;

import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.gemvietnam.base.ContainerActivity;
import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.timekeeping.pref.PrefWrapper;
import com.gemvietnam.timekeeping.screen.login.LoginPresenter;

public class MainActivity extends ContainerActivity {

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment) new LoginPresenter(this).getFragment();
//    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PrefWrapper.getPreference(getViewContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        
    }

    @Override
    public void initLayout(View rootView) {

    }
}
