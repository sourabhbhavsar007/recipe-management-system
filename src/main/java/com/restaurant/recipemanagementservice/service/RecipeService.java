package com.restaurant.recipemanagementservice.service;

import com.restaurant.recipemanagementservice.model.Recipe;

import java.util.List;

public interface RecipeService {
    
    List<Recipe> getAllRecipes();

    Recipe findRecipeById(long id);
    
    void deleteRecipeById(long id);

    Recipe addRecipe(Recipe recipe);

    Recipe updateRecipe(Recipe recipe, long id);

    List<Recipe> searchRecipeByCriteria(String searchCriteria);
}
