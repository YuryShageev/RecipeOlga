package recipeolga.Services;

import org.springframework.stereotype.Service;
import recipeolga.Model.Recipe;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final Map<Integer, Recipe> recipeMap = new HashMap<>();
    private static Integer id = 0;

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
            throw new IllegalArgumentException("Рецепт с данным id не найден");
        }
        return recipeMap.remove(id);
    }

    @Override
    public Recipe updateRecipe(int id, Recipe recipe) {
        if (!recipeMap.containsKey(id)) {
            throw new IllegalArgumentException("Рецепт с данным id не найден");
        }
        return recipeMap.put(id, recipe);
    }
}
