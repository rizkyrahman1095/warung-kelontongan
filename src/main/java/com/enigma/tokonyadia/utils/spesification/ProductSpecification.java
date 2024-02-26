package com.enigma.tokonyadia.utils.spesification;

import com.enigma.tokonyadia.dto.request.ProductDto;
import com.enigma.tokonyadia.entity.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification{
    public static Specification<Product> getSpecification(ProductDto productDto){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (productDto.getProductName() != null){
              predicates.add(criteriaBuilder.like(root.get("name"), "%" + productDto.getProductName() + "%"));
            }
            if (productDto.getProductPrice() != null){
                predicates.add(criteriaBuilder.equal(root.get("price"), productDto.getProductPrice()));
            }
            if (productDto.getProductStock() != null){
                predicates.add(criteriaBuilder.equal(root.get("stock"), productDto.getProductStock()));
            }
            Predicate[] predicates1 = predicates.toArray(new Predicate[predicates.size()]);
            return criteriaBuilder.and(predicates1);
        });
    }
}
