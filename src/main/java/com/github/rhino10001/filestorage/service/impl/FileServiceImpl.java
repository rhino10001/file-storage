package com.github.rhino10001.filestorage.service.impl;

import com.github.rhino10001.filestorage.dto.response.FileDataResponse;
import com.github.rhino10001.filestorage.model.FileDataEntity;
import com.github.rhino10001.filestorage.repository.FileDataRepository;
import com.github.rhino10001.filestorage.repository.FileRepository;
import com.github.rhino10001.filestorage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    private final FileDataRepository fileDataRepository;
    private final FileRepository fileRepository;

    @Autowired
    public FileServiceImpl(FileDataRepository fileDataRepository, FileRepository fileRepository) {
        this.fileDataRepository = fileDataRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public List<FileDataResponse> listFiles() {
        return fileDataRepository.findAll().stream()
                .map(fde -> new FileDataResponse(fde.getId(), fde.getFileName()))
                .collect(Collectors.toList());
    }

    @Override
    public FileDataResponse saveFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String destDir = fileRepository.getDestDir();
        FileDataEntity fileData = fileDataRepository.save(new FileDataEntity(destDir, fileName));
        try {
            fileRepository.write(file, fileData.getDir(), fileData.getFileSystemName());
        } catch (Exception e) {
            fileDataRepository.delete(fileData);
            throw e;
        }
        return new FileDataResponse(fileData.getId(), fileData.getFileName());
    }

    @Override
    public Resource getFileAsResource(long id) {
        FileDataEntity fileData = fileDataRepository.findById(id).orElseThrow(NoSuchElementException::new);
        File source = new File(fileData.getDir(), fileData.getFileSystemName());
        return new FileSystemResource(source);
    }

    @Override
    public void deleteFile(long id) {
        FileDataEntity fileData = fileDataRepository.findById(id).orElseThrow(NoSuchElementException::new);
        fileRepository.delete(fileData.getDir(), fileData.getFileSystemName());
        fileDataRepository.deleteById(id);
    }
}
