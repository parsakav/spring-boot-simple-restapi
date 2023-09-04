package com.pgu.springboot.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Table(name = "tbl_roles")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "role_name",nullable = false,unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles",cascade = CascadeType.ALL)
  private   Set<User> users;

}
