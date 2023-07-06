package rhino1001.filestorage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "files_data")
public class FileDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String dirPath;

    @Column(nullable = false, unique = true)
    private String fileName;

    private FileDataEntity() {}

    public FileDataEntity(String dirPath, String fileName) {
        this.dirPath = dirPath;
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

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }
}
