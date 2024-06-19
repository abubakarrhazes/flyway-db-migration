package com.example.store_backend.domain;


import com.example.store_backend.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(name = "user_name", nullable = false)
    private String userName;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = true)
    private String lastName;
    @Column(name = "other_name", nullable = false)
    private String otherName;
    @Column(name = "email", nullable = false, unique = true)
    private  String email;

    @Column(name = "password", nullable = false, unique = true)
    private  String password;
    @Column(name = "address" , nullable = false)
    private  String address;
    @Column(name = "phone_number" , nullable = false)
    private  String phoneNumber;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    @Generated(GenerationTime.ALWAYS)
    private Date updatedAt;

}
