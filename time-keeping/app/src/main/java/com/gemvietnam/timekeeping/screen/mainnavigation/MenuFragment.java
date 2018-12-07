package com.gemvietnam.timekeeping.screen.mainnavigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.timekeeping.R;
import com.gemvietnam.timekeeping.pref.PrefWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.gemvietnam.timekeeping.R.id.btnLogout;

/**
 * The Menu Fragment
 */
public class MenuFragment extends ViewFragment<MenuContract.Presenter>
        implements MenuContract.View, View.OnClickListener {
    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvCheckInOut)
    TextView tvCheckInOut;

    @BindView(R.id.tvLeaveApp)
    TextView tvLeaveApp;

    @BindView(R.id.tvChangePassword)
    TextView tvChangePassword;

    @BindView(R.id.btnLogout)
    Button btnLogout;

    @BindView(R.id.layoutName)
    LinearLayout layoutName;

    @BindView(R.id.layoutCheckInOut)
    LinearLayout layoutChekInOut;

    @BindView(R.id.layoutLeaveApp)
    LinearLayout layoutLeaveApp;

    @BindView(R.id.layoutChangePassword)
    LinearLayout layoutChangePassword;

    @BindView(R.id.tvID)
    TextView tvID;

    public static MenuFragment getInstance() {
        return new MenuFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.menu_drawer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initLayout(View rootView) {
        super.initLayout(rootView);
        ButterKnife.bind(this, rootView);
        layoutName.setOnClickListener(this);
        layoutChekInOut.setOnClickListener(this);
        layoutLeaveApp.setOnClickListener(this);
        layoutChangePassword.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        tvName.setText(PrefWrapper.getUser(getViewContext()).getmFullName());
        tvID.setText("id : " + PrefWrapper.getUser(getViewContext()).getCode());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutName:
                mPresenter.clickText(0);
                break;
            case R.id.layoutCheckInOut:
                mPresenter.clickText(1);
                break;

            case R.id.layoutLeaveApp:
                mPresenter.clickText(2);
                break;

            case R.id.layoutChangePassword:
                mPresenter.clickText(3);
                break;
            case R.id.btnLogout:
                mPresenter.logout(PrefWrapper.getxAuthToken(getViewContext()));
                break;

        }
    }
}
