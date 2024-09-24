package com.sahabuddin.blogappapis.services.impl;

import com.sahabuddin.blogappapis.entities.Category;
import com.sahabuddin.blogappapis.exceptions.ResourceNotFoundException;
import com.sahabuddin.blogappapis.payloads.CategoryDto;
import com.sahabuddin.blogappapis.repositories.CategoryRepository;
import com.sahabuddin.blogappapis.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "category id", categoryId));
        return this.convertCategoryToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return this.categoryRepository.findAll().stream().map(this::convertCategoryToCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.convertCategoryDtoToCategory(categoryDto);
        return this.convertCategoryToCategoryDto(this.categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "category id", categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        return this.convertCategoryToCategoryDto(this.categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "category id", categoryId));
        this.categoryRepository.delete(category);

    }

    private Category convertCategoryDtoToCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }

    private CategoryDto convertCategoryToCategoryDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }
}
