package com.personal.expensemanager.services;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.personal.expensemanager.entities.Category;
import com.personal.expensemanager.entities.SubCategory;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(value = {"/sql/seed.sql"})
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    //CREATE
    @Test
    public void shouldCreateNewCategory() throws Exception {
        Category category = this.categoryService.create("Transport");
        assertEquals(3,category.getId());
    }
    @Test
    public void shouldCreateMultipleCategories() {
        Category firt = new Category("Education");
        Category second = new Category("Bill");
        Category third = new Category("Shopping");
        List<Category> prev = Lists.newArrayList(this.categoryService.findAll());
        assertEquals(2, prev.size());
        List<Category> newCategories = new ArrayList<>();
        newCategories.add(firt);
        newCategories.add(second);
        newCategories.add(third);
        this.categoryService.createMultiple(newCategories);

        List<Category> categories = Lists.newArrayList(this.categoryService.findAll());
        assertEquals(5,categories.size());
    }

    //READ
    @Test
    public void shouldFindCategoryById() {
        Category category = this.categoryService.create("Transport");
        Category result = this.categoryService.findById(category.getId());
        assertEquals("Transport",result.getName());
    }
    @Test
    public void shouldFindCategoriesByName() {
        this.categoryService.create("Transport");
        this.categoryService.create("Fee");
        Category result = this.categoryService.findByName("Transport");
        assertEquals("Transport",result.getName());
        assertEquals("Fee",this.categoryService.findByName("Fee").getName());
    }
    @Test
    public void shouldFindAllCategories() {
        Category category = this.categoryService.create("Transport");
        Category category1 = this.categoryService.create("Fee");
        Category category2 = this.categoryService.create("Sport");
        Iterable<Category> results = this.categoryService.findAll();
        List<Category> categories = Lists.newArrayList(results);
        assertEquals(5,categories.size());
    }
    @Test
    public void shouldFindListOfSubCategoriesAssociated() {
        List<SubCategory> subCategories = this.categoryService.findById(1).getSubCategories();
        assertEquals(3,subCategories.size());

        this.subCategoryService.create("Butter", 1);
        List<SubCategory> subCategories1 = this.categoryService.findById(1).getSubCategories();
        assertEquals(4,subCategories1.size());
    }

    //UPDATE
    @Test
    public void shouldUpdateCategoryById() {
        Category category = new Category("DailyFood");
        Category updatedCategory = this.categoryService.updateCategory(1, category);
        assertEquals("DailyFood",updatedCategory.getName());
    }

    //DELETE
    @Test
    public void shouldDeleteCategoryById() {
        Category category = this.categoryService.create("Fee");
        Iterable<Category> results = this.categoryService.findAll();
        List<Category> categories = Lists.newArrayList(results);
        assertEquals(3,categories.size());

        this.categoryService.delete(category.getId());
        Iterable<Category> newResults = this.categoryService.findAll();
        List<Category> newCategories = Lists.newArrayList(newResults);
        assertEquals(2,newCategories.size());
    }
    @Test
    @Transactional
    public void shouldDeleteCategoryByName() {
        Category category = this.categoryService.create("Fee");
        assertEquals(1,this.categoryService.findById(1).getId());

        this.categoryService.deleteByName("Fee");
        List<Category> categories = Lists.newArrayList(this.categoryService.findAll());
        assertEquals(2,categories.size());
    }
}