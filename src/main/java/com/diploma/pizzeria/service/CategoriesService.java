package com.diploma.pizzeria.service;

import com.diploma.pizzeria.entities.Category;
import com.diploma.pizzeria.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {

    private CategoriesRepository repository;

    @Autowired
    public void setRepository(CategoriesRepository repository){this.repository=repository;}

    public void saveCategory(Category category){ repository.save(category);}

    public List<Category> getAllCategories(){return repository.findAllByOrderById();}

    public Category getCategoryByCategoryEng(String categoryEng){
        return repository.findCategoryByCategoryEng(categoryEng);
    }

    public void removeById(int id){repository.deleteById(id);}

    public boolean existsWithId(int id){ return repository.existsById(id);}

    public Category getById(int id) { return repository.getById(id);}
}