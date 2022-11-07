package com.javatechie.springdataredisexample.Resources;

import com.javatechie.springdataredisexample.entity.Product;
import com.javatechie.springdataredisexample.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductResource {
    @Autowired
    private ProductDao dao;

    @PostMapping
    public Product save(@RequestBody Product product){
        return dao.save(product);
    }
    @GetMapping
    public List<Product> getAllProducts(){
        return dao.findAll();
    }

//     product having price more than 5000 will be cached
    @GetMapping("/{id}")
    @Cacheable(key = "#id",value = "Product",unless = "#result.price < 5000")
    public Product findProduct(@PathVariable int id){
        return dao.findProductById(id);
    }
    // When deleted from DB reflect the same in Cache (@CacheEvict)
    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id",value = "Product")
    public String deleteProduct(@PathVariable int id){
        return dao.deleteProduct(id);
    }
}
