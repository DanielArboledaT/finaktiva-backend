package com.finaktiva.ms.entity;

import com.finaktiva.ms.security.enums.RolNombre;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles")
public class RolEntity implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idrol;

    @Column(name = "rolnombre")
    @Enumerated(EnumType.STRING)
    private RolNombre rolNombre;

    public Integer getIdrole() {
        return idrol;
    }

    public void setIdrole(Integer idrole) {
        this.idrol = idrole;
    }

    public RolNombre getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(RolNombre rolNombre) {
        this.rolNombre = rolNombre;
    }
}
