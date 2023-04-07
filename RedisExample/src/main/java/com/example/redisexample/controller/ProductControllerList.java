package com.example.redisexample.controller;

import com.example.redisexample.entity.Product;
import com.example.redisexample.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/list")
public class ProductControllerList {

    @Autowired
    private ProductDao dao;

    @PostMapping("/save")
    public Product saveList (@RequestBody Product product) {
        return dao.saveList(product);
    }

    @GetMapping
    public List<Product> getAll() {
        return dao.findAllList();
    }

    @Cacheable(key = "#id", value = "Product")
    @GetMapping("/{id}")
    public Product findById(@PathVariable("id") int id) {
        return dao.findProductByIdList(id);
    }

    @CacheEvict(key = "#id", value = "Product")
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") int id) {
        Product product = dao.findProductByIdList(id);
        if (product.getId() == id) {
            return dao.deleteProductList(product.getId());
        } else if (product.getId() == null){
            return "Not found product id: " + id;
        } else {
            return null;
        }
    }
}
