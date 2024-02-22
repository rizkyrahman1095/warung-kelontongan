package com.enigma.tokonyadia.service;


import com.enigma.tokonyadia.entity.Role;
import com.enigma.tokonyadia.utils.constant.Eroll;

public interface RoleService {
    Role getOrSave(Eroll eroll);
}
