package com.diploma.pizzeria.service;

import com.diploma.pizzeria.entities.Order;
import com.diploma.pizzeria.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OrdersService {

    private OrdersRepository repository;
    private List<Integer> pages;

    public OrdersService(List<Integer> pages) {
        this.pages = pages;
    }

    @Autowired
    public void setRepository(OrdersRepository repository) {
        this.repository = repository;
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

    //defining pages to know how many to print in paging on a html page
    public List<Integer> definePageNumber(int pageSize){
        double num = Math.ceil(repository.findAll().size()/(float)pageSize);
        int pagesNum;
        for (pagesNum=1; pagesNum<=num; pagesNum++)
            setPages(pagesNum);
        return getPages();
    }

    //defining pages to know how many to print in paging on a html page
    public List<Integer> definePageNumberForOrderDateBetween(int pageSize, Timestamp start, Timestamp end){
        double num = Math.ceil(getOrdersByOrderDateBetween(start, end).size()/(float)pageSize);
        int pagesNum;
        for (pagesNum=1; pagesNum<=num; pagesNum++)
            setPages(pagesNum);
        return getPages();
    }

    //defining pages to know how many to print in paging on a html page
    public List<Integer> definePageNumberForOrderDateBefore(int pageSize, Timestamp end){
        double num = Math.ceil(getOrdersByOrderDateBefore(end).size()/(float)pageSize);
        int pagesNum;
        for (pagesNum=1; pagesNum<=num; pagesNum++)
            setPages(pagesNum);
        return getPages();
    }

    //defining pages to know how many to print in paging on a html page
    public List<Integer> definePageNumberForOrderDateAfter(int pageSize, Timestamp start){
        double num = Math.ceil(getOrdersByOrderDateAfter(start).size()/(float)pageSize);
        int pagesNum;
        for (pagesNum=1; pagesNum<=num; pagesNum++)
            setPages(pagesNum);
        return getPages();
    }

    public void saveOrder(Order order){repository.save(order);}

    public List<Order> getAllOrdersPaging(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize){
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Order> pageResult = repository.findAllByOrderByOrderDateDesc(paging);
        return pageResult.getContent();
    }

    public List<Order> getOrdersByOrderDateBetweenPaging(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
                                                         Timestamp start, Timestamp end){
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Order> pageResult = repository.findAllByOrderDateBetweenOrderByOrderDateDesc(start, end, paging);
        return pageResult.getContent();
    }

    public List<Order> getOrdersByOrderDateBeforePaging(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
                                                         Timestamp end){
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Order> pageResult = repository.findAllByOrderDateBeforeOrderByOrderDateDesc(end, paging);
        return pageResult.getContent();
    }

    public List<Order> getOrdersByOrderDatAfterPaging(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
                                                        Timestamp start){
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Order> pageResult = repository.findAllByOrderDateAfterOrderByOrderDateDesc(start, paging);
        return pageResult.getContent();
    }

    public Order getOrderById(Long id){
        return repository.getById(id);
    }

    public List<Order> getOrdersByOrderDateBetween(Timestamp start, Timestamp end){
        return repository.findAllByOrderDateBetweenOrderByOrderDateDesc(start, end);
    }

    public List<Order> getOrdersByOrderDateBefore(Timestamp end){
        return repository.findAllByOrderDateBeforeOrderByOrderDateDesc(end);
    }

    public List<Order> getOrdersByOrderDateAfter(Timestamp start){
        return repository.findAllByOrderDateAfterOrderByOrderDateDesc(start);
    }

    public void deleteOrder(Long id){
        repository.deleteById(id);
    }

    public List<Order> getAllOrders(){
        return repository.findAll();
    }

    public List<Order> getOrdersByUserId(long id){
        return repository.findAllByUserIdOrderByOrderDateDesc(id);
    }
}