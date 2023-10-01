package com.epam.esm.authorizationserver.user.dto;

import com.epam.esm.authorizationserver.common.validation.OnCreate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Min(value = 1, message = "ID should be greater than 0")
    private Integer id;

    @NotEmpty(groups = OnCreate.class, message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @NotEmpty(groups = OnCreate.class, message = "Lastname should not be empty")
    @Size(min = 2, max = 30, message = "lastname should be between 2 and 30 characters")
    private String lastName;

    @Size(min=3, max=320)
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    private String role;
}
