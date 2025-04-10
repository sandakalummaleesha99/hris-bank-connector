package hris_bank_adapter.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class BankInfoDTO {

    @XmlElement(name = "BankName")
    private String bankName;

    @XmlElement(name = "BankAccount")
    private String bankAccount;

    @XmlElement(name = "BankCode")
    private String bankCode;

    @XmlElement(name = "BranchCode")
    private String branchCode;

    @XmlElement(name = "Salary")
    private double salary;
}
