package com.pgu.springboot.repository;

import com.pgu.springboot.entity.Ads;
import com.pgu.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdsRepository extends JpaRepository<Ads,Long> {
}
