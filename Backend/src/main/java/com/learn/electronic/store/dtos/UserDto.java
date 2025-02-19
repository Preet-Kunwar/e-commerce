package com.learn.electronic.store.dtos;

import com.learn.electronic.store.entities.Role;
import com.learn.electronic.store.validation.ImageNameValid;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min=3,max = 40,message = "Invalid User Name")
   @Schema( name = "username", accessMode = Schema.AccessMode.READ_ONLY, description = "user name of new user !!")
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$",message = "Invalid User Email")
    @NotBlank(message = "Email is Required")
    private String email;

    @NotBlank(message = "Enter Valid Password")
    private String password;

    @Size(min = 4,max = 6,message = "Enter Valid Gender")
    private String gender;

    @NotBlank(message = "Write something about yourself !!")
    private String about;

    @ImageNameValid
    private String imageName;

    private List<RoleDto> roles;

}
