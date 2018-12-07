package com.gemvietnam.base;

import com.gemvietnam.event.NetworkEvent;
import com.gemvietnam.eventbus.EventBusWrapper;
import com.gemvietnam.utils.ActivityUtils;
import com.gemvietnam.utils.DialogUtils;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyCache;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import butterknife.ButterKnife;

/**
 * Base activity
 * Created by neo on 2/5/2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements ConnectivityChangeListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//    overridePendingTransition(CoreDefault.ANIM_IN, CoreDefault.ANIM_OUT);
    setContentView(getLayoutId());
    // Inject views
    ButterKnife.bind(this);

    // Prepare layout
    initLayout();

    ConnectionBuddy.getInstance().clearNetworkCache(this, savedInstanceState);
  }

  public void initLayout() {
  }

  public void showAlertDialog(String message) {
    DialogUtils.showErrorAlert(this, message);
  }

  public void showProgress() {
    DialogUtils.showProgressDialog(this);
  }

  public void hideProgress() {
    DialogUtils.dismissProgressDialog();
  }

  public void onRequestError(String errorCode, String errorMessage) {
    DialogUtils.showErrorAlert(this, errorMessage);
    hideProgress();
  }

  public void onRequestSuccess() {
    hideProgress();
  }

  /**
   * Return layout resource id for activity
   */
  protected abstract int getLayoutId();

  /**
   * Hide keyboard of current focus view
   */
  public void hideKeyboard() {
    View view = this.getCurrentFocus();
    if (view != null) {
      InputMethodManager imm =
          (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
      getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
  }

  /**
   * Hide keyboard of current focus view
   */
  public void hideKeyboard(View view) {
    if (view != null) {
      InputMethodManager imm =
          (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
      getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
  }

  /**
   * Show keyboard for {@link EditText}
   */
  public void showKeyboard(EditText editText) {
    editText.requestFocus();
    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
  }

  public void addFragment(int containerId, BaseFragment fragment, boolean addToBackStack,
                          String tag) {
    if (fragment == null) return;

    ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, containerId,
        addToBackStack, tag);
  }

  public void addFragment(int containerId, BaseFragment fragment, boolean addToBackStack) {
    if (fragment == null) return;

    addFragment(containerId, fragment, addToBackStack, fragment.getClass().getSimpleName());
  }

  public void addFragment(int containerId, BaseFragment fragment) {
    addFragment(containerId, fragment, false, null);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    FragmentManager manager = getSupportFragmentManager();
    if (manager != null) {
      for (Fragment fragment : manager.getFragments()) {
        if (fragment != null) {
          fragment.onActivityResult(requestCode, resultCode, data);
        }
      }
    }
  }

  @Override
  protected void onStart() {
    super.onStart();

    // Register for connectivity changes
//    ConnectionBuddy.getInstance().registerForConnectivityEvents(this, this);
  }

  @Override
  protected void onStop() {
    // Unregister from connectivity events
//    ConnectionBuddy.getInstance().unregisterFromConnectivityEvents(this);

    super.onStop();
  }

  /**
   * Override this method if you want to manually handle connectivity change events.
   *
   * @param event ConnectivityEvent which holds all data about network connection state.
   */
  @Override
  public void onConnectionChange(ConnectivityEvent event) {
    if (null == event) return;
    ConnectivityState state = event.getState();
    if (null == state) return;

    switch (state.getValue()) {
      case ConnectivityState.DISCONNECTED:
        EventBusWrapper.post(new NetworkEvent(NetworkEvent.Action.DISCONNECTED));
        break;
      case ConnectivityState.CONNECTED:
        EventBusWrapper.post(new NetworkEvent(NetworkEvent.Action.CONNECTED));
        break;
      default:
        break;
    }
  }
}
