package com.restaurant.recipemanagementservice.controller;


import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.restaurant.recipemanagementservice.model.Recipe;
import com.restaurant.recipemanagementservice.repository.criteria.SearchCriteria;
import com.restaurant.recipemanagementservice.repository.specification.RecipeSpecification;
import com.restaurant.recipemanagementservice.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RecipeController.class)
class RecipeContollerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    private List<Recipe> recipes;
    private Recipe recipe;
    private Recipe recipeEntity;
    private Recipe recipeRequest;

    @BeforeEach
    public void setUp() {

        recipe = new Recipe();
        recipe.setId(1);
        recipe.setName("Smoked Salmon");
        recipe.setServes(2);
        recipe.setVegetarian(false);
        recipe.setLastUpdated(new Date());
        recipe.setInstructions("Preheat oven, add Salmon and olive oil, fennel, onion and red chilly.");
        recipe.setIngredients("Salmon, oil, mayonnaise");

        recipes = new ArrayList<Recipe>();
        recipes.add(recipe);

        Recipe recipeEntity = new Recipe();
        recipeEntity.setId(1);
        recipeEntity.setName("Smoked Salmon");
        recipeEntity.setServes(2);
        recipeEntity.setVegetarian(false);
        recipeEntity.setLastUpdated(new Date());
        recipeEntity.setInstructions("Preheat oven, add Salmon and olive oil, fennel, onion and red chilly.");
        recipe.setIngredients("Salmon, oil, mayonnaise");

        recipeRequest = new Recipe();
        recipeEntity.setId(1);
        recipeEntity.setName("Smoked Salmon");
        recipeEntity.setServes(2);
        recipeEntity.setVegetarian(false);
        recipeEntity.setLastUpdated(new Date());
        recipeEntity.setInstructions("Preheat oven, add Salmon and olive oil, fennel, onion and red chilly.");
        recipe.setIngredients("Salmon, oil, mayonnaise");

    }

    @Test
    //@WithMockUser(username = "user-1", password = "user-1-password", roles = "USER")
    void testGetAll() throws Exception {

        Mockito.when(recipeService.getAllRecipes()).thenReturn(recipes);

        mockMvc.perform(get("/api/v1/recipe/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    //@WithMockUser(username = "user-1", password = "user-1-password", roles = "USER")
    void testFindById() throws Exception {

        long id = 1;
        Mockito.when(recipeService.findRecipeById(id)).thenReturn(recipe);

        mockMvc.perform(get("/api/v1/recipe/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    //@WithMockUser(username = "user-1", password = "user-1-password", roles = "USER")
    void testDeleteRecipeById() throws Exception {
        long id = 1;

        doNothing().when(recipeService).deleteRecipeById(id);

        mockMvc.perform(delete("/api/v1/recipe/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    //@WithMockUser(username = "user-1", password = "user-1-password", roles = "USER")
    void testAddRecipe() throws Exception {

        Mockito.when(recipeService.addRecipe(recipeEntity)).thenReturn(recipe);

        mockMvc.perform(
                post("/api/v1/recipe/").content(asJsonString(recipeRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }

    @Test
    //@WithMockUser(username = "user-1", password = "user-1-password", roles = "USER")
    void testCreateRecipe_when_invalidRecipeName() throws Exception {

        Mockito.when(recipeService.addRecipe(recipeEntity)).thenReturn(recipe);

        Recipe recipeRequestInput = recipeRequest;
        recipeRequestInput.setName("This test is for the testing the long long long long long name for recipe");
        mockMvc.perform(post("/api/v1/recipe/").content(asJsonString(recipeRequestInput))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
    }

    @Test
    //@WithMockUser(username = "user-1", password = "user-1-password", roles = "USER")
    void testUpdateRecipe() throws Exception {

        long id = 1;

        Mockito.when(recipeService.addRecipe(recipeEntity)).thenReturn(recipe);

        mockMvc.perform(put("/api/v1/recipe/{id}", id).content(asJsonString(recipeRequest))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201));
    }

    @Test
    //@WithMockUser(username = "user1", password = "user1Pass", roles = "USER")
    void testSearchCriteria() throws Exception {

        RecipeSpecification recipeSpecification = new RecipeSpecification(new SearchCriteria("isVegetarian", ":", true));

        Mockito.when(recipeService.searchRecipeByCriteria(recipeSpecification.toString())).thenReturn(recipes);

        mockMvc.perform(get("/api/v1/recipe/searchByCriteria/{searchByCriteria}", "isVegetarian:true")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(status().is(200));
    }

    public String asJsonString(final Object obj) {

        try {
            return new ObjectMapper().writeValueAsString(obj);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

