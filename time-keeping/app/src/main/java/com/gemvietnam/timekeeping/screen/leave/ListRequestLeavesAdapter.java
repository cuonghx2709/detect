package com.gemvietnam.timekeeping.screen.leave;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gemvietnam.timekeeping.Constant;
import com.gemvietnam.timekeeping.R;
import com.gemvietnam.timekeeping.data.remote.dto.leave.leaveRequest.LeaveRequestListDTO;
import com.gemvietnam.timekeeping.utils.DateUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListRequestLeavesAdapter extends
        RecyclerView.Adapter<ListRequestLeavesAdapter.RequestLeaveHolder> {

    private Context context;
    private ArrayList<LeaveRequestListDTO> data;
    private RequestLeaveClickListener requestLeaveClickListener;

    public ListRequestLeavesAdapter(Context context,
                                    ArrayList<LeaveRequestListDTO> data,
                                    RequestLeaveClickListener requestLeaveClickListener) {
        this.context = context;
        this.data = data;
        this.requestLeaveClickListener = requestLeaveClickListener;
    }

    @Override
    public RequestLeaveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.request_leave_item, parent, false);
        return new RequestLeaveHolder(view);
    }

    @Override
    public void onBindViewHolder(RequestLeaveHolder holder, int position) {

        LeaveRequestListDTO leave = data.get(position);
        if (null != leave.getRequestDate())
            holder.tvRequestDate.setText(DateUtils.convertTimeStampToDate(leave.getRequestDate()));
        if (null != leave.getLeaveType())
            holder.tvTypeLeave.setText(leave.getLeaveType());
        holder.tvDescription.setSelected(true);
        if (null != leave.getReason())
            holder.tvDescription.setText(leave.getReason());
        if (null != leave.getStartDate() && null != leave.getEndDate())
            holder.tvTimeStartEnd.setText(DateUtils.convertTimeStampToDateTime(leave.getStartDate()) +
                    "  -  " + DateUtils.convertTimeStampToDateTime(leave.getEndDate()));
        if(leave.getStatus().equals(Constant.STATUS_PENDING)){
            holder.ivStatus.setImageResource(R.drawable.ic_status_pending);
        } else  if(leave.getStatus().equals(Constant.STATUS_APPROVED)){
            holder.ivStatus.setImageResource(R.drawable.ic_status_approved);
        } else
            holder.ivStatus.setImageResource(R.drawable.ic_ejected);


    }

    @Override
    public int getItemCount() {
        return (null == data) ? 0 : data.size();
    }

    class RequestLeaveHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvRequestDate)
        TextView tvRequestDate;

        @BindView(R.id.tvTypeLeave)
        TextView tvTypeLeave;

        @BindView(R.id.tvDescription)
        TextView tvDescription;

        @BindView(R.id.tvTimeStartEnd)
        TextView tvTimeStartEnd;

        @BindView(R.id.ivStatus)
        ImageView ivStatus;

        public RequestLeaveHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestLeaveClickListener.onClick(v, getAdapterPosition());
                }
            });
        }
    }

    public interface RequestLeaveClickListener {
        void onClick(View view, int position);
    }
}
