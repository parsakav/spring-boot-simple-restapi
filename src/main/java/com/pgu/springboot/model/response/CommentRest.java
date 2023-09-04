package com.pgu.springboot.model.response;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentRest {
    private String userId;
    private String comment;

}
