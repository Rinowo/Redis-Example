package com.example.redisexample.repository;

import com.example.redisexample.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ProductDao {

    @Autowired
    private RedisTemplate template;

    /* Hashes Demo */

    public static final String HASH_KEY = "Product-Hashes";


    public Product saveHashes(Product product){
        template.opsForHash().put(HASH_KEY, product.getId().toString(), product);
        return product;
    }

    public List<Product> findAllHashes(){
        return template.opsForHash().values(HASH_KEY);
    }

    public Product findProductByIdHashes(int id){
        return (Product) template.opsForHash().get(HASH_KEY, id);
    }


    public String deleteProductHashes(int id){
        template.opsForHash().delete(HASH_KEY, id);
        return "product removed !!";
    }

    /* List Demo */
    public static final String LIST_KEY = "Product-Lists";

    private ListOperations<String, Product> listOps;

    public Product saveList(Product product) {
        template.opsForList().leftPush(LIST_KEY, product);
        return product;
    }

    public List<Product> findAllList() {
        Long size = template.opsForList().size(LIST_KEY);
        return template.opsForList().range(LIST_KEY, 0, size - 1);
    }

    public Product findProductByIdList(int id) {
        List<Product> products = findAllList();
        for (Product product: products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public String deleteProductList(int id){
        listOps.remove(LIST_KEY, 1, new Product(id));
        return "product removed !!";
    }


    public static final String SORTEDSET_KEY = "Product-Sorted";

    public Product saveSorted(Product product) {
        template.opsForZSet().add(SORTEDSET_KEY, product, product.getId());
        return product;
    }

    public Set<Product> findAllSortedSet() {
        return template.opsForZSet().range(SORTEDSET_KEY, 0, -1);
    }
}
