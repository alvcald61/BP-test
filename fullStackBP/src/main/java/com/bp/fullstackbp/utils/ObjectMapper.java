package com.bp.fullstackbp.utils;


import com.bp.fullstackbp.entities.BankAccount;
import com.bp.fullstackbp.entities.Movement;
import com.bp.fullstackbp.enumerate.AccountType;
import com.bp.fullstackbp.enumerate.MovementType;
import com.bp.fullstackbp.request.BankAccountRequest;
import com.bp.fullstackbp.request.MovementRequest;
import com.bp.fullstackbp.response.BankAccountResponse;
import com.bp.fullstackbp.response.MovementResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.text.ParseException;
import java.util.Date;

public class ObjectMapper {
  ModelMapper modelMapper;


  public <T> T map(Object source, Class<T> destinationClass) {
    modelMapper = new ModelMapper();
    return modelMapper.map(source, destinationClass);
  }

  public BankAccount map(BankAccountRequest source) {

    modelMapper = new ModelMapper();
    BankAccount account = modelMapper.map(source, BankAccount.class);
    account.setAccountType(AccountType.valueOf(source.getAccountType()));
    return account;
  }

  public BankAccountResponse mapBankAccountResponse(BankAccount source) {
    modelMapper = new ModelMapper();
    BankAccountResponse account = modelMapper.map(source, BankAccountResponse.class);
    account.setAccountType(source.getAccountType().getAltName());
    return account;
  }


  public Movement map(MovementRequest source) throws ParseException {
    modelMapper = new ModelMapper();
    modelMapper.addMappings(new PropertyMap<MovementRequest, Movement>() {
      @Override
      protected void configure() {
        skip(destination.getTransactionDate());
      }
    });
    Movement account = modelMapper.map(source, Movement.class);
    account.setMovementType(MovementType.valueOf(source.getMovementType()));
    Date date = DateUtil.parseDate(source.getTransactionDate());
    account.setTransactionDate(date);
    return account;
  }

  public MovementResponse mapMovementResponse(Movement source) {
    modelMapper = new ModelMapper();
    modelMapper.addMappings(new PropertyMap<MovementResponse, Movement>() {
      @Override
      protected void configure() {
        skip(destination.getTransactionDate());
      }
    });
    MovementResponse account = modelMapper.map(source, MovementResponse.class);
    account.setMovementType(source.getMovementType().getAltName());
    account.setTransactionDate(DateUtil.formatDate(source.getTransactionDate()));
    return account;
  }

}
