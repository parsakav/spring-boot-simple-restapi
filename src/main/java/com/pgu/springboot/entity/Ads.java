package com.pgu.springboot.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Table(name = "tbl_ads")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "confirmation",nullable = false)
    private boolean confirm;
    @Column(name = "title",nullable = false)

    private String title;
    @Column(name = "price",nullable = false)

    private double price;
    @Column(name = "description",nullable = false)

    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id",nullable = false)
    private User user;

    @OneToOne(optional = true)
    private Rented rented;

    @OneToMany(mappedBy = "ads")

    private Set<Comments> commentsSet;

    @Enumerated(EnumType.STRING)
  private AdsLimit adsLimit;

    private Date date;

}
