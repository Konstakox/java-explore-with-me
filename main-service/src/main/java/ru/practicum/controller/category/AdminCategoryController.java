package ru.practicum.controller.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.service.category.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewCategoryDto createCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("Запрос на создание категории {}", newCategoryDto);
        return categoryService.createCategory(newCategoryDto);
    }

    @DeleteMapping("{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable @Positive Integer catId) {
        log.info("Запрос на удаление категории с id {}", catId);
        categoryService.deleteCategoryById(catId);
    }

    @PatchMapping("{catId}")
    @ResponseStatus(HttpStatus.OK)
    public NewCategoryDto updateCategory(@PathVariable @Positive Integer catId,
                                         @RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("Запрос на изменение категории с id {}, {}", catId, newCategoryDto);
        return categoryService.updateCategory(catId, newCategoryDto);
    }
}
