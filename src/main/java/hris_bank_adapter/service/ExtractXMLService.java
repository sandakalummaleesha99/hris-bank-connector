package hris_bank_adapter.service;

import hris_bank_adapter.wsdl.DocumentRequest;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author @maleeshasa
 * @Date 2025-04-15
 */
public interface ExtractXMLService {

    /**
     * This method is allowed to extract data from xml files
     *
     * @param newFiles {@link List<File>} - List of new files
     * @return {@link Map<String, DocumentRequest>} - data list of extracted files
     * @author @maleeshasa
     */
    Map<String, DocumentRequest> extractXmlFiles(List<File> newFiles);
}
