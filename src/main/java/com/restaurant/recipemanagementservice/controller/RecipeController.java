package com.restaurant.recipemanagementservice.controller;

import com.restaurant.recipemanagementservice.model.Recipe;
import com.restaurant.recipemanagementservice.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recipe")
public class RecipeController {

    Logger logger = LoggerFactory.getLogger(RecipeController.class);

    @Autowired
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService){
        this.recipeService = recipeService;
    }


    /**
     * This method fetches all the recipes.
     * @return response entity
     */
    @GetMapping("/")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        logger.info("Requesting to get all the recipes...");
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }


    /**
     * This method fetches the recipe with given id.
     * @return response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> findRecipeById(@PathVariable long id) {
        logger.info("Requesting to fetch recipe with Id : {}", id);
        return ResponseEntity.ok(recipeService.findRecipeById(id));
    }


    /**
     * This method deletes recipe with given id.
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteRecipeById(@Valid @PathVariable long id) {
        logger.info("Requesting to delete recipe with Id : {}", id);
        recipeService.deleteRecipeById(id);
    }


    /**
     * This method adds recipe with given request body payload.
     *
     * @param recipe object
     * @return the response entity
     */
    @PostMapping("/")
    public ResponseEntity<Recipe> addRecipe(@Valid @RequestBody Recipe recipe) {
        logger.info("Requesting to add recipe...");
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.addRecipe(recipe));
    }


    /**
     * This method updates recipe based on given id.
     *
     * @param recipe object
     * @param id  of recipe to be updated
     * @return the response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@Valid @RequestBody Recipe recipe, @PathVariable long id) {
        logger.info("Requesting to update recipe with Id : {}", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.updateRecipe(recipe, id));
    }


    /**
     * This method searches for recipe from the database based on given search criteria.
     * For instance, we search recipe which is vegetarian as isVegetarian:true
     *
     * @param searchCriteria string as the search criteria
     * @return list of recipes matching the search criteria
     */
    @GetMapping("/searchByCriteria/{searchCriteria}")
    public List<Recipe> searchRecipeByCriteria(@PathVariable String searchCriteria) {
        logger.info("Requesting to update recipe with search criteria  : {}", searchCriteria);
        return recipeService.searchRecipeByCriteria(searchCriteria);
    }


}
