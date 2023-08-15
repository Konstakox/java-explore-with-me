package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.model.Category;

@UtilityClass
public class CategoryMapper {

    public Category toCategory(NewCategoryDto newCategoryDto) {
        return Category.builder()
                .name(newCategoryDto.getName())
                .build();
    }

    public NewCategoryDto toNewCategoryDto(Category category) {
        return NewCategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
