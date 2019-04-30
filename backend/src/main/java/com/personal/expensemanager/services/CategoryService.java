package com.personal.expensemanager.services;

import com.personal.expensemanager.entities.Category;
import com.personal.expensemanager.repositories.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private ICategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category create(String name) {
        Optional<Category> optional = Optional.ofNullable(this.categoryRepository.findByName(name));
        if(optional.isPresent()){
            throw new IllegalArgumentException(name +" is already added.");
        }
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
        Optional<Category> optional = Optional.ofNullable(this.categoryRepository.findOne(id));
        if(optional.isPresent()){
            this.categoryRepository.delete(id);
        }else {
            throw new IllegalArgumentException("Category not found with id: " + id);
        }
    }

    public Category findByName(String name) {
        return this.categoryRepository.findByName(name);
    }

    public void deleteByName(String name) {
        this.categoryRepository.deleteByName(name);
    }

    public Category updateCategory(Category category) {
        Category newCategory = this.categoryRepository.findOne(category.getId());
        newCategory.setName(category.getName());
        return this.categoryRepository.save(newCategory);
    }

    public Iterable<Category> createMultiple(List<Category> newCategories) {
        boolean flag = false;
        for(Category category : newCategories){
            Optional<Category> optional = Optional.ofNullable(this.categoryRepository.findByName(category.getName()));
            if(optional.isPresent()){
                flag = true;
                break;
            }
        }
        if(flag){
            throw new IllegalArgumentException("Duplicate Entry");
        }

        return  this.categoryRepository.save(newCategories);
    }
}
