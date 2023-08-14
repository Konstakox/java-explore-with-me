package ru.practicum.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.exeption.MyCategoryNotEmpty;
import ru.practicum.exeption.MyNotFoundException;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public NewCategoryDto createCategory(NewCategoryDto newCategoryDto) {
        Category newCategory = categoryRepository.save(CategoryMapper.toCategory(newCategoryDto));
        return CategoryMapper.toNewCategoryDto(newCategory);
    }

    @Override
    public void deleteCategoryById(Integer catId) {
        categoryRepository.findById(catId)
                .orElseThrow(() -> new MyNotFoundException("Категория не найдена с id:" + catId));
        Event result = eventRepository.findBycategoryId(catId);
        if (result != null) {
            throw new MyCategoryNotEmpty("Категория не пустая с id:" + catId);
        }
        categoryRepository.deleteById(catId);
    }

    @Override
    public NewCategoryDto updateCategory(Integer catId, NewCategoryDto newCategoryDto) {
        Category updateCategory = CategoryMapper.toCategory(newCategoryDto);
        updateCategory.setId(catId);
        return CategoryMapper.toNewCategoryDto(categoryRepository.save(updateCategory));
    }

    @Override
    public List<NewCategoryDto> getAllCategories(Integer from, Integer size) {
        PageRequest page = PageRequest.of(from, size);
        return categoryRepository.findAll(page).stream()
                .map(CategoryMapper::toNewCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public NewCategoryDto getCategoriesById(Integer catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new MyNotFoundException("Категория не найдена с id:" + catId));
        return CategoryMapper.toNewCategoryDto(category);
    }
}
