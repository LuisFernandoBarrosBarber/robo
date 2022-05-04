package com.robo.robo.service;

import com.robo.robo.configuration.MineradosStorageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FileService {

    private final MineradosStorageProperties storageProperties;

    public InputStream getFileAsIOStream() throws FileNotFoundException {

        return new FileInputStream(storageProperties.getFile());
    }
}
