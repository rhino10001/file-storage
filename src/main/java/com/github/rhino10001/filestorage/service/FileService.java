package com.github.rhino10001.filestorage.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import com.github.rhino10001.filestorage.dto.FileDataDTO;

import java.util.List;

public interface FileService {

    List<FileDataDTO> listFiles();

    FileDataDTO saveFile(MultipartFile file);

    Resource getFileAsResource(long id);

    void deleteFile(long id);
}