package recipeolga.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipeolga.Model.Recipe;
import recipeolga.Services.RecipeService;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;


    @GetMapping("/{id}")
    Recipe getRecipe(@Validated @PathVariable Integer id) {
        return recipeService.getRecipe(id);
    }

    @PostMapping
    Recipe addRecipe(@RequestBody Recipe recipe) {
        return recipeService.addRecipe(recipe);
    }
}
