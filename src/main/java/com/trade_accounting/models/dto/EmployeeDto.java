package com.trade_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto implements UserDetails {

    private Long id;

    private String lastName;

    private String firstName;

    private String middleName;

    private String sortNumber;

    private String phone;

    @Pattern(regexp = "([0-9]+){12}")
    private String inn;

    private String description;

    private String email;

    private String password;

    private DepartmentDto departmentDto;

    private PositionDto positionDto;

    private Set<RoleDto> roleDto;

    private ImageDto imageDto;

    public EmployeeDto(Long id,
                       String lastName,
                       String firstName,
                       String middleName,
                       String sortNumber,
                       String phone,
                       @Pattern(regexp = "([0-9]+){12}") String inn,
                       String description,
                       String email,
                       String password) {

        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.sortNumber = sortNumber;
        this.phone = phone;
        this.inn = inn;
        this.description = description;
        this.email = email;
        this.password = password;
    }


    @Override
    public boolean isEnabled() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public String getUsername() { return email; }

    @Override
    public String getPassword() { return password; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleDto;
    }

}