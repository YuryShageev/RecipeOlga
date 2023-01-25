package recipeolga.Services;

import org.springframework.stereotype.Service;
import recipeolga.Model.Ingredient;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final Map<Integer, Ingredient> ingredientMap = new HashMap<>();
    private static Integer id = 0;

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        ingredientMap.put(id++, ingredient);
        return ingredient;
    }

    @Override
    public Ingredient getIngredient(Integer id) {
        return ingredientMap.get(id);
    }
}
