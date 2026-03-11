package com.nhnacademy.shoppingmall.common.util;

import java.util.LinkedList;
import java.util.List;

public class RecentProductManager {
    private static final int MAX_SIZE = 5;

    /**
     * 최근 본 상품 리스트를 갱신합니다. (LIFO / MRU 전략)
     * @param currentList 현재 세션에 저장된 상품 ID 리스트
     * @param productId 방금 조회한 상품 ID
     * @return 갱신된 리스트
     */
    public static List<Integer> addRecentProduct(List<Integer> currentList, int productId) {
        if (currentList == null) {
            currentList = new LinkedList<>();
        }
        currentList.remove(Integer.valueOf(productId));
        currentList.add(0, productId);
        if (currentList.size() > MAX_SIZE) {
            currentList.remove(MAX_SIZE);
        }

        return currentList;
    }
}