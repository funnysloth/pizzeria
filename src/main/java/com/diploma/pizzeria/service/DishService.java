package com.diploma.pizzeria.service;

import com.diploma.pizzeria.entities.Dish;
import com.diploma.pizzeria.repositories.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class DishService {

    private DishRepository repository;
    private List<Integer> pages;

    public DishService(List<Integer> pages) {
        this.pages = pages;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages.clear();
        for (int i = 1; i <= pages; i++) {
            this.pages.add(i);
        }
    }

    @Autowired
    public void setRepository(DishRepository repository) {
        this.repository = repository;
    }

    //defining pages to know how many to print in paging on a html page
    public List<Integer> definePageNumberForCategory(int categoryId, int pageSize){
        float num = getDishesByCategory(categoryId).size()/(float)pageSize;
        int pagesNum;
        for (pagesNum=0; pagesNum<=num; pagesNum++)
            setPages(pagesNum+1);
        return getPages();
    }

    //defining pages to know how many to print in paging on a html page
    public List<Integer> definePageNumber(int pageSize){
        float num = repository.findAll().size()/(float)pageSize;
        int pagesNum;
        for (pagesNum=0; pagesNum<=num; pagesNum++)
            setPages(pagesNum+1);
        return getPages();
    }

    //defining pages to know how many to print in paging on a html page
    public List<Integer> definePageNumberForStatus(String status, int pageSize){
        float num = getDishesByStatus(status).size()/(float)pageSize;
        int pagesNum;
        for (pagesNum=0; pagesNum<=num; pagesNum++)
            setPages(pagesNum+1);
        return getPages();
    }

    //defining pages to know how many to print in paging on a html page
    public List<Integer> definePageNumberForCategoryAndStatus(int categoryId,  String status, int pageSize){
        float num = getDishesByCategoryAndStatus(categoryId, status).size()/(float)pageSize;
        int pagesNum;
        for (pagesNum=0; pagesNum<=num; pagesNum++)
            setPages(pagesNum+1);
        return getPages();
    }

    public void saveDish(Dish dish){
        repository.save(dish);
    }

    public List<Dish> getDishesByCategory(int categoryId){
        return repository.findAllByCategoryId(categoryId);
    }

    public List<Dish> getDishesByStatus(String status){
        return repository.findAllByStatus(status);
    }

    public List<Dish> getDishesByCategoryAndStatus(int categoryId, String status){
        return repository.findAllByCategoryIdAndStatus(categoryId, status);
    }

    public List<Dish> getAllDishes(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize){
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Dish> pageResult = repository.findAll(paging);
        return pageResult.getContent();
    }

    public List<Dish> getDishesByCategoryPaging( int categoryId, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize){
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Dish> pageResult = repository.findAllByCategoryIdOrderByIdDesc(categoryId, paging);
        return pageResult.getContent();
    }

    public List<Dish> getDishesByStatusPaging(String status, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize){
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Dish> pageRequest = repository.findAllByStatusOrderByIdDesc(status, paging);
        return pageRequest.getContent();
    }

    public List<Dish> getDishesByCategoryAndStatusPaging( int categoryId, String status, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize){
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Dish> pageResult = repository.findAllByCategoryIdAndStatusOrderByIdDesc(categoryId, status, paging);
        return pageResult.getContent();
    }

    public List<Dish> getDishesBySearchNamePaging(String search, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize){
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Dish> pageResult = repository.findAllByDishNameContainsOrderByIdDesc(search, paging);
        return pageResult.getContent();
    }

    public void removeById(Long id){repository.deleteById(id);}

    public boolean existsWithId(Long id){ return repository.existsById(id);}

    public Dish getById(Long id) {return repository.getById(id);}
}