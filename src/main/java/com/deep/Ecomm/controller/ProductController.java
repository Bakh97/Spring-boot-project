package com.deep.Ecomm.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.deep.Ecomm.model.Product;
import com.deep.Ecomm.service.ProductServices;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductServices service;
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> product = service.getproducts();
        if(product.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        Product product=service.getProductById(id);
        if(product!=null){
            return new ResponseEntity<>(product,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> createProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile) {
                                            try{
                Product product1  = service.addProduct(product,imageFile);
        return new ResponseEntity<>(product1,HttpStatus.OK);
                                            }
                                            catch(Exception e){
                                                    return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
                                            }
    }

    @GetMapping("product/{prodId}/image")
    public ResponseEntity<byte[]>getImageByProductId(@PathVariable int prodId){
            Product product = service.getProductById(prodId);
            byte[] imageFile = product.getImageData();

            return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
    }

    @PutMapping("product/{id}")
    public ResponseEntity<String>updateProduct(@PathVariable int id,
                                                @RequestPart Product product,
                                                @RequestPart MultipartFile imageFile ){
                Product product1;
                try {
                    product1 = service.updateProduct(id,product,imageFile);
                } catch (IOException e) {
                     return new ResponseEntity<>("failed to update",HttpStatus.BAD_REQUEST);
                
                }
                if(product1!=null){
                    return new ResponseEntity<>("Prouctupdated",HttpStatus.OK);
                }else{
                    return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
                }
                                                }

                 @DeleteMapping("/product/{id}")
                 public ResponseEntity<String> deleteProduct(@PathVariable int id){
                    Product product1 =service.getProductById(id);
                    if(product1!=null){
                        service.deleteProduct(id);
                        return new ResponseEntity<>("product deleted",HttpStatus.OK);
                    }else{
                        return new ResponseEntity<>("product not found",HttpStatus.NOT_FOUND);
                    }
                 }  
                 @GetMapping("products/search")
                 public ResponseEntity<List<Product>> searchProductByKeyword(@RequestParam String keyword){
                          List<Product>products = service.searchProductByKeyword(keyword);
                          if(!products.isEmpty()){
                            return new ResponseEntity<>(products,HttpStatus.OK);
                          }else{
                            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
                          }
                 }                            
}
