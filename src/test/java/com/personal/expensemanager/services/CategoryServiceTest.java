package com.personal.expensemanager.services;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.personal.expensemanager.entities.Category;
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

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldCreateNewCategory() throws Exception {
        Category category = this.categoryService.create("Food");
        assertEquals(1,category.getId());
    }

    @Test
    public void shouldFindCategoryById() {
        Category category = this.categoryService.create("Food");
        Category result = this.categoryService.findById(1);
        assertEquals("Food",result.getName());
    }

    @Test
    public void shouldFindCategoriesByName() {
        this.categoryService.create("Food");
        this.categoryService.create("Food");
        List<Category> result = Lists.newArrayList(this.categoryService.findByName("Food"));
        assertEquals("Food",result.get(0).getName());
        assertEquals(2,result.size());
    }

    @Test
    public void shouldFindAllCategories() {
        Category category = this.categoryService.create("Food");
        Category category1 = this.categoryService.create("Food");
        Category category2 = this.categoryService.create("Food");
        Iterable<Category> results = this.categoryService.findAll();
        List<Category> categories = Lists.newArrayList(results);
        assertEquals(3,categories.size());
    }

    @Test
    public void shouldDeleteCategoryById() {
        Category category = this.categoryService.create("Food");
        Iterable<Category> results = this.categoryService.findAll();
        List<Category> categories = Lists.newArrayList(results);
        assertEquals(1,categories.size());

        this.categoryService.delete(category.getId());
        Iterable<Category> newResults = this.categoryService.findAll();
        List<Category> newCategories = Lists.newArrayList(newResults);
        assertEquals(0,newCategories.size());
    }

    @Test
    @Transactional
    public void shouldDeleteCategoryByName() {
        Category category = this.categoryService.create("Food");
        assertEquals(1,this.categoryService.findById(1).getId());

        this.categoryService.deleteByName("Food");
        List<Category> categories = Lists.newArrayList(this.categoryService.findAll());
        assertEquals(0,categories.size());

        this.categoryService.create("Food");
        this.categoryService.create("Food");
        this.categoryService.create("Foo");
        List<Category> categories1 = Lists.newArrayList(this.categoryService.findAll());
        assertEquals(3,categories1.size());

        this.categoryService.deleteByName("Food");
        List<Category> categories2 = Lists.newArrayList(this.categoryService.findAll());
        assertEquals(1,categories2.size());
    }
}