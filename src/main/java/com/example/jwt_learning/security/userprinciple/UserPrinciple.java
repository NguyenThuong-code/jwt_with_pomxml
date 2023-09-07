package com.example.jwt_learning.security.userprinciple;

import com.example.jwt_learning.models.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrinciple implements UserDetails {
    private Long id;
    private String name;
    private String userName;
    private String email;
    @JsonIgnore
    private String password;
    private String avatar;
    private  Collection<? extends  GrantedAuthority> roles;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public UserPrinciple(Long id, String name, String userName, String email, String password, String avatar, Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.roles = roles;
    }
    public static UserPrinciple build(Users users){
        List<GrantedAuthority> authorities = users.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
    return new UserPrinciple(
            users.getId(),
            users.getName(),
            users.getUsername(),
            users.getEmail(),
            users.getPassword(),
            users.getAvatar(),
            authorities
    );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
}
