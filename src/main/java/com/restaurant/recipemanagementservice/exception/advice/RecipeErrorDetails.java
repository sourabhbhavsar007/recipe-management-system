package com.restaurant.recipemanagementservice.exception.advice;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class RecipeErrorDetails {

    private Date timestamp;

    private String message;

    private String details;

}