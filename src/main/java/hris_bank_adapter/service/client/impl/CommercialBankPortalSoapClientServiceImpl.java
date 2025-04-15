package hris_bank_adapter.service.client.impl;

import hris_bank_adapter.service.client.CommercialBankPortalSoapClientService;
import hris_bank_adapter.wsdl.DocumentRequest;
import hris_bank_adapter.wsdl.DocumentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommercialBankPortalSoapClientServiceImpl implements CommercialBankPortalSoapClientService {

    private final Jaxb2Marshaller marshaller;

    @Value("${xml.base.commercial.endpoint}")
    private String xmlBaseCommercialEndpoint;

    @Override
    public DocumentResponse transferExtractedSalaryData(DocumentRequest request) {
        try {
            WebServiceTemplate template = new WebServiceTemplate(marshaller);
            return (DocumentResponse) template.marshalSendAndReceive(xmlBaseCommercialEndpoint, request);

        } catch (Exception e) {
            log.error("Error occurred while processing the request: {}", String.valueOf(e));
        }
        return null;
    }
}
