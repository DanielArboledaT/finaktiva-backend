package com.finaktiva.ms.service;

import com.finaktiva.ms.entity.RolEntity;
import com.finaktiva.ms.repository.IRolrepository;
import com.finaktiva.ms.security.enums.RolNombre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    private IRolrepository rolrepository;

    public Optional<RolEntity> getByRolNombre(RolNombre rolNombre) {

        return rolrepository.findByRolNombre(rolNombre);

    }

}
