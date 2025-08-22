package com.deep.Ecomm.service;

import java.io.IOException;
import java.math.BigDecimal;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.deep.Ecomm.Repository.ProductRepo;
import com.deep.Ecomm.model.Product;

@Service
public class ProductServices {

    @Autowired
    ProductRepo repo;
//     List<Product> products = Arrays.asList(
//    		 new Product(1,"A",BigDecimal.valueOf(10)),
//    		 new Product(2,"A",BigDecimal.valueOf(20)));
//     

    public List<Product> getproducts(){
        return repo.findAll();
    }
    
    public Product addProduct(Product product,MultipartFile imageFile) throws IOException {
    	product.setImageName(imageFile.getName());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }


    public Product getProductById(int prodId){
        return repo.findById(prodId).orElse(null);
    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        product.setImageData(imageFile.getBytes());
        product.setImageName(imageFile.getName());
        product.setImageType(imageFile.getContentType());
        return repo.save(product);
    }

    public void deleteProduct(int id) {
       repo.deleteById(id);
    }

    public List<Product> searchProductByKeyword(String keyword) {
     return repo.searchKeyword(keyword);
    }
    
}
