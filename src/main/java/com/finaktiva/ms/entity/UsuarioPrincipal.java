package com.finaktiva.ms.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioPrincipal implements UserDetails {

    private String nombre;
    private String username;
    private String email;
    private String password;

    private Collection<? extends GrantedAuthority> autorities;

    public UsuarioPrincipal(String nombre, String username, String email, String password, Collection<? extends GrantedAuthority> autorities) {
        this.nombre = nombre;
        this.username = username;
        this.email = email;
        this.password = password;
        this.autorities = autorities;
    }

    public static UsuarioPrincipal build(UsuarioEntity usuario) {

        List<GrantedAuthority> autorities = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getRolNombre().name())).collect(Collectors.toList());

        return new UsuarioPrincipal(
                usuario.getNombre(), usuario.getUsername(), usuario.getEmail(),
                usuario.getPassword(), autorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return autorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
