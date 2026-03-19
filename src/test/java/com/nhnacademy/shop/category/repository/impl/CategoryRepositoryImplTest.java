package com.nhnacademy.shop.category.repository.impl;


import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.config.RootConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringJUnitConfig(classes = {RootConfig.class})
public class CategoryRepositoryImplTest {
    @Autowired
    private CategoryRepository categoryRepository;
    @Test
    @DisplayName("카테고리 저장 및 전체 조회 테스트")
    void saveAndFindAl_Success() {
        String categoryName = "가전제품";
        int result = categoryRepository.save(categoryName);
        List<Category> categories = categoryRepository.findAll();
        assertEquals(1, result, " 성공해야 함");
        assertTrue(categories.stream().anyMatch(c->c.getCategoryName().equals(categoryName)),
                "저장한 카테고리 이름이 목록에 있어야 함");
    }
    @Test
    @DisplayName("카테고리 이름 수정 테스트")
    void update_Success() {
        String oldName = "test_game";
        String newName = "test_game123";
        categoryRepository.save(oldName);

        int result = categoryRepository.update(oldName, newName);
        List<Category> categories = categoryRepository.findAll();

        assertEquals(1, result, "수정이 성공해야 합니다.");
        assertTrue(categories.stream().anyMatch(c -> c.getCategoryName().equals(newName)),
                "새로운 이름으로 수정되어야 합니다.");
        assertFalse(categories.stream().anyMatch(c -> c.getCategoryName().equals(oldName)),
                "기존의 이름은 목록에서 제외되어야 합니다.");
    }
    @Test
    @DisplayName("카테고리 삭제 테스트")
    void delete_Success() {
        String categoryName = "제거";
        categoryRepository.save(categoryName);
        int result = categoryRepository.delete(categoryName);
        List<Category> categories = categoryRepository.findAll();
        assertEquals(1, result, "delete가 성공해야 합니다.");
        assertFalse(categories.stream().anyMatch(c -> c.getCategoryName().equals(categoryName)),
                "삭제된 카테고리는 조회되지 않아야 합니다.");
    }
    @Test
    @DisplayName("카테고리 저장 시 이름 isEmpty()일때 예외 발생")
    void save_Fail_IsEmpty() {
        assertThrows(IllegalArgumentException.class, ()-> {
            new Category(1, "");
        }, "빈 문자열은 도메인 생성 단계에서 막혀야 합니다.");
    }
}
