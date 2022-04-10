package com.finaktiva.ms.repository;

import com.finaktiva.ms.entity.UsuarioRolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUsuarioRolRepository extends JpaRepository<UsuarioRolEntity, Integer> {

    @Query(nativeQuery = true, value = "UPDATE bmc5uciucq7njjeum5ju.usuario_rol "
            + "SET "
            + "idrol = :idrol, "
            + "WHERE idusuario = :idusuario")
    public void actualizarUsuarioRol(@Param("idrol") Integer idrol, @Param("idusuario") Integer idusuario);

}
