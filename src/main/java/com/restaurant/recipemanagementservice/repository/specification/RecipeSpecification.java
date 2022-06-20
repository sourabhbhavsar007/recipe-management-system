package com.restaurant.recipemanagementservice.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import com.restaurant.recipemanagementservice.exception.RecipeCriteriaNotFoundException;
import com.restaurant.recipemanagementservice.model.Recipe;
import com.restaurant.recipemanagementservice.repository.criteria.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RecipeSpecification implements Specification<Recipe> {

    private static final long serialVersionUID = 8243117206275218850L;

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        try {

            if (criteria.getOperation().equalsIgnoreCase(">")) {
                return builder.greaterThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());

            } else if (criteria.getOperation().equalsIgnoreCase("<")) {
                return builder.lessThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());

            } else if (criteria.getOperation().equalsIgnoreCase(":")) {

                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");

                } else {
                    return builder.equal(root.get(criteria.getKey()), criteria.getValue());
                }

            }

        } catch (IllegalArgumentException e) {

            throw new RecipeCriteriaNotFoundException(e.getMessage());
        }

        return null;
    }

}