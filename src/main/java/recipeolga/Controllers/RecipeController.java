package recipeolga.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import recipeolga.Model.Recipe;
import recipeolga.Services.RecipeService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    @PutMapping("/{id}")
    ResponseEntity<Recipe> updateRecipe(@PathVariable Integer id, @Valid @RequestBody Recipe recipe) {
        return ResponseEntity.ok(recipeService.updateRecipe(id, recipe));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Recipe> removeRecipe(@PathVariable Integer id) {
        return ResponseEntity.ok(recipeService.removeRecipe(id));
    }

    @GetMapping
    ResponseEntity<Collection<Recipe>> getRecipesByIngredientId() {
        return ResponseEntity.ok(recipeService.getAll());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
