package com.bp.fullstackbp.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class PageAuxResponse<T> {
  List<T> content;
  double totalWithdrawals;
  double totalDeposits;
  int page;
  int size;
  int totalElements;

}
