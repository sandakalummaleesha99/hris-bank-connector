package hris_bank_adapter.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentDTO {

    @XmlElement(name = "Header")
    private HeaderDTO headerDTO;

    @XmlElement(name = "EmployeeInfo")
    private List<EmployeeInfoDTO> employeeInfoDTOS;

    @XmlElement(name = "Footer")
    private FooterDTO footerDTO;
}
