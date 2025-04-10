package hris_bank_adapter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class BankInfo {

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "bank_account_no")
    private String bankAccountNo;

    @Column(name = "transaction_amount")
    private String transactionAmount;

    @Column(name = "transaction_currency")
    private String transactionCurrency;

    @Column(name = "transaction_description")
    private String transactionDescription;
}
