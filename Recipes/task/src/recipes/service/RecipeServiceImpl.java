package recipes.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.entities.Recipe;

import recipes.repository.RecipeRepository;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService{

    @Resource
    private RecipeRepository recipeRepository;

    @Override
    public Recipe findRecipeById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isPresent()) {
            return recipe.get();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
    }

    @Override
    public Recipe saveRecipe(Recipe recipe) { return recipeRepository.save(recipe); }

    @Override
    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public List<Recipe> search(String name, String category) {
        return (name == null ? searchByCategory(category) : searchByName(name));
    }

    @Override
    public void checkRecipeOwner(String recipeUsername, String usernameToCheck) {
        if (!recipeUsername.equals(usernameToCheck)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can create/update your recipes only!");
        }
    }

    public List<Recipe> searchByName(String name) {
        return new ArrayList<>(recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name));
    }

    public List<Recipe> searchByCategory(String category) {
        return new ArrayList<>(recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category));
    }

}

