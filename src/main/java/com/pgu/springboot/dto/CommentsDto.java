package com.pgu.springboot.dto;

import com.pgu.springboot.entity.Ads;
import com.pgu.springboot.entity.User;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {
    private User user;
    private Ads ads;
    private String comment;
}
