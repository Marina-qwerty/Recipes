package recipes.model;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import recipes.entities.Recipe;
import recipes.entities.User;

import java.time.LocalDateTime;

public class Converter {

    public static Recipe mapToRecipeEntity(RecipeDTO recipeDTO) {
        Recipe recipeEntity = new Recipe();
        recipeEntity.setName(recipeDTO.getName());
        recipeEntity.setCategory(recipeDTO.getCategory());
        recipeEntity.setDate(LocalDateTime.now());
        recipeEntity.setDescription(recipeDTO.getDescription());
        recipeEntity.setIngredients(recipeDTO.getIngredients());
        recipeEntity.setDirections(recipeDTO.getDirections());
        return recipeEntity;
    }
}

