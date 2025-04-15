package hris_bank_adapter.service.impl;

import hris_bank_adapter.model.BankInfo;
import hris_bank_adapter.model.XMLTransactionInfo;
import hris_bank_adapter.model.XMLTransactionLog;
import hris_bank_adapter.repository.XMLTransactionLogRepository;
import hris_bank_adapter.service.ExtractXMLService;
import hris_bank_adapter.service.ProcessPaymentService;
import hris_bank_adapter.service.client.CommercialBankPortalSoapClientService;
import hris_bank_adapter.wsdl.DocumentRequest;
import hris_bank_adapter.wsdl.DocumentResponse;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author @maleeshasa
 * @Date 2025-04-15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessPaymentServiceImpl implements ProcessPaymentService {

    private final ExtractXMLService extractXMLService;
    private final XMLTransactionLogRepository xmlTransactionLogRepository;
    private final CommercialBankPortalSoapClientService commercialBankPortalSoapClientService;

    @Value("${xml.base.file.path}")
    private String xmlBaseFilePath;

    /**
     * This method is responsible for processing salary payments of xml file
     * and transfer to commercial bank portal using the ProcessPaymentService.
     *
     * @author @maleeshasa
     */
    @Transactional
    @Override
    public void processPayments() {
        log.info("ProcessPaymentServiceImpl.processPayments() => started.");
        File xmlFolder = new File(xmlBaseFilePath);
        File[] files = xmlFolder.listFiles((dir, name) -> name.endsWith(".xml"));

        List<File> newFiles = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName().replaceFirst("\\.xml$", "");
                List<XMLTransactionLog> logs = xmlTransactionLogRepository.findByFileName(fileName);
                if (logs.size() > 1) {
                    log.error("Duplicate file name found in database.");

                } else if (logs.isEmpty()) {
                    log.info("{} is a new file.", fileName);
                    newFiles.add(file);

                } else {
                    log.info("File already processed: {}", file.getName());
                }
            }

        } else {
            log.info("XML folder is empty.");
        }

        if (!newFiles.isEmpty()) {
            log.info("Extracting data from xml files...");
            Map<String, DocumentRequest> extractXmlFiles = extractXMLService.extractXmlFiles(newFiles);

            List<XMLTransactionLog> xmlTransactionLogs = new ArrayList<>();
            for (Map.Entry<String, DocumentRequest> entry : extractXmlFiles.entrySet()) {
                String fName = entry.getKey();
                DocumentRequest request = entry.getValue();

                log.info("Transferring data to commercial bank portal...");
                DocumentResponse response = commercialBankPortalSoapClientService.transferExtractedSalaryData(request);

                XMLTransactionLog xmlTransactionLog = new XMLTransactionLog();
                xmlTransactionLog.setFileName(fName);
                xmlTransactionLog.setReadDateTime(LocalDateTime.now());

                if (response.getStatusCode() == HttpStatus.OK.value()) {
                    log.info("Payment is deposited to the bank account!");
                    List<XMLTransactionInfo> xmlTransactionInfos = new ArrayList<>();
                    for (DocumentResponse.BankTransferResponse.PaymentRefNumber paymentRefNumber : response.getBankTransferResponse().getPaymentRefNumber()) {

                        Optional<DocumentRequest.EmployeeInfo> employeeInfo = request.getEmployeeInfos().stream()
                                .filter(f -> f.getEpfNo().equals(paymentRefNumber.getKey()))
                                .findAny();

                        XMLTransactionInfo xmlTransactionInfo = new XMLTransactionInfo();
                        if (employeeInfo.isPresent()) {
                            DocumentRequest.EmployeeInfo info = employeeInfo.get();
                            xmlTransactionInfo.setEpfNo(info.getEpfNo());
                            xmlTransactionInfo.setFirstName(info.getFirstName());
                            xmlTransactionInfo.setMiddleName(info.getMiddleName());
                            xmlTransactionInfo.setLastName(info.getLastName());
                            xmlTransactionInfo.setNic(info.getNic());
                            xmlTransactionInfo.setBankInfo(new BankInfo(
                                    info.getBankInfo().getBankName(),
                                    info.getBankInfo().getBankCode(),
                                    info.getBankInfo().getBranchCode(),
                                    info.getBankInfo().getBankAccount(),
                                    info.getBankInfo().getSalary(),
                                    info.getBankInfo().getCurrency(),
                                    info.getBankInfo().getDescription()
                            ));
                            xmlTransactionInfo.setPaymentRefNumber(paymentRefNumber.getValue());
                            xmlTransactionInfo.setXmlTransactionLog(xmlTransactionLog);
                        } else {
                            continue;
                        }
                        xmlTransactionInfos.add(xmlTransactionInfo);
                    }
                    xmlTransactionLog.setXmlTransactionInfos(xmlTransactionInfos);
                    xmlTransactionLog.setStatus("PROCEED");
                    log.info("Xml log is saved for file: {}", fName);

                } else {
                    xmlTransactionLog.setStatus("FAILED");
                    log.error("Error processing payment: {}", response.getMessage());
                }
                xmlTransactionLogs.add(xmlTransactionLog);
            }
            xmlTransactionLogRepository.saveAll(xmlTransactionLogs);
        }
        log.info("ProcessPaymentServiceImpl.processPayments() => ended.");
    }
}
