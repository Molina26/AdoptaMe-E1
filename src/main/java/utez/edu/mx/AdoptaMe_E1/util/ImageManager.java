package utez.edu.mx.AdoptaMe_E1.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class ImageManager {

    private final AppProperties appProperties;

    private final Logger logger = LoggerFactory.getLogger(ImageManager.class);

    public ImageManager(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String insertImage(MultipartFile imageFile){

        String pathToSave = appProperties.getImageSavePath();

        if (pathToSave != null) {

            String uniqueNameFile = UUID.randomUUID()+"_"+imageFile.getOriginalFilename();

            Path rootPath = Paths.get(pathToSave).resolve(uniqueNameFile);

            Path rootAbsolutePath = rootPath.toAbsolutePath();

            try {
                Files.copy(imageFile.getInputStream(), rootAbsolutePath);

                return uniqueNameFile;

            } catch (IOException e) {
                logger.error("error to write the file");
            }

        }

        return null;
    }

}
