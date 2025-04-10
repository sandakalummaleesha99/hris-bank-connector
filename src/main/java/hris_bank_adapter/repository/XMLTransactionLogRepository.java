package hris_bank_adapter.repository;

import hris_bank_adapter.model.XMLTransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XMLTransactionLogRepository extends JpaRepository<XMLTransactionLog, Integer> {

    List<XMLTransactionLog> findByFileName(String fileName);
}
