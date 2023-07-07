package com.github.rhino10001.filestorage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "files_data")
public class FileDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String dir;

    @Column(nullable = false, unique = true)
    private String fileName;

    private FileDataEntity() {}

    public FileDataEntity(String dir, String fileName) {
        this.dir = dir;
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
