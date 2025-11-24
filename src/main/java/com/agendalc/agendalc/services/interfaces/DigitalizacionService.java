
package com.agendalc.agendalc.services.interfaces;

import com.agendalc.agendalc.dto.DigitalizacionRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DigitalizacionService {
    void guardarDocumentosDigitalizados(DigitalizacionRequest request, List<MultipartFile> files);
}
