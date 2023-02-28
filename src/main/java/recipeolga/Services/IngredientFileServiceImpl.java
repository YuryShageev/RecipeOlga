package recipeolga.Services;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import recipeolga.exception.FileProcessingException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service("ingredientFileService")
public class IngredientFileServiceImpl implements FileService {

    @Value("src/main/resources")
    private String dataFilePathIngredient;
    @Value("ingredient.json")
    private String dataFileNameIngredient;



    @Override

    public boolean saveToFile(String json) {
        try {
            cleanDataFile();
            Files.writeString(Path.of(dataFilePathIngredient, dataFileNameIngredient), json);
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    @Override
    public String readFromFile() {
        if (Files.exists(Path.of(dataFilePathIngredient, dataFileNameIngredient))) {
            try {
                return Files.readString(Path.of(dataFilePathIngredient, dataFileNameIngredient));
            } catch (IOException e) {
                throw new FileProcessingException("Не удалось прочитать файл");
            }
        } else {
            return "{ }";
        }
    }

    @Override
    public boolean cleanDataFile() {
        try {
            Path path = Path.of(dataFilePathIngredient, dataFileNameIngredient);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public File getDataFileTxt() {
        return new File(dataFilePathIngredient + "/" + dataFileNameIngredient);
    }
}
