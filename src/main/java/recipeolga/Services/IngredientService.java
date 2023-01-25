package recipeolga.Services;

import recipeolga.Model.Ingredient;

public interface IngredientService {
    Ingredient addIngredient(Ingredient ingredient);

    Ingredient getIngredient(Integer id);
}
