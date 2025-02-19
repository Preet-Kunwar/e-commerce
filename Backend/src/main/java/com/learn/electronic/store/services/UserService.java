package com.learn.electronic.store.services;

import com.learn.electronic.store.dtos.PageableResponse;
import com.learn.electronic.store.dtos.UserDto;

import java.util.List;

public interface UserService {
    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto,String userId);

    //delete
    void deleteUser(String userId);

    //get all users
    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get one user by id
    UserDto getUserById(String userId);

    // get one user by email
    UserDto getUserByEmail(String email);

    //search user
    List<UserDto> searchUser(String keyword);

}
