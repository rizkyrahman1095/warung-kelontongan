package com.enigma.tokonyadia.service;

import com.enigma.tokonyadia.dto.request.ProductSearchDTO;
import com.enigma.tokonyadia.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product create(Product product);
    List<Product> getAll();
    Product getById(String id);
    Product update(Product product);
    void delete(String id);

    List<Product> findByName(String name, Integer stock);

    Page<Product> getProductPerPage(Pageable pageable, ProductSearchDTO productSearchDTO);
}
