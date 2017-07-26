package com.personal.expensemanager.services;

import com.personal.expensemanager.entities.Category;
import com.personal.expensemanager.entities.SubCategory;
import com.personal.expensemanager.repositories.ICategoryRepository;
import com.personal.expensemanager.repositories.ISubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubCategoryService {

    private ICategoryRepository categoryRepository;
    private ISubCategoryRepository subCategoryRepository ;

    @Autowired
    public void setSubCategoryRepository(ISubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    @Autowired
    public void setCategoryRepository(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }



    public SubCategory create(String name, int category_id) {
        Category category = this.categoryRepository.findOne(category_id);
        SubCategory subCategory = new SubCategory(name,category);
        return this.subCategoryRepository.save(subCategory);
    }
}
