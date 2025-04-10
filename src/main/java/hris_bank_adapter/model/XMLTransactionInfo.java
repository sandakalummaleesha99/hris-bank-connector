package hris_bank_adapter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "xml_transaction_info")
public class XMLTransactionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "epf_no")
    private String epfNo;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "nic")
    private String nic;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "bankName", column = @Column(name = "bank_name")),
            @AttributeOverride(name = "bankCode", column = @Column(name = "bank_code")),
            @AttributeOverride(name = "branchCode", column = @Column(name = "branch_code")),
            @AttributeOverride(name = "bankAccountNo", column = @Column(name = "bank_account_no")),
            @AttributeOverride(name = "transactionAmount", column = @Column(name = "transaction_amount")),
            @AttributeOverride(name = "transactionCurrency", column = @Column(name = "transaction_currency")),
            @AttributeOverride(name = "transactionDescription", column = @Column(name = "transaction_description")),
    })
    private BankInfo bankInfo;

    @JoinColumn(name = "xml_transaction_log_id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private XMLTransactionLog xmlTransactionLog;
}
