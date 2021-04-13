package com.timebusker.entity;

public class User {

  private final static int st = 0;
  private String username;
  private String password;
  private String uid;

  public User(String uid, String username, String password) {
    this.username = username;
    this.password = password;
    this.uid = uid;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  @Override
  public String toString() {
    return String.format("UserInfo:[uid:%s,username:%s,password:%s]", uid, username, password);
  }
}
