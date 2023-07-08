package com.github.rhino10001.filestorage.repository;

import com.github.rhino10001.filestorage.model.FileDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDataRepository extends JpaRepository<FileDataEntity, Long> {
}
