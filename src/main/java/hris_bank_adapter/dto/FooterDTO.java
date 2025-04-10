package hris_bank_adapter.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class FooterDTO {

    @XmlElement(name = "CtrlSum")
    private BigDecimal ctrlSum;
}
