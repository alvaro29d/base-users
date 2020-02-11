package com.ado.base.users.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
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
    @NotNull
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
