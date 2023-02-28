package recipeolga.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import recipeolga.Model.Ingredient;
import recipeolga.exception.FileProcessingException;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final FileService fileService;

    private Map<Integer, Ingredient> ingredientMap = new HashMap<>();
    private static Integer id = 0;

    public IngredientServiceImpl(@Qualifier("ingredientFileService") FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (ingredientMap.containsValue(ingredient)) {
            throw new NotFoundException("Ингредиент не найден");
        }
        ingredientMap.put(id++, ingredient);
        saveToFileIngredient();
        return ingredient;
    }

    @Override
    public Ingredient getIngredient(Integer id) {
        if (!ingredientMap.containsKey(id)) {
            throw new NotFoundException("Ингредиент с заданным id не найден");
        }
        return ingredientMap.get(id);
    }

    @Override
    public Collection<Ingredient> getAll() {
        return ingredientMap.values();
    }

    @Override
    public Ingredient removeIngredient(int id) {
        if (!ingredientMap.containsKey(id)) {
            throw new NotFoundException("Ингредиент с заданным id не найден");
        }
        Ingredient removedIngredient = ingredientMap.remove(id);
        saveToFileIngredient();
        return removedIngredient;
    }

    @Override
    public Ingredient updateIngredient(int id, Ingredient ingredient) {
        if (!ingredientMap.containsKey(id)) {
            throw new NotFoundException("Ингредиент с заданным id не найден");
        }
        ingredientMap.put(id, ingredient);
        saveToFileIngredient();
        return ingredient;
    }
@PostConstruct
    private void initIngredient() throws FileProcessingException {
        readFromFileIngredient();
    }

    private void readFromFileIngredient() throws FileProcessingException {
        try {
            String json = fileService.readFromFile();
            ingredientMap = new ObjectMapper().readValue(json, new TypeReference<Map<Integer, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Файл не удалось прочитать");
        }
    }

    private void saveToFileIngredient() throws FileProcessingException {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Файл не удалось сохранить");
        }
    }
}
