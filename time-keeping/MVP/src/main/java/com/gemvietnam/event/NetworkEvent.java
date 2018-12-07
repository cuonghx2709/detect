package com.gemvietnam.event;

/**
 * Created by bavv on 6/02/17.
 */
public class NetworkEvent {

  private Object data;

  private Action action;

  public NetworkEvent(Action action) {
    this.action = action;
  }

  public NetworkEvent(Action action, Object data) {
    this.action = action;
    this.data = data;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public Action getAction() {
    return action;
  }

  public void setAction(Action action) {
    this.action = action;
  }

  public enum Action {
    DISCONNECTED,
    CONNECTED,
  }
}
