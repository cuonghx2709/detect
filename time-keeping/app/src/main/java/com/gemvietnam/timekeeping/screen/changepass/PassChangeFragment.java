package com.gemvietnam.timekeeping.screen.changepass;

import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.timekeeping.Main2Activity;
import com.gemvietnam.timekeeping.R;
import com.gemvietnam.timekeeping.pref.PrefWrapper;
import com.gemvietnam.timekeeping.screen.backPressed.OnBackPressedListener;
import com.gemvietnam.timekeeping.utils.UserUtil;

import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * The PassChange Fragment
 */
public class PassChangeFragment extends ViewFragment<PassChangeContract.Presenter> implements PassChangeContract.View {
    @BindView(R.id.save_btn)
    Button save_btn;
    @BindView(R.id.current_pass_et)
    EditText current_pass_et;
    @BindView(R.id.new_pass_et)
    EditText new_pass_et;
    @BindView(R.id.confirm_pass_et)
    EditText confirm_pass_et;
    @BindView(R.id.cancel_change_pass)
    Button cancel_change_pass;
    @BindView(R.id.eyes_confirm_pass)
    ImageView eyes_confirm;
    @BindView(R.id.eyes_current_pass)
    ImageView eyes_current;
    @BindView(R.id.eyes_new_pass)
    ImageView eyes_new;

    public static PassChangeFragment getInstance() {
        return new PassChangeFragment();
    }

    private boolean showPassWordConfirm;
    private boolean showPassWordNew;
    private boolean showPassWordCurrent;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pass_change;
    }

    @Override
    public void initLayout(View rootView) {
        super.initLayout(rootView);
        ButterKnife.bind(this,rootView);

        ((Main2Activity) getViewContext()).setOnBackPressedListener(() -> mPresenter.onBackPressed());

        showPassWordConfirm = false;
        showPassWordNew = false;
        save_btn.setOnClickListener(v -> {
            if(current_pass_et.getText().toString().trim().equals("")){
                current_pass_et.setError(getString(R.string.current_password_empty));
                current_pass_et.requestFocus();
            } else if(new_pass_et.getText().toString().trim().equals("")){
                new_pass_et.setError(getString(R.string.new_password_empty));
                new_pass_et.requestFocus();
            } else if(confirm_pass_et.getText().toString().trim().equals("")){
                confirm_pass_et.setError(getString(R.string.confirm_password_empty));
                confirm_pass_et.requestFocus();
            } else {
                String token = PrefWrapper.getxAuthToken(getViewContext()), currentpass, newpass, confirmpass;
                currentpass = current_pass_et.getText().toString();
                confirmpass = confirm_pass_et.getText().toString();
                newpass = new_pass_et.getText().toString();
                if (!newpass.equals(confirmpass)) {
                    showAlertDialog(getViewContext().getString(R.string.confirm_pass));
                } else {
                    mPresenter.getDataChangePass(token, currentpass, newpass);
                }
            }



        });
        cancel_change_pass.setOnClickListener(v -> {
            Intent intent = new Intent(getViewContext(), Main2Activity.class);
            getViewContext().startActivity(intent);
        });
        eyes_current.setVisibility(View.GONE);
        eyes_confirm.setVisibility(View.GONE);
        eyes_new.setVisibility(View.GONE);
        current_pass_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    eyes_current.setVisibility(View.VISIBLE);
                } else {
                    eyes_current.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        new_pass_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    eyes_new.setVisibility(View.VISIBLE);
                } else {
                    eyes_new.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirm_pass_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    eyes_confirm.setVisibility(View.VISIBLE);
                } else {
                    eyes_confirm.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        eyes_confirm.setOnClickListener(v -> {
            if (showPassWordConfirm) {
                confirm_pass_et.setTransformationMethod(new PasswordTransformationMethod());
            } else {
                confirm_pass_et.setTransformationMethod(null);
            }
            showPassWordConfirm = !showPassWordConfirm;
        });
        eyes_new.setOnClickListener(v -> {
            if (showPassWordNew){
                new_pass_et.setTransformationMethod(new PasswordTransformationMethod());
            }else {
                new_pass_et.setTransformationMethod(null);
            }
            showPassWordNew = !showPassWordNew;
        });
        eyes_current.setOnClickListener(v -> {
            if (showPassWordCurrent){
                current_pass_et.setTransformationMethod(new PasswordTransformationMethod());
            }else {
                current_pass_et.setTransformationMethod(null);
            }
            showPassWordCurrent = !showPassWordCurrent;
        });
    }

}
