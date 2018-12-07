package com.gemvietnam.timekeeping.data.remote;

import com.gemvietnam.timekeeping.data.remote.dto.InOutRecordDTOList;
import com.gemvietnam.timekeeping.data.remote.dto.ResponseDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.leaveRequest.LeaveRequestResultDTO;
import com.gemvietnam.timekeeping.data.remote.dto.leave.totalLeave.TotalLeaveResultDTO;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InternalService {
    @FormUrlEncoded
    @POST("time_keeping/check_login.php")
    Call<ResponseDTO<JsonElement>> login(@Field("username") String username,
                                         @Field("password") String password);

    @FormUrlEncoded
    @POST("time_keeping/add_leave_requests.php")
    Call<ResponseDTO<ResponseDTO>>
    addLeave(@Field("token") String token,
             @Field("leaveType") int leaveType,
             @Field("reason") String reason,
             @Field("startDate") String startDate,
             @Field("endDate") String endDate,
             @Field("status") int status,
             @Field("requestDate") String requestDate);

    @FormUrlEncoded
    @POST("time_keeping/update_leave_requests.php")
    Call<ResponseDTO<ResponseDTO>>
    updateLeave(@Field("token") String token,
                @Field("id") String id,
                @Field("reason") String reason,
                @Field("startDate") String startDate,
                @Field("endDate") String endDate,
                @Field("type") int type);

    @FormUrlEncoded
    @POST("time_keeping/delete_leave_requests.php")
    Call<ResponseDTO<ResponseDTO>>
    deleteLeave(@Field("token") String token,
                @Field("id") String id);

    @FormUrlEncoded
    @POST("time_keeping/leave_list.php")
    Call<ResponseDTO<LeaveRequestResultDTO>>
    listLeave(@Field("token") String token,
              @Field("month") int month,
              @Field("year") int year);


    @FormUrlEncoded
    @POST("time_keeping/total_leave.php")
    Call<ResponseDTO<TotalLeaveResultDTO>>
    totalLeaves(@Field("token") String token);

    @FormUrlEncoded
    @POST("time_keeping/check_in_out_list.php")
    Call<ResponseDTO<InOutRecordDTOList>>
    getListCheckInOut(@Field("token") String token,
                      @Field("year") int year,
                      @Field("month") int month);

    @FormUrlEncoded
    @POST("time_keeping/update_check_in_out.php")
    Call<ResponseDTO<ResponseDTO>>
    updateCheckinOut(@Field("token") String token,
                     @Field("qrCode") String qrCode,
                     @Field("id") String id,
                     @Field("time") String time,
                     @Field("isTimeIn") int isTimeIn);

    @FormUrlEncoded
    @POST("time_keeping/log_out.php")
    Call<ResponseDTO<Object>>
    logout(@Field("token") String token);

    @FormUrlEncoded
    @POST("time_keeping/change_password.php")
    Call<ResponseDTO<Object>>
    changePassword(@Field("token") String token, @Field("currentPass") String currentPass, @Field("newPass") String newPass);

    @FormUrlEncoded
    @POST("time_keeping/update_embeddings.php")
    Call<ResponseDTO<ResponseDTO>> updateEmbeddings(@Field("token") String token,
                                                    @Field("embeddings") String embeddings);
}
