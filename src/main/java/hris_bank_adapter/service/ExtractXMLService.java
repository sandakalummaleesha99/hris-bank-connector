package hris_bank_adapter.service;

import hris_bank_adapter.dto.DocumentDTO;

import java.io.File;
import java.util.List;

public interface ExtractXMLService {

    List<DocumentDTO> extractXmlFiles(List<File> newFiles);
}
