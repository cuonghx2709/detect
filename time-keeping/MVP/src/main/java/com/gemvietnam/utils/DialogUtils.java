package com.gemvietnam.utils;

import com.gemvietnam.base.log.Logger;
import com.gemvietnam.common.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.provider.Settings;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Dialog Utils
 * Created by neo on 2/15/2016.
 */
public class DialogUtils {
  private static AlertDialog sAlert;
  private static ProgressDialog sProgress;

  public static void dismissAlert() {
    if (sAlert != null && sAlert.isShowing())
      sAlert.dismiss();
  }

  /**
   * Show alert message with message id
   */
  public static void showAlert(Context context, int msgId) {
    showAlert(context, msgId, null);
  }

  /**
   * Show alert message with message id
   */
  public static void showAlert(Context context, int msgId, DialogInterface.OnClickListener onClickListener) {
    dismissProgressDialog();
    if (!isValidContext(context)) {
      return;
    }

    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setMessage(msgId);
    builder.setPositiveButton(R.string.ok, onClickListener);
    builder.create().show();
  }

  /**
   * Show alert message with message
   */
  public static void showAlert(Context context, String msg) {
    if (null == msg || msg.trim().isEmpty()) return;

    dismissProgressDialog();
    if (!isValidContext(context)) {
      return;
    }

    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setMessage(msg);
    builder.setPositiveButton(R.string.ok, null);
    builder.create().show();
  }

//  private static AlertDialog sErrorAlert;

  /**
   * Show alert message with message id
   */
  public static synchronized void showErrorAlert(Context context, String message) {
    if (null == message || message.trim().isEmpty()) return;

    dismissProgressDialog();
    if (!isValidContext(context)) {
      return;
    }

    if (sAlert != null && sAlert.isShowing()) {
      sAlert.dismiss();
    }
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle(R.string.title_alert);
    builder.setPositiveButton(R.string.ok, null);
    sAlert = builder.create();
    sAlert.setMessage(message);
    try {
      sAlert.show();
    } catch (WindowManager.BadTokenException ex) {
      Logger.e(ex);
    }
  }

  public static synchronized void showErrorAlert(Context context, String msg, boolean keepExisting) {
    if (null == msg || msg.trim().isEmpty()) return;

    dismissProgressDialog();
    if (!isValidContext(context)) {
      return;
    }

    if (sAlert == null) {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle(R.string.title_error);
      builder.setPositiveButton(R.string.ok, null);
      sAlert = builder.create();
    } else if (sAlert.isShowing()) {
      if (!keepExisting) {
        sAlert.dismiss();
      } else {
        return;
      }
    }

    sAlert.setMessage(msg);
    try {
      sAlert.show();
    } catch (WindowManager.BadTokenException ex) {
      Logger.e(ex);
    }
  }

  /**
   * Show alert message with message id
   */
  public static synchronized void showErrorAlert(Context context, int messageId, boolean keepExisting) {
    dismissProgressDialog();
    if (!isValidContext(context)) {
      return;
    }

    if (sAlert == null) {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle(R.string.title_error);
      builder.setPositiveButton(R.string.ok, null);
      sAlert = builder.create();
    } else if (sAlert.isShowing()) {
      if (!keepExisting) {
        sAlert.dismiss();
      } else {
        return;
      }
    }

    sAlert.setMessage(context.getString(messageId));
    try {
      sAlert.show();
    } catch (WindowManager.BadTokenException ex) {
      Logger.e(ex);
    }
  }

  /**
   * Show dialog with 1 button
   */
  public static void showErrorAlert(Context context, String message, int buttonTextId, DialogInterface.OnClickListener onClickListener) {
    if (null == message || message.trim().isEmpty()) return;

    dismissProgressDialog();
    if (!isValidContext(context)) {
      return;
    }
    if (context instanceof Activity && ((Activity) context).isFinishing()) {
      return;
    }

    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setMessage(message);
    builder.setTitle(R.string.title_error);
    builder.setPositiveButton(buttonTextId, onClickListener);
    builder.setCancelable(false);
    builder.create().show();
  }

  /**
   * Show dialog progress.
   *
   * @param activity the context is running.
   */
  public static synchronized void showProgressDialog(final Activity activity) {
    try {
      dismissProgressDialog();
      if (!isValidContext(activity)) {
        return;
      }

      if (sAlert != null && sAlert.isShowing()) {
        sAlert.dismiss();
      }
      if (sProgress != null && sProgress.isShowing()) {
        sProgress.dismiss();
      }
      sProgress = ProgressDialog.show(activity, "", activity.getString(R.string.loading),
          true, false);
    } catch (Exception e) {
      Logger.e(e);
    }
  }

  /**
   * Show progress dialog
   */
  public static synchronized void showProgressDialog(final Context context) {
    try {
      dismissProgressDialog();
      if (!isValidContext(context)) {
        return;
      }

      if (sAlert != null && sAlert.isShowing()) {
        sAlert.dismiss();
      }
      sProgress = ProgressDialog.show(context, "", context.getString(R.string.loading),
          true, false);
//            sProgress = new ProgressDialog(context);
//            sProgress.show();
//            sProgress.setMessage(context.getString(R.string.loading));
    } catch (Exception ex) {
      Logger.e(ex);
    }
  }

  /**
   * Dismiss progress.
   *
   * @param activity the context is running.
   */
  public static void dismissProgressDialog(final Activity activity) {
    try {
      if (!isValidContext(activity)) {
        return;
      }

      if (sProgress != null && sProgress.isShowing()) {
        sProgress.dismiss();
      }
    } catch (Exception e) {
      Logger.e(e);
    }
  }

