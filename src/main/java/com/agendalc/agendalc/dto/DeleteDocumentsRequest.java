package com.agendalc.agendalc.dto;

import java.util.List;

public class DeleteDocumentsRequest {

    private List<Long> documentoIds;

    public List<Long> getDocumentoIds() {
        return documentoIds;
    }

    public void setDocumentoIds(List<Long> documentoIds) {
        this.documentoIds = documentoIds;
    }
}
