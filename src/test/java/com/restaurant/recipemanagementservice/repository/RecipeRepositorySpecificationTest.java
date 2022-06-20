package com.restaurant.recipemanagementservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.restaurant.recipemanagementservice.model.Recipe;
import com.restaurant.recipemanagementservice.repository.criteria.SearchCriteria;
import com.restaurant.recipemanagementservice.repository.specification.RecipeSpecification;
import com.restaurant.recipemanagementservice.repository.specification.RecipeSpecificationBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class RecipeRepositorySpecificationsTest {

    @MockBean
    private RecipeRepository recipeRepository;

    private Recipe kibbeling;
    private Recipe salad;
    List<Recipe> recipes = new ArrayList<>();

    @BeforeEach
    void setUp() {
        kibbeling = new Recipe();
        kibbeling.setName("Kibbeling");
        kibbeling.setServes(3);
        kibbeling.setVegetarian(false);
        kibbeling.setLastUpdated(new Date());
        kibbeling.setInstructions("Deep fry cod fish, serve with mayonnaise");
        kibbeling.setIngredients("Cod fish, oil, pepper");


        salad = new Recipe();
        salad.setName("Salad");
        salad.setServes(1);
        salad.setVegetarian(true);
        salad.setLastUpdated(new Date());
        salad.setInstructions("Mix green leafy vegetables");
        salad.setIngredients("Spinach, brocolli");

        recipes.add(kibbeling);
        recipes.add(salad);
    }

    @Test
    void testGivenVegetarian_whenGettingListOfRecipe_thenGetListOfVegRecipes() {

        RecipeSpecification recipeSpecification = new RecipeSpecification(new SearchCriteria("isVegetarian", ":", true));

        Mockito.when(recipeRepository.findAll(recipeSpecification)).thenReturn(recipes);

        List<Recipe> results = recipeRepository.findAll(recipeSpecification);

        assertThat(results).extracting("isVegetarian").contains(true);
    }

    @Test
    void testGivenName_whenGettingListOfRecipe_thenGetListOfAllRecipeWithName() {

        RecipeSpecification recipeSpecification = new RecipeSpecification(new SearchCriteria("name", ":", "Salad"));

        Mockito.when(recipeRepository.findAll(recipeSpecification)).thenReturn(recipes);

        List<Recipe> results = recipeRepository.findAll(recipeSpecification);

        assertThat(results).extracting("name").contains("Salad");
    }

    @Test
    void testGivenServesIsGreater_whenGettingListOfRecipe_thenAllRecipeForMoreServes() {

        RecipeSpecification recipeSpecification = new RecipeSpecification(new SearchCriteria("serves", ">", 2));

        Mockito.when(recipeRepository.findAll(recipeSpecification)).thenReturn(recipes);

        List<Recipe> results = recipeRepository.findAll(recipeSpecification);

        assertThat(results).extracting("serves").contains(3);
    }

    @Test
    void testGivenServesIsLess_whenGettingListOfRecipe_thenAllRecipeForLessServes() {
        RecipeSpecification recipeSpecification = new RecipeSpecification(new SearchCriteria("serves", "<", 2));

        Mockito.when(recipeRepository.findAll(recipeSpecification)).thenReturn(recipes);

        List<Recipe> results = recipeRepository.findAll(recipeSpecification);

        assertThat(results).extracting("serves").contains(1);
    }

    @Test
    void testGivenWrongOperation_whenGettingListOfRecipe_thenShouldReturnAllRecipes() {
        RecipeSpecification recipeSpecification = new RecipeSpecification(new SearchCriteria("serves", "<=", 2));

        Mockito.when(recipeRepository.findAll(recipeSpecification)).thenReturn(recipes);

        List<Recipe> results = recipeRepository.findAll(recipeSpecification);

        assertThat(results).extracting("serves").contains(1);
    }


    @Test
    void testGivenNameOrServes_whenGettingListOfRecipes_thenShouldReturnCombination() {

        RecipeSpecificationBuilder builder = new RecipeSpecificationBuilder();

        SearchCriteria spec = new SearchCriteria("name", ":", "Salad");
        SearchCriteria spec1 = new SearchCriteria("'serves", "<", "2");
        Specification<Recipe> specs = builder.with(spec).with(spec1).build();

        Mockito.when(recipeRepository.findAll(specs)).thenReturn(recipes);

        List<Recipe> results = recipeRepository.findAll(specs);

        assertThat(results).extracting("name").contains("Salad");
        assertThat(results).extracting("serves").contains(1);
    }

    @Test
    void testGivenNameAndServes_whenGettingListOfRecipes_thenShouldReturnCombination() {

        RecipeSpecificationBuilder builder = new RecipeSpecificationBuilder();

        SearchCriteria spec = new SearchCriteria("name", ":", "Kibbeling");
        SearchCriteria spec1 = new SearchCriteria("serves", ">", "2");

        Specification<Recipe> specs = builder.with(spec).with(spec1).build();

        Mockito.when(recipeRepository.findAll(specs)).thenReturn(recipes);

        List<Recipe> results = recipeRepository.findAll(specs);

        assertThat(results).extracting("name").contains("Kibbeling");
    }

    @AfterEach
    void deleteEntity() {
        recipeRepository.delete(salad);
        recipeRepository.delete(kibbeling);
    }
}


