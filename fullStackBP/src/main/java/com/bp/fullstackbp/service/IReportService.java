package com.bp.fullstackbp.service;

import com.bp.fullstackbp.response.BankAccountReport;
import com.bp.fullstackbp.response.PageAuxResponse;
import com.itextpdf.text.DocumentException;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.text.ParseException;

public interface IReportService {

  PageAuxResponse<BankAccountReport> getBankAccountsByUserId(Long userId, String startDate, String endDate, Pageable pageable) throws ParseException;

  Object reportToPDF(PageAuxResponse<BankAccountReport> response) throws IOException, DocumentException;
}
