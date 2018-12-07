package com.gemvietnam.timekeeping.screen.leave;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.timekeeping.Constant;
import com.gemvietnam.timekeeping.Main2Activity;
import com.gemvietnam.timekeeping.R;
import com.gemvietnam.timekeeping.customView.monthYearPicker.SlideDateTimeListener;
import com.gemvietnam.timekeeping.customView.monthYearPicker.SlideDateTimePicker;
import com.gemvietnam.timekeeping.data.remote.dto.leave.leaveRequest.LeaveRequestListDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.leaveRequest.LeaveRequestListRequestDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.totalLeave.TotalLeaveResultDTO;
import com.gemvietnam.timekeeping.pref.PrefWrapper;
import com.gemvietnam.timekeeping.screen.backPressed.OnBackPressedListener;
import com.gemvietnam.timekeeping.utils.InternetUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * The leave Fragment
 */
public class LeaveFragment extends ViewFragment<LeaveContract.Presenter>
        implements LeaveContract.View, View.OnClickListener {
//  @BindView(R.id.add_ic)
//  ImageView mAdd;

    @BindView(R.id.btnDrawer)
    ImageView btnDrawer;

    @BindView(R.id.btnAddLeave)
    ImageView btnAddLeave;

    @BindView(R.id.tvLeaveTotal)
    TextView tvLeaveTotal;

    @BindView(R.id.tvLeaveUsed)
    TextView tvLeaveUsed;

    @BindView(R.id.listRequestLeaves)
    RecyclerView listRequestLeaves;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    @BindView(R.id.tvMonthYear)
    TextView tvMonthYear;

    @BindView(R.id.ivMonthBack)
    ImageView ivMonthBack;

    @BindView(R.id.ivMonthNext)
    ImageView ivMonthNext;

    private Calendar selectedDate;

    private ListRequestLeavesAdapter listRequestLeavesAdapter;

    private ArrayList<LeaveRequestListDTO> leaveRequestListDTOs;

    private LeaveRequestListRequestDTO leaveRequestListRequestDTO;

    public static LeaveFragment getInstance() {
        return new LeaveFragment();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void initLayout(View rootView) {
        super.initLayout(rootView);
        ButterKnife.bind(this, rootView);

        selectedDate = new GregorianCalendar();
        selectedDate.setTime(new Date());
        tvMonthYear.setText(selectedDate.get(Calendar.MONTH) + 1 + "/" + selectedDate.get(Calendar.YEAR));
        swipeRefresh.setRefreshing(true);
        leaveRequestListRequestDTO = new LeaveRequestListRequestDTO
                (selectedDate.get(Calendar.MONTH) + 1, selectedDate.get(Calendar.YEAR));
        mPresenter.getListRequestLeaves(PrefWrapper.getxAuthToken(getViewContext()),
                leaveRequestListRequestDTO);
        mPresenter.getTotalLeaves(PrefWrapper.getxAuthToken(getViewContext()));

        initListLeaves();
        refresh();
        btnAddLeave.setOnClickListener(this);
        btnDrawer.setOnClickListener(this);
        ivMonthBack.setOnClickListener(this);
        ivMonthNext.setOnClickListener(this);
        tvMonthYear.setOnClickListener(this);
    }

    void initListLeaves() {
        leaveRequestListDTOs = new ArrayList<>();
        listRequestLeaves.setItemAnimator(new DefaultItemAnimator());
        listRequestLeaves.setLayoutManager(new LinearLayoutManager(getViewContext()));
        listRequestLeavesAdapter = new ListRequestLeavesAdapter(getViewContext(),
                leaveRequestListDTOs, new ListRequestLeavesAdapter.RequestLeaveClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (leaveRequestListDTOs.get(position).getStatus().equals(Constant.STATUS_PENDING)) {
                    mPresenter.onClickItemLeave(leaveRequestListDTOs.get(position));
                }
            }
        });
        ((Main2Activity) getViewContext()).setOnBackPressedListener(new OnBackPressedListener() {
            @Override
            public void doBack() {
              mPresenter.onBack();
            }
        });
        listRequestLeaves.setAdapter(listRequestLeavesAdapter);
        listRequestLeavesAdapter.notifyDataSetChanged();
    }

    void refresh() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                leaveRequestListRequestDTO = new LeaveRequestListRequestDTO
                        (selectedDate.get(Calendar.MONTH) + 1, selectedDate.get(Calendar.YEAR));
                mPresenter.getListRequestLeaves(PrefWrapper.getxAuthToken(getViewContext()),
                        leaveRequestListRequestDTO);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_leave;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddLeave:
                mPresenter.onClickAddLeave();
                break;
            case R.id.btnDrawer:
                Main2Activity.drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.ivMonthBack:
                if (1 == selectedDate.get(Calendar.MONTH)) {
                    selectedDate.add(Calendar.YEAR, -1);
                    selectedDate.set(Calendar.MONTH, 11);
                } else {
                    selectedDate.add(Calendar.MONTH, -1);
                }
                tvMonthYear.setText(selectedDate.get(Calendar.MONTH) + 1 + "/" + selectedDate.get(Calendar.YEAR));
                leaveRequestListRequestDTO.setMonth(selectedDate.get(Calendar.MONTH) + 1);
                leaveRequestListRequestDTO.setYear(selectedDate.get(Calendar.YEAR));
                swipeRefresh.setRefreshing(true);
                mPresenter.getListRequestLeaves(PrefWrapper.getxAuthToken(getViewContext()),
                        leaveRequestListRequestDTO);
                break;
            case R.id.ivMonthNext:
                if (11 == selectedDate.get(Calendar.MONTH)) {
                    selectedDate.add(Calendar.YEAR, 1);
                    selectedDate.set(Calendar.MONTH, 1);
                } else {
                    selectedDate.add(Calendar.MONTH, 1);
                }
                leaveRequestListRequestDTO.setMonth(selectedDate.get(Calendar.MONTH) + 1);
                leaveRequestListRequestDTO.setYear(selectedDate.get(Calendar.YEAR));
                tvMonthYear.setText(selectedDate.get(Calendar.MONTH) + 1 + "/"
                        + selectedDate.get(Calendar.YEAR));
                swipeRefresh.setRefreshing(true);
                mPresenter.getListRequestLeaves(PrefWrapper.getxAuthToken(getViewContext()),
                        leaveRequestListRequestDTO);
                break;
            case R.id.tvMonthYear:
                new SlideDateTimePicker.Builder(getBaseActivity().getSupportFragmentManager())
                        .setListener(new SlideDateTimeListener() {
                            @Override
                            public void onDateTimeSet(Date date) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                selectedDate.setTime(date);
                                tvMonthYear.setText(selectedDate.get(Calendar.MONTH) + 1
                                        + "/" + selectedDate.get(Calendar.YEAR));
                                leaveRequestListRequestDTO.setMonth(selectedDate.get(Calendar.MONTH) + 1);
                                leaveRequestListRequestDTO.setYear(selectedDate.get(Calendar.YEAR));
                                swipeRefresh.setRefreshing(true);
                                mPresenter.getListRequestLeaves(PrefWrapper.getxAuthToken(getViewContext()),
                                        leaveRequestListRequestDTO);
                            }
                        })
                        .setInitialDate(selectedDate.getTime())
                        .build()
                        .show();
                break;
        }
    }

    @Override
    public void setListRequestLeaves(ArrayList<LeaveRequestListDTO> leaves) {
        if (null != leaves) {
            Log.e("leaves", leaves.toString());
            this.leaveRequestListDTOs.clear();
            this.leaveRequestListDTOs.addAll(leaves);
            listRequestLeavesAdapter.notifyDataSetChanged();
            swipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void getListRequestLeaves(String message) {
        if(null != this.leaveRequestListDTOs){
            leaveRequestListDTOs.clear();
            listRequestLeavesAdapter.notifyDataSetChanged();
        }
        swipeRefresh.setRefreshing(false);
        if (!InternetUtils.hasConnection(getViewContext())) {
            Toast.makeText(getViewContext(), R.string.not_internet, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setTotalLeaves(TotalLeaveResultDTO totalLeaves) {
        if (null != totalLeaves.getNumberInTotal())
            tvLeaveTotal.setText(totalLeaves.getNumberInTotal().toString());
        if (null != totalLeaves.getNumberInUsed())
            tvLeaveUsed.setText(totalLeaves.getNumberInUsed().toString());
    }

    @Override
    public void onReload() {
        Log.e("reload","true view");
        leaveRequestListRequestDTO = new LeaveRequestListRequestDTO
                (selectedDate.get(Calendar.MONTH) + 1, selectedDate.get(Calendar.YEAR));
        mPresenter.getListRequestLeaves(PrefWrapper.getxAuthToken(getViewContext()),
                leaveRequestListRequestDTO);
        mPresenter.getTotalLeaves(PrefWrapper.getxAuthToken(getViewContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("resume leave", "true");
        mPresenter.getListRequestLeaves(PrefWrapper.getxAuthToken(getViewContext()),
                leaveRequestListRequestDTO);
        mPresenter.getTotalLeaves(PrefWrapper.getxAuthToken(getViewContext()));
        listRequestLeavesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
