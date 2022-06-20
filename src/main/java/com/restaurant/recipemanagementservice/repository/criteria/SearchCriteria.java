package com.restaurant.recipemanagementservice.repository.criteria;

import java.io.Serializable;
import lombok.Data;

@Data
public class SearchCriteria implements Serializable {

    private static final long serialVersionUID = 9222863854541646666L;

    private String key;

    private String operation;

    private transient Object value;

    private boolean orPredicate;

    public SearchCriteria(final String key, final String operation, final Object value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SearchCriteria(final boolean orPredicate, final String key, final String operation, final Object value) {
        super();
        this.orPredicate = orPredicate;
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

}
