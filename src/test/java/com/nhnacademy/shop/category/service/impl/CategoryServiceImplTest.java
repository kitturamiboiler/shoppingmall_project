package com.nhnacademy.shop.category.service.impl;


import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;

import com.nhnacademy.shoppingmall.category.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("카테고리 목록 조회 테스트")
    void getCategoryList_Success() {
        List<Category> categoryList = List.of(new Category(1, "가구"), new Category(2, "의류"));
        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<Category> result = categoryService.getCategoryList();
        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
    }
    @Test
    @DisplayName("카테고리 수정 성공 테스트")
    void modifyCategory_Success() {
        String oldName = "오래된 거";
        String newName = "새 거";
        when(categoryRepository.findAll()).thenReturn(List.of(new Category(1,"다른 거")));
        categoryService.modifyCategory(oldName, newName);
        verify(categoryRepository).update(oldName, newName);
    }
    @Test
    @DisplayName("카테고리 수정 실패 -> 수정하려는 카테고리 이름이 empty일때 ")
    void modifyCategory_Failed_IsEmpty() {
        assertThrows(IllegalArgumentException.class, ()->
            categoryService.modifyCategory("기존", " ")
        );
    }
    @Test
    @DisplayName("카테고리 수정 실패 -> 중복일때")
    void modifyCategory_Failed_Duplicated() {
        String oldName = "공기";
        String newName = "산소";
        when(categoryRepository.findAll()).thenReturn(List.of(new Category(1, "산소")));
        assertThrows(IllegalStateException.class, () ->
                categoryService.modifyCategory(oldName, newName)
        );
    }
}
