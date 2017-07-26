package com.personal.expensemanager.constrollers;

import com.personal.expensemanager.entities.Category;
import com.personal.expensemanager.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public Category create(@RequestBody Map<String, String> payload){
        return this.categoryService.create(payload.get("name"));
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public Iterable<Category> findAll(){
        return this.categoryService.findAll();
    }

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
    public Category findOne(@PathVariable int id) {
        return this.categoryService.findById(id);
    }

    @RequestMapping(value = "/categories/findByName", method = RequestMethod.POST)
    public Iterable<Category> findByName(@RequestBody Map<String,String> payload) {
        return this.categoryService.findByName(payload.get("name"));
    }

}
