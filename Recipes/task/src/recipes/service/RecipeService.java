package recipes.service;

import recipes.entities.Recipe;
import recipes.entities.User;
import recipes.model.RecipeDTO;

import java.util.List;

public interface RecipeService {

    Recipe findRecipeById(Long id);

    Recipe saveRecipe(Recipe recipe);

    void deleteRecipeById(Long id);

    List<Recipe> search(String name, String category);

    void checkRecipeOwner(String recipeUsername, String usernameToCheck);

}
