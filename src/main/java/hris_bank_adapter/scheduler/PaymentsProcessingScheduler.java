package hris_bank_adapter.scheduler;

import hris_bank_adapter.service.ProcessPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author @maleeshasa
 * @Date 2025-04-15
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentsProcessingScheduler {

    private final ProcessPaymentService processPaymentService;

    /**
     * This method is scheduled to run every second.
     * It processes salary payments of xml file
     * and transfer to commercial bank portal using the ProcessPaymentService.
     *
     * @author @maleeshasa
     */
    @Scheduled(cron = "*/1 * * * * *") // Runs every second
    public void processPayments() {
        log.info("PaymentsProcessingScheduler.processPayments() => started.");
        processPaymentService.processPayments();
    }
}
