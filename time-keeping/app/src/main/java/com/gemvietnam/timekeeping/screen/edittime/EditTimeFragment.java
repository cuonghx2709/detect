package com.gemvietnam.timekeeping.screen.edittime;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.timekeeping.Constant;
import com.gemvietnam.timekeeping.Main2Activity;
import com.gemvietnam.timekeeping.R;
import com.gemvietnam.timekeeping.customView.slideDateTimePicker.SlideDateTimeListener;
import com.gemvietnam.timekeeping.customView.slideDateTimePicker.SlideDateTimePicker;
import com.gemvietnam.timekeeping.data.remote.dto.leave.addLeave.AddLeaveRequestDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.leaveRequest.LeaveRequestListDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.updateLeave.UpdateLeaveRequestDTO;
import com.gemvietnam.timekeeping.pref.PrefWrapper;
import com.gemvietnam.timekeeping.screen.backPressed.OnBackPressedListener;
import com.gemvietnam.timekeeping.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The EditTime Fragment
 */
public class EditTimeFragment extends ViewFragment<EditTimeContract.Presenter>
        implements EditTimeContract.View, View.OnClickListener {

    @BindView(R.id.tvCancel)
    TextView tvCancel;

    @BindView(R.id.tvSave)
    TextView tvSave;

    @BindView(R.id.layoutTimeStart)
    RelativeLayout layoutTimeStart;

    @BindView(R.id.layoutTimeEnd)
    RelativeLayout layoutTimeEnd;

    @BindView(R.id.spinTitle)
    Spinner spinTitle;

    @BindView(R.id.fieldDescription)
    EditText fieldDescription;

    @BindView(R.id.tvTimeStart)
    TextView tvTimeStart;

    @BindView(R.id.tvTimeEnd)
    TextView tvTimeEnd;

    private Date dateStart, dateEnd;

    private AddLeaveRequestDTO addLeaveRequestDTO;

    private int type = 1;

    private LeaveRequestListDTO leaveRequestListDTO;


    public static EditTimeFragment getInstance() {
        return new EditTimeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_time;
    }

    @Override
    public void initLayout(View rootView) {
        super.initLayout(rootView);
        ButterKnife.bind(this, rootView);

        ((Main2Activity) getViewContext()).setOnBackPressedListener(new OnBackPressedListener() {
            @Override
            public void doBack() {
                mPresenter.onClickCancel();
            }
        });

        layoutTimeStart.setOnClickListener(this);
        layoutTimeEnd.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvSave.setOnClickListener(this);

    }

    @Override
    public void BindViewDataSpiner(ArrayList<String> data) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getViewContext(),
                R.layout.support_simple_spinner_dropdown_item, data);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinTitle.setAdapter(arrayAdapter);
        spinTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    type = 1;
                else
                    type = 2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void getRequestLeave(LeaveRequestListDTO leaveRequestListDTO) {
        if (null != leaveRequestListDTO) {
            this.leaveRequestListDTO = leaveRequestListDTO;
            Log.e("request", leaveRequestListDTO.toString());
            tvCancel.setText(getString(R.string.tv_delete));
            tvTimeEnd.setVisibility(View.VISIBLE);
            tvTimeStart.setVisibility(View.VISIBLE);
            dateStart = new Date(leaveRequestListDTO.getStartDate());
            dateEnd = new Date(leaveRequestListDTO.getEndDate());
            tvTimeStart.setText(DateUtils.getDateTimeString(leaveRequestListDTO.getStartDate()));
            tvTimeEnd.setText(DateUtils.getDateTimeString(leaveRequestListDTO.getEndDate()));
            fieldDescription.setText(leaveRequestListDTO.getReason());
            if (leaveRequestListDTO.getLeaveType().equals(Constant.LEAVE_PAID)) {
                spinTitle.setSelection(0);
            } else
                spinTitle.setSelection(1);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutTimeStart:
                new SlideDateTimePicker.Builder(getBaseActivity().getSupportFragmentManager())
                        .setMinDate(new Date())
                        .setListener(new SlideDateTimeListener() {
                            @Override
                            public void onDateTimeSet(Date date) {
                                dateStart = date;
                                tvTimeStart.setVisibility(View.VISIBLE);
                                tvTimeStart.setText(DateUtils.getDateTimeString(date));
                            }
                        })
                        .setInitialDate(new Date())
                        .build()
                        .show();
                break;
            case R.id.layoutTimeEnd:
                new SlideDateTimePicker.Builder(getBaseActivity().getSupportFragmentManager()).
                        setMinDate(new Date()).
                        setListener(new SlideDateTimeListener() {
                            @Override
                            public void onDateTimeSet(Date date) {
                                dateEnd = date;
                                tvTimeEnd.setVisibility(View.VISIBLE);
                                tvTimeEnd.setText(DateUtils.getDateTimeString(date));
                            }
                        })
                        .setInitialDate(dateStart)
                        .build()
                        .show();
                break;
            case R.id.tvCancel:
                if (null == leaveRequestListDTO)
                    getViewContext().onBackPressed();
                else {
                    mPresenter.deleteRequestLeave(PrefWrapper.getxAuthToken(getViewContext()),
                            leaveRequestListDTO.getUuid());
                }

                break;
            case R.id.tvSave:
                if (checkInfo()) {
                    if (null == leaveRequestListDTO) {
                        addLeaveRequestDTO = new AddLeaveRequestDTO(type,
                                fieldDescription.getText().toString().trim(),
                                DateUtils.getStringDateAddLeave(dateStart),
                                DateUtils.getStringDateAddLeave(dateEnd),
                                2, DateUtils.getStringDateAddLeave(new Date()));
                        mPresenter.addLeave(PrefWrapper.getxAuthToken(getViewContext()), addLeaveRequestDTO);
                    } else {
                        mPresenter.updateRequestLeave(PrefWrapper.getxAuthToken(getViewContext()),
                                new UpdateLeaveRequestDTO(leaveRequestListDTO.getUuid(),
                                        fieldDescription.getText().toString().trim(),
                                        DateUtils.getStringDateAddLeave(dateStart),
                                        DateUtils.getStringDateAddLeave(dateEnd), type));
                    }
                }

                break;
        }
    }

    boolean checkInfo() {
        if (null == dateStart) {
            Toast.makeText(getViewContext(), "you must enter time start", Toast.LENGTH_SHORT).show();
            return false;
        } else if (null == dateEnd) {
            Toast.makeText(getViewContext(), "you must enter time end", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
