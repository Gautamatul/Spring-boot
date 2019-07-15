package com.example.learn.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.learn.model.ApplicationUser;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {

	@Query("SELECT u from ApplicationUser u where u.username = :username")
	public ApplicationUser findOne(@Param("username") String username);

	@Transactional
	public int deleteByusername(String username);
}