  /**
   * Dismiss progress dialog
   */
  public static void dismissProgressDialog() {
    try {
      if (sProgress != null && sProgress.isShowing()) {
        sProgress.dismiss();
      }
    } catch (Exception ex) {
      Logger.e(ex);
    }
  }

  /**
   * Show alert dialog with action
   */
  public static void showAlertAction(Context context, int message, DialogInterface.OnClickListener listener) {
    showAlertAction(context, context.getString(message), listener);
  }

  public static void showAlertAction(Context context, int title, int message, int button, DialogInterface.OnClickListener listener) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setMessage(message).setTitle(context.getString(title))
        .setPositiveButton(button, listener)
        .create().show();
  }

  public static void showAlertAction(Context context, String title, String message,
                                     int buttonPositive, int buttonNagative,
                                     DialogInterface.OnClickListener listener) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setMessage(message)
        .setTitle(title)
        .setPositiveButton(buttonPositive, listener)
        .setNegativeButton(buttonNagative, null)
        .create().show();
  }

  /**
   * Show alert dialog with action
   */
  public static void showAlertAction(Context context, String message, DialogInterface.OnClickListener listener) {
    if (null == message || message.trim().isEmpty()) return;

    dismissProgressDialog();
    if (!isValidContext(context)) {
      return;
    }

    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setMessage(message);
    builder.setPositiveButton(R.string.ok, listener);
    builder.setNegativeButton(R.string.cancel, null);
    builder.create().show();
  }

  /**
   * Show dialog with title, messge and button behavior
   */
  public static void showDialogMessage(final Context context,
                                       @StringRes final int titleId,
                                       @StringRes final int messageId,
                                       final DialogInterface.OnClickListener listener, final boolean isCancel) {
    showDialogMessage(context, context.getString(titleId), context.getString(messageId), listener, isCancel);
  }

  /**
   * @param context  the context is running.
   * @param title    of dialog.
   * @param message  of dialog.
   * @param listener callback when clicked button ok.
   * @param isCancel true logParams button cancel.
   */
  public static synchronized void showDialogMessage(final Context context,
                                       final String title, final String message,
                                       final DialogInterface.OnClickListener listener, final boolean isCancel) {
    if (null == message || message.trim().isEmpty()) return;

    dismissProgressDialog();

    // Check context valid
    if (!isValidContext(context)) {
      return;
    }

    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
    if (title != null)
      builder.setTitle(title);
    builder.setMessage(message);
    builder.setCancelable(false);
    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        if (listener != null) {
          listener.onClick(dialog, 12);
        }
        dialog.dismiss();
      }
    });
    if (isCancel) {
      builder.setNegativeButton(R.string.cancel,
          new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                            if (listener != null) {
//                                listener.onClick(dialog, 13);
//                            }
              dialog.dismiss();
            }
          });
    }
    if (sAlert == null || !sAlert.isShowing()) {
      sAlert = builder.create();
      sAlert.show();
    }
    TextView messageView = (TextView) sAlert
        .findViewById(android.R.id.message);
    if (messageView != null) {
      messageView.setGravity(Gravity.CENTER);
    }
  }

  /**
   * Show dialog when network error
   *
   * @param activity Activity logParams dialog
   */
  public static void showNetworkErrorDialog(final Activity activity) {
    dismissProgressDialog();
//    dismissAlert();
    // Check context valid
    if (!isValidContext(activity)) {
      return;
    }

    // Open Wireless setting
    DialogInterface.OnClickListener settingsListener = new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        if (!activity.isFinishing()) {
          activity.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
        }
      }
    };

    // Show dialog
    showDialogMessage(activity, activity.getString(R.string.title_network_lost),
        activity.getString(R.string.msg_network_lost), settingsListener,
        true);
  }

  /**
   * Check if context is valid
   */
  public static boolean isValidContext(Context context) {
    if (context == null) {
      return false;
    }

    if (context instanceof Activity && ((Activity) context).isFinishing()) {
      return false;
    }

    return true;
  }

  public static synchronized void showProgressDialogWithMessage(final Context context,
                                                                int title, int message) {
    try {
      dismissProgressDialog();
      if (!isValidContext(context)) {
        return;
      }

      if (sAlert != null && sAlert.isShowing()) {
        sAlert.dismiss();
      }
      if (sProgress != null && sProgress.isShowing()) {
        sProgress.dismiss();
      }
      sProgress = ProgressDialog.show(context, context.getString(title), context.getString(message),
          true, false);

    } catch (Exception e) {
      Logger.e(e);
    }
  }

  public static AlertDialog.Builder showCustomDialog(Context context, String title, String message, final CustomDialogCallback callback, boolean flag) {
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
    final View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_progress_dialog, null);
    dialogBuilder.setView(dialogView);
    dialogBuilder.setCancelable(false);
    final AlertDialog b = dialogBuilder.create();

    TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvTitle);
    TextView tvMessage = (TextView) dialogView.findViewById(R.id.tvMessage);
    Button btnSkip = (Button) dialogView.findViewById(R.id.btnSkip);
    Button btnUpdate = (Button) dialogView.findViewById(R.id.btnUpdate);

    if (flag) {
      btnSkip.setEnabled(false);
    }

    tvTitle.setText(title);
    tvMessage.setText(message);
    btnSkip.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        b.dismiss();
      }
    });

    btnUpdate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        b.dismiss();
        if (null != callback) {
          callback.onClick();
        }
      }
    });

    b.show();

    return dialogBuilder;
  }

  public interface CustomDialogCallback {
    void onClick();
  }
}