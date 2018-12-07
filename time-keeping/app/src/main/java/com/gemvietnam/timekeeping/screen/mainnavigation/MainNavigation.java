package com.gemvietnam.timekeeping.screen.mainnavigation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

import com.gemvietnam.timekeeping.R;
import com.gemvietnam.timekeeping.data.remote.ServiceBuilder;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;
import com.gemvietnam.timekeeping.data.remote.dto.LoginResponseDTO;
import com.gemvietnam.timekeeping.pref.PrefWrapper;

import butterknife.OnClick;


public class MainNavigation extends FrameLayout {

    public MainNavigation(@NonNull Context context) {
        super(context);
    }
}
