package com.learn.electronic.store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private String categoryId;

    @NotBlank(message = "title is required !!")
    @Size(min = 4,message = "Atleast 4 character required !!")
    private String title;
    @NotBlank(message = "description is required !!")
    private String description;
    private String coverImage;


}
