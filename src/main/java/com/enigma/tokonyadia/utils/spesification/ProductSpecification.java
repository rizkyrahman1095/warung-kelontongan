package com.enigma.tokonyadia.utils.spesification;

import com.enigma.tokonyadia.dto.request.ProductSearchDTO;
import com.enigma.tokonyadia.entity.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification{
    public static Specification<Product> getSpecification(ProductSearchDTO productSearchDTO){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (productSearchDTO.getProductName() != null){
              predicates.add(criteriaBuilder.like(root.get("name"), "%" + productSearchDTO.getProductName() + "%"));
            }
            if (productSearchDTO.getProductPrice() != null){
                predicates.add(criteriaBuilder.equal(root.get("price"), productSearchDTO.getProductPrice()));
            }
            if (productSearchDTO.getProductStock() != null){
                predicates.add(criteriaBuilder.equal(root.get("stock"), productSearchDTO.getProductStock()));
            }
            Predicate[] predicates1 = predicates.toArray(new Predicate[predicates.size()]);
            return criteriaBuilder.and(predicates1);
        });
    }
}
