package io.github.vendas.domain.entities;

import io.github.vendas.domain.RoleCollection;
import io.github.vendas.domain.enums.UserRoles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 5, max = 15, message = "Nome de usuário deve ter entre 5 e 15 caracteres!")
    @NotEmpty(message = "O Nome de usuário é obrigatório!")
    @Column(nullable = false, unique = true, length = 15)
    private String nomeUsuario;

    @Length(min = 8, message = "A senha deve ter no mínimo 8 caracteres!")
    @NotEmpty(message = "A Senha é obrigatória!")
    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoles role;

    public Usuario(String nomeUsuario, String senha, UserRoles role) {
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new RoleCollection(this.role));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.nomeUsuario;
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
