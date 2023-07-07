package com.github.rhino10001.filestorage.repository;

import org.springframework.web.multipart.MultipartFile;

public interface FileRepository {

    void write(MultipartFile file, String dir, String fileName);

    String getDestDir(String fileName);

    void delete(String dir, String fileName);
}
