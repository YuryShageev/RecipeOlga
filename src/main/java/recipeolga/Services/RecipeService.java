package recipeolga.Services;

import recipeolga.Model.Recipe;

public interface RecipeService {

    Recipe addRecipe(Recipe recipe);

    Recipe getRecipe(Integer id);
}
