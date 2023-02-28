package recipeolga.Services;

import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import recipeolga.Model.Ingredient;
import recipeolga.Model.Recipe;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final Map<Integer, Recipe> recipeMap = new HashMap<>();
    private static Integer id = 0;
    private IngredientService ingredientService;

    @Override
    public Recipe addRecipe(Recipe recipe) {
        recipeMap.put(id++, recipe);
        return recipe;
    }

    @Override
    public Recipe getRecipe(Integer id) {
        return recipeMap.getOrDefault(id, null);
    }

    @Override
    public Collection<Recipe> getAll() {
        return recipeMap.values();
    }

    @Override
    public Recipe removeRecipe(int id) {
        if (!recipeMap.containsKey(id)) {
            throw new NotFoundException("Рецепт с данным id не найден");
        }
        return recipeMap.remove(id);
    }

    @Override
    public Recipe updateRecipe(int id, Recipe recipe) {
        if (!recipeMap.containsKey(id)) {
            throw new NotFoundException("Рецепт с данным id не найден");
        }
        return recipeMap.put(id, recipe);
    }

    @Override
    public Collection<Recipe> getByIngredientId(Integer ingredientId) {
        Ingredient ingredient = ingredientService.getIngredient(id);
        return recipeMap.values().stream().filter(recipe -> recipe.getIngredients().contains(ingredient)).collect(Collectors.toList());
    }

    @Override
    public Collection getAll(int page, int size) {
        return recipeMap.values().stream().skip((long) size * (page - 1)).limit(size).collect(Collectors.toList());
    }
}
