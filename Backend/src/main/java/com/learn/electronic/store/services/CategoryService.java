package com.learn.electronic.store.services;

import com.learn.electronic.store.dtos.CategoryDto;
import com.learn.electronic.store.dtos.PageableResponse;

public interface CategoryService {
    //create
    CategoryDto create(CategoryDto categoryDto);

    // update
    CategoryDto update(CategoryDto categoryDto,String categoryId);

    //delete
    void delete(String categoryId);

    // get all category
    PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir);

    // get single category
    CategoryDto get(String categoryId);
}
