package com.nitin.blog.services;

import java.util.List;

import com.nitin.blog.payloads.UserDto;

public interface UserService 
{
	UserDto registerNewUser(UserDto user);
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUser();
	void deleteUser(Integer userId);
	

}
