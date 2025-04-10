package hris_bank_adapter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "xml_transaction_log")
public class XMLTransactionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "read_date_time")
    private LocalDateTime readDateTime;

    @Column(name = "file_name")
    private String fileName;

    @OneToMany(mappedBy = "xmlTransactionLog", fetch = FetchType.LAZY)
    private List<XMLTransactionInfo> xmlTransactionInfos = new ArrayList<>();
}
