package com.enigma.tokonyadia.service;

import com.enigma.tokonyadia.dto.request.ProductDto;
import com.enigma.tokonyadia.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<?> create(Product product);
    List<Product> getAll();
    ResponseEntity<?> getById(String id);
    ResponseEntity<?> update(String id);
    void delete(String id);

    List<Product> findByName(String name, Integer stock);

    ResponseEntity<?> getProductPerPage(Integer page, Integer size, String sortBy, String direction, ProductDto productDto);
}
