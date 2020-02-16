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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NotEmpty(message = "invalid.user.email.notEmpty")
    @Email
    @Column(unique = true)
    private String email;

//    @NotNull(message = "invalid.user.fullName")
    private String fullName;
    @PastOrPresent
    private LocalDate dateOfBirth;

    private String password;

    private ZonedDateTime mdt;
    private ZonedDateTime cdt;


}
