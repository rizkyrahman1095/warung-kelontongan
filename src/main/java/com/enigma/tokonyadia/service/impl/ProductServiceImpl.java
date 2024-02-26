package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.dto.request.CustomerDto;
import com.enigma.tokonyadia.dto.request.ProductDto;
import com.enigma.tokonyadia.dto.response.ControllerResponse;
import com.enigma.tokonyadia.entity.Customer;
import com.enigma.tokonyadia.entity.Product;
import com.enigma.tokonyadia.repository.ProductRepository;
import com.enigma.tokonyadia.security.JwtUtils;
import com.enigma.tokonyadia.service.ProductService;
import com.enigma.tokonyadia.utils.constant.ApiUrlConstant;
import com.enigma.tokonyadia.utils.spesification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final JwtUtils jwtUtils;

    private final ProductRepository productRepository;

    public ProductServiceImpl(JwtUtils jwtUtils, ProductRepository productRepository) {
        this.jwtUtils = jwtUtils;
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<?> create(Product product) {
        Product productSave = productRepository.save(product);
        ControllerResponse<ProductDto> response = ControllerResponse.<ProductDto>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message(ApiUrlConstant.CREATE)
                .data(productToProductDto(productSave))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public ResponseEntity<?> getById(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            ControllerResponse<ProductDto> response = ControllerResponse.<ProductDto>builder()
                    .status(HttpStatus.OK.getReasonPhrase())
                    .message(ApiUrlConstant.GETDATA)
                    .data(productToProductDto(optionalProduct.get()))
                    .build();
            return ResponseEntity.ok(response);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found");
    }

    @Override
    public ResponseEntity<?> update(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        validateId(id);
        ControllerResponse<ProductDto> responseUpdate = ControllerResponse.<ProductDto>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message(ApiUrlConstant.UPDATE)
                .data(productToProductDto(optionalProduct.get()))
                .build();
        return ResponseEntity.ok(responseUpdate);
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
    public Page<Product> getProductPerPage(Pageable pageable, ProductDto productDto) {
        Specification<Product> productSpecification = ProductSpecification.getSpecification(productDto);
        return productRepository.findAll(productSpecification, pageable);
    }

    private Optional<Product> validateId(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
        return optionalProduct;
    }

    public ProductDto productToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProductName(product.getName());
        productDto.setProductStock(product.getStock());
        productDto.setProductPrice(product.getPrice());
        return productDto;
    }
}
