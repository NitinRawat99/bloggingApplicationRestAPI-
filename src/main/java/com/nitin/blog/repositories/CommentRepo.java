package com.nitin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nitin.blog.entities.Comment;
public interface CommentRepo extends JpaRepository<Comment,Integer>{

}
