package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.dto.request.ProductSearchDTO;
import com.enigma.tokonyadia.entity.Product;
import com.enigma.tokonyadia.repository.ProductRepository;
import com.enigma.tokonyadia.service.ProductService;
import com.enigma.tokonyadia.utils.spesification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) return optionalProduct.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }

    @Override
    public Product update(Product product) {
        validateId(product.getId());
        return create(product);
    }

    @Override
    public void delete(String id) {
        Optional<Product> optionalProduct = validateId(id);
        Product product = optionalProduct.get();
        productRepository.delete(product);
    }

    @Override
    public List<Product> findByName(String name, Integer stock) {
        return productRepository.findProductByNameOrStock(name, stock);
    }

    @Override
    public Page<Product> getProductPerPage(Pageable pageable, ProductSearchDTO productSearchDTO) {
        Specification<Product> productSpecification = ProductSpecification.getSpecification(productSearchDTO);
        return productRepository.findAll(productSpecification, pageable);
    }

    private Optional<Product> validateId(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
        return optionalProduct;
    }
}
