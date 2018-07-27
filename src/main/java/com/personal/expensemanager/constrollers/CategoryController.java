package com.personal.expensemanager.constrollers;

import com.personal.expensemanager.entities.Category;
import com.personal.expensemanager.services.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/category")
@Api(value = "categories", description = "Operations pertaining to Categories in Expense Manager")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

//    CREATE NEW CATEGORY
@ApiOperation(value = "Create category by providing required information", response = Category.class)
@RequestMapping(value = "/add", method = RequestMethod.POST)
    public Category create(@RequestBody Map<String, String> payload) throws Exception {
        return this.categoryService.create(payload.get("name"));
    }
//    CREATE MULTIPLE CATEGORIES
@ApiOperation(value = "Create multiple categories", response = Iterable.class)
@RequestMapping(value = "/addMulCategories", method = RequestMethod.POST)
    public Iterable<Category> createMulCategories(@RequestBody List<Category> payload) throws Exception {
            return this.categoryService.createMultiple(payload);
    }

//    LIST OUT ALL CATEGORIES
    @ApiOperation(value = "View a list of categories", response = Iterable.class)
    @RequestMapping(value = "/categories", method = RequestMethod.GET, produces = "application/json")
    public Iterable<Category> findAll(){
        return this.categoryService.findAll();
    }

//    GET PARTICULAR CATEGORY
    @ApiOperation(value = "Find category by its id", response = Category.class)
    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    public Category findById(@PathVariable int id) {
        return this.categoryService.findById(id);
    }

//    GET CATEGORY BY NAME
    @ApiOperation(value = "Find category by name", response = Category.class)
    @RequestMapping(value = "/find/name", method = RequestMethod.POST)
    public Category findByName(@RequestBody Map<String,String> payload) {
        return this.categoryService.findByName(payload.get("name"));
    }

//    UPDATE CATEGORY
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "Update category by providing required information", response = Category.class)
    public Category updateCategory(@RequestBody Category category){
        return this.categoryService.updateCategory(category);
    }

//    DELETE CATEGORY BY ID
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete category by id")
    public void deleteById(@PathVariable int id) {
        this.categoryService.delete(id);
    }

}
