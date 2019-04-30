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



    public SubCategory create(String name, int categoryId) {
        Category category = this.categoryRepository.findOne(categoryId);
        SubCategory subCategory = new SubCategory(name,category);
        return this.subCategoryRepository.save(subCategory);
    }

    public SubCategory findById(int id) {
        return this.subCategoryRepository.findOne(id);
    }

    public Iterable<SubCategory> findByName(String name) {
        return this.subCategoryRepository.findByName(name);
    }

    public SubCategory updateById(int id, SubCategory subCategory) {
        SubCategory old = this.subCategoryRepository.findOne(id);
        old.setName(subCategory.getName());
        old.setCategory(subCategory.getCategory());
        return this.subCategoryRepository.save(old);
    }

    public void delete(int id) {
        this.subCategoryRepository.delete(id);
    }

    public Iterable<SubCategory> findAll() {
        return this.subCategoryRepository.findAll();
    }
}
