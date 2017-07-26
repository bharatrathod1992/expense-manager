package com.personal.expensemanager.services;

import com.personal.expensemanager.entities.Category;
import com.personal.expensemanager.entities.SubCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Sql(value = {"/sql/seed.sql"})
public class SubCategoryServiceTest {
    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void shouldCreateNewSubCategory() {
        Category category = this.categoryService.create("Food");
        assertEquals("Food",this.categoryService.findById(category.getId()).getName());
        SubCategory subCategory = this.subCategoryService.create("Pizza",category.getId());
        assertEquals("Pizza",subCategory.getName());
    }
}