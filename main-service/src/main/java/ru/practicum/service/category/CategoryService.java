package ru.practicum.service.category;

import ru.practicum.dto.category.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    NewCategoryDto createCategory(NewCategoryDto newCategoryDto);

    void deleteCategoryById(Integer catId);

    NewCategoryDto updateCategory(Integer catId, NewCategoryDto newCategoryDto);

    List<NewCategoryDto> getAllCategories(Integer from, Integer size);

    NewCategoryDto getCategoriesById(Integer catId);
}
