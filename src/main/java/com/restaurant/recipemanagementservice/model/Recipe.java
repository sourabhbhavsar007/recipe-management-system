package com.restaurant.recipemanagementservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    @Length(min = 5, max = 50)
    private String name;

    @Column(name = "isVegetarian")
    private boolean isVegetarian;

    @Column(name = "serves")
    @Max(value = 8)
    private int serves;

    @Column(name = "instructions")
    @Length(min = 5, max = 100)
    private String instructions;

    @Column(name = "ingredients")
    @Length(min = 5, max = 100)
    String ingredients;

    @Column(name = "lastUpdated")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date lastUpdated;

}
