package hris_bank_adapter.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class HeaderDTO {

    @XmlElement(name = "CompanyRegName")
    private String companyRegName;

    @XmlElement(name = "CompanyRegNo")
    private String companyRegNo;
}