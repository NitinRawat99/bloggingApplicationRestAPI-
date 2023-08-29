package com.nitin.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nitin.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>
{
	
	

	Optional<User> findByEmail(String email);

	
}
