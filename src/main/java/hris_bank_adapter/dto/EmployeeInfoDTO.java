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
public class EmployeeInfoDTO {

    @XmlElement(name = "EpfNo")
    private String epfNo;

    @XmlElement(name = "FirstName")
    private String firstName;

    @XmlElement(name = "MiddleName")
    private String middleName;

    @XmlElement(name = "LastName")
    private String lastName;

    @XmlElement(name = "NIC")
    private String NIC;

    @XmlElement(name = "BankInfo")
    private BankInfoDTO bankInfoDTO;
}
