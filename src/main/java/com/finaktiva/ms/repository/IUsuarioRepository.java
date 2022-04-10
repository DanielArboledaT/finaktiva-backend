package com.finaktiva.ms.repository;

import com.finaktiva.ms.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    Optional<UsuarioEntity> findByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);

}
