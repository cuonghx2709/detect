package com.gemvietnam.timekeeping.data.remote.callback;

import android.util.Log;

import com.gemvietnam.timekeeping.R;
import com.gemvietnam.timekeeping.data.remote.dto.ResponseDTO;
import com.gemvietnam.timekeeping.utils.App;
import com.gemvietnam.utils.DialogUtils;

import retrofit2.Call;
import retrofit2.Response;

public abstract class BaseCallback<T> implements retrofit2.Callback<ResponseDTO<T>> {
    private static final String NETWORK_ERROR = "9999";
    public static final String SUCCESS = "SUCCESS";
    private ResponseDTO<T> mBody;

    @Override
    public void onResponse(Call<ResponseDTO<T>> call, Response<ResponseDTO<T>> response) {
        mBody = null;
        String responseCode = NETWORK_ERROR;
        String message = "";

        if (response.isSuccessful()) {
            mBody = response.body();
            message = mBody.getMessage();
        }
        // not success
        if (mBody == null) {
            try {
                onError(getServerMsg() + "1");
            } catch (IllegalStateException | NullPointerException ex) {
                ex.printStackTrace();
            }
            return;
        }
        //request success
        try {
            if (mBody.getMessage().equals(SUCCESS)) {
                onSuccess(mBody.getResult());
                DialogUtils.dismissProgressDialog();
            } else {
                onError(mBody.getMessage() + "2");
            }

        } catch (IllegalStateException | NullPointerException ex) {
            ex.printStackTrace();
        }


    }

    @Override
    public void onFailure(Call<ResponseDTO<T>> call, Throwable t) {
        Log.e("onFailure", t.toString() + " " + call.request());
    }

    public ResponseDTO<T> getBody() {
        return mBody;
    }


    private String getServerMsg() {
        return App.getInstance().getString(R.string.msg_server_error);
    }

    public abstract void onError(String errorCode);

    public abstract void onSuccess(T data);
}
