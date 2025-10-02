package com.sec.security.model.dto;

import com.sec.security.model.Role;
import com.sec.security.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Builder
//this class acts as a proxy for user entity
public class UserDetail implements UserDetails {
    private Long id;
    private String fullName;
    private String username;
    private String password;
    private Role role;
    private LocalDate creationDate;

    //mapper method for decoupling The user from spring security
    public static UserDetail buildFromUser(User user) {
        return UserDetail.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUserName())
                .password(user.getPassword())
                .role(user.getRole())
                .creationDate(user.getCreationDate())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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

}
