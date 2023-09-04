package com.pgu.springboot.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Table(name = "tbl_users")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "occupation",nullable = false)
    private String occupation;

    @Column(name = "username",nullable = false,unique = false)

    private String username;
    @Column(name = "password",nullable = false)


    private String password;
    @Column(name = "encryptedpassword",nullable = false)


    private String encryptedPassword;
    @Column(name = "firstname",nullable = false)


    private String firstName;
    @Column(name = "lastname",nullable = false)


    private String lastName;
    @Column(name = "userid",nullable = false,unique = true)


    private String userId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name="user_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private Set<Role> roles;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)


    private Set<Ads> ads;
    @OneToMany(mappedBy = "user")

    private Set<Rented> rented;

    @OneToMany(mappedBy = "user")
    private Set<Comments> comment;



}
