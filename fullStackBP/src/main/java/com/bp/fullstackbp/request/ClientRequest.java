package com.bp.fullstackbp.request;


import com.google.gson.Gson;
import lombok.Data;

@Data
public class ClientRequest {
  private Long id;

  private String name;

  private String gender;

  private int age;

  private String identifier;

  private String address;

  private String phoneNumber;

  private String password;

  private boolean isActive;

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }
}
