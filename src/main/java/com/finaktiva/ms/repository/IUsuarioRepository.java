package com.finaktiva.ms.repository;

import com.finaktiva.ms.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    Optional<UsuarioEntity> findByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);

    @Query(nativeQuery = true, value = "UPDATE bmc5uciucq7njjeum5ju.usuarios "
            + "SET "
            + "nombre = :nombre, "
            + "apellido = :apellido, "
            + "cedula = :cedula, "
            + "email = :email, "
            + "password = :password, "
            + "username = :username "
            + "WHERE idusuario = :idusuario")
    public void actualizarUsuario(@Param("nombre") String nombre, @Param("cedula") String cedula,
                                  @Param("email") String email, @Param("password") String password,
                                  @Param("username") String username, @Param("idusuario") Integer idusuario);

}
