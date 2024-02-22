package com.enigma.tokonyadia.service.impl;


import com.enigma.tokonyadia.entity.Role;
import com.enigma.tokonyadia.repository.RoleRepository;
import com.enigma.tokonyadia.service.RoleService;
import com.enigma.tokonyadia.utils.constant.Eroll;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;


    @Override
    public Role getOrSave(Eroll eroll) {

        Optional<Role> optionalRole = repository.findByEroll(eroll);
        if (optionalRole.isPresent())
            return optionalRole.get();
        Role roleInstace = Role.builder()
                .eroll(eroll)
                .build();
        return repository.save(roleInstace);
    }

}
