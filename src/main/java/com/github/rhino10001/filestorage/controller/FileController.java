package com.github.rhino10001.filestorage.controller;

import com.github.rhino10001.filestorage.dto.response.FileDataResponse;
import com.github.rhino10001.filestorage.exception.SaveFileException;
import com.github.rhino10001.filestorage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public List<FileDataResponse> list() {
        return fileService.listFiles();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FileDataResponse save(@RequestParam("file") MultipartFile file) {
        String exMessage = null;
        try {
            return fileService.saveFile(file);
        } catch (Exception ex) {
            exMessage = "File name should be unique";
            throw new SaveFileException(exMessage, ex);
        }
    }

    @GetMapping("/{id}/download")
    @ResponseStatus(HttpStatus.OK)
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
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable long id) {
        fileService.deleteFile(id);
    }
}
