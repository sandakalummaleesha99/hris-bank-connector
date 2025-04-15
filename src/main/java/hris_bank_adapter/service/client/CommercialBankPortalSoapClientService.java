package hris_bank_adapter.service.client;

import hris_bank_adapter.wsdl.DocumentRequest;
import hris_bank_adapter.wsdl.DocumentResponse;

public interface CommercialBankPortalSoapClientService {

    DocumentResponse transferExtractedSalaryData(DocumentRequest request);
}
