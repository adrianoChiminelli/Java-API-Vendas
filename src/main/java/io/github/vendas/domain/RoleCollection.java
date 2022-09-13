package io.github.vendas.domain;

import io.github.vendas.domain.enums.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@AllArgsConstructor
public class RoleCollection implements GrantedAuthority {

    private UserRoles role;

    @Override
    public String getAuthority() {
        return this.role.toString();
    }
}
