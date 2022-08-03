package com.bp.fullstackbp.controller;

import com.bp.fullstackbp.exceptions.CustomException;
import com.bp.fullstackbp.request.BankAccountRequest;
import com.bp.fullstackbp.response.BankAccountReport;
import com.bp.fullstackbp.response.BankAccountResponse;
import com.bp.fullstackbp.response.PageAuxResponse;
import com.bp.fullstackbp.response.StandardResponse;
import com.bp.fullstackbp.service.impl.BankAccountService;
import com.bp.fullstackbp.service.impl.ReportService;
import com.itextpdf.text.DocumentException;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class BankAccountController {
  private final BankAccountService bankAccountService;

  private final ReportService reportService;

  public BankAccountController(BankAccountService bankAccountService, ReportService reportService) {
    this.bankAccountService = bankAccountService;
    this.reportService = reportService;
  }

  @GetMapping("/listarTodos")
  public StandardResponse<List<BankAccountResponse>> getAllBankAccounts() {
    try {
      return new StandardResponse<>(bankAccountService.getAllBankAccounts());
    } catch (CustomException e) {
      return new StandardResponse<>(e);
    }
  }

  @PutMapping("/actualizar")
  public StandardResponse<BankAccountResponse> updateBankAccount(@RequestBody BankAccountRequest bankAccountRequest) {
    try {
      return new StandardResponse<>(bankAccountService.updateBankAccount(bankAccountRequest));
    } catch (CustomException e) {
      return new StandardResponse<>(e);
    }
  }

  @PostMapping("/guardar")
  public StandardResponse<BankAccountResponse> createBankAccount(@RequestBody BankAccountRequest bankAccountRequest) {
    try {
      return new StandardResponse<>(bankAccountService.saveBankAccount(bankAccountRequest));
    } catch (CustomException e) {
      return new StandardResponse<>(e);
    }
  }

  @DeleteMapping("/eliminar/{id}")
  public StandardResponse<Boolean> deleteBankAccount(@PathVariable Long id) {
    try {
      return new StandardResponse<>(bankAccountService.deleteBankAccount(id));
    } catch (CustomException e) {
      return new StandardResponse<>(e);
    }
  }

  @GetMapping("/reporte/{userId}")
  public StandardResponse<?> getBankAccountsByUserId(@PathVariable Long userId, @RequestParam String startDate,
                                                     @RequestParam String endDate, Pageable pageable, @RequestParam(required = false) String format) {
    try {
      PageAuxResponse<BankAccountReport> response = reportService.getBankAccountsByUserId(userId, startDate, endDate, pageable);
      if (format != null && format.equals("PDF")) {
        return new StandardResponse<>(reportService.reportToPDF(response));
      }
      return new StandardResponse<>(response);
    } catch (CustomException e) {
      return new StandardResponse<>(e);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    } catch (DocumentException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @GetMapping("/cliente/{id}")
  public StandardResponse<List<BankAccountResponse>> getMovement(@PathVariable Long id) {
    try {
      return new StandardResponse<>(bankAccountService.getMovement(id));
    } catch (CustomException e) {
      return new StandardResponse<>(e);
    }
  }

}
