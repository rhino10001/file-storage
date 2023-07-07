package com.github.rhino10001.filestorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.github.rhino10001.filestorage.dto.FileDataDTO;
import com.github.rhino10001.filestorage.service.FileService;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public List<FileDataDTO> list() {
        return fileService.listFiles();
    }

    @PostMapping
    public FileDataDTO save(@RequestParam("file") MultipartFile file) {
        return fileService.saveFile(file);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable long id) {
        Resource resource = fileService.getFileAsResource(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=\"%s\"", resource.getFilename()));
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .headers(responseHeaders)
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        fileService.deleteFile(id);
    }
}
