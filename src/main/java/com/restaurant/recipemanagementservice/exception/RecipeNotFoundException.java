package com.restaurant.recipemanagementservice.exception;

public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException(String message) {
        super(message);
    }

}
