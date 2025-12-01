package com.agendalc.agendalc.config;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String uploadDir;

    private String plataformaUrl;

    public String getPlataformaUrl() {
        return plataformaUrl;
    }

    public void setPlataformaUrl(String plataformaUrl) {
        this.plataformaUrl = plataformaUrl;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

}