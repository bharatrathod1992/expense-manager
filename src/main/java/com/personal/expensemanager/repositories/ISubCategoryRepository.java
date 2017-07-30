package com.personal.expensemanager.repositories;

import com.personal.expensemanager.entities.Category;
import com.personal.expensemanager.entities.SubCategory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ISubCategoryRepository extends CrudRepository<SubCategory,Integer> {

    Iterable<SubCategory> findByName(String name);
}
