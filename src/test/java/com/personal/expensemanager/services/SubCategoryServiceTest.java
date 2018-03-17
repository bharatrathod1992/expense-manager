package com.personal.expensemanager.services;

import com.personal.expensemanager.entities.Category;
import com.personal.expensemanager.entities.SubCategory;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Sql(value = {"/sql/seed.sql"})
public class SubCategoryServiceTest {
    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private CategoryService categoryService;

    //CREATE
    @Test
    public void shouldCreateNewSubCategory() throws Exception {
        Category category = this.categoryService.create("Fee");
        assertEquals("Fee",this.categoryService.findById(category.getId()).getName());
        SubCategory subCategory = this.subCategoryService.create("Pizza",1);
        assertEquals("Pizza",subCategory.getName());
    }

    //READ
    @Test
    public void shouldFindSubCategoryById() {
        SubCategory subCategory = this.subCategoryService.create("dinner",1);
        SubCategory result = this.subCategoryService.findById(subCategory.getId());
        assertEquals("dinner",result.getName());
    }
    @Test
    public void shouldFindSubCategoriesByName() {
        this.subCategoryService.create("dinner",1);
        this.subCategoryService.create("dinner",1);
        List<SubCategory> results = Lists.newArrayList(this.subCategoryService.findByName("dinner"));
        assertEquals(2,results.size());
        assertEquals("dinner",results.get(0).getName());
    }
    @Test
    public void shouldFindAllSubCategories() {
        List<SubCategory> subCategories = Lists.newArrayList(this.subCategoryService.findAll());
        assertEquals(5,subCategories.size());
    }
    @Test
    public void shouldFindCategoryAssociatedWith() {
        SubCategory subCategory = this.subCategoryService.create("dinner",1);
        SubCategory result = this.subCategoryService.findById(subCategory.getId());
        assertEquals("dinner",result.getName());
        assertEquals("Food",result.getCategory().getName());
    }

    //UPDATE
    @Test
    public void shouldUpdateTheSubCategoryById() {
        Category category = this.categoryService.findById(1);
        assertEquals("Food",category.getName());
        SubCategory subCategory = new SubCategory("Burger",category);
        assertEquals("Chapati",this.subCategoryService.findById(1).getName());
        SubCategory subCategoryResult = this.subCategoryService.updateById(1,subCategory);
        assertEquals("Burger",subCategory.getName());
        assertEquals("Burger",this.subCategoryService.findById(1).getName());
    }

    //DELETE
    @Test
    @Transactional
    public void shouldDeleteSubCategoryById() {
        Category category = this.categoryService.findById(1);
        SubCategory subCategory = this.subCategoryService.create("Sandwich",1);
        assertEquals("Sandwich",this.subCategoryService.findById(subCategory.getId()).getName());
        this.subCategoryService.delete(subCategory.getId());
        List<SubCategory> subCategories = Lists.newArrayList(this.subCategoryService.findAll());
        assertEquals(5,subCategories.size());
    }
}