package com.pgu.springboot.service;

import com.pgu.springboot.dto.CommentsDto;
import com.pgu.springboot.entity.Ads;
import com.pgu.springboot.entity.Comments;
import com.pgu.springboot.entity.User;
import com.pgu.springboot.repository.CommentRepository;
import com.pgu.springboot.security.SecurityConstant;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Repository
@Transactional
public class CommentsService {
    private final UserService userService;
    private final AdsService adsService;

    private final CommentRepository commentRepository;

    public CommentsService(CommentRepository commentRepository, AdsService adsService, UserService userService) {
        this.commentRepository = commentRepository;
        this.adsService = adsService;
        this.userService = userService;
    }

    public CommentsDto save(Comments comments) {
        Comments r = commentRepository.save(Objects.requireNonNull(comments));
        CommentsDto commentsDto = new CommentsDto();
        BeanUtils.copyProperties(r, commentsDto);
        return commentsDto;
    }

    public CommentsDto comment(String authorization, Long ads_id, String text) {
        String username = Jwts.parserBuilder().setSigningKey(SecurityConstant.getSigningKey()).build()
                .parseClaimsJws(authorization.replace("Bearer", "").trim()).getBody().getSubject();

        Optional<User> user = userService.findUserByName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);

        }
        Ads ads = adsService.getAds(ads_id);
        System.out.println(ads);
        System.out.println(ads.getRented().getUser().getId());
        if (ads.getRented() == null || !ads.getRented().getUser().equals(user.get())) {
            throw new IllegalStateException("This user can't add comment");
        } else if (ads.getCommentsSet().stream().anyMatch(comments -> {
            if (comments.getUser().getUsername().equals(username)) {
                return true;
            }
            return false;

        })) {
            throw new IllegalStateException("Duplicate comment ");

        }
        Comments comment = new Comments();
        comment.setUser(user.get());
        comment.setAds(ads);
        comment.setText(text);

        return this.save(comment);

    }

}
