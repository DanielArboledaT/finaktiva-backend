package com.finaktiva.ms.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "username")
        })
//@NamedQuery(name="usuario.findAll", query="SELECT u FROM usuario u")
public class UsuarioEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idusuario;

    @Column()
    private String nombre;

    @Column()
    private String apellido;

    @Column()
    private String cedula;

    @Column()
    private String estado;

    @Column()
    private String email;

    @Column()
    private String password;

    @Column()
    private String username;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(	name = "usuario_rol",
            joinColumns = @JoinColumn(name = "idusuario"),
            inverseJoinColumns = @JoinColumn(name = "idrol"))
    private Set<RolEntity> roles = new HashSet<>();

    public UsuarioEntity() {
    }

    public UsuarioEntity(String nombre, String email, String password, String username) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public Set<RolEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolEntity> roles) {
        this.roles = roles;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
