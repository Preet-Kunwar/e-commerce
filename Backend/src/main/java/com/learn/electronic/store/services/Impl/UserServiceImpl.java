package com.learn.electronic.store.services.Impl;

import com.learn.electronic.store.config.AppConstant;
import com.learn.electronic.store.dtos.PageableResponse;
import com.learn.electronic.store.dtos.UserDto;
import com.learn.electronic.store.entities.Role;
import com.learn.electronic.store.entities.User;
import com.learn.electronic.store.exceptions.ResourceNotFoundException;
import com.learn.electronic.store.helper.Helper;
import com.learn.electronic.store.repositories.RoleRepository;
import com.learn.electronic.store.repositories.UserRepository;
import com.learn.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Value("${user.profile.image.path}")
    private String imagePath;
    @Override
    public UserDto createUser(UserDto userDto) {
        //Generate UserId
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        User user=dtoToEntity(userDto);

        //Password encode
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //assign NORMAL role to user
        // by default jo bhi api se user banega usko NORMAL user bana denge

        Role role=new Role();
        role.setRoleId(UUID.randomUUID().toString());
        role.setName(AppConstant.ROLE_NORMAL);
        Role roleNormal = roleRepository.findByName(AppConstant.ROLE_NORMAL).orElse(role);
        user.setRoles(List.of(roleNormal));

        User savedUser = userRepository.save(user);
        UserDto newDto=entityToDto(savedUser);
        return newDto;
    }



    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());


        //save
        User updatedUser = userRepository.save(user);
        UserDto updatedDto = entityToDto(updatedUser);

        return updatedDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));

        // delete user profile image
        String fullPathName= imagePath + user.getImageName();

        try {
            Path path= Paths.get(fullPathName);
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // delete user
        userRepository.delete(user);
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

        //sort
        Sort sort= (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber-1,pageSize,sort);

        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);

        return response;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with given email"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }





    private User dtoToEntity(UserDto userDto) {
       /* User user = User.builder()
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .about(userDto.getAbout())
                .gender(userDto.getAbout())
                .imageName(userDto.getImageName()).build();*/
        return mapper.map(userDto,User.class);
    }
    private UserDto entityToDto(User savedUser) {
       /* UserDto userDto = UserDto.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .about(savedUser.getAbout())
                .gender(savedUser.getAbout())
                .imageName(savedUser.getImageName()).build();*/
        return mapper.map(savedUser,UserDto.class);
    }

}
