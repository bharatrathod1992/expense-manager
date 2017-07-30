package com.personal.expensemanager.repositories;

import com.personal.expensemanager.entities.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ICategoryRepository extends CrudRepository<Category, Integer> {
    Category findByName(String food);

    @Modifying
    @Transactional
    @Query(value = "delete from Category c where c.name = ?1")
    void deleteByName(String name);

}
