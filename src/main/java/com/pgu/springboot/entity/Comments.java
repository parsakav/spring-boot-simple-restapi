package com.pgu.springboot.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "tbl_comments")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "text",nullable = false)
    private String text;
    @ManyToOne
    @JoinColumn(name = "ads_id")
    private Ads ads;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
