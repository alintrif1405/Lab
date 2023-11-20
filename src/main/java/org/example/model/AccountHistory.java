package org.example.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "account_histories")
public class AccountHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="old_lastname")
    private String old_lastname;

    @Column(name="old_firstname")
    private String old_firstname;

    @Column(name="old_email")
    private String old_email;

    @Column(name="old_password")
    private String old_password;

    @Enumerated(EnumType.STRING)
    @Column(name="old_role", length = 20)
    private ERole old_role;

    @Column(name="new_lastname")
    private String new_lastname;

    @Column(name="new_firstname")
    private String new_firstname;

    @Column(name="new_email")
    private String new_email;

    @Column(name="new_password")
    private String new_password;

    @Enumerated(EnumType.STRING)
    @Column(name="new_role", length = 20)
    private ERole new_role;

    @Column(name="date_modified")
    private LocalDate date_modified;

}
