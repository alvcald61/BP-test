package com.bp.fullstackbp.service.impl;

import com.bp.fullstackbp.entities.Movement;
import com.bp.fullstackbp.enumerate.MovementType;
import com.bp.fullstackbp.exceptions.CustomException;
import com.bp.fullstackbp.repository.BankAccountRepository;
import com.bp.fullstackbp.repository.ClientRepository;
import com.bp.fullstackbp.repository.MovementRepository;
import com.bp.fullstackbp.response.BankAccountReport;
import com.bp.fullstackbp.response.PageAuxResponse;
import com.bp.fullstackbp.service.IReportService;
import com.bp.fullstackbp.utils.DateUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
public class ReportService implements IReportService {

  private final BankAccountRepository bankAccountRepository;

  private final MovementRepository movementRepository;

  private final EntityManager entityManager;

  private final ClientRepository clientRepository;

  public ReportService(BankAccountRepository bankAccountRepository, MovementRepository movementRepository, EntityManager entityManager, ClientRepository clientRepository) {
    this.bankAccountRepository = bankAccountRepository;
    this.movementRepository = movementRepository;
    this.entityManager = entityManager;
    this.clientRepository = clientRepository;
  }

  @Override
  public PageAuxResponse<BankAccountReport> getBankAccountsByUserId(Long userId, String start, String end, Pageable pageable) throws ParseException {
    if (start == null || end == null) {
      throw new CustomException("La fecha de inicio y fecha de fin sin requeridos", HttpStatus.BAD_REQUEST);
    }
    if (clientRepository.findById(userId).isEmpty()) {
      throw new CustomException("El usuario no existe", HttpStatus.NOT_FOUND);
    }
    Date startDate = DateUtil.parseDate(start);
    Date endDate = DateUtil.parseDate(end);
    System.out.println(startDate);
    System.out.println(endDate);
    if (startDate.after(endDate)) {
      throw new CustomException("La fecha de inicio no puede ser mayor a la fecha de fin", HttpStatus.BAD_REQUEST);
    }
    List<Movement> list = getReport(userId, startDate, endDate, pageable);
    double totalDeposit = list.stream()
      .reduce(0.0, (accumulator, element) -> {
        if (element.getMovementType().equals(MovementType.DEPOSIT)) {
          return accumulator + element.getAmount();
        } else {
          return accumulator;
        }
      }, Double::sum);

    double totalWithdrawal = list.stream()
      .reduce(0.0, (accumulator, element) -> {
        if (element.getMovementType().equals(MovementType.WITHDRAWAL)) {
          return accumulator + element.getAmount();
        } else {
          return accumulator;
        }
      }, Double::sum);
    List<BankAccountReport> report = list.stream().map(BankAccountReport::new).collect(toList());
    int totalSize = getTotalSzie(userId, startDate, endDate);
    return new PageAuxResponse<>(report, totalWithdrawal, totalDeposit, pageable.getPageNumber(), pageable.getPageSize(), totalSize);
  }

  @Override
  public Object reportToPDF(PageAuxResponse<BankAccountReport> response) throws IOException, DocumentException {
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream("report.pdf"));
    document.open();
    Font font = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.BLACK);
    document.add(new Chunk("Reporte de Movimiento\n\n", font));

    document.add(new Paragraph("\n"));
    PdfPTable table = new PdfPTable(8);
    addTableHeader(table);
    addRows(table, response);

    document.add(table);
    document.add(new Paragraph("\nTotal de depósitos: " + response.getTotalDeposits()));
    document.add(new Paragraph("\nTotal de retiros: " + response.getTotalWithdrawals()));
    document.close();
    byte[] inFileBytes = Files.readAllBytes(Paths.get("report.pdf"));
    byte[] encoded = java.util.Base64.getEncoder().encode(inFileBytes);
    return new String(encoded);
  }

  private void addRows(PdfPTable table, PageAuxResponse<BankAccountReport> response) {
    response.getContent().forEach(bankAccountReport -> {
      PdfPCell cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      cell.setPhrase(new Phrase(bankAccountReport.getClient()));
      table.addCell(cell);
      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      cell.setPhrase(new Phrase(bankAccountReport.getAccountNumber()));
      table.addCell(cell);
      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      cell.setPhrase(new Phrase(bankAccountReport.getAccountType()));
      table.addCell(cell);
      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      cell.setPhrase(new Phrase(Double.toString(bankAccountReport.getInitialBalance())));
      table.addCell(cell);
      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      cell.setPhrase(new Phrase(Double.toString(bankAccountReport.getAmount())));
      table.addCell(cell);
      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      cell.setPhrase(new Phrase(Double.toString(bankAccountReport.getAvailableBalance())));
      table.addCell(cell);
      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      cell.setPhrase(new Phrase(bankAccountReport.getTransactionDate()));
      table.addCell(cell);
      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      cell.setPhrase(new Phrase(bankAccountReport.isStatus() ? "Activo" : "Inactivo"));
      table.addCell(cell);
    });

  }

  private void addTableHeader(PdfPTable table) {
    Stream.of("Cliente", "Numero de Cuenta", "Tipo", "Saldo Inicial", "Movimiento", "Saldo Disponible", "Fecha", "Estado")
      .forEach(columnTitle -> {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setBorderWidth(2);
        header.setPhrase(new Phrase(columnTitle));
        table.addCell(header);
      });

  }

  private int getTotalSzie(Long userId, Date start, Date end) {
    String query = "SELECT COUNT(m) FROM Movement m WHERE m.bankAccount.client.id = :userId AND m.transactionDate  BETWEEN :start AND :end";
    TypedQuery<Long> typedQuery = entityManager.createQuery(query, Long.class);
    typedQuery.setParameter("userId", userId);
    typedQuery.setParameter("start", start);
    typedQuery.setParameter("end", end);
    return typedQuery.getSingleResult().intValue();
  }

  private List<Movement> getReport(Long userId, Date start, Date end, Pageable pageable) throws ParseException {
    TypedQuery<Movement> query = entityManager.createQuery("select m from Movement m inner join m.bankAccount b where b.client.id = :userId" +
      " and m.transactionDate>= :startDate and m.transactionDate<= :endDate ORDER BY m.transactionDate ASC", Movement.class);
    query.setParameter("userId", userId);
    query.setParameter("startDate", start);
    query.setParameter("endDate", end);
    query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    query.setMaxResults(pageable.getPageSize());
    return new ArrayList<>(query.getResultList());
  }
}
