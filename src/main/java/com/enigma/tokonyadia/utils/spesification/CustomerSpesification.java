package com.enigma.tokonyadia.utils.spesification;

import com.enigma.tokonyadia.dto.request.CustomerDto;
import com.enigma.tokonyadia.entity.Customer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomerSpesification {
    public static Specification<Customer> getSpecification(CustomerDto customerDto){
        return (root, query, criteriaBuilder) -> {
            //intrface atau fungsi yang menghasilkan nilai boolean
            List<Predicate> predicates = new ArrayList<>();
            if (customerDto.getName() != null){
                Predicate customerFullNamepredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + customerDto.getName().toLowerCase() + "%");
                predicates.add(customerFullNamepredicate);
            }
            if (customerDto.getAddress() != null){
                Predicate customerAddressPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + customerDto.getAddress().toLowerCase() + "%");
                predicates.add(customerAddressPredicate);
            }
//            if (customerSearchDTO.getCustomerBirthDate() != null){
//                Predicate customerBirthDate = criteriaBuilder.equal(root.get("birthDate"), customerSearchDTO.getCustomerBirthDate());
//                predicates.add(customerBirthDate);
//            }
            if (!(customerDto.getBirthDate() == null)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String modifiedDateFormated = sdf.format(customerDto.getBirthDate());

                final Predicate createdDataPredicate = criteriaBuilder.equal(criteriaBuilder.function("TO_CHAR",
                        String.class, root.get("birthDate"), criteriaBuilder.literal("yyyy-MM-dd")), modifiedDateFormated);
                predicates.add(createdDataPredicate);
            }

            Predicate[] arrayPredicate = predicates.toArray(new Predicate[predicates.size()]);
            return criteriaBuilder.and(arrayPredicate);

        };
    }
}
