package hris_bank_adapter.service.impl;

import hris_bank_adapter.dto.DocumentDTO;
import hris_bank_adapter.exception.BaseException;
import hris_bank_adapter.model.XMLTransactionLog;
import hris_bank_adapter.repository.XMLTransactionLogRepository;
import hris_bank_adapter.service.ExtractXMLService;
import hris_bank_adapter.service.ProcessPaymentService;
import hris_bank_adapter.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessPaymentServiceImpl implements ProcessPaymentService {

    private final ExtractXMLService extractXMLService;
    private final XMLTransactionLogRepository xmlTransactionLogRepository;

    @Value("${xml.base.file.path}")
    private String xmlBaseFilePath;

    @Override
    public CommonResponse processPayments() {

        File xmlFolder = new File(xmlBaseFilePath);
        File[] files = xmlFolder.listFiles((dir, name) -> name.endsWith(".xml"));

        List<File> newFiles = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                List<XMLTransactionLog> logs = xmlTransactionLogRepository.findByFileName(file.getName().replaceFirst("\\.xml$", ""));
                if (logs.size() > 1) {
                    throw new BaseException(500, "Duplicate file name found in database.");

                } else if (logs.isEmpty()) {
                    newFiles.add(file);

                } else {
                    log.info("File already processed: {}", file.getName());
                }

            }
        }

        List<DocumentDTO> extractedFiles = extractXMLService.extractXmlFiles(newFiles);

        return null;
    }
}
