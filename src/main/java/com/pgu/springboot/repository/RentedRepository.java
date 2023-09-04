package com.pgu.springboot.repository;

import com.pgu.springboot.entity.Rented;
import com.pgu.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentedRepository extends JpaRepository<Rented,Long> {
}
