package com.gemvietnam.timekeeping.data.remote.callback;

import android.content.Context;

import com.gemvietnam.timekeeping.R;
import com.gemvietnam.timekeeping.data.remote.dto.MonthYearRequestDTO;
import com.gemvietnam.timekeeping.data.remote.dto.ResponseDTO;
import com.gemvietnam.timekeeping.utils.MessageMap;
import com.gemvietnam.utils.DialogUtils;
import com.gemvietnam.utils.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;


public abstract class CommonCallBack<T> extends BaseCallback<T>  {
  private Context mContext;
  private Call<ResponseDTO<T>> mCall;

  protected CommonCallBack(Context mcontext){this.mContext = mContext;}

  @Override
  public void onError(String errorCode) {
    DialogUtils.dismissProgressDialog();
    String errorMessage;

    if (MessageMap.getMessageValue(errorCode) != null){
       errorMessage = MessageMap.getMessageValue(errorCode).toString();
    }else {
      errorMessage ="null message";
    }

    if (StringUtils.isEmpty(errorMessage)){
      errorMessage = mContext.getString(R.string.msg_server_error);
    }
    DialogUtils.showAlert(mContext, errorMessage);
  }

  @Override
  public void onSuccess(T data) {
    DialogUtils.dismissProgressDialog();
  }
}
