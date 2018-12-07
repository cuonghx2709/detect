package com.gemvietnam.timekeeping.data.remote.dto;

import com.google.gson.annotations.SerializedName;



public class ResponseDTO<T> {
  @SerializedName("messageKey")
  private String message;
  @SerializedName("result")
  private T result;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getResult() {
    return result;
  }

  public void setResult(T result) {
    this.result = result;
  }
}
