package com.ado.base.users.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = User.UNIQUE_USER_EMAIL, columnNames = {"email"}),
        @UniqueConstraint(name = User.UNIQUE_USER_NAME, columnNames = {"name"})
})
public class User {

    public static final String UNIQUE_USER_EMAIL = "unique_user_email";
    public static final String UNIQUE_USER_NAME = "unique_user_name";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "invalid.user.email.notEmpty")
    @Email
    private String email;

    @NotEmpty(message = "invalid.user.name.notEmpty")
    private String name;

    @PastOrPresent
    private LocalDate dateOfBirth;


    private String password;

    private ZonedDateTime mdt;
    private ZonedDateTime cdt;


}
