package com.finaktiva.ms.repository;

import com.finaktiva.ms.entity.RolEntity;
import com.finaktiva.ms.security.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRolrepository extends JpaRepository<RolEntity, Integer> {

    Optional<RolEntity> findByRolNombre(RolNombre rolNombre);

}
