package rhino1001.filestorage.repository;

import org.springframework.web.multipart.MultipartFile;

public interface FileRepository {

    boolean write(MultipartFile file, String dir, String fileName);

    String getDestDir(String fileName);

    boolean delete(String dir, String fileName);
}
