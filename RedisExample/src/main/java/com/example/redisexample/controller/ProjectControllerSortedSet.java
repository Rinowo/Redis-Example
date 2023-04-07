package com.example.redisexample.controller;

import com.example.redisexample.entity.Product;
import com.example.redisexample.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/sorted-set")
public class ProjectControllerSortedSet {

    @Autowired
    private ProductDao dao;

    @PostMapping("/save")
    public Product save(@RequestBody Product product) {
        return dao.saveSorted(product);
    }

    @GetMapping
    public Set<Product> getALl() {
        return dao.findAllSortedSet();
    }
}
