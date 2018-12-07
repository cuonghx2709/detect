package com.gemvietnam.timekeeping.screen.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.timekeeping.Main2Activity;
import com.gemvietnam.timekeeping.data.remote.callback.BaseCallback;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;
import com.gemvietnam.timekeeping.data.remote.dto.LoginRequestDTO;
import com.gemvietnam.timekeeping.data.remote.dto.LoginResponseDTO;
import com.gemvietnam.timekeeping.data.remote.dto.ResponseDTO;
import com.gemvietnam.timekeeping.data.remote.dto.User;
import com.gemvietnam.timekeeping.faceTracker.FaceTrackerActivity;
import com.gemvietnam.timekeeping.pref.PrefWrapper;
import com.gemvietnam.timekeeping.screen.home.HomePresenter;
import com.gemvietnam.timekeeping.screen.leave.LeavePresenter;
import com.gemvietnam.timekeeping.utils.MessageMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;

/**
 * The Login Presenter
 */
public class LoginPresenter extends Presenter<LoginContract.View, LoginContract.Interactor>
  implements LoginContract.Presenter {

	static final String USER_NOT_EXITS="USER_NOT_EXITS";
	static final String INCORRECT_PASSWORD="INCORRECT_PASSWORD";

  public LoginPresenter(ContainerView containerView) {
    super(containerView);
  }


	@Override
	public LoginContract.View onCreateView() {
		return LoginFragment.getInstance();
	}

	@Override
	public void start() {
	}

	@Override
	public LoginContract.Interactor onCreateInteractor() {
		return new LoginInteractor(this);
	}

	@Override
	public void login(String user, String password) {
		if (user.equals("") || password.equals("")){
			Toast.makeText(getViewContext(),"User Name or PassWord is must not be null",Toast.LENGTH_LONG).show();

		}else {
			mView.showProgress();
			mInteractor.login(user, password, new BaseCallback<JsonElement>() {

				@Override
				public void onError(String errorCode) {
					mView.hideProgress();
					Toast.makeText(getViewContext(),getBody().getMessage(),Toast.LENGTH_LONG).show();
				}
				@Override
				public void onSuccess(JsonElement data) {
					mView.showProgress();
					LoginResponseDTO dto= new Gson().fromJson(data, LoginResponseDTO.class);
					PrefWrapper.saveToken(getViewContext(),dto.getToken());
					PrefWrapper.saveUser(getViewContext(),dto.getUserDTO());
					Log.e("user",PrefWrapper.getUser(getViewContext()).getmId());
					if(PrefWrapper.getFirstLogin(getViewContext())){
						Intent intent = new Intent(getViewContext(), Main2Activity.class);
						getViewContext().startActivity(intent);
					} else {
						Toast.makeText(getViewContext(), "You must update face.", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(getViewContext(), FaceTrackerActivity.class);
						getViewContext().startActivity(intent);
					}

//					mView.saveUserPass();
				}
			});
//			mView.showProgress();
//			try {
//				JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
//				PrefWrapper.saveUser(getViewContext(),
//						new Gson().fromJson(String.valueOf(jsonObject.getJSONObject("result").getJSONObject("userDTO")), User.class));
//				PrefWrapper.saveToken(getViewContext(),jsonObject.getJSONObject("result").getString("token"));
//				Log.e("user",PrefWrapper.getUser(getViewContext()).toString());
//				Intent intent = new Intent(getViewContext(), Main2Activity.class);
//					getViewContext().startActivity(intent);
//					mView.hideProgress();
//
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}


		}
	}




}
