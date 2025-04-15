package hris_bank_adapter.service.impl;

import hris_bank_adapter.enums.HRISXMLElements;
import hris_bank_adapter.service.ExtractXMLService;
import hris_bank_adapter.wsdl.DocumentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author @maleeshasa
 * @Date 2025-04-15
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class ExtractXMLServiceImpl implements ExtractXMLService {

    /**
     * This method is allowed to extract data from xml files
     *
     * @param newFiles {@link List<File>} - List of new files
     * @return {@link List<DocumentRequest>} - data list of extracted files
     * @author @maleeshasa
     */
    @Override
    public Map<String, DocumentRequest> extractXmlFiles(List<File> newFiles) {
        log.info("ExtractXMLServiceImpl.extractXmlFiles() => started.");
        Map<String, DocumentRequest> documents = new HashMap<>();

        for (File file : newFiles) {
            try {
                String fileName = file.getName().replaceFirst("\\.xml$", "");
                DocumentRequest extractedData = new DocumentRequest();
                List<DocumentRequest.EmployeeInfo> employeeInfos = new ArrayList<>();

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                org.w3c.dom.Document xmlDocument = dBuilder.parse(file);
                xmlDocument.getDocumentElement().normalize();

                NodeList headerNodes = xmlDocument.getElementsByTagName(HRISXMLElements.HEADER.getElement());
                if (headerNodes.getLength() > 0) {
                    Element headerElement = (Element) headerNodes.item(0);
                    DocumentRequest.Header header = new DocumentRequest.Header();
                    header.setCompanyRegName(headerElement.getElementsByTagName(HRISXMLElements.COMPANY_REG_NAME.getElement()).item(0).getTextContent());
                    header.setCompanyRegNo(headerElement.getElementsByTagName(HRISXMLElements.COMPANY_REG_NO.getElement()).item(0).getTextContent());
                    extractedData.setHeader(header);
                }

                NodeList footerNodes = xmlDocument.getElementsByTagName(HRISXMLElements.FOOTER.getElement());
                if (footerNodes.getLength() > 0) {
                    Element footerElement = (Element) footerNodes.item(0);
                    DocumentRequest.Footer footer = new DocumentRequest.Footer();
                    footer.setCtrlSum(
                            BigDecimal.valueOf(Double.parseDouble(footerElement.getElementsByTagName(HRISXMLElements.CTRL_SUM.getElement()).item(0).getTextContent()
                            )));
                    extractedData.setFooter(footer);
                }

                NodeList nList = xmlDocument.getElementsByTagName(HRISXMLElements.EMPLOYEE_INFO.getElement());
                for (int i = 0; i < nList.getLength(); i++) {
                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        DocumentRequest.EmployeeInfo employeeInfo = new DocumentRequest.EmployeeInfo();
                        DocumentRequest.EmployeeInfo.BankInfo bankInfo = new DocumentRequest.EmployeeInfo.BankInfo();

                        // Access EmployeeInfo fields
                        employeeInfo.setEpfNo(element.getElementsByTagName(HRISXMLElements.EPF_NO.getElement()).item(0).getTextContent());
                        employeeInfo.setFirstName(element.getElementsByTagName(HRISXMLElements.FIRST_NAME.getElement()).item(0).getTextContent());
                        employeeInfo.setMiddleName(element.getElementsByTagName(HRISXMLElements.MIDDLE_NAME.getElement()).item(0).getTextContent());
                        employeeInfo.setLastName(element.getElementsByTagName(HRISXMLElements.LAST_NAME.getElement()).item(0).getTextContent());
                        employeeInfo.setNic(element.getElementsByTagName(HRISXMLElements.NIC.getElement()).item(0).getTextContent());

                        // Access nested BankInfo
                        Element bankInfoElement = (Element) element.getElementsByTagName(HRISXMLElements.BANK_INFO.getElement()).item(0);
                        bankInfo.setBankName(bankInfoElement.getElementsByTagName(HRISXMLElements.BANK_NAME.getElement()).item(0).getTextContent());
                        bankInfo.setBankAccount(bankInfoElement.getElementsByTagName(HRISXMLElements.BANK_ACCOUNT_NO.getElement()).item(0).getTextContent());
                        bankInfo.setBankCode(bankInfoElement.getElementsByTagName(HRISXMLElements.BANK_CODE.getElement()).item(0).getTextContent());
                        bankInfo.setBranchCode(bankInfoElement.getElementsByTagName(HRISXMLElements.BANK_BRANCH_CODE.getElement()).item(0).getTextContent());
                        bankInfo.setSalary(Double.parseDouble(bankInfoElement.getElementsByTagName(HRISXMLElements.SALARY.getElement()).item(0).getTextContent()));

                        employeeInfo.setBankInfo(bankInfo);
                        employeeInfos.add(employeeInfo);
                    }
                }
                extractedData.setEmployeeInfos(employeeInfos);
                documents.put(fileName, extractedData);

            } catch (SAXException | IOException | ParserConfigurationException e) {
                throw new RuntimeException(e);

            } catch (Exception e) {
                System.err.println("JAXB setup failed.");
                e.printStackTrace();
            }
        }
        log.info("ExtractXMLServiceImpl.extractXmlFiles() => ended.");
        return documents;
    }
}
