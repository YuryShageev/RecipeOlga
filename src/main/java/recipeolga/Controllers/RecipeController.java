package recipeolga.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.webjars.NotFoundException;
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
    @Operation(summary = "Удаление рецептов по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Рецепт удалён")
    })
    @Parameters(value = {@Parameter(name = "id", example = "1")})
    ResponseEntity<Recipe> removeRecipe(@PathVariable Integer id) {
        return ResponseEntity.ok(recipeService.removeRecipe(id));
    }

    @GetMapping
    @Operation(summary = "Получение рецептов по ID ингредиента")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Рецепты получены")})
    @Parameters(value = {@Parameter(name = "id", example = "1")})
    ResponseEntity<Collection<Recipe>> getRecipesByIngredientId(@RequestParam Integer ingredient) {
        return ResponseEntity.ok(recipeService.getByIngredientId(ingredient));
    }

    @GetMapping("/list")
    ResponseEntity<Collection<Recipe>> getRecipeByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(recipeService.getAll(page, size));
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException notFoundException) {
        return notFoundException.getMessage();
    }
}
