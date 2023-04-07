package com.example.redisexample.controller;


import com.example.redisexample.entity.Product;
import com.example.redisexample.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/hashes")
public class ProductControllerHashes {

    @Autowired
    private ProductDao daoHashes;

    @PostMapping(path = "/save")
    public Product save(@RequestBody Product product) {
        return daoHashes.saveHashes(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return daoHashes.findAllHashes();
    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id", value = "Product", unless = "#result.price > 1000")
    public Product findProduct(@PathVariable int id) {
        return daoHashes.findProductByIdHashes(id);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id", value = "Product")
    public String remove(@PathVariable int id) {
        return daoHashes.deleteProductHashes(id);
    }
}
