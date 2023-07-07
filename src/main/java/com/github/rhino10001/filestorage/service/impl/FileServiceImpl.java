package com.github.rhino10001.filestorage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.github.rhino10001.filestorage.dto.FileDataDTO;
import com.github.rhino10001.filestorage.model.FileDataEntity;
import com.github.rhino10001.filestorage.repository.FileDataRepository;
import com.github.rhino10001.filestorage.repository.FileRepository;
import com.github.rhino10001.filestorage.service.FileService;

import java.io.File;
import java.util.List;
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
    public List<FileDataDTO> listFiles() {
        return fileDataRepository.findAll().stream()
                .map(fde -> new FileDataDTO(fde.getId(), fde.getFileName()))
                .collect(Collectors.toList());
    }

    @Override
    public FileDataDTO saveFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String destDir = fileRepository.getDestDir(fileName);
        FileDataEntity fileData = fileDataRepository.save(new FileDataEntity(destDir, fileName));
        fileRepository.write(file, fileData.getDir(), fileData.getFileName());
        return new FileDataDTO(fileData.getId(), fileData.getFileName());
    }

    @Override
    public Resource getFileAsResource(long id) {
        FileDataEntity fileData = fileDataRepository.findById(id).get();
        File source = new File(fileData.getDir(), fileData.getFileName());
        return new FileSystemResource(source);
    }

    @Override
    public void deleteFile(long id) {
        FileDataEntity fileData = fileDataRepository.findById(id).get();
        fileRepository.delete(fileData.getDir(), fileData.getFileName());
        fileDataRepository.deleteById(id);
    }
}
