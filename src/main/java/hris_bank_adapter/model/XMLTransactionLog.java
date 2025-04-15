package hris_bank_adapter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "xml_transaction_log")
@NoArgsConstructor
@AllArgsConstructor
public class XMLTransactionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "read_date_time")
    private LocalDateTime readDateTime;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "xmlTransactionLog", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<XMLTransactionInfo> xmlTransactionInfos = new ArrayList<>();
}
