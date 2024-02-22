package com.enigma.tokonyadia.controller;

import com.enigma.tokonyadia.dto.request.ProductSearchDTO;
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
        Product product1 = productService.create(product);
        ControllerResponse<Product> response = ControllerResponse.<Product>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Successfully create new product")
                .data(product1)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public PageResponseWrapper<Product> getAllProduct(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                      @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                      @RequestParam(name = "sort-by", defaultValue = "name") String sortBy,
                                                      @RequestParam(name = "direction", defaultValue = "ASC") String direction,
                                                      @ModelAttribute ProductSearchDTO productSearchDTO
                                       ){
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> resultPage = productService.getProductPerPage(pageable, productSearchDTO);
        return new PageResponseWrapper<>(resultPage);
    }


    @GetMapping( "/{id}")
    public Product getProductById(@PathVariable String id) {
        Product product = productService.getById(id);
        return product;
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        Product updatedProduct = productService.update(product);
        return updatedProduct;
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
