package com.personal.expensemanager.constrollers;

import com.personal.expensemanager.entities.Category;
import com.personal.expensemanager.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

//    CREATE NEW CATEGORY
    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public Category create(@RequestBody Map<String, String> payload) throws Exception {
        return this.categoryService.create(payload.get("name"));
    }
//    CREATE MULTIPLE CATEGORIES
    @RequestMapping(value = "/addMulCategories", method = RequestMethod.POST)
    public Iterable<Category> createMulCategories(@RequestBody List<Category> payload) throws Exception {
            return this.categoryService.createMultiple(payload);
    }

//    LIST OUT ALL CATEGORIES
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public Iterable<Category> findAll(){
        return this.categoryService.findAll();
    }

//    GET PARTICULAR CATEGORY
    @RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
    public Category findById(@PathVariable int id) {
        return this.categoryService.findById(id);
    }

//    GET CATEGORY BY NAME
    @RequestMapping(value = "/categories/findByName", method = RequestMethod.POST)
    public Category findByName(@RequestBody Map<String,String> payload) {
        return this.categoryService.findByName(payload.get("name"));
    }

//    UPDATE CATEGORY
    @RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
    public Category updateCategory(@RequestBody Category category){
        return this.categoryService.updateCategory(category);
    }

//    DELETE CATEGORY BY ID
    @RequestMapping(value = "/categories/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable int id) {
        this.categoryService.delete(id);
    }

}
