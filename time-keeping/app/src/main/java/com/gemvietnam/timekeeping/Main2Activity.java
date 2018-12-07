package com.gemvietnam.timekeeping;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.FrameLayout;

import com.gemvietnam.base.ContainerActivity;
import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.timekeeping.screen.backPressed.OnBackPressedListener;
import com.gemvietnam.timekeeping.screen.mainnavigation.MenuPresenter;

import butterknife.BindView;


public class Main2Activity extends ContainerActivity
        implements DrawerLayout.DrawerListener, MenuPresenter.OnItemClicked {

    @BindView(R.id.left_drawer)
    FrameLayout leftDrawer;

    public static DrawerLayout drawerLayout;
    //    @BindView(R.id.menu_ic)
//    ImageView menu_ic;

    protected OnBackPressedListener onBackPressedListener;


    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }


    @Override
    public ViewFragment onCreateFirstFragment() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawerLayout.addDrawerListener(this);
    }

    @Override
    public void initLayout() {
        super.initLayout();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.left_drawer,
                        new MenuPresenter(null)
                                .setMainActivity(Main2Activity.this)
                                .setOnItemClick(this)
                                .getFragment())
                .commit();
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onItemClick() {
        if (drawerLayout.isDrawerOpen(leftDrawer)) drawerLayout.closeDrawer(leftDrawer);
    }


    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null) {
            onBackPressedListener.doBack();
        } else
            super.onBackPressed();

    }

    @Override
    public void initLayout(View rootView) {

    }
}
