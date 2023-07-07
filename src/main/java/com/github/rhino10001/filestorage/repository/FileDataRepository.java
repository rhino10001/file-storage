package com.github.rhino10001.filestorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.github.rhino10001.filestorage.model.FileDataEntity;

@Repository
public interface FileDataRepository extends JpaRepository<FileDataEntity, Long> {
}
