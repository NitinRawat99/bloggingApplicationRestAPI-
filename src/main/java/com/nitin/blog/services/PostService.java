package com.nitin.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nitin.blog.entities.Post;
import com.nitin.blog.payloads.PostDto;
import com.nitin.blog.payloads.PostResponse;


public interface PostService 
{
	
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	// update
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	// delete 
	
	void deletePost(Integer postId);
	
	// get All posts
	PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
	
	//get single post
	PostDto getPostById(Integer postId);
	
	// getAllPostByCategory
	
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	// get all posts by user
	List<PostDto> getPostsByUser(Integer userId);
	
	
	// search posts
	List<PostDto> searchPosts(String keywords);

}
