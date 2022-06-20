package com.restaurant.recipemanagementservice.repository.specification;

import com.restaurant.recipemanagementservice.model.Recipe;
import com.restaurant.recipemanagementservice.repository.criteria.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeSpecificationBuilder {

    private final List<SearchCriteria> searchCriteria;

    public RecipeSpecificationBuilder() {
        searchCriteria = new ArrayList<>();
    }

    public RecipeSpecificationBuilder with(SearchCriteria searchCriteria) {
        return this.with(searchCriteria.getKey(), searchCriteria.getOperation(), searchCriteria.getValue());
    }

    public RecipeSpecificationBuilder with(String key, String operation, Object value) {

        boolean isOr = false;
        if (key.contains("'")) {
            isOr = true;
            key = key.substring(1);
        }

        if (("true".equals(value) || "false".equals(value)) && value instanceof String) {
            value = Boolean.valueOf((String) value);
        }
        searchCriteria.add(new SearchCriteria(isOr, key, operation, value));
        return this;
    }


    public Specification<Recipe> build() {
        if (searchCriteria.isEmpty()) {
            return null;
        }

        List<Specification<Recipe>> specificationList = searchCriteria.stream().map(RecipeSpecification::new).collect(Collectors.toList());

        Specification<Recipe> result = specificationList.get(0);

        for (int i = 1; i < searchCriteria.size(); i++) {
            Specification<Recipe> specification = Specification.where(result);

            if (specification != null) {
                result = searchCriteria.get(i).isOrPredicate() ? specification.or(specificationList.get(i)) : specification.and(specificationList.get(i));
            }
        }
        return result;
    }
}
