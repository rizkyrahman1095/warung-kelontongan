package com.enigma.tokonyadia.utils.spesification;

import com.enigma.tokonyadia.dto.request.CustomerSearchDTO;
import com.enigma.tokonyadia.entity.Customer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomerSpesification {
    public static Specification<Customer> getSpecification(CustomerSearchDTO customerSearchDTO){
        return (root, query, criteriaBuilder) -> {
            //intrface atau fungsi yang menghasilkan nilai boolean
            List<Predicate> predicates = new ArrayList<>();
            if (customerSearchDTO.getCustomerFullName() != null){
                Predicate customerFullNamepredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + customerSearchDTO.getCustomerFullName().toLowerCase() + "%");
                predicates.add(customerFullNamepredicate);
            }
            if (customerSearchDTO.getCustomerAddress() != null){
                Predicate customerAddressPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + customerSearchDTO.getCustomerAddress().toLowerCase() + "%");
                predicates.add(customerAddressPredicate);
            }
//            if (customerSearchDTO.getCustomerBirthDate() != null){
//                Predicate customerBirthDate = criteriaBuilder.equal(root.get("birthDate"), customerSearchDTO.getCustomerBirthDate());
//                predicates.add(customerBirthDate);
//            }
            if (!(customerSearchDTO.getCustomerBirthDate() == null)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String modifiedDateFormated = sdf.format(customerSearchDTO.getCustomerBirthDate());

                final Predicate createdDataPredicate = criteriaBuilder.equal(criteriaBuilder.function("TO_CHAR",
                        String.class, root.get("birthDate"), criteriaBuilder.literal("yyyy-MM-dd")), modifiedDateFormated);
                predicates.add(createdDataPredicate);
            }

            Predicate[] arrayPredicate = predicates.toArray(new Predicate[predicates.size()]);
            return criteriaBuilder.and(arrayPredicate);

        };
    }
}
