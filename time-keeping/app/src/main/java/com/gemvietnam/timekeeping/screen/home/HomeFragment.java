package com.gemvietnam.timekeeping.screen.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.timekeeping.Main2Activity;
import com.gemvietnam.timekeeping.R;
import com.gemvietnam.timekeeping.customView.monthYearPicker.SlideDateTimeListener;
import com.gemvietnam.timekeeping.customView.monthYearPicker.SlideDateTimePicker;
import com.gemvietnam.timekeeping.data.remote.dto.InOutRecordDTOList;
import com.gemvietnam.timekeeping.data.remote.dto.MonthYearRequestDTO;
import com.gemvietnam.timekeeping.data.remote.dto.UpdateCheckInOutDTO;
import com.gemvietnam.timekeeping.faceTracker.FaceTrackerActivity;
import com.gemvietnam.timekeeping.interfaces.Classifier;
import com.gemvietnam.timekeeping.others.QrCodeResultCallback;
import com.gemvietnam.timekeeping.others.ScannerQRCodeFragment;
import com.gemvietnam.timekeeping.pref.PrefWrapper;
import com.gemvietnam.timekeeping.screen.backPressed.OnBackPressedListener;
import com.gemvietnam.timekeeping.screen.home.adapter.CheckInOutAdapter;
import com.gemvietnam.timekeeping.utils.DateUtils;
import com.gemvietnam.timekeeping.utils.InternetUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.gemvietnam.timekeeping.Constant.INCORRECT_QR_CODE;
import static com.gemvietnam.timekeeping.Constant.IN_OUT_RECORD_NOT_FOUND;


/**
 * The Home Fragment
 */
public class HomeFragment extends ViewFragment<HomeContract.Presenter>
        implements HomeContract.View, QrCodeResultCallback, View.OnClickListener {

    public static final int REQEST_DECTECT_FACE = 0;

    @BindView(R.id.ivDrawer)
    ImageView ivDrawer;

    @BindView(R.id.qr_code_ic)
    ImageView mQrCode;

    @BindView(R.id.home_sc)
    LinearLayout mHomeScreen;

    @BindView(R.id.calendar_tv)
    TextView tvMonthYear;

    @BindView(R.id.check_in_out_rv)
    RecyclerView mCheckInOut_rv;

    @BindView(R.id.text_user)
    TextView textUser;

    @BindView(R.id.llQrcode)
    LinearLayout llQrcode;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    @BindView(R.id.ivMonthBack)
    ImageView ivMonthBack;

    @BindView(R.id.ivMonthNext)
    ImageView ivMonthNext;

    @BindView(R.id.tvTotalCount)
    TextView tvTotalCount;

    @BindView(R.id.put_device)
    TextView put_device;

    @BindView(R.id.detect_iv)
    ImageView mDetectIv;

    private ActionBarDrawerToggle toggle;

    CheckInOutAdapter checkInOutAdapter;

    private MonthYearRequestDTO monthYearRequestDTO;

    private Calendar selectedDate;

    boolean doubleBackToExitPressedOnce = false;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 5 && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            llQrcode.setVisibility(View.VISIBLE);
            put_device.setVisibility(View.VISIBLE);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.llQrcode, ScannerQRCodeFragment.getNewInstance(HomeFragment.this));
            ft.addToBackStack(ScannerQRCodeFragment.class.getSimpleName());
            ft.commitAllowingStateLoss();
