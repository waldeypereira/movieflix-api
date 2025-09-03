package com.wfrocha.movieflix.controller;

import com.wfrocha.movieflix.controller.request.CategoryRequest;
import com.wfrocha.movieflix.controller.response.CategoryResponse;
import com.wfrocha.movieflix.entity.Category;
import com.wfrocha.movieflix.mapper.CategoryMapper;
import com.wfrocha.movieflix.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/movieflix/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        List<CategoryResponse> categories =
                CategoryMapper.toCategoryResponseList(categoryService.findAll());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable int id) {
        return categoryService.findById(id)
                .map(CategoryMapper::toCategoryResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> save(@RequestBody CategoryRequest request) {
        Category category = CategoryMapper.toCategory(request);
        Category savedCategory = categoryService.save(category);
        CategoryResponse response = CategoryMapper.toCategoryResponse(savedCategory);

        URI location = URI.create("/movieflix/category/" + savedCategory.getId());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable long id,
                                                   @RequestBody CategoryRequest request) {
        Category category = CategoryMapper.toCategory(request);
        category.setId(id);
        Category updatedCategory = categoryService.update(category);
        return ResponseEntity.ok(CategoryMapper.toCategoryResponse(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
