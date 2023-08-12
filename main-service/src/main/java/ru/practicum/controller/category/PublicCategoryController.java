package ru.practicum.controller.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.service.category.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Validated
@Slf4j
public class PublicCategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<NewCategoryDto> getAllCategories(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Запрос категории с параметрами: from {}, size {}", from, size);
        return categoryService.getAllCategories(from, size);
    }

    @GetMapping("{catId}")
    public NewCategoryDto getCategoriesById(@PathVariable @Positive Integer catId) {
        log.info("Запрос категории с id={}", catId);
        return categoryService.getCategoriesById(catId);
    }
}