//            Toast.makeText(getViewContext(), "SCAN SUCCESS", Toast.LENGTH_LONG).show();
        } else {

        }

    }


    @Override
    public void onStart() {
        super.onStart();
        ((Main2Activity) getViewContext()).setOnBackPressedListener(new OnBackPressedListener() {
            @Override
            public void doBack() {
                Log.e("back", "home");
                if (llQrcode.getVisibility() == View.GONE) {
                    if (doubleBackToExitPressedOnce) {
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory(Intent.CATEGORY_HOME);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                    }

                    doubleBackToExitPressedOnce = true;
                    Toast.makeText(getViewContext(), R.string.click_back_to_exit, Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);
                } else {
                    put_device.setVisibility(View.GONE);
                    llQrcode.setVisibility(View.GONE);
                }
            }
        });
    }



    @Override
    public void initLayout(View rootView) {
        super.initLayout(rootView);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getViewContext());
        mCheckInOut_rv.setLayoutManager(linearLayoutManager);
        selectedDate = new GregorianCalendar();
        selectedDate.setTime(new Date());
        tvMonthYear.setText(selectedDate.get(Calendar.MONTH) + 1 + "/" + selectedDate.get(Calendar.YEAR));
        swipeRefresh.setRefreshing(true);
        monthYearRequestDTO = new MonthYearRequestDTO(selectedDate.get(Calendar.MONTH)
                + 1, selectedDate.get(Calendar.YEAR));
        mPresenter.getDataCheckInOut(PrefWrapper.getxAuthToken(getViewContext()), monthYearRequestDTO);


        mQrCode.setOnClickListener(new View.OnClickListener() {
            int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA);

            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),
                        android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= 22) {
                        requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                                5);
                    } else {

                    }
                } else {
                    llQrcode.setVisibility(View.VISIBLE);
                    put_device.setVisibility(View.VISIBLE);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.llQrcode, ScannerQRCodeFragment.getNewInstance(HomeFragment.this));
                    ft.addToBackStack(ScannerQRCodeFragment.class.getSimpleName());
                    ft.commit();
                }

            }

        });
        refresh();

        ivDrawer.setOnClickListener(this);
        ivMonthBack.setOnClickListener(this);
        ivMonthNext.setOnClickListener(this);
        tvMonthYear.setOnClickListener(this);
        mDetectIv.setOnClickListener(this);

        textUser.setText(PrefWrapper.getUser(getViewContext()).getmFullName());

    }

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initList() {
    }

    void refresh() {
        swipeRefresh.setOnRefreshListener(() -> mPresenter.getDataCheckInOut(PrefWrapper.getxAuthToken(getViewContext()),
                monthYearRequestDTO));
    }

    @Override
    public void BindViewListCheckInOut(InOutRecordDTOList inOutRecordDTO) {
        swipeRefresh.setRefreshing(false);

        tvTotalCount.setText(DateUtils.getTotal(inOutRecordDTO.getCount_total()));
        mCheckInOut_rv.setAdapter(mPresenter.getCheckInOutAdapter());
    }

    @Override
    public void BindViewListCheckInOutError(String message) {
        if (!InternetUtils.hasConnection(getViewContext())) {
            Toast.makeText(getViewContext(), R.string.not_internet, Toast.LENGTH_SHORT).show();
        }
        swipeRefresh.setRefreshing(false);
        //
    }

    @Override
    public void BindViewUpdateCheckInOut(UpdateCheckInOutDTO updateCheckInOutDTO) {
        mCheckInOut_rv.setAdapter(mPresenter.getCheckInOutAdapter());
    }

    @Override
    public void BindViewUpdateCheckInOutError(String message) {
        if (!InternetUtils.hasConnection(getViewContext())) {
            Toast.makeText(getViewContext(), R.string.not_internet, Toast.LENGTH_SHORT).show();
        }

        switch (message) {
            case INCORRECT_QR_CODE: {
                Toast.makeText(getViewContext(), R.string.incorrect_qr_code,
                        Toast.LENGTH_LONG).show();
            }
            case IN_OUT_RECORD_NOT_FOUND: {
                Toast.makeText(getViewContext(), R.string.in_out_record_not_found,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void loadBackCheckInOut() {
        swipeRefresh.setRefreshing(true);
        monthYearRequestDTO = new MonthYearRequestDTO(selectedDate.get(Calendar.MONTH)
                + 1, selectedDate.get(Calendar.YEAR));
        mPresenter.getDataCheckInOut(PrefWrapper.getxAuthToken(getViewContext()), monthYearRequestDTO);

    }


    @Override
    public void onSuccessScanQrCode(String value) {
        put_device.setVisibility(View.GONE);
        llQrcode.setVisibility(View.GONE);
        mPresenter.onBack();
        Log.e("TAG", "Value: " + value);
        mPresenter.sendQRCode(value);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivMonthBack:
                if (1 == selectedDate.get(Calendar.MONTH)) {
                    selectedDate.add(Calendar.YEAR, -1);
                    selectedDate.set(Calendar.MONTH, 11);
                } else {
                    selectedDate.add(Calendar.MONTH, -1);
                }
                swipeRefresh.setRefreshing(true);
                monthYearRequestDTO.setmMonth(selectedDate.get(Calendar.MONTH) + 1);
                monthYearRequestDTO.setmYear(selectedDate.get(Calendar.YEAR));
                tvMonthYear.setText(selectedDate.get(Calendar.MONTH) + 1 + "/"
                        + selectedDate.get(Calendar.YEAR));
                mPresenter.getDataCheckInOut(PrefWrapper.getxAuthToken(getViewContext()),
                        monthYearRequestDTO);
                break;
            case R.id.ivMonthNext:
                if (11 == selectedDate.get(Calendar.MONTH)) {
                    selectedDate.add(Calendar.YEAR, 1);
                    selectedDate.set(Calendar.MONTH, 1);
                } else {
                    selectedDate.add(Calendar.MONTH, 1);
                }
                swipeRefresh.setRefreshing(true);
                monthYearRequestDTO.setmMonth(selectedDate.get(Calendar.MONTH) + 1);
                monthYearRequestDTO.setmYear(selectedDate.get(Calendar.YEAR));
                tvMonthYear.setText(selectedDate.get(Calendar.MONTH) + 1 + "/"
                        + selectedDate.get(Calendar.YEAR));
                mPresenter.getDataCheckInOut(PrefWrapper.getxAuthToken(getViewContext()),
                        monthYearRequestDTO);
                break;
            case R.id.calendar_tv:
                new SlideDateTimePicker.Builder(getBaseActivity().getSupportFragmentManager())
                        .setListener(new SlideDateTimeListener() {
                            @Override
                            public void onDateTimeSet(Date date) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                selectedDate.setTime(date);
                                tvMonthYear.setText(selectedDate.get(Calendar.MONTH) + 1
                                        + "/" + selectedDate.get(Calendar.YEAR));
                                monthYearRequestDTO.setmMonth(selectedDate.get(Calendar.MONTH) + 1);
                                monthYearRequestDTO.setmYear(selectedDate.get(Calendar.YEAR));
                                swipeRefresh.setRefreshing(true);
                                mPresenter.getDataCheckInOut(PrefWrapper.getxAuthToken(getViewContext()),
                                        monthYearRequestDTO);
                            }
                        })
                        .setInitialDate(selectedDate.getTime())
                        .build()
                        .show();
                break;
            case R.id.ivDrawer:
//                showProgress();
                Main2Activity.drawerLayout.openDrawer(GravityCompat.START);
//                new Handler().postDelayed(() -> hideProgress(), 2000);
                break;
            case R.id.detect_iv:
                startActivityForResult(new Intent(getViewContext(), FaceTrackerActivity.class), REQEST_DECTECT_FACE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQEST_DECTECT_FACE){
            mPresenter.sendQRCode("2018");
        }
    }
}


