package com.heeseung.community1.security;

import com.heeseung.community1.domain.Account;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails {
    private Account account;

    public PrincipalDetails(Account account) {
        this.account = account;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> role = new ArrayList<>();

        role.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return String.valueOf(account.getRole());
            }
        });
        return role;
    }

    public String getRole() {
        if (account != null) {
            switch (account.getRole()) {
                case 1:
                    return "user";
                case 2:
                    return "mod";
                case 3:
                    return "admin";
                default:
                    return "anonymousUser";
            }
        } else {
            return "anonymousUser";
        }
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
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
