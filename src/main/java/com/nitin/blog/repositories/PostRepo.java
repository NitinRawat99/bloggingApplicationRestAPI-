package com.nitin.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nitin.blog.entities.Category;
import com.nitin.blog.entities.Post;
import com.nitin.blog.entities.User;
import com.nitin.blog.payloads.PostDto;

public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	List<Post> findByTitleContaining(String Title);
}
