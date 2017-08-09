package com.personal.expensemanager.repositories;

import com.personal.expensemanager.entities.SubCategory;
import org.springframework.data.repository.CrudRepository;

public interface ISubCategoryRepository extends CrudRepository<SubCategory,Integer> {

    Iterable<SubCategory> findByName(String name);
}
