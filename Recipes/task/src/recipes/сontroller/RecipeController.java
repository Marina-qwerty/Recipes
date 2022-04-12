package recipes.сontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.entities.Recipe;
import recipes.entities.User;
import recipes.model.Converter;
import recipes.model.RecipeDTO;
import recipes.service.RecipeService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/recipe")
public class RecipeController {

    @Resource
    RecipeService recipeService;

    @PostMapping("/new")
    public ResponseEntity<Map<String, Long>> postRecipe(@Valid @RequestBody RecipeDTO recipeDto,
                                                        @AuthenticationPrincipal User user) {
        Recipe recipe = Converter.mapToRecipeEntity(recipeDto);
        recipe.setUser(user);
        return new ResponseEntity<Map<String, Long>>(Map.of("id", recipeService.saveRecipe(recipe).getId()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable long id) {
        return new ResponseEntity<>(recipeService.findRecipeById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> search(@RequestParam(required = false) String name,
                                               @RequestParam(required = false) String category) {
        if((name == null && category == null) || (name != null && category != null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "wrong parameters were passed");
        }
        return new ResponseEntity<List<Recipe>>(recipeService.search(name, category), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable long id,
                                          @Valid @RequestBody RecipeDTO recipeDto,
                                          @AuthenticationPrincipal User user) {
        Recipe recipe = recipeService.findRecipeById(id);
        recipeService.checkRecipeOwner(recipe.getUser().getUsername(), user.getUsername());
        Recipe updatedRecipe = Converter.mapToRecipeEntity(recipeDto);
        updatedRecipe.setId(id);
        updatedRecipe.setUser(user);
        return new ResponseEntity<>(recipeService.saveRecipe(updatedRecipe), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable long id, @AuthenticationPrincipal User user) {
        Recipe recipe = recipeService.findRecipeById(id);
        recipeService.checkRecipeOwner(recipe.getUser().getUsername(), user.getUsername());
        recipeService.deleteRecipeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}