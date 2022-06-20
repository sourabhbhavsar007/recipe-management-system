package com.restaurant.recipemanagementservice.service;

import com.restaurant.recipemanagementservice.constants.ErrorConstants;
import com.restaurant.recipemanagementservice.exception.RecipeNotFoundException;
import com.restaurant.recipemanagementservice.model.Recipe;
import com.restaurant.recipemanagementservice.repository.RecipeRepository;
import com.restaurant.recipemanagementservice.repository.specification.RecipeSpecificationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RecipeServiceImpl implements RecipeService {

    Logger logger = LoggerFactory.getLogger(RecipeServiceImpl.class);

    @Autowired
    private RecipeRepository recipeRepository;


    /**
     * Fetches all the recipes
     * @return List of recipes
     */
    @Override
    public List<Recipe> getAllRecipes() {

        logger.info("Fetching all recipes...");
        return recipeRepository.findAll();
    }


    /**
     * Fetches recipe with given id
     * @param id
     * @return Recipe response
     */
    @Override
    public Recipe findRecipeById(long id) {

        logger.info("Fetching recipe with Id : {}", id);

        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(recipe.isEmpty())
            throw new RecipeNotFoundException(ErrorConstants.RECIPE_NOT_FOUND);

        return recipe.get();
    }


    /**
     * Deletes recipe with given id
     * @param id
     */
    @Override
    public void deleteRecipeById(long id) {

        logger.info("Deleting recipe with Id : {}", id);

        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(recipe.isEmpty())
            throw new RecipeNotFoundException(ErrorConstants.RECIPE_NOT_FOUND);

        recipeRepository.deleteById(id);
    }

    /**
     * Adds recipe passed in Recipe object
     * @param recipe object
     * @return Recipe response
     */
    @Override
    public Recipe addRecipe(Recipe recipe) {

        logger.info("Adding new recipe...");

        Optional<Recipe> searchedRecipe = recipeRepository.findById(recipe.getId());
        if (searchedRecipe.isPresent())
            throw new RecipeNotFoundException(ErrorConstants.INTEGRITY_VIOLATION);

        return recipeRepository.save(recipe);
    }

    /**
     * Updates recipe with given id, and recipe object
     * @param id, recipe object to be updated
     * @return Recipe response
     */
    @Override
    public Recipe updateRecipe(Recipe recipe, long id) {

        logger.info("Updating recipe with Id : {}", id);

        Optional<Recipe> searchedRecipe = recipeRepository.findById(id);
        if (searchedRecipe.isEmpty())
            throw new RecipeNotFoundException(ErrorConstants.RECIPE_NOT_FOUND);

        Recipe recipeToBeUpdated = searchedRecipe.get();
        recipeToBeUpdated.setInstructions(recipe.getInstructions());
        recipeToBeUpdated.setName(recipe.getName());
        recipeToBeUpdated.setServes(recipe.getServes());
        recipeToBeUpdated.setVegetarian(recipe.isVegetarian());
        recipeToBeUpdated.setLastUpdated(recipe.getLastUpdated());
        recipeToBeUpdated.setIngredients(recipe.getIngredients());

        return recipeRepository.save(recipeToBeUpdated);
    }

    /**
     * This method is used for advanced search, based on criteria.
     * For instance, search vegetarian recipe, which serves 4 persons, etc.
     *
     * We can use Querydsl(open source project) or @Query to form native SQL query.
     *
     * We are currently using Specification which we build and JpaSpecificationExecutor.
     *
     * @param searchCriteria
     * @return List of matching recipes based on search criteria.
     */
    @Override
    public List<Recipe> searchRecipeByCriteria(String searchCriteria) {
        //regex
        Pattern pattern = Pattern.compile("([A-Za-z0-9'_ ]+?)(:|<|>)([A-Za-z0-9_ ]+?),");
        Matcher matcher = pattern.matcher(searchCriteria + ",");

        RecipeSpecificationBuilder recipeSpecificationBuilder = new RecipeSpecificationBuilder();
        while (matcher.find()) {
            recipeSpecificationBuilder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Recipe> recipeSpecification = recipeSpecificationBuilder.build();

        return recipeRepository.findAll(recipeSpecification);
    }

}
