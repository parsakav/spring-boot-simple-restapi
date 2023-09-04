package com.pgu.springboot.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Table(name = "tbl_rented")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Rented {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",unique = true,nullable = false)
    private User user;

    @OneToOne(mappedBy = "rented",optional = false)
    @JoinColumn(name = "ads_id")

    private Ads ads;

    @Temporal(TemporalType.DATE)
    private Date date;

}
