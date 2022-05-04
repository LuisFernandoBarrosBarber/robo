package com.robo.robo.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "minerados")
public class MineradosStorageProperties {

    private String file;

    public String getFile() {
        return this.file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
