package com.github.rhino10001.filestorage.service;

import com.github.rhino10001.filestorage.dto.response.FileDataResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    List<FileDataResponse> listFiles();

    FileDataResponse saveFile(MultipartFile file);

    Resource getFileAsResource(long id);

    void deleteFile(long id);
}
