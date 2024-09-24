package com.sahabuddin.blogappapis.services;

import com.sahabuddin.blogappapis.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto getCategory(Long categoryId);

    List<CategoryDto> getAllCategories();

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);

    void deleteCategory(Long categoryId);
}
