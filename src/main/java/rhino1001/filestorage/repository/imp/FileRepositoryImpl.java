package rhino1001.filestorage.repository.imp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import rhino1001.filestorage.repository.FileRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
public class FileRepositoryImpl implements FileRepository {

    @Value("${storage.rootLocation}")
    private String rootLocation;

    @Value("${storage.bufferSize}")
    private int buffSize;

    @Override
    public boolean write(MultipartFile file, String dir, String fileName) {
        try {
            Files.createDirectories(Path.of(dir));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File dest = new File(dir, fileName);
        try (InputStream is = file.getInputStream();
             OutputStream os = new FileOutputStream(dest)) {
            byte[] buff = new byte[buffSize];
            while (is.available() > 0) {
                int i = is.read(buff);
                os.write(buff, 0, i);
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getDestDir(String fileName) {
        int hash = Math.abs(fileName.hashCode() % 10000);
        return rootLocation + File.separator + hash / 100 + File.separator + hash % 100;
    }

    @Override
    public boolean delete(String dir, String fileName) {
        try {
            return Files.deleteIfExists(Path.of(dir + File.separator + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
