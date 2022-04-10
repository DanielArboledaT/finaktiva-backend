package com.finaktiva.ms.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "usuario_rol")
public class UsuarioRolEntity implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idRolUsuario;

    @NotNull
    private Integer idrol;

    @NotNull
    private Integer idUsuario;

    public Integer getIdRolUsuario() {
        return idRolUsuario;
    }

    public void setIdRolUsuario(Integer idRolUsuario) {
        this.idRolUsuario = idRolUsuario;
    }

    public Integer getIdrol() {
        return idrol;
    }

    public void setIdrol(Integer idrol) {
        this.idrol = idrol;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
