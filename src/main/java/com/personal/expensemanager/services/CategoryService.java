package com.personal.expensemanager.services;

import com.personal.expensemanager.entities.Category;
import com.personal.expensemanager.repositories.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private ICategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category create(String name){
        Category category = new Category(name);
        return this.categoryRepository.save(category);
    }

    public Category findById(int id) {
        return this.categoryRepository.findOne(id);
    }

    public Iterable<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    public void delete(int id) {
        this.categoryRepository.delete(id);
    }

    public Category findByName(String name) {
        return this.categoryRepository.findByName(name);
    }

    public void deleteByName(String name) {
        this.categoryRepository.deleteByName(name);
    }

    public Category updateCategory(int id,Category category) {
        Category newCategory = this.categoryRepository.findOne(id);
        newCategory.setName(category.getName());
        return this.categoryRepository.save(newCategory);
    }

    public void createMultiple(List<Category> newCategories) {
        this.categoryRepository.save(newCategories);
    }
}
