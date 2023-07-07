package com.github.rhino10001.filestorage.repository.impl;

import com.github.rhino10001.filestorage.repository.FileRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
public class FileRepositoryImpl implements FileRepository {

    @Value("${storage.rootLocation}")
    private String rootLocation;

    @Value("${storage.bufferSize}")
    private int buffSize;

    @PostConstruct
    public void createRootDir() {
        try {
            Files.createDirectories(Path.of(rootLocation));
        } catch (IOException e) {
            throw new RuntimeException("Error creating root directory:" + rootLocation, e);
        }
    }

    @Override
    public void write(MultipartFile file, String dir, String fileName) {
        File dest = new File(dir, fileName);

        if (dest.exists()) throw new IllegalArgumentException("File already exists.");

        try {
            Files.createDirectories(Path.of(dir));
        } catch (IOException e) {
            throw new RuntimeException("Error creating subdirectories %s"
                    .formatted(Path.of(dir, fileName).relativize(dest.toPath())), e);
        }

        try (InputStream is = file.getInputStream();
             OutputStream os = new FileOutputStream(dest)) {
            byte[] buff = new byte[buffSize];
            while (is.available() > 0) {
                int i = is.read(buff);
                os.write(buff, 0, i);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error during writing file " + dest.toPath(), e);
        }
    }

    @Override
    public void delete(String dir, String fileName) {
        File file = new File(dir, fileName);
        try {
            Files.deleteIfExists(file.toPath());
            if (file.exists()) throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("Error during removing file " + file.toPath(), e);
        }
    }

    @Override
    public String getDestDir(String fileName) {
        int hash = Math.abs(fileName.hashCode() % 10000);
        return rootLocation + File.separator + hash / 100 + File.separator + hash % 100;
    }
}
