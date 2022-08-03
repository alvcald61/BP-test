package com.bp.fullstackbp.response;


import lombok.Data;

@Data
public class ClientResponse {
  private Long id;

  private String name;

  private String gender;

  private int age;

  private String identifier;

  private String address;

  private String phoneNumber;
  
  private String password;

  private boolean isActive;
}
