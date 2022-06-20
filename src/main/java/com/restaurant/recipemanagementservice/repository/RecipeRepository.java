package com.restaurant.recipemanagementservice.repository;

import com.restaurant.recipemanagementservice.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

//We extend the JpaSpecificationExecutor interface because we intend to search recipe based on criteria which we pass as specification.
//Alternatively we can also use @Query for native SQL query based on given search criteria.
//We also can use Querydsl, which is an open source project for searching based on criterias.
public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {
}
