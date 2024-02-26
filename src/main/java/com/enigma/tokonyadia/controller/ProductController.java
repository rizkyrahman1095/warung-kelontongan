package com.enigma.tokonyadia.controller;

import com.enigma.tokonyadia.dto.request.ProductDto;
import com.enigma.tokonyadia.dto.response.ControllerResponse;
import com.enigma.tokonyadia.dto.response.PageResponseWrapper;
import com.enigma.tokonyadia.entity.Product;
import com.enigma.tokonyadia.service.ProductService;
import com.enigma.tokonyadia.utils.constant.ApiUrlConstant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrlConstant.PRODUCT)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> createNewProduct(@RequestBody Product product){
       return productService.create(product);
    }

    @GetMapping
    public ResponseEntity<?> getAllProduct(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                      @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                      @RequestParam(name = "sort-by", defaultValue = "name") String sortBy,
                                                      @RequestParam(name = "direction", defaultValue = "ASC") String direction,
                                                      @ModelAttribute ProductDto productDto
                                       ){
        return productService.getProductPerPage(page, size, sortBy, direction, productDto);
    }


    @GetMapping( "/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
       return productService.getById(id);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody String id) {
        return productService.update(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable String id) {
        productService.delete(id);
    }

    @GetMapping("/by-name")
    public List<Product> searchProductByName(@RequestParam(name = "product-name", required = false) String name, @RequestParam(name = "product-stock", required = false) Integer stock){
        return productService.findByName(name, stock);
    }

}
