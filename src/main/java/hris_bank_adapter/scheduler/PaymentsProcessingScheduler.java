package hris_bank_adapter.scheduler;

import hris_bank_adapter.service.ExtractXMLService;
import hris_bank_adapter.service.ProcessPaymentService;
import hris_bank_adapter.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentsProcessingScheduler {

    private final ProcessPaymentService processPaymentService;

    @Scheduled(cron = "*/1 * * * * *") // Runs every second
    public ResponseEntity<CommonResponse> processPayments() {

        CommonResponse response = processPaymentService.processPayments();
        return null;
    }
}
