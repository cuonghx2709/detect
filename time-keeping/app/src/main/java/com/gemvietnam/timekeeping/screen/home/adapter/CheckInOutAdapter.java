package com.gemvietnam.timekeeping.screen.home.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gemvietnam.timekeeping.Constant;
import com.gemvietnam.timekeeping.R;
import com.gemvietnam.timekeeping.data.remote.dto.InOutRecordDTO;
import com.gemvietnam.timekeeping.data.remote.dto.InOutRecordDTOList;
import com.gemvietnam.timekeeping.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CheckInOutAdapter extends RecyclerView.Adapter<CheckInOutAdapter.CheckInOutHolder> {
    private Context context;
    private List<InOutRecordDTO> items;
    private InOutRecordDTOList inOutRecordDTOList;

    public CheckInOutAdapter(Context context, List<InOutRecordDTO> items) {
        this.context = context;
        this.items = items;
    }

//    public class CheckInOutHolder extends RecyclerView.ViewHolder

    @Override
    public CheckInOutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check_in_out, parent, false);
        return new CheckInOutHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CheckInOutHolder holder, int position) {

        InOutRecordDTO inOutRecordDTO = items.get(position);
        if (inOutRecordDTO.getDayOfWeek().equals(Constant.SUNDAY) ||
                inOutRecordDTO.getDayOfWeek().equals(Constant.SATURDAY)) {
            holder.layoutDateTime.setBackgroundColor(ContextCompat.getColor(context, R.color.md_red_600));
        } else {
            holder.layoutDateTime.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
        if (null != items.get(position).getDateCheck())
            holder.dayofMonth.setText(String.valueOf(items.get(position).getDateCheck()));
        if (null != items.get(position).getCheckInTime()) {
            holder.timeIn.setText(DateUtils.getTimeString(items.get(position).getCheckInTime()));
        } else {
            holder.timeIn.setText("--");
        }

        if (null != items.get(position).getCheckOutTime()) {
            holder.timeOut.setText(DateUtils.getTimeString(items.get(position).getCheckOutTime()));
        } else {
            holder.timeOut.setText("--");
        }
        holder.dayofWeek.setText(items.get(position).getDayOfWeek());
//        holder.count.setText(String.valueOf(items.get(position).getTotal()));
        holder.count.setText(DateUtils.getTotal(items.get(position).getTotal()));
//        holder.countTotal.setText(DateUtils.getTotal(inOutRecordDTOList.getCount_total()));


    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CheckInOutHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dayofmonth)
        TextView dayofMonth;
        @BindView(R.id.dayofweek)
        TextView dayofWeek;
        @BindView(R.id.time_in_tv)
        TextView timeIn;
        @BindView(R.id.time_out_tv)
        TextView timeOut;
        @BindView(R.id.count)
        TextView count;
        @BindView(R.id.layoutDateTime)
        LinearLayout layoutDateTime;

        public CheckInOutHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
