package com.gemvietnam.timekeeping.screen.login;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.timekeeping.R;
import com.gemvietnam.timekeeping.pref.PrefWrapper;
import com.gemvietnam.timekeeping.utils.InternetUtils;

import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.gemvietnam.timekeeping.utils.UserUtil.convertPassWord;


/**
 * The Login Fragment
 */
public class LoginFragment extends ViewFragment<LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.login_btn)
    Button mLogin;
    @BindView(R.id.user_name_et)
    EditText mUserName;
    @BindView(R.id.pass_word_et)
    EditText mPassWord;
    @BindView(R.id.btnDeletePass)
    ImageView mDeletePass;
    @BindView(R.id.btnDeleteUser)
    ImageView mDeleteUser;
    @BindView(R.id.tvForgotPassword)
    TextView tvForgotPassword;
    @BindView(R.id.checkboxSave)
    CheckBox cbSave;


    public static LoginFragment getInstance() {
        return new LoginFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }


    @Override
    public void initLayout(View rootView) {
        super.initLayout(rootView);
        ButterKnife.bind(this, rootView);
        if (null != PrefWrapper.getUsername(getViewContext()) &&
                null != PrefWrapper.getPassword(getViewContext())) {
            mUserName.setText(PrefWrapper.getUsername(getViewContext()));
            mPassWord.setText(PrefWrapper.getPassword(getViewContext()));
            cbSave.setChecked(true);
        }

        mLogin.setOnClickListener(v -> {

            if (InternetUtils.hasConnection(getViewContext())) {
                showProgress();
                String username, password;
                username = mUserName.getText().toString().trim();

                password = mPassWord.getText().toString().trim();
                if (cbSave.isChecked()) {
                    PrefWrapper.setUsername(getViewContext(), username);
                    PrefWrapper.setPassword(getViewContext(), mPassWord.getText().toString().trim());
                } else {
                    PrefWrapper.setUsername(getViewContext(), null);
                    PrefWrapper.setPassword(getViewContext(), null);
                }
                mPresenter.login(username, password);
            } else {
                hideProgress();
                Toast.makeText(getViewContext(), R.string.internet_not_available, Toast.LENGTH_LONG).show();
            }
        });
        tvForgotPassword.setOnClickListener(v -> {

        });
        mDeleteUser.setOnClickListener(v -> mUserName.setText(""));
        mDeletePass.setOnClickListener(v -> mPassWord.setText(""));
        mDeletePass.setVisibility(View.GONE);
        mDeleteUser.setVisibility(View.GONE);
        mUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    mDeleteUser.setVisibility(View.VISIBLE);
                } else {
                    mDeleteUser.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    mDeletePass.setVisibility(View.VISIBLE);
                } else {
                    mDeletePass.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPassWord.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mPassWord.setOnEditorActionListener((v, actionId, event) -> {
            if (InternetUtils.hasConnection(getViewContext())) {
                showProgress();
                String username, password = null;
                username = mUserName.getText().toString().trim();

                try {
                    password = convertPassWord(mPassWord.getText().toString().trim());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                if (cbSave.isChecked()) {
                    PrefWrapper.setUsername(getViewContext(), username);
                    PrefWrapper.setPassword(getViewContext(), mPassWord.getText().toString().trim());
                } else {
                    PrefWrapper.setUsername(getViewContext(), null);
                    PrefWrapper.setPassword(getViewContext(), null);
                }
                mPresenter.login(username, password);
            } else {
                hideProgress();
                Toast.makeText(getViewContext(), R.string.internet_not_available, Toast.LENGTH_LONG).show();
            }
            return true;
        });
    }
}
