package com.restaurant.recipemanagementservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.restaurant.recipemanagementservice.model.Recipe;
import com.restaurant.recipemanagementservice.repository.RecipeRepository;
import com.restaurant.recipemanagementservice.repository.criteria.SearchCriteria;
import com.restaurant.recipemanagementservice.repository.specification.RecipeSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class RecipeServiceImplTest {

    @Autowired
    private RecipeService recipeService;

    @MockBean(name = "recipeRepository")
    private RecipeRepository recipeRepository;

    private List<Recipe> recipes;
    private Recipe recipe;

    @BeforeEach
    void setUp() {

        recipe = new Recipe();
        recipe.setId(1);
        recipe.setName("Smoked Salmon");
        recipe.setServes(2);
        recipe.setVegetarian(false);
        recipe.setLastUpdated(new Date());
        recipe.setInstructions("Preheat oven, add Salmon and olive oil, fennel, onion and red chilly.");
        recipe.setIngredients("Salmon, oil, mayonnaise");

        recipes = new ArrayList<>();
        recipes.add(recipe);

    }

    @Test
    void whenGetAll_thenReturnAllRecipesList() {

        Mockito.when(recipeRepository.findAll()).thenReturn(recipes);

        List<Recipe> recipes = recipeService.getAllRecipes();

        assertThat(recipes.size()).isPositive();
    }

    @Test
    void whenFindById_thenReturnRecipeMatchingGivenId() {

        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        Optional<Recipe> recipe = Optional.ofNullable(recipeService.findRecipeById(1L));

        assertThat(recipe).isPresent();
    }

    @Test
    void whenAddRecipe_thenReturnRecipesList() {

        Mockito.when(recipeRepository.save(recipe)).thenReturn(recipe);

        Recipe recipes = recipeService.addRecipe(recipe);

        assertThat(recipes.getId()).isEqualTo(1L);
    }


    @Test
    void whenFindAll_thenReturnRecipesList() {

        RecipeSpecification recipeSpecification = new RecipeSpecification(new SearchCriteria("isVegetarian", ":", true));

        Mockito.when(recipeRepository.findAll(recipeSpecification)).thenReturn(recipes);

        List<Recipe> recipes = recipeService.searchRecipeByCriteria("isVegetarian:true");

        assertThat(recipes.size()).isZero();
    }
}